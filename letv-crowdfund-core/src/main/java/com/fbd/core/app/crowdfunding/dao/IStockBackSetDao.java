package com.fbd.core.app.crowdfunding.dao;

import com.fbd.core.app.crowdfunding.model.StockBackSetModel;
import com.fbd.core.base.BaseDao;

public interface IStockBackSetDao  extends BaseDao<StockBackSetModel>{
    
    public void updateByLoanNo(StockBackSetModel model);
    public StockBackSetModel getByLoanNo(String loanNo);
    public void deleteByLoanNo(String loanNo);
}