/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICrowdfundBonusJobServiceImpl.java 
 *
 * Created: [2016-8-30 下午4:45:59] by haolingfeng
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

package com.fbd.core.app.reward.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.push.dao.IPushDataLogDao;
import com.fbd.core.app.push.model.PushDataLogModel;
import com.fbd.core.app.reward.dao.ICrowdfundBonusDao;
import com.fbd.core.app.reward.service.ICrowdfundBonusJobService;
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
@Service("crowdfundBonusJobService")
public class CrowdfundBonusJobServiceImpl implements ICrowdfundBonusJobService{
  @Resource 
  private ICrowdfundBonusDao crowdfundBonusDao;
  @Resource
  private IPushDataLogDao pushDataLogDao;
    /* (non-Javadoc)
     * @see com.fbd.core.app.reward.service.ICrowdfundBonusJobService#pushReciveBonus(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> pushReciveBonus(Map<String, Object> param) {
        try{
            // TODO Auto-generated method stub
            Map<String,Object>pushMap = pushDataLogDao.selectModel("loanShare");
            if(pushMap!=null){
                param.put("completeTime", pushMap.get("lastPushTime"));
            }
            param.put("curDate", DateUtil.date2Str(new Date(), DateUtil.SE_DATE_FORMAT));/*
            param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
            param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));*/
            PushDataLogModel pushDataLogModel=new PushDataLogModel();
            pushDataLogModel.setId(PKGenarator.getId());
            List<Map<String,Object>>list = crowdfundBonusDao.pushReciveBonus(param);
            if(list!=null&&list.size()>0){
                Map<String,Object>map = list.get(0);
    
                if(map!=null){
                    pushDataLogModel.setLastPushTime((String)map.get("business_date"));
                    pushDataLogModel.setMemo("收到投资分红、兑付款项");
                    pushDataLogModel.setEventCode("loanShare");
                    pushDataLogModel.setPushTime(DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
        
                    pushDataLogDao.savePushDataLog(pushDataLogModel);
                }
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
        // TODO Auto-generated method stub
      //  return crowdfundBonusDao.pushReciveBonus(params);
    }

}
