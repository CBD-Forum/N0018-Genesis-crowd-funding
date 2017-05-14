package com.fbd.core.app.crowdfunding.model;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:领投跟投关系表 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class LeadFollowModel {
    private String id;

    private String loanNo;

    private String orderId;

    private String investor;

    private String superInvestor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getInvestor() {
        return investor;
    }

    public void setInvestor(String investor) {
        this.investor = investor == null ? null : investor.trim();
    }

    public String getSuperInvestor() {
        return superInvestor;
    }

    public void setSuperInvestor(String superInvestor) {
        this.superInvestor = superInvestor == null ? null : superInvestor.trim();
    }
}