/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: WithDrawQueryServiceImpl.java 
 *
 * Created: [2016-8-30 上午10:54:35] by haolingfeng
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

package com.fbd.core.app.withdraw.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.bank.dao.IUserBankDao;
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockChain.util.BlockChainStringUtil;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.push.dao.IPushDataLogDao;
import com.fbd.core.app.push.model.PushDataLogModel;
import com.fbd.core.app.sxyPay.common.LetvPayUtils;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxDataParam;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.sxyPay.utils.SAXParser;
import com.fbd.core.app.sxyPay.utils.SxyPayMd5;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.withdraw.dao.ISystemWithDrawDao;
import com.fbd.core.app.withdraw.dao.IWithDrawDao;
import com.fbd.core.app.withdraw.model.SystemWithdrawModel;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.app.withdraw.service.IWithDrawQueryService;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.SpringPropertiesHolder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Service("withDrawQueryService")
public class WithDrawQueryServiceImpl implements IWithDrawQueryService {
    private static final Logger logger = LoggerFactory.getLogger(WithDrawQueryServiceImpl.class);

    
    @Resource
    private IWithDrawDao withDrawDao ;
    
    @Resource
    private IUserBillService userBillService;
    
    @Resource
    private ISystemBillService systemBillService;
    
    @Resource
    private IUserDao userDao;
    @Resource
    private IPushDataLogDao pushDataLogDao;
    @Resource
    private IUserBankDao userBankDao;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IBusinessConfigDao businessConfigDao;
    @Resource
    private ISystemWithDrawDao systemWithDrawDao;
    /**
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-30 上午11:01:08
     */
    public void updateWithDraw() {
        List<WithDrawModel> withModel = this.withDrawDao.getWithDrawList();
        
        for(WithDrawModel model : withModel){
           
            QueryWithDrawSxyPay(model);
        }
        
    }
    /**
     * 众筹提现
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月17日 下午5:00:56
     */
    public List<Map<String,Object>>selectWithDrawData(Map<String,Object>param){
        return withDrawDao.selectWithDrawData(param);
    }
    /**
     * 提现成功
     */
    public List<Map<String,Object>>selectWithDrawDataSuccess(Map<String,Object>param){
        return withDrawDao.selectWithDrawDataSuccess(param);
    }
    /**
     * 提现区块链审核成功
     */
    public List<Map<String,Object>>selectWithDrawBlockData(Map<String,Object>param){
        return withDrawDao.selectWithDrawBlockData(param);
    }
    /**
     * 提现申请成功
     */
    public List<Map<String, Object>> pushWithDrawData(
            Map<String, Object> param) {
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("withDraw_apply");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
            param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
            param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
            param.put("payMethod", SpringPropertiesHolder.getProperty("paymentMethod"));

            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list = selectWithDrawData(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
    
                if(map!=null){
                    pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                    pushDataLogModel.setMemo("投资方提现申请推送");
                    pushDataLogModel.setEventCode("withDraw_apply");
                    pushDataLogModel.setPushTime(DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
        
                    pushDataLogDao.savePushDataLog(pushDataLogModel);
                }
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 提现成功
     */
    public List<Map<String, Object>> pushWithDrawSuccessData(
            Map<String, Object> param) {
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("swithDraw");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
            param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
            param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
            param.put("payMethod", SpringPropertiesHolder.getProperty("paymentMethod"));

            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list = selectWithDrawDataSuccess(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
    
                if(map!=null){
                    pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                    pushDataLogModel.setMemo("投资方提现成功推送");
                    pushDataLogModel.setEventCode("swithDraw");
                    pushDataLogModel.setPushTime(DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
                    pushDataLogDao.savePushDataLog(pushDataLogModel);
                }
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 提现区块链审核成功
     */
    public List<Map<String, Object>> pushWithDrawAuditingSuccessData(
            Map<String, Object> param) {
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("awithDraw");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
            param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
            param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
            param.put("payMethod", SpringPropertiesHolder.getProperty("paymentMethod"));

            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list = selectWithDrawBlockData(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
    
                if(map!=null){
                    pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                    pushDataLogModel.setMemo("投资方提现区块链审核成功推送");
                    pushDataLogModel.setEventCode("awithDraw");
                    pushDataLogModel.setPushTime(DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
        
                    pushDataLogDao.savePushDataLog(pushDataLogModel);
                }
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * <?xml version="1.0" encoding="utf-8"?>
     *  <message> <status>状态值</status> 
     *  <statusdesc>状态值说明</statusdesc>
     *   <v_fee>手续费</v_fee> 
     *   <paydate>yyyymmdd hh:mi:ss</paydate>
     *    <transdesc>转账说明</transdesc> 
     *    </message>
     *    
     *查询转账是否成功
     * @param model
     */
    private String  QueryWithDrawSxyPay(WithDrawModel model) {
        String status="";
         String resultStr = "";
       try {
           Map<String,String> paramsMap = this.getQueryWithDrawSxyParams(model);
           //提交Map
           String url = SxyPayConstants.Config.SXY_WITHDRAW_QUERY;
           Client cc = Client.create();
           WebResource rr = cc.resource(url);
           MultivaluedMap queryParams = new MultivaluedMapImpl();
           queryParams.add("v_mid", paramsMap.get("v_mid")); // 商户编号
           queryParams.add("v_data", paramsMap.get("v_data")); 
           queryParams.add("v_mac", paramsMap.get("v_mac"));
           queryParams.add("v_version", paramsMap.get("v_version"));
           resultStr = rr.queryParams(queryParams).get(String.class);      
           int statusCode;
         
           try {
                   status = SAXParser.SAXParseNodeValue(resultStr, "status");
                   String statusdesc = SAXParser.SAXParseNodeValue(resultStr, "statusdesc");
                   String v_fee =SAXParser.SAXParseNodeValue(resultStr, "v_fee");
                   String paydate =SAXParser.SAXParseNodeValue(resultStr, "paydate");
                   String transdesc =SAXParser.SAXParseNodeValue(resultStr, "transdesc");  
                    if("1".equals(status)){
                        
                      //  model.setFee(Double.valueOf(v_fee));
                        model.setCompleteTime(DateUtil.str2Date(paydate,DateUtil.DEFAULT_DATE_TIME_FORMAT));
                      
                       WithDrawSuccess(model);
                   }   else{
                       
                       throw new ApplicationException(statusdesc);
                   }
           } catch (Exception e) {
               e.printStackTrace();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return resultStr;
   }
    /**
     * 提现成功
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月29日 下午12:03:08
     */
    public void WithDrawSuccess(WithDrawModel model){
        //区块链转账
        Map<String,String> param = this.getTransferParams(model);
        String requestUrl = "";
        String platformResultStr = MockUtils.transfer(param);
        Map<String,Object> platformResultMap = JsonHelper.getMapFromJson(platformResultStr);  
       if(BlockChainCore.ResultStatus.SUCCESS.equals(platformResultMap.get("status"))){    
            BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
            blockAsynTran.setParentId(model.getOrderId());
            blockAsynTran.setType(BlockChainCore.Type.withdraw_block);
            blockAsynTran.setId(PKGenarator.getId());
            blockAsynTran.setCreateTime(new Date());
            blockAsynTranDao.save(blockAsynTran);
      }else{
           String message = platformResultMap.get("status")==null?"":platformResultMap.get("message").toString();
          throw new ApplicationException(message);
      }
    }
    public Map<String,String>getQueryWithDrawSxyParams(WithDrawModel model){
        Map<String,String> map = new HashMap<String, String>();
        map.put(SyxParam.v_mid,SxyPayConstants.Config.SXY_SHBH);
        try {
            map.put(SyxParam.v_data,java.net.URLEncoder.encode(model.getOrderId(),"GBK"));

            map.put(SyxParam.v_mac,SxyPayMd5.createMd5(map.get(SyxParam.v_mid).toString()+java.net.URLEncoder.encode(map.get(SyxParam.v_data).toString(), "utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put(SyxParam.v_version,SxyPayConstants.VERSION);
        return map;
     }
    /**
     * 余额查询
     * Description: 
     *
     * @param 
     * @return Map<String,String>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月30日 下午4:46:11
     */
    public Map<String,String>getQueryBalanceSxyParams(){
        Map<String,String> map = new HashMap<String, String>();
        map.put(SyxParam.v_mid,SxyPayConstants.Config.SXY_SHBH);
        map.put(SyxParam.v_mac,SxyPayMd5.createMd5(map.get(SyxParam.v_mid).toString()));
      
        return map;
     }
    /**
     * 查询余额
     * Description: 
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月30日 下午4:48:47
     */
    public String  QueryBalanceSxy() {
       String balance="";         
       String resultStr = "";
       try {
           Map<String,String> paramsMap = this.getQueryBalanceSxyParams();
           //提交Map
           String url = SxyPayConstants.Config.SXY_BALANCE_QUERY;
           Client cc = Client.create();
           WebResource rr = cc.resource(url);
           MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
           queryParams.add("v_mid", paramsMap.get("v_mid")); // 商户编号 
           queryParams.add("v_mac", paramsMap.get("v_mac"));
           resultStr = rr.queryParams(queryParams).get(String.class);   
           System.out.println("余额查询返回数据:"+resultStr);
           try {
               balance = SAXParser.SAXParseNodeValue(resultStr, "balance");   
               String status  = SAXParser.SAXParseNodeValue(resultStr, "status");       
               String statusdesc = SAXParser.SAXParseNodeValue(resultStr, "statusdesc"); 
               if("0".equals(status)){
                   
               }else{
                   throw new ApplicationException(statusdesc);
               }

           } catch (Exception e) {
               e.printStackTrace();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return balance;
   }
    /**
     * Description: 提现成功以后区块链处理
     *
     * @param 
     * @return Map<String,String>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-30 上午11:16:08
     */
    private Map<String, String> getTransferParams(WithDrawModel model) {
        Map<String,String> platformParams = new HashMap<String,String>();
        UserModel userModel = this.userDao.findByUserId(model.getUserId());
        platformParams.put("serviceID", "transfer");
        platformParams.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
        platformParams.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_IN_ACCOUNT); //转出账户地址
        platformParams.put("sourceKey", FbdCoreConstants.BLOCK_CHAIN_PLATFORM_IN_KEY);
        platformParams.put("targetAddress",userModel.getBlockChainAddress());  //转入到平台中间账户
        platformParams.put("amount",String.valueOf(Arith.round(model.getActualMoney())));
        platformParams.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"/withdraw/withDrawLastNotify.html");
        return platformParams;
    }

    /**
     * Description: 组装参数
     *
     * @param 
     * @return Map<String,String>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-30 上午11:16:08
     */
    
    private Map<String, String> getWithDrawQuery(WithDrawModel model) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(SyxParam.v_mid,SxyPayConstants.Config.SXY_SHBH);
        map.put(SyxParam.v_data,model.getOrderId());
        map.put(SyxParam.v_mac,SxyPayMd5.createMd5(map.get(SyxParam.v_mid).toString()+map.get(SyxParam.v_data)));
        map.put(SyxParam.v_version,SxyPayConstants.VERSION);
        return map;
    }
    /**
     * 
     * Description: 支付兑付款项
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-31 上午9:45:04
     */
    public List<Map<String,Object>>pushWithdraw(Map<String,Object>param){
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("withDraw");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.SE_DATE_FORMAT));
            param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
            param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list =withDrawDao.selectWithdraw(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
    
                if(map!=null){
                    pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                    pushDataLogModel.setMemo("支付兑付款项");
                    pushDataLogModel.setEventCode("withDraw");
                    pushDataLogModel.setPushTime(DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
        
                    pushDataLogDao.savePushDataLog(pushDataLogModel);
                }
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 众筹提现
     */
    public List<Map<String,Object>>pushWithdrawData(Map<String,Object>param){
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("withDrawData");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.SE_DATE_FORMAT));
            param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
            param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list =withDrawDao.selectWithDrawData(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
    
                if(map!=null){
                    pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                    pushDataLogModel.setMemo("众筹提现数据推送");
                    pushDataLogModel.setEventCode("withDrawData");
                    pushDataLogModel.setPushTime(DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
        
                    pushDataLogDao.savePushDataLog(pushDataLogModel);
                }
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 发送提现请求
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月29日 下午12:14:34
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void sendWithDrawSxyPay(WithDrawModel model) {
        try {
            Map<String,String> paramsMap = this.getWithDrawSxyParams(model);
            //提交Map
            String url = SxyPayConstants.Config.SXY_WITHDRAW;
            Client cc = Client.create();
            WebResource rr = cc.resource(url);
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            queryParams.add("v_mid", paramsMap.get("v_mid")); // 商户编号
            queryParams.add("v_data", paramsMap.get("v_data")); 
            queryParams.add("v_mac", paramsMap.get("v_mac"));
            queryParams.add("v_version", paramsMap.get("v_version"));
            String resultStr = rr.queryParams(queryParams).get(String.class);

            String status = SAXParser.SAXParseNodeValue(resultStr, "status");
            //提现成功
            if("0".equals(status)){       
                String v_fee = SAXParser.SAXParseNodeValue(resultStr, "v_fee");
                if(v_fee!=null &&!"".equals(v_fee))
                     model.setWithdrawFee(Double.valueOf(v_fee));
                withDrawBySXYSuccess(model);
            }else{
                String statusdesc = SAXParser.SAXParseNodeValue(resultStr, "statusdesc");
                if(statusdesc!=null && !"".equals(statusdesc))
                model.setMemo(statusdesc);
                this.withDrawDao.updateBySelective(model);
                //提现失败解冻用户的提现金额，通知用户提现失败
                withDrawFailDealUser(model);
                throw new ApplicationException(statusdesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
    
    
    /**
     * 提现失败解冻用户资金
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-12-1 下午6:39:11
     */
    @SuppressWarnings("unchecked")
    public void withDrawFailDealUser(WithDrawModel model){
       
        String requestID = BlockChainStringUtil.getUniqueTransferNoEnc(model.getOrderId());
        //添加一条事务通知数据
        BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
        blockAsynTran.setId(PKGenarator.getId());
        blockAsynTran.setParentId(model.getOrderId());  //支持编号
        blockAsynTran.setCreateTime(new Date());
        blockAsynTran.setUpdateTime(new Date());
        blockAsynTran.setType(BlockChainCore.Type.withdraw_fail_user_deal);
        blockAsynTran.setRequestID(requestID);
        this.blockAsynTranDao.save(blockAsynTran);
        
        UserModel userModel = this.userDao.findByUserId(model.getUserId());
        //发送提现审核拒绝转账请求(将用户的钱转到平台中间账户的钱转到用户账户(冻结资金解冻))
        Map<String,String> params = new HashMap<String,String>();
        params.put("serviceID", "transfer");
        params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
        params.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT); //转出账户地址
        params.put("sourceKey", FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_KEY);
        params.put("targetAddress",userModel.getBlockChainAddress());  //转入到平台中间账户
        double amount =Arith.round(model.getActualMoney());
        if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
            amount = 0.01;
        }
        params.put("amount",String.valueOf(amount));
        params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"withdraw/withDrawRefuseNotify.html");
        String transferNo = BlockChainStringUtil.getUniqueTransferNoEnc(model.getOrderId());
        params.put("requestID",requestID);
        params.put("transferNO",transferNo);
        logger.info("========>提现失败转账请求参数-params："+params);
        String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
        String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
        logger.info("========>提现失败转账响应参数-resultStr："+resultStr);
        Map<String,Object> platformResultMap = JsonHelper.getMapFromJson(resultStr);
        String platformState = platformResultMap.get("status").toString();
        if(BlockChainCore.ResultStatus.ServiceSuccess.equals(platformState)){ //转账成功
           logger.info("==========提现失败转账提交成功===============");
       }else{
            String message = platformResultMap.get("status")==null?"":platformResultMap.get("status").toString();
             logger.info("提现失败转账【中间账户转账到用户账户】失败-【"+message+"】！");
            throw new ApplicationException(message);
        }
    }
    
    
    
    
    
    
    
    
    
    
    public void withDrawBlockChainDealUser(WithDrawModel model){
        String orderId =PKGenarator.getOrderId();
        String requestID = BlockChainStringUtil.getUniqueTransferNoEnc(model.getOrderId());
                    
         BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
         blockAsynTran.setParentId(model.getOrderId());
         blockAsynTran.setRequestID(requestID);
         blockAsynTran.setType(BlockChainCore.Type.withdraw_user_deal);
         blockAsynTran.setCreateTime(new Date());
         blockAsynTran.setId(PKGenarator.getId());
         blockAsynTranDao.save(blockAsynTran);
        //发送提现转账请求(将用户的钱转到平台应收账户)
        Map<String,String> params = new HashMap<String,String>();
        params.put("serviceID", "transfers");
        params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
        params.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT); //转出账户地址(冻结中间账户)
        params.put("sourceKey", FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_KEY);
        params.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_IN_ACCOUNT);  //转入到平台应收账户账户
        double amount =0;
        if(model.getWithdrawFee()!=null)
           amount = Arith.add(model.getAmt(),model.getWithdrawFee());
        else
            amount =Arith.add(model.getAmt(),1);
        if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
            amount = 0.01;
        }
        params.put("amount",String.valueOf(amount));
        params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"withdraw/withDrawAuditNotify.html");
        params.put("transferNO",orderId );
        params.put("requestID",requestID );
        
        String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
        logger.info("===========>提现成功后回收用户众筹令请求参数-params:"+params);
        String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
        logger.info("===========>提现成功后回收用户众筹令响应参数-resultStr:"+resultStr);
        @SuppressWarnings("unchecked")
        Map<String,Object> platformResultMap = JsonHelper.getMapFromJson(resultStr);
        String platformState = platformResultMap.get("status").toString();
      
        if(BlockChainCore.ResultStatus.ServiceSuccess.equals(platformState)){ //转账成功
             logger.info("===================>提现成功后回收用户众筹令提交成功============");
        }else{
             String message = platformResultMap.get("status")==null?"":platformResultMap.get("message").toString();
             model.setMemo("用户区块链失败:"+message);
             withDrawDao.updateBySelective(model);
             logger.info("提现拒绝转账【中间账户转账到用户账户】失败-【"+message+"】！");
            throw new ApplicationException(message);
        }
    }
    /**
     * 用户记账
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月19日 下午7:57:18
     */
    public void withDrawUserSuccess(WithDrawModel model){
        model.setFinancialAuditTime(DateUtil.dateTime2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
        model.setState("withdraw_success");
        withDrawDao.updateBySelective(model);
       if(model.getWithdrawFee()!=null){
          //扣除提现用户账单
            this.userBillService.addWithDrawUserBill(model, model.getAmt()+model.getWithdrawFee());
       }else{
           this.userBillService.addWithDrawUserBill(model, model.getAmt()+1);
       }
       sendWithDrawSuccessMessage(model);
    }

    /**
     * 提现成功
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月27日 下午3:56:29
     */
    private void sendWithDrawSuccessMessage(WithDrawModel withDraw){
        
        Map<String, String> params = new HashMap<String,String>();
        params.put("time",DateUtil.dateTime2Str(new Date(), null));
        params.put("money",Arith.format(withDraw.getAmt()));
        String backCard = withDraw.getBankCardId();
        params.put("bankCard",backCard);
        try{
            logger.info("发送提现申请成功手机短信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_WITHDRAW_PASS_MOBILE;
            SendMessageUtil.sendMobileMessage(nodeCode,withDraw.getUserId(), params);
            logger.info("发送提现申请成功手机短信结束");
        }catch(Exception e){
            logger.error("发送提现申请成功手机短信失败，"+e.getMessage());
        }
         try{
             logger.info("发送提现申请成功站内信开始");
             String nodeCode = FbdCoreConstants.NODE_TYPE_WITHDRAW_APPLY_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.NODE_TYPE_WITHDRAW_MSG, withDraw.getUserId(), params);
             logger.info("发送提现申请成功站内信结束");
         }catch(Exception e){
             logger.error("发送提现申请成功站内信失败，"+e.getMessage());
         }
     }
     
     
    /**
     * 平台提现申请冻结
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月24日 上午9:58:43
     */
    public void systemWithDrawUserSuccess(SystemWithdrawModel model){
        this.systemBillService.addBill(CrowdfundCoreConstants.systemTradeType.withDrawFee.getValue(), model.getAmt()+model.getFee(),
                FbdCoreConstants.tradeDirection.out.getValue(), "","平台提现",model.getOrderId());
     }
    /**
     * 系统记账
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月19日 下午7:57:40
     */
    public void withDrawSystemSuccess(WithDrawModel model){
        double systemFee=0;
        if(model.getWithdrawFee()!=null){
           //平台收取手续费
           systemFee =model.getFee() - model.getWithdrawFee() ;
        }else{
            systemFee =model.getFee() -1;
        }
        this.systemBillService.addBill(CrowdfundCoreConstants.systemTradeType.withDrawFee.getValue(), systemFee,
                FbdCoreConstants.tradeDirection.in.getValue(), "","用户提现收取手续费",model.getOrderId());
    }
    
    
    public void withDrawBlockChainDealSystem(WithDrawModel model){
        String orderId =PKGenarator.getOrderId();
        String requestID = BlockChainStringUtil.getUniqueTransferNoEnc(model.getOrderId());
                    
         BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
         blockAsynTran.setParentId(model.getOrderId());
         blockAsynTran.setRequestID(requestID);
         blockAsynTran.setType(BlockChainCore.Type.withdraw_system_deal);
         blockAsynTran.setCreateTime(new Date());
         blockAsynTran.setId(PKGenarator.getId());
         blockAsynTranDao.save(blockAsynTran);
         
        //发送提现转账请求(将用户的钱转到平台中间账户)
        Map<String,String> params = new HashMap<String,String>();
        params.put("serviceID", "transfers");
        params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
        params.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT); //转出账户地址
        params.put("sourceKey", FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_KEY);
        params.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_INCOME_ACCOUNT);  //转入到平台手续费账户
        double amount =0;
        if(model.getWithdrawFee()!=null){
           amount = Arith.sub(model.getFee(),model.getWithdrawFee());
        }else{
            amount = Arith.sub(model.getFee(),1);
        }
        if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
            amount = 0.01;
        }
        params.put("amount",String.valueOf(amount));
        params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"withdraw/withDrawAuditNotify.html");
        params.put("transferNO",orderId );
        params.put("requestID",requestID );
        
        String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
        logger.info("===========>提现成功后转账区块链手续费请求参数-params:"+params);
        String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
        logger.info("===========>提现成功后转账区块链手续费响应参数-resultStr:"+resultStr);
        @SuppressWarnings("unchecked")
        Map<String,Object> platformResultMap = JsonHelper.getMapFromJson(resultStr);
        String platformState = platformResultMap.get("status").toString();
      
        if(BlockChainCore.ResultStatus.ServiceSuccess.equals(platformState)){ //转账成功
             logger.info("=============>提现成功后转账区块链手续费事务提交成功");
        }else{
             String message = platformResultMap.get("status")==null?"":platformResultMap.get("message").toString();
             model.setMemo(message);
             withDrawDao.updateBySelective(model);
             logger.info("提现拒绝转账【中间账户转账到用户账户】失败-【"+message+"】！");
            throw new ApplicationException(message);
        }
    }
    /**
     * 
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月22日 下午6:24:57
     */
    public void SystemWithDrawBlockChain(SystemWithdrawModel model){
        String orderId =PKGenarator.getOrderId();
        String requestID = PKGenarator.getOrderId();
                    
         BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
         blockAsynTran.setParentId(model.getOrderId());
         blockAsynTran.setRequestID(requestID);
         blockAsynTran.setType(BlockChainCore.Type.platform_withdraw);
         blockAsynTran.setCreateTime(new Date());
         blockAsynTran.setId(PKGenarator.getId());
         blockAsynTranDao.save(blockAsynTran);
         
        //发送提现转账请求(将平台收入账户转到发行账户)
        Map<String,String> platformParams = new HashMap<String,String>();
        platformParams.put("serviceID", "transfers");
        platformParams.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
        platformParams.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_INCOME_ACCOUNT); //转出账户地址
        platformParams.put("sourceKey", FbdCoreConstants.BLOCK_CHAIN_PLATFORM_INCOME_KEY);
        platformParams.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_IN_ACCOUNT);  //转入到平台发行账户
        double amount =model.getAmt();
       
        if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
            amount = 0.01;
        }
        platformParams.put("amount",String.valueOf(amount));
        platformParams.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"withdraw/systemWithDrawAuditNotify.html");
        platformParams.put("transferNO",orderId );
        platformParams.put("requestID",requestID );

        String platformResultStr = MockUtils.transfer( platformParams);
        @SuppressWarnings("unchecked")
        Map<String,Object> platformResultMap = JsonHelper.getMapFromJson(platformResultStr);
        String platformState = platformResultMap.get("status").toString();
      
        if(BlockChainCore.ResultStatus.ServiceSuccess.equals(platformState)){ //转账成功
             
        }else{
             String message = platformResultMap.get("status")==null?"":platformResultMap.get("message").toString();
             logger.info("提现拒绝转账【中间账户转账到用户账户】失败-【"+message+"】！");
            throw new ApplicationException(message);
        }
    }
    /**
     * 首信易支付成功
     * @param model
     */
    public void withDrawBySXYSuccess(WithDrawModel model){

        model.setStateName("提现成功");
        model.setState(CrowdfundCoreConstants.withDrawState.success.getValue());
        model.setCompleteTime(new Date());
        this.withDrawDao.updateBySelective(model);
        //转账区块链手续费到平台手续费账户
        withDrawBlockChainDealSystem(model);
        //转账用户平台资金到平台应收账户中
        withDrawBlockChainDealUser(model);
    }
    /**
     * 平台提现首信易支付成功
     * @param model
     */
    public void platformWithDrawBySXYSuccess(SystemWithdrawModel model){

        model.setState(CrowdfundCoreConstants.withDrawState.success.getValue());
        model.setCompleteTime(new Date());
        this.systemWithDrawDao.updateById(model);
        /*withBlockChainDealSystem(model);
        withBlockChainDealUser(model);*/
        
        SystemWithDrawBlockChain(model);
    }
    private Map<String, String> getWithDrawSxyParams(WithDrawModel model) {
        Map<String,String> map = new HashMap<String, String>();
        map.put(SyxParam.v_mid,SxyPayConstants.Config.SXY_SHBH);
        try {
            String v_data=this.getRequestWithDrawData(model);
           // map.put(SyxParam.v_data,java.net.URLEncoder.encode(this.getRequestWithDrawData(model),"GBK"));
           // String v_data ="1|10|11538-20160929-672672$6216610100015348564|赵文文|中国银行星城国际支行|北京市|北京市|10|20160929|104100000004";      
            String v_data1 = java.net.URLEncoder.encode(v_data, "UTF-8");
            map.put(SyxParam.v_data,java.net.URLEncoder.encode(v_data,"GBK"));

           // map.put(SyxParam.v_mac,SxyPayMd5.createMd5(map.get(SyxParam.v_mid).toString()+java.net.URLEncoder.encode(map.get(SyxParam.v_data).toString(),"UTF-8")));
            map.put(SyxParam.v_mac,SxyPayMd5.createMd5(map.get(SyxParam.v_mid).toString()+v_data1));

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        map.put(SyxParam.v_version,SxyPayConstants.VERSION);
        return map;
    }
    

    

    private String getRequestWithDrawData(WithDrawModel model) {
        
        Map<String,String> map = new HashMap<String, String>();
        map.put(SyxDataParam.FZXXZHS, "1");
       // map.put(SyxDataParam.FZZJE, String.valueOf(10));
        if(model.getAmt()<10){
            throw new ApplicationException("提取金额不能小于10元");
        }else{
            map.put(SyxDataParam.FZZJE, Arith.format(model.getAmt()));
        }
        map.put(SyxDataParam.PCH, PKGenarator.getWithDrawOrderId());
        if(model.getBankCardId()!=null)
            map.put(SyxDataParam.SFZH, model.getBankCardId());
        else
            throw new ApplicationException("卡号不能为空");
 

        UserModel user = this.userDao.findByUserId(model.getUserId());
        if(user.getRealName()!=null)
        map.put(SyxDataParam.SFZHM,user.getRealName());
        else
            throw new ApplicationException("用户名称不能为空");
        UserBankModel bankModel = this.userBankDao.getBnakByBankAccount(model.getBankCardId());
        if(bankModel.getOpenAcctBank()!=null)
          map.put(SyxDataParam.SFKHH, bankModel.getOpenAcctBank());
        else
            throw new ApplicationException("收方开户行的信息不能为空");
            
        
        map.put(SyxDataParam.SFSF, bankModel.getBankProvinceName());
        if("北京市".equals(bankModel.getBankProvinceName())||"上海市".equals(bankModel.getBankProvinceName())||"天津市".equals(bankModel.getBankProvinceName())||"重庆市".equals(bankModel.getBankProvinceName()))     
           map.put(SyxDataParam.SFCS, bankModel.getBankProvinceName());
        else
            map.put(SyxDataParam.SFCS, bankModel.getBankCityName());
    
        map.put(SyxDataParam.FKJE, Arith.format(model.getAmt()));

        map.put(SyxDataParam.KHBS, model.getOrderId());

        map.put(SyxDataParam.LHH, bankModel.getBank());

        return LetvPayUtils.getWithDrawDataValue(map);
    }
    /**
     * 调用三方支付将钱转账到用户账户
     * @param model
     */
    public void sendRequestThird(WithDrawModel model) {
        if(model.getState().equals(FbdCoreConstants.withDrawState.refuse.getValue())){
            this.userBillService.addCancelWithDrawUserBill(model);
        }else{
            //如果审核成功   调用第三方  判断用户使用的哪个第三方
            if(LetvPayConstants.thirdType.SXY.equals(model.getThirdWtihDrawType())){
                String balance = QueryBalanceSxy();
                double amount = model.getAmt();
                if(balance!=null && amount<=Double.valueOf(balance)){
                    try{
                        this.sendWithDrawSxyPay(model);
                    }catch(Exception e){
                        throw new ApplicationException(e.getMessage());
                    }
                }else {
                    throw new ApplicationException("账户余额不足,账户余额为:"+balance);
                }    
            }else{
            }
        }
    }
    
    
    /**
     * 平台提现
     * @param model
     */
    public void platformWithDraw(SystemWithdrawModel model) {
    
        String balance = QueryBalanceSxy();
        double amount = model.getAmt();
        if(balance!=null && amount<=Double.valueOf(balance)){
             this.platformSendWithDrawSxyPay(model);
        }else {
            throw new ApplicationException("账户余额不足,账户余额为:"+balance);
        }    
    }       
    /**
     * 平台发送提现请求
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月29日 下午12:14:34
     */
    public void platformSendWithDrawSxyPay(SystemWithdrawModel model) {
        try {
            Map<String,String> paramsMap = this.getPlatWithDrawSxyParams(model);
            //提交Map
            String url = SxyPayConstants.Config.SXY_WITHDRAW;
            Client cc = Client.create();
            WebResource rr = cc.resource(url);
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            queryParams.add("v_mid", paramsMap.get("v_mid")); // 商户编号
            queryParams.add("v_data", paramsMap.get("v_data")); 
            queryParams.add("v_mac", paramsMap.get("v_mac"));
            queryParams.add("v_version", paramsMap.get("v_version"));
            String resultStr = rr.queryParams(queryParams).get(String.class);

            String status = SAXParser.SAXParseNodeValue(resultStr, "status");
          

            //发送成功
            
            if("0".equals(status)){       
                String v_fee = SAXParser.SAXParseNodeValue(resultStr, "v_fee");
                if(v_fee!=null &&!"".equals(v_fee))
                     model.setFee(Double.parseDouble(v_fee));
                platformWithDrawBySXYSuccess(model);
            } else{
                String statusdesc = SAXParser.SAXParseNodeValue(resultStr, "statusdesc");
                throw new ApplicationException(statusdesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    private Map<String, String> getPlatWithDrawSxyParams(SystemWithdrawModel model) {
        Map<String,String> map = new HashMap<String, String>();
        map.put(SyxParam.v_mid,SxyPayConstants.Config.SXY_SHBH);
        try {
            String v_data=this.getPlatformRequestWithDrawData(model);
           // map.put(SyxParam.v_data,java.net.URLEncoder.encode(this.getRequestWithDrawData(model),"GBK"));
           // String v_data ="1|10|11538-20160929-672672$6216610100015348564|赵文文|中国银行星城国际支行|北京市|北京市|10|20160929|104100000004";      
            String v_data1 = java.net.URLEncoder.encode(v_data, "UTF-8");
            map.put(SyxParam.v_data,java.net.URLEncoder.encode(v_data,"GBK"));

           // map.put(SyxParam.v_mac,SxyPayMd5.createMd5(map.get(SyxParam.v_mid).toString()+java.net.URLEncoder.encode(map.get(SyxParam.v_data).toString(),"UTF-8")));
            map.put(SyxParam.v_mac,SxyPayMd5.createMd5(map.get(SyxParam.v_mid).toString()+v_data1));

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        map.put(SyxParam.v_version,SxyPayConstants.VERSION);
        return map;
    }


    private String getPlatformRequestWithDrawData(SystemWithdrawModel model) {
        
        Map<String,String> map = new HashMap<String, String>();
        map.put(SyxDataParam.FZXXZHS, "1");
       // map.put(SyxDataParam.FZZJE, String.valueOf(10));
        if(model.getAmt()<10){
            throw new ApplicationException("提取金额不能小于10元");
        }else{
            map.put(SyxDataParam.FZZJE, Arith.format(model.getAmt()));
        }
        map.put(SyxDataParam.PCH, PKGenarator.getWithDrawOrderId());
        map.put(SyxDataParam.SFZH, businessConfigDao.getBusConfig(CrowdfundCoreConstants.crowdFundSystmeWithDrawParam.bankcardid).getCode());
        map.put(SyxDataParam.SFZHM,businessConfigDao.getBusConfig(CrowdfundCoreConstants.crowdFundSystmeWithDrawParam.userName).getCode());
        map.put(SyxDataParam.SFKHH, businessConfigDao.getBusConfig(CrowdfundCoreConstants.crowdFundSystmeWithDrawParam.OpenAcctBank).getCode());
       
            
        String provinceName = businessConfigDao.getBusConfig(CrowdfundCoreConstants.crowdFundSystmeWithDrawParam.ProvinceName).getCode();
        map.put(SyxDataParam.SFSF,provinceName);
        String cityName = businessConfigDao.getBusConfig(CrowdfundCoreConstants.crowdFundSystmeWithDrawParam.CityName).getCode();

        if("北京市".equals(provinceName)||"上海市".equals(provinceName)||"天津市".equals(provinceName)||"重庆市".equals(provinceName))     
           map.put(SyxDataParam.SFCS, provinceName);
        else
            map.put(SyxDataParam.SFCS, cityName);

        map.put(SyxDataParam.FKJE, Arith.format(model.getAmt()));

        map.put(SyxDataParam.KHBS, model.getOrderId());
        String bankcode = businessConfigDao.getBusConfig(CrowdfundCoreConstants.crowdFundSystmeWithDrawParam.bankcode).getCode();

        map.put(SyxDataParam.LHH, bankcode);

        return LetvPayUtils.getWithDrawDataValue(map);
    }
}
