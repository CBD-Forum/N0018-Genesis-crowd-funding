/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdFundProductTransferJobServiceImpl.java 
 *
 * Created: [2016-8-12 上午10:01:30] by haolingfeng
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

package com.fbd.core.app.crowdfunding.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.service.ICrowdFundProductTransferJobService;
import com.fbd.core.app.push.dao.IPushDataLogDao;
import com.fbd.core.app.push.model.PushDataLogModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 修改产品转让状态
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Service("crowdFundProductTransferJobService")
public class CrowdFundProductTransferJobServiceImpl implements ICrowdFundProductTransferJobService {

    
    private static final Logger logger = LoggerFactory.getLogger(CrowdFundProductTransferJobServiceImpl.class);
 
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private IPushDataLogDao pushDataLogDao;
    @Resource
    ICrowdfundProductTransferDao crowdfundProductTransferDao;
    
    
    public void updateTransferEnd(String transferNo) {
        CrowdfundProductTransferModel model = this.crowdfundProductTransferDao.getByTransferNo(transferNo);
        String supportNo =  model.getSupportNo();
        CrowdfundingSupportModel supportModel = new CrowdfundingSupportModel();
        if(!model.getTransferState().equals(CrowdfundCoreConstants.transferStateFbd.transfered.getValue())){
            try {
                model.setTransferState(CrowdfundCoreConstants.transferStateFbd.transfered.getValue());
                crowdfundProductTransferDao.update(model);
           //     supportModel.sett
                //查询项目是否放款完成
                CrowdfundingModel crowdModel = this.crowdfundingDao.getByloanNo(model.getLoanNo()); 
                //发送转让结束站内信
                this.sendTransferEndMessage(model, crowdModel);
            } catch (Exception e) {
                throw new ApplicationException("更新转让失败");
            }
        }else{
            
        }
    }
    
    
    /**
     * 
     * Description: 发送转让结束信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendTransferEndMessage(CrowdfundProductTransferModel model,CrowdfundingModel loan){
        Map<String, String> params = new HashMap<String,String>();
        params.put("loanName",loan.getLoanName());
        params.put("transferNo",model.getTransferNo());
        try{
            logger.info("发送转让结束站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_PRODUCT_TRANSFER_END_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getTransferUser(), params);
            logger.info("发送转让结束站内信结束");
        }catch(Exception e){
            logger.error("发送转让结束站内信失败，"+e.getMessage());
        }
    }


    /**
     * 转让收款
     */
    @Override
    public List<Map<String, Object>> transferRecive(Map<String, Object> param) {
        // TODO Auto-generated method stub
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("transferRecive");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.SE_DATE_FORMAT));
            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list = crowdfundProductTransferDao.transferRecive(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
                if(map!=null){
                    pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                    pushDataLogModel.setMemo("转让收款推送");
                    pushDataLogModel.setEventCode("transferRecive");
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
     * 转让付款 
     */
    @Override
    public List<Map<String, Object>> transferPay(Map<String, Object> param) {
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("transferPay");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.SE_DATE_FORMAT));
            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list = crowdfundProductTransferDao.transferPay(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
    
                if(map!=null){
                    pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                    pushDataLogModel.setMemo("转让付款推送");
                    pushDataLogModel.setEventCode("transferPay");
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

}
