/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundRefundAuditDaoImpl.java 
 *
 * Created: [2016-8-24 下午5:08:22] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundRefundAuditDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundRefundAuditModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 退款审核
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Repository("crowdfundRefundAuditDao")
public class CrowdfundRefundAuditDaoImpl extends BaseDaoImpl<CrowdfundRefundAuditModel> implements ICrowdfundRefundAuditDao {

    /**
     * Description: 查询项目方审核投资申请退款记录
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-24 下午5:16:36
     */
    public List<Map<String, Object>> getLoanAuditRefundList(
            CrowdfundRefundAuditModel model) {
        return this.selectMapByFields("selectLoanAuditRefundList", model);
    }
    
    /**
     * Description: 统计项目方审核投资申请退款记录
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-24 下午5:16:36
     */
    public long getLoanAuditRefundCount(
            CrowdfundRefundAuditModel model) {
        return this.getCount("selectLoanAuditRefundCount",model);
    }

}
