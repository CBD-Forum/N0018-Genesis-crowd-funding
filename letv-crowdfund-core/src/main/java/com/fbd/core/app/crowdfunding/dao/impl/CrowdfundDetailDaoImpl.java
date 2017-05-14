/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundDetailDaoImpl.java 
 *
 * Created: [2015-5-21 上午11:41:12] by haolingfeng
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

import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundDetailDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundDetailModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹详情
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundDetailDao")
public class CrowdfundDetailDaoImpl extends BaseDaoImpl<CrowdfundDetailModel>
implements ICrowdfundDetailDao{
    
    public void updateByLoanNo(CrowdfundDetailModel model){
        this.update("updateByLoanNo", model);
    }
    
    public void deleteByLoanNo(String loanNo){
        this.deleteByField("deleteByLoanNo", loanNo);
    }

}
