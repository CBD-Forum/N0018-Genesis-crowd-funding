package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:众筹审核流程 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class CrowdfundAuditModel extends BaseModel{
    private String id;

    private String loanNo;

    private String auditor;

    private String auditState;

    private String auditOpinion;

    private Date auditTime;

    private String loanState;
    
    private String loanName;
    
    private String loanStateName;
    
    private String auditStateName;

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

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState == null ? null : auditState.trim();
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion == null ? null : auditOpinion.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getLoanState() {
        return loanState;
    }

    public void setLoanState(String loanState) {
        this.loanState = loanState == null ? null : loanState.trim();
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public String getLoanStateName() {
        return loanStateName;
    }

    public void setLoanStateName(String loanStateName) {
        this.loanStateName = loanStateName;
    }

    public String getAuditStateName() {
        return auditStateName;
    }

    public void setAuditStateName(String auditStateName) {
        this.auditStateName = auditStateName;
    }
    
    
}