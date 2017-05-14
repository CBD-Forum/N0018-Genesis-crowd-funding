/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IRechargeServiceJobImpl.java 
 *
 * Created: [2016-8-29 下午5:09:11] by haolingfeng
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

package com.fbd.core.app.recharge.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.push.dao.IPushDataLogDao;
import com.fbd.core.app.push.model.PushDataLogModel;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.service.IRechargeServiceJob;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.SpringPropertiesHolder;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("rechargeServiceJob")
public class RechargeServiceJobImpl implements IRechargeServiceJob{
    @Resource
    private IRechargeDao rechargeDao;
    @Resource
    private IPushDataLogDao pushDataLogDao;
    /**
     * 充值成功
     */
    public List<Map<String, Object>> pushRechargeData(
            Map<String, Object> param) {
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("recharge");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
            param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
            param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list = rechargeDao.selectRechargeData(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
    
                if(map!=null){
                    pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                    pushDataLogModel.setMemo("投资方充值推送");
                    pushDataLogModel.setEventCode("recharge");
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
     * 区块链成功
     */
    public List<Map<String, Object>> pushBlockRechargeData(
            Map<String, Object> param) {
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("brecharge");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
            param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
            param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list = rechargeDao.selectBlockChainRechargeData(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
    
                if(map!=null){
                    pushDataLogModel.setLastPushTime(DateUtil.dateTime2Str((Date)map.get("reconciliationTime"), DateUtil.DEFAULT_DATE_TIME_FORMAT));
                    pushDataLogModel.setMemo("投资方区块链充值推送");
                    pushDataLogModel.setEventCode("brecharge");
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
