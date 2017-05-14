package com.fbd.core.app.crowdfunding.model;

import com.fbd.core.base.BaseModel;

public class CrowdfundLeadInvestorModel extends BaseModel{
    private String id;

    private String loanNo;

    private String leadInvestor;

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

    public String getLeadInvestor() {
        return leadInvestor;
    }

    public void setLeadInvestor(String leadInvestor) {
        this.leadInvestor = leadInvestor == null ? null : leadInvestor.trim();
    }
}