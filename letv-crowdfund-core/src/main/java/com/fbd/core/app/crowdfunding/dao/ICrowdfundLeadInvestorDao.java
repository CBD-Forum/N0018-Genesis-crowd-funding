package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundLeadInvestorModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundLeadInvestorDao extends BaseDao<CrowdfundLeadInvestorModel>{
    
    /**
     * 
     * Description: 查询项目领投人
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-8 下午2:43:19
     */
    public List<Map<String,Object>> getLoanLearder(String loanNo);
    
    
    public long getByLeaderAndLoanNo(String loanNo,String leader);
}