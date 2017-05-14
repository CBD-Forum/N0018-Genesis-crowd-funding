package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundingFounderDao  extends BaseDao<CrowdfundingFounderModel>{
    
    
    
    public List<CrowdfundingFounderModel> selectList(CrowdfundingFounderModel model);
    
    
    public long selectCount(CrowdfundingFounderModel model);
   
}