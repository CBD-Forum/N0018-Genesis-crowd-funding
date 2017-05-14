/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundUserPrizeServiceImpl.java 
 *
 * Created: [2016-8-10 下午6:02:21] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.user.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.user.dao.ICrowdfundUserPrizeDao;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.app.user.service.ICrowdfundUserPrizeService;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.util.Arith;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 抽奖
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Service
public class CrowdfundUserPrizeServiceImpl implements ICrowdfundUserPrizeService {
    
    
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundUserPrizeServiceImpl.class);
    

    @Resource
    private ICrowdfundingDao crowdfundingDao;
    
    @Resource
    private ICrowdfundUserPrizeDao crowdfundUserPrizeDao ;
    
//    @Resource
//    private StdScheduler scheduler;
    @Resource
    private ICrowdfundingBackSetDao crowdfundingBackSetDao;
    
    /**
     * Description: 保存中奖用户
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-10 下午6:06:32
     */
    public void saveCrowdfundUserPrize(String loanNo,String backNo) {
        
        
        
        logger.info("=============项目抽奖开始====================");
        Map<String,Object> map = this.getHzAndSz();
        logger.info("=============深沪两市指数-map："+map);
        if(null != map){
            //查询抽奖的产品类型项目
            CrowdfundingModel model = this.crowdfundingDao.getPrizeLoan(loanNo);
            model.setBackNo(backNo);
            //进行抽奖
            logger.info("=========项目【"+loanNo+"】抽奖开始==================");
            this.savePrize(model,map);
        }else{
            logger.info("============项目【"+loanNo+"】抽奖，深沪未开始，进入到下一期开奖=======");
        }
    }

    /**
     * Description: 获取沪指深指
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-11 上午10:30:29
     */
    
    private Map<String,Object> getHzAndSz() {
        logger.info("==================获取深沪两市指数开始======================");
        String url = "http://hq.sinajs.cn/list=s_sh000001,s_sz399001";   
        Map<String,Object> map = null;
        try {   
            URL u = new URL(url);   
            byte[] b = new byte[256];   
            InputStream in = null;   
            ByteArrayOutputStream bo = new ByteArrayOutputStream();   
            try {   
                in = u.openStream();   
                int i;   
                while ((i = in.read(b)) != -1) {   
                    bo.write(b, 0, i);   
                }   
                String result = bo.toString();   
                byte[] t_utf8 = result.getBytes("GBK");
                
                String ut_utf8 = new String(t_utf8, "GBK");
                String[] stocks = ut_utf8.split(";");   
                map = new HashMap<String,Object>();
                if(!"".equals(stocks[0].split(",")[1]) && !"".equals(stocks[1].split(",")[1])){
                    map.put("hz", stocks[0].split(",")[1]);
                    map.put("sz", stocks[1].split(",")[1]);
                }
                bo.reset();   
            } catch (Exception e) {   
                System.out.println(e.getMessage());   
            } finally {   
                if (in != null) {   
                    in.close();   
                }   
            } 
            logger.info("==================获取深沪两市指数结束======================");
        } catch (Exception ex) {   
            System.out.println(ex.getMessage());   
        }  
        return map;
    }

    /**
     * Description: 进行抽奖
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-10 下午6:34:39
     */
    
    private void savePrize(CrowdfundingModel model,Map<String,Object> map) {
        double hz = Double.valueOf(map.get("hz").toString());
        double sz = Double.valueOf(map.get("sz").toString());
        
        /*计算公式（沪深两市指数结果进行计算，N为参与抽奖的人数，P为中奖名额）：
                        ①（沪市指数+深市指数）*100，（例如，沪市指数3382.06，深市指数11732.25，（3382.06+11732.25）*100，则A值为1511431）
                        ②人数N除以名额P，取商的整数部分B（例如，参与抽奖人数为2401，获奖名额为30，则B值为80）
                        ③ A除以B，余数即为首个中奖号X（例如，1511431除以80，商为18892，余数为71，则首个中奖号为71）
                        ④其他中奖号为X+B、X+2B、X+3B、……、X+(P-1)B*/
        //查询该项目一共参与抽奖人数
        int countPrize = Integer.parseInt(this.crowdfundUserPrizeDao.getCountPrize(model.getLoanNo())+"");
        CrowdfundingBackSetModel backModel=crowdfundingBackSetDao.getByLoanNoAndSetNo(model.getLoanNo(), model.getBackNo());
        //抽奖名额
        int prizeNum = backModel.getPrizeNum();
        //（沪市指数+深市指数）*100，（例如，沪市指数3382.06，深市指数11732.25，（3382.06+11732.25）*100，则A值为1511431）
        DecimalFormat format=new DecimalFormat("#");
        int AValue = Integer.parseInt(format.format(Arith.mul(Arith.add(hz, sz),100)));
        // 人数N除以名额P，取商的整数部分B（例如，参与抽奖人数为2401，获奖名额为30，则B值为80）
        int BValue = countPrize/prizeNum;
        
        //计算第一个中奖名单 得到余数 A除以B，余数即为首个中奖号X（例如，1511431除以80，商为18892，余数为71，则首个中奖号为71）
        int firstNum = (int) (AValue%BValue);
        
        //对指定的项目编号记录进行未中奖操作
        crowdfundUserPrizeDao.updateNoPrizeByLoanNo(model.getLoanNo());
        
        
        StringBuffer prizeStr=new StringBuffer();
        prizeStr.append(firstNum);
        //修改 根据项目编号和抽奖单号
        this.crowdfundUserPrizeDao.updatePrize(firstNum,model.getLoanNo());
        
        
        //其他中奖号为X+B、X+2B、X+3B、……、X+(P-1)B
        for(int i =1;i<=prizeNum-1;i++){
            int otherPrizeNum = (int) (firstNum+BValue*i);
            //修改
            this.crowdfundUserPrizeDao.updatePrize(otherPrizeNum,model.getLoanNo());
            prizeStr.append(",").append(otherPrizeNum);
        }
        model.setPrizeStatus("end");
        crowdfundingDao.update(model);
        sendPrizeLoanUser(model.getLoanNo(), prizeStr.toString());
        CrowdfundUserPrizeModel queryModel=new CrowdfundUserPrizeModel();
        queryModel.setLoanNo(model.getLoanNo());
        Map<String,Map<String,Object>> userMap=new HashMap<String,Map<String,Object>>();
        List<Map<String, Object>> dataList=crowdfundUserPrizeDao.getUserPrizeList(queryModel);
        for(Map<String, Object> data:dataList){
            String prizeUser=(String) data.get("prizeUser");
            String isPrize=(String) data.get("isPrize");
            
            Map<String,Object> userD=userMap.get(prizeUser);
            if(userD==null){
                userD=new HashMap<String,Object>();
                userD.put("yes", 0);//成功
                userD.put("no", 0);//失败
            }
            
            if("1".equals(isPrize)){
                userD.put("yes", (Integer)userD.get("yes")+1);
                sendPrizeUser(model.getLoanNo(), prizeUser);
            }else if("0".equals(isPrize)){
                userD.put("no", (Integer)userD.get("no")+1);
            }
            userMap.put(prizeUser, userD);
        }
        Set<String> userSet=userMap.keySet();
        Iterator<String> userIter=userSet.iterator();
        while(userIter.hasNext()){
            String userId=userIter.next();
            if((Integer)userMap.get(userId).get("no")>0){
                sendPrizeNoUser(model.getLoanNo(), userId, prizeStr.toString());
            }
        }
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.user.service.ICrowdfundUserPrizeService#sendPrizeLoanUser(java.util.Map)
     */
    @Override
    public void sendPrizeLoanUser(String loanNo,String prizeStr) {
        // TODO Auto-generated method stub
        Map<String, String> params = new HashMap<String,String>();       
        CrowdfundingModel loanModel=this.crowdfundingDao.getByloanNo(loanNo);
        params.put("loanNo", loanNo);
        params.put("loanName", loanModel.getLoanName());
        params.put("prizeStr", prizeStr);
         try{
             logger.info("发送站内信开始");
             String nodeCode = FbdCoreConstants.NODE_PRIZE_LOAN_USER_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_INVEST_TRANSFER_MSG, loanModel.getLoanUser(), params);
             logger.info("发送站内信结束");
         }catch(Exception e){
             logger.error("发送站内信失败，"+e.getMessage());
         }
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.user.service.ICrowdfundUserPrizeService#sendPrizeUser(java.util.Map)
     */
    @Override
    public void sendPrizeUser(String loanNo,String userId) {
        // TODO Auto-generated method stub
        Map<String, String> params = new HashMap<String,String>();
        CrowdfundingModel loanModel=this.crowdfundingDao.getByloanNo(loanNo);
        params.put("loanNo", loanNo);
        params.put("loanName", loanModel.getLoanName());
        
        try{
            logger.info("发送短信开始");
            String nodeCode = FbdCoreConstants.NODE_PRIZE_USER_MOBILE;
            SendMessageUtil.sendMobileMessage(nodeCode, userId, params);
            logger.info("发送短信结束");
        }catch(Exception e){
            logger.error("发送短信失败，"+e.getMessage());
        }
         try{
             logger.info("发送站内信开始");
             String nodeCode = FbdCoreConstants.NODE_PRIZE_USER_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_INVEST_TRANSFER_MSG, userId, params);
             logger.info("发送站内信结束");
         }catch(Exception e){
             logger.error("发送站内信失败，"+e.getMessage());
         }
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.user.service.ICrowdfundUserPrizeService#sendPrizeNoUser(java.util.Map)
     */
    @Override
    public void sendPrizeNoUser(String loanNo,String userId,String prizeStr) {
        // TODO Auto-generated method stub
        Map<String, String> params = new HashMap<String,String>();
        CrowdfundingModel loanModel=this.crowdfundingDao.getByloanNo(loanNo);
        params.put("loanNo", loanNo);
        params.put("loanName", loanModel.getLoanName());
        params.put("prizeStr", prizeStr);
        try{
            logger.info("发送短信开始");
            String nodeCode = FbdCoreConstants.NODE_PRIZE_NO_USER_MOBILE;
            SendMessageUtil.sendMobileMessage(nodeCode, userId, params);
            logger.info("发送短信结束");
        }catch(Exception e){
            logger.error("发送短信失败，"+e.getMessage());
        }
         try{
             logger.info("发送站内信开始");
             String nodeCode = FbdCoreConstants.NODE_PRIZE_NO_USER_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_INVEST_TRANSFER_MSG, userId, params);
             logger.info("发送站内信结束");
         }catch(Exception e){
             logger.error("发送站内信失败，"+e.getMessage());
         }
    }
    
}
