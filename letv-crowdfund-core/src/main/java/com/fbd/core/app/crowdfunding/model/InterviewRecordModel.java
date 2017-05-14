package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 约谈
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class InterviewRecordModel extends BaseModel{
    private String id;

    private String applyUser;

    private String interviewContent;

    private String loanNo;

    private String state;

    private String receiveUser;

    private Date interviewTime;
    
    private String isStationMsg;
    
    private String isEmail;
    
    private String loanType;
    
    private String loanNoLike;
    
    private String applyUserLike;
    
    private String receiveUserLike;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser == null ? null : applyUser.trim();
    }

    public String getInterviewContent() {
        return interviewContent;
    }

    public void setInterviewContent(String interviewContent) {
        this.interviewContent = interviewContent == null ? null : interviewContent.trim();
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser == null ? null : receiveUser.trim();
    }

    public Date getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(Date interviewTime) {
        this.interviewTime = interviewTime;
    }

    public String getIsStationMsg() {
        return isStationMsg;
    }

    public void setIsStationMsg(String isStationMsg) {
        this.isStationMsg = isStationMsg;
    }

    public String getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(String isEmail) {
        this.isEmail = isEmail;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanNoLike() {
        return loanNoLike;
    }

    public void setLoanNoLike(String loanNoLike) {
        this.loanNoLike = loanNoLike;
    }

    public String getApplyUserLike() {
        return applyUserLike;
    }

    public void setApplyUserLike(String applyUserLike) {
        this.applyUserLike = applyUserLike;
    }

    public String getReceiveUserLike() {
        return receiveUserLike;
    }

    public void setReceiveUserLike(String receiveUserLike) {
        this.receiveUserLike = receiveUserLike;
    }
    
    
}