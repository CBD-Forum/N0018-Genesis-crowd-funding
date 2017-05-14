package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundRefundAuditModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundRefundAuditDao extends BaseDao<CrowdfundRefundAuditModel>{

    /**
     * Description: 查询项目方审核投资申请退款记录
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-24 下午5:16:36
     */
    
    List<Map<String, Object>> getLoanAuditRefundList(
            CrowdfundRefundAuditModel model);

    /**
     * Description: 统计项目方审核投资申请退款记录
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-24 下午5:47:47
     */
    
    long getLoanAuditRefundCount(CrowdfundRefundAuditModel model);

}