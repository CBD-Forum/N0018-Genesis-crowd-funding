package com.fbd.core.app.crowdfunding.dao;

import com.fbd.core.app.crowdfunding.model.CrowdfundDailyIncomeModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundDailyIncomeDao extends BaseDao<CrowdfundDailyIncomeModel>{
 //   public int save(CrowdfundDailyIncomeModel crowdfundDailyIncomeModel);
    public int saveCrowdModel(CrowdfundDailyIncomeModel crowdfundDailyIncomeModel);
}