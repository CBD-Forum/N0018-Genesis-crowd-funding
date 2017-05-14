package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import com.fbd.core.app.crowdfunding.model.CrowdfundPhotoModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundPhotoDao extends BaseDao<CrowdfundPhotoModel>{
    
    public void deleteByLoanNo(String loanNo);
    
    
    public List<CrowdfundPhotoModel> getByLoanNo(String loanNo);
}