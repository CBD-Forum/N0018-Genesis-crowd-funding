package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import com.fbd.core.app.crowdfunding.model.CrowdfundAuditModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundAuditDao extends BaseDao<CrowdfundAuditModel>{
    
    /**
     * 
     * Description: 通用查询投资审核列表
     * 
     * @param
     * @return List<InvestModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午5:30:15
     */
    public List<CrowdfundAuditModel> getList(CrowdfundAuditModel model);
    
    /**
     * 
     * Description: 插入审核记录
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-30 上午11:14:25
     */
    public void addLoanAudit(String operator, String loanNo, String auditState,
            String auditOpinion, String loanState);
}