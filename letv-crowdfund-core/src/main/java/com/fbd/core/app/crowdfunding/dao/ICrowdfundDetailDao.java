package com.fbd.core.app.crowdfunding.dao;

import com.fbd.core.app.crowdfunding.model.CrowdfundDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹详情
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface ICrowdfundDetailDao extends BaseDao<CrowdfundDetailModel>{
    public void updateByLoanNo(CrowdfundDetailModel model);
    public void deleteByLoanNo(String loanNo);
}