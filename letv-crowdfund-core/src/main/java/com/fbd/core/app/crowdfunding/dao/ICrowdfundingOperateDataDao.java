package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import com.fbd.core.app.crowdfunding.model.CrowdfundingOperateDataModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundingOperateDataDao extends BaseDao<CrowdfundingOperateDataModel> {
    
    
    
    public List<CrowdfundingOperateDataModel> selectList(CrowdfundingOperateDataModel model);
    
    
    public long selectCount(CrowdfundingOperateDataModel model);
    
    
    
    public void updateloanNoByLoanNo(CrowdfundingOperateDataModel model);
    
    
    
}