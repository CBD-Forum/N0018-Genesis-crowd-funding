/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundBonusAuditDaoImpl.java 
 *
 * Created: [2016-8-24 下午6:27:19] by haolingfeng
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
import com.fbd.core.app.crowdfunding.dao.ICrowdfundBonusAuditDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundBonusAuditModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:项目分红审核记录 
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Repository("crowdfundBonusAuditDao")
public class CrowdfundBonusAuditDaoImpl extends BaseDaoImpl<CrowdfundBonusAuditModel> implements ICrowdfundBonusAuditDao {

    /**
     * Description: 查询分红项目    审核记录 列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-24 下午6:31:22
     */
    public List<Map<String, Object>> getLoanAuditBonusList(
            CrowdfundBonusAuditModel model) {
        return this.selectMapByFields("selectLoanAuditBonusList", model);
    }

    /**
     * Description: 统计分红项目    审核记录 列表
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-24 下午6:31:33
     */
    public long getLoanAuditBonusCount(CrowdfundBonusAuditModel model) {
        return this.getCount("selectLoanAuditBonusCount",model);
    }


}
