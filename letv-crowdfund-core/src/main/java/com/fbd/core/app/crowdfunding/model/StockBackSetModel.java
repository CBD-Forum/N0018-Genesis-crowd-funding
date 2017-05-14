package com.fbd.core.app.crowdfunding.model;

import com.fbd.core.base.BaseModel;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 股权回报设置
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class StockBackSetModel extends BaseModel{
    private String id;

    private Double projectFinanceAmt;

    private Double investFinanceAmt;

    private Double projectBonusRatio;

    private Double investBonusRatio;

    private Integer financeNum;

    private Double miniInvestAmt;
    
    private String loanNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Double getProjectFinanceAmt() {
        return projectFinanceAmt;
    }

    public void setProjectFinanceAmt(Double projectFinanceAmt) {
        this.projectFinanceAmt = projectFinanceAmt;
    }

    public Double getInvestFinanceAmt() {
        return investFinanceAmt;
    }

    public void setInvestFinanceAmt(Double investFinanceAmt) {
        this.investFinanceAmt = investFinanceAmt;
    }

    public Double getProjectBonusRatio() {
        return projectBonusRatio;
    }

    public void setProjectBonusRatio(Double projectBonusRatio) {
        this.projectBonusRatio = projectBonusRatio;
    }

    public Double getInvestBonusRatio() {
        return investBonusRatio;
    }

    public void setInvestBonusRatio(Double investBonusRatio) {
        this.investBonusRatio = investBonusRatio;
    }

    public Integer getFinanceNum() {
        return financeNum;
    }

    public void setFinanceNum(Integer financeNum) {
        this.financeNum = financeNum;
    }

    public Double getMiniInvestAmt() {
        return miniInvestAmt;
    }

    public void setMiniInvestAmt(Double miniInvestAmt) {
        this.miniInvestAmt = miniInvestAmt;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }
    
    
}