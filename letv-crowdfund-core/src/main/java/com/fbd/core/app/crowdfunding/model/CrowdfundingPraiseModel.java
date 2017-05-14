package com.fbd.core.app.crowdfunding.model;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹点赞
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class CrowdfundingPraiseModel {
    private String id;

    private String praiseSessionId;

    private String loanNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPraiseSessionId() {
        return praiseSessionId;
    }

    public void setPraiseSessionId(String praiseSessionId) {
        this.praiseSessionId = praiseSessionId == null ? null : praiseSessionId.trim();
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }
}