package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundBonusAuditModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundBonusAuditDao extends BaseDao<CrowdfundBonusAuditModel>{

    /**
     * Description: 查询分红项目    审核记录 列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-24 下午6:31:22
     */
    
    List<Map<String, Object>> getLoanAuditBonusList(
            CrowdfundBonusAuditModel model);

    /**
     * Description: 统计分红项目    审核记录 列表
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-24 下午6:31:33
     */
    
    long getLoanAuditBonusCount(CrowdfundBonusAuditModel model);
}