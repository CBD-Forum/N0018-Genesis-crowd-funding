/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: StockBackSetDaoImpl.java 
 *
 * Created: [2015-5-19 下午12:09:21] by haolingfeng
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
import com.fbd.core.app.crowdfunding.dao.IStockBackSetDao;
import com.fbd.core.app.crowdfunding.model.StockBackSetModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 股权回报
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("stockBackSetDao")
public class StockBackSetDaoImpl extends BaseDaoImpl<StockBackSetModel>
implements IStockBackSetDao{
    
    public void updateByLoanNo(StockBackSetModel model){
        this.update("updateByLoanNo", model);
    }
    
    public StockBackSetModel getByLoanNo(String loanNo){
        return this.selectByField("selectByLoanNo", loanNo).get(0);
    }
    
    public void deleteByLoanNo(String loanNo){
        this.deleteByField("deleteByLoanNo", loanNo);
    }

}
