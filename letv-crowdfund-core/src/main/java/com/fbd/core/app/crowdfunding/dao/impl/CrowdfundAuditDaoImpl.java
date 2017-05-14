/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundAuditDaoImpl.java 
 *
 * Created: [2015-5-27 下午2:02:09] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundAuditDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundAuditModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.helper.PKGenarator;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundAuditDao")
public class CrowdfundAuditDaoImpl extends BaseDaoImpl<CrowdfundAuditModel>
implements ICrowdfundAuditDao{
    /**
     * 
     * Description: 通用查询投资审核列表
     * 
     * @param
     * @return List<InvestModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午5:30:15
     */
    public List<CrowdfundAuditModel> getList(CrowdfundAuditModel model) {
        return this.selectByModel("selectList", model);
    }
    
    
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
            String auditOpinion, String loanState) {
        CrowdfundAuditModel model = new CrowdfundAuditModel();
        model.setId(PKGenarator.getId());
        model.setAuditTime(new Date());
        model.setAuditor(operator);
        model.setLoanNo(loanNo);
        model.setAuditState(auditState);
        model.setLoanState(loanState);
        model.setAuditOpinion(auditOpinion);
        this.save(model);
    }
}
