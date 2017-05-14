package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹关注
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class CrowdfundingAttentionModel extends BaseModel{
    private String id;

    private String attentionUser;

    private String loanNo;
    
    private String attentionType;
    
    private Date attentionTime;
    
    private String loanType;
    
    private String attentionUserLike;
    
    private String loanUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAttentionUser() {
        return attentionUser;
    }

    public void setAttentionUser(String attentionUser) {
        this.attentionUser = attentionUser == null ? null : attentionUser.trim();
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }

    public String getAttentionType() {
        return attentionType;
    }

    public void setAttentionType(String attentionType) {
        this.attentionType = attentionType;
    }

    public Date getAttentionTime() {
        return attentionTime;
    }

    public void setAttentionTime(Date attentionTime) {
        this.attentionTime = attentionTime;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getAttentionUserLike() {
        return attentionUserLike;
    }

    public void setAttentionUserLike(String attentionUserLike) {
        this.attentionUserLike = attentionUserLike;
    }

    public String getLoanUser() {
        return loanUser;
    }

    public void setLoanUser(String loanUser) {
        this.loanUser = loanUser;
    }
    
    
}