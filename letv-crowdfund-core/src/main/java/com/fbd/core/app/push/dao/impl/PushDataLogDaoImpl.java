/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: PushDataLogDaoImpl.java 
 *
 * Created: [2016-8-24 下午6:19:50] by haolingfeng
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

package com.fbd.core.app.push.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.push.dao.IPushDataLogDao;
import com.fbd.core.app.push.model.PushDataLogModel;
import com.fbd.core.base.BaseDaoImpl;


/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository
public class PushDataLogDaoImpl extends BaseDaoImpl<PushDataLogModel> implements IPushDataLogDao {

   
    @Override
    public int savePushDataLog(PushDataLogModel model) {
        // TODO Auto-generated method stub
        return this.save(model);
    }
    /**
     * 推送数据的最近一次推送数据
     * Description: 
     *
     * @param 
     * @return PushDataLogModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-24 下午6:36:07
     */
    public Map<String,Object> selectModel(String eventCode){
      //  return this.selectOneByField("selectlast", eventCode);
        return this.selectOneMapByField("selectlast", eventCode);
    }

}
