package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class CrowdfundDailyIncomeModel extends BaseModel {
    private String id;

    private String incomeNo;

    private String loanNo;

    private String supportNo;

    private String incomeUser;

    private Double supportAmt;

    private Double incomeAmt;

    private Double incomeRate;

    private Date incomeTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIncomeNo() {
        return incomeNo;
    }

    public void setIncomeNo(String incomeNo) {
        this.incomeNo = incomeNo == null ? null : incomeNo.trim();
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }

    public String getSupportNo() {
        return supportNo;
    }

    public void setSupportNo(String supportNo) {
        this.supportNo = supportNo == null ? null : supportNo.trim();
    }

    public String getIncomeUser() {
        return incomeUser;
    }

    public void setIncomeUser(String incomeUser) {
        this.incomeUser = incomeUser == null ? null : incomeUser.trim();
    }

    public Double getSupportAmt() {
        return supportAmt;
    }

    public void setSupportAmt(Double supportAmt) {
        this.supportAmt = supportAmt;
    }

    public Double getIncomeAmt() {
        return incomeAmt;
    }

    public void setIncomeAmt(Double incomeAmt) {
        this.incomeAmt = incomeAmt;
    }

    public Double getIncomeRate() {
        return incomeRate;
    }

    public void setIncomeRate(Double incomeRate) {
        this.incomeRate = incomeRate;
    }

    public Date getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(Date incomeTime) {
        this.incomeTime = incomeTime;
    }
}