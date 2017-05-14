package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:众筹评论 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class CrowdfundingCommentModel extends BaseModel{
    private String id;

    private String loanNo;

    private Date discussTime;

    private String discussUser;

    private String pid;

    private String state;

    private String auditor;

    private Date auditTime;

    private String content;
    
    private String loanNoLike;
    
    private String loanNameLike;
    
    private String discussUserLike;

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

    public Date getDiscussTime() {
        return discussTime;
    }

    public void setDiscussTime(Date discussTime) {
        this.discussTime = discussTime;
    }

    public String getDiscussUser() {
        return discussUser;
    }

    public void setDiscussUser(String discussUser) {
        this.discussUser = discussUser == null ? null : discussUser.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getLoanNoLike() {
        return loanNoLike;
    }

    public void setLoanNoLike(String loanNoLike) {
        this.loanNoLike = loanNoLike;
    }

    public String getLoanNameLike() {
        return loanNameLike;
    }

    public void setLoanNameLike(String loanNameLike) {
        this.loanNameLike = loanNameLike;
    }

    public String getDiscussUserLike() {
        return discussUserLike;
    }

    public void setDiscussUserLike(String discussUserLike) {
        this.discussUserLike = discussUserLike;
    }
    
    
}