/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingPraiseDaoImpl.java 
 *
 * Created: [2015-3-27 下午4:30:02] by haolingfeng
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

package com.fbd.core.app.crowdfunding.dao.impl;

import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingPraiseDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingPraiseModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹点赞
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundingPraiseDao")
public class CrowdfundingPraiseDaoImpl extends BaseDaoImpl<CrowdfundingPraiseModel>
implements ICrowdfundingPraiseDao{
    
    
    public CrowdfundingPraiseModel selectByPraiseSessionId(String praiseSessionId){
        return this.selectOneByField("selectByPraiseSessionId", praiseSessionId);
    }

    
    public CrowdfundingPraiseModel selectByModel(CrowdfundingPraiseModel model){
        return this.selectOneByField("selectByModel", model);
    }
}
