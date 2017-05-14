package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹关注
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface ICrowdfundingAttentionDao extends BaseDao<CrowdfundingAttentionModel>{
    public List<Map<String,Object>> getList(CrowdfundingAttentionModel model);
    public long getCount(CrowdfundingAttentionModel model);
}