/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundLeadInvestorDaoImpl.java 
 *
 * Created: [2015-6-6 上午11:09:46] by haolingfeng
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundLeadInvestorDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundLeadInvestorModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:项目领投人 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundLeadInvestorDao")
public class CrowdfundLeadInvestorDaoImpl extends BaseDaoImpl<CrowdfundLeadInvestorModel>
implements ICrowdfundLeadInvestorDao{

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
    public List<Map<String,Object>> getLoanLearder(String loanNo){
        return this.selectMapByFields("selectLoanLeader", loanNo);
    }
    
    public long getByLeaderAndLoanNo(String loanNo,String leader){
        Map<String,Object> amap = new HashMap<String,Object>();
        amap.put("loanNo",loanNo);
        amap.put("leadInvestor",leader);
        return this.getCount("getByLeaderAndLoanNo",amap);
    }
}
