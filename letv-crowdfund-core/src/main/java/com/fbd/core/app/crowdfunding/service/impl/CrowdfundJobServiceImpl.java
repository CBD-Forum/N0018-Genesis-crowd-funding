/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundJobServiceImpl.java 
 *
 * Created: [2015-6-9 下午3:27:27] by haolingfeng
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
 * ProjectName: fbd-core 
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
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.service.ICrowdfundJobService;
import com.fbd.core.app.push.dao.IPushDataLogDao;
import com.fbd.core.app.push.model.PushDataLogModel;
import com.fbd.core.app.user.dao.ICrowdfundUserPrizeDao;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.quartz.util.QuartzUtils;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.SpringPropertiesHolder;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹jobservice
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("crowdfundJobService")
public class CrowdfundJobServiceImpl implements ICrowdfundJobService{
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    
    @Resource
    private ICrowdfundingProgressDao crowdfundingProgressDao;
    
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    
    @Resource
    private ICrowdfundUserPrizeDao crowdfundUserPrizeDao ;
    
//    @Resource
//    private StdScheduler scheduler;
      
    @Resource
    private IPushDataLogDao pushDataLogDao;
    
    /**
     * 
     * Description: 到截止日期时变更项目状态
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-11 下午12:02:31
     */
    public void updateCrowdFundFunded(String loanNo){
        try{
            CrowdfundingModel model = this.crowdfundingDao.getByloanNo(loanNo);
            String funded = CrowdfundCoreConstants.crowdFundingState.funded.getValue();
            if(model.getLoanState().equals(funded)){
                return;
            }
            model.setLoanState(funded);
            this.crowdfundingDao.update(model);
            //项目进展-筹资完成
            String progressNode = CrowdfundCoreConstants.CROWDFUND_PROGRESS_FUNDEND;
            boolean isNeedAdd = crowdfundingProgressDao.isNeedAdd(loanNo, progressNode);
            if(isNeedAdd){
                long supportCnt = crowdfundingSupportDao.getSupportUserCounts(loanNo);
                double ratio = Arith.round(model.getApproveAmt()/model.getFundAmt()*100);
                
                Map<String,String> params = new HashMap<String,String>();
                params.put("approveAmt",Arith.format(model.getApproveAmt()));
                params.put("supportRatio",String.valueOf(ratio));
                params.put("supportUserCnt",String.valueOf(supportCnt));
                crowdfundingProgressDao.saveProgress(progressNode, loanNo, params);
            }
        }catch(Exception e){
            throw new ApplicationException("更新众筹项目失败");
        }
    }
    
    @Override
    public List<Map<String, Object>> pushLoanFail(Map<String, Object> param) {
        // TODO Auto-generated method stub
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("loanfail");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.SE_DATE_FORMAT));
/*
            param.put("payMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
            param.put("payAccount", SpringPropertiesHolder.getProperty("paymentAccount"));*/
            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list = crowdfundingDao.pushLoanFail(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
    
                if(map!=null){
                    pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                    pushDataLogModel.setMemo("募集失败退款推送");
                    pushDataLogModel.setEventCode("loanfail");
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

    /* (non-Javadoc)
     * @see com.fbd.core.app.crowdfunding.service.ICrowdfundJobService#selectIsnvestmentFunds(java.util.Map)
     */
    /**
     * 投资划款
     */
        @Override
        public List<Map<String, Object>> pushIsnvestmentFunds(
                Map<String, Object> param) {
            // TODO Auto-generated method stub
                    try{
                        // TODO Auto-generated method stub
                        Map<String,Object>pushMap = pushDataLogDao.selectModel("investmentFunds");
                        if(pushMap!=null){
                            param.put("completeTime", pushMap.get("lastPushTime"));
                        }
                        param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.SE_DATE_FORMAT));
/*
                        param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
                        param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));*/
                        PushDataLogModel pushDataLogModel=new PushDataLogModel();
                        pushDataLogModel.setId(PKGenarator.getId());
                        List<Map<String,Object>>list = crowdfundingDao.selectIsnvestmentFunds(param);
                        if(list!=null&&list.size()>0){
                            Map<String,Object>map = list.get(0);
                
                            if(map!=null){
                                pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                                pushDataLogModel.setMemo("投资划款推送");
                                pushDataLogModel.setEventCode("investmentFunds");
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
         * 募集转账
         */
    public List<Map<String, Object>> pushRaiseTransfer(
            Map<String, Object> param) {
        // TODO Auto-generated method stub
                try{
                    // TODO Auto-generated method stub
                    Map<String,Object>pushMap = pushDataLogDao.selectModel("raiseTransfer");
                    if(pushMap!=null){
                        param.put("completeTime", pushMap.get("lastPushTime"));
                    }
                    param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.SE_DATE_FORMAT));

                    param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
                    param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
                    PushDataLogModel pushDataLogModel=new PushDataLogModel();
                    pushDataLogModel.setId(PKGenarator.getId());
                    List<Map<String,Object>>list = crowdfundingDao.raiseTransfer(param);
                    if(list!=null&&list.size()>0){
                        Map<String,Object>map = list.get(0);
            
                        if(map!=null){
                            pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                            pushDataLogModel.setMemo("募集转账推送");
                            pushDataLogModel.setEventCode("raiseTransfer");
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
     * 支付收益
     */
public List<Map<String, Object>> pushDailyIncomeData(
        Map<String, Object> param) {
    // TODO Auto-generated method stub
            try{
                // TODO Auto-generated method stub
                Map<String,Object>pushMap = pushDataLogDao.selectModel("DailyIncome");
               /* if(pushMap!=null){
                    param.put("completeTime", pushMap.get("lastPushTime"));
                }*/
                param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.SE_DATE_FORMAT));
/*
                param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));c
                param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));*/
                PushDataLogModel pushDataLogModel=new PushDataLogModel();
                pushDataLogModel.setId(PKGenarator.getId());
                List<Map<String,Object>>list = crowdfundingSupportDao.selectDailyIncomeData(param);
                if(list!=null&&list.size()>0){
                    Map<String,Object>map = list.get(0);
        
                    if(map!=null){
                        pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                        pushDataLogModel.setMemo("支付收益推送");
                        pushDataLogModel.setEventCode("DailyIncome");
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
 * 项目结算
 */
    public List<Map<String, Object>> pushJSData(
            Map<String, Object> param) {
        // TODO Auto-generated method stub
                try{
                    // TODO Auto-generated method stub
                    Map<String,Object>pushMap = pushDataLogDao.selectModel("projectJs");
                    if(pushMap!=null){
                        param.put("completeTime", pushMap.get("lastPushTime"));
                    }
                    param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.SE_DATE_FORMAT));
                    PushDataLogModel pushDataLogModel=new PushDataLogModel();
                    pushDataLogModel.setId(PKGenarator.getId());
                    List<Map<String,Object>>list = crowdfundingDao.selectJsData(param);
                    if(list!=null&&list.size()>0){
                        Map<String,Object>map = list.get(0);
            
                        if(map!=null){
                            pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                            pushDataLogModel.setMemo("项目结算推送");
                            pushDataLogModel.setEventCode("projectJs");
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
