package com.fbd.core.app.crowdfunding.dao;

import com.fbd.core.app.crowdfunding.model.CrowdfundingPraiseModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:众筹点赞 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface ICrowdfundingPraiseDao extends BaseDao<CrowdfundingPraiseModel>{
    
    
    public CrowdfundingPraiseModel selectByPraiseSessionId(String praiseSessionId);
    
    
    public CrowdfundingPraiseModel selectByModel(CrowdfundingPraiseModel model);
}