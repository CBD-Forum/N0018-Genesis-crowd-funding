package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import com.fbd.core.app.crowdfunding.model.CrowdfundingInvestAfterModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundingInvestAfterDao extends BaseDao<CrowdfundingInvestAfterModel> {
    
    
    public List<CrowdfundingInvestAfterModel> selectList(CrowdfundingInvestAfterModel model);
    
    public long selectCount(CrowdfundingInvestAfterModel model);
   
}