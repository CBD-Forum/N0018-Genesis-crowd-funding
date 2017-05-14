package com.fbd.core.app.reward.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 奖励发放明细
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class RewardAssignModel extends BaseModel{
    private String id;

    private String rewardType;

    private String investNo;

    private String loanNo;

    private Double assignAmt;

    private String bonusState;

    private String getUser;

    private String payUser;

    private Date assignTime;

    private Double investAmt;

    private String investor;

    private String invitorType;
    
//    private String status;
    
    private String getUserLike;
    private Date assignStartTime;
    private Date assignEndTime;
    private String loanName;
    private String rewardTypeName;
    
   private String createTime;
    /**
 * @return the createTime
 */
public String getCreateTime() {
    return createTime;
}

/**
 * @param createTime the createTime to set
 */
public void setCreateTime(String createTime) {
    this.createTime = createTime;
}

    private String loanUser;
    private String loanProvince;
    private String loanCity;
    
    private Integer bonusNum;
    
    private String loanBonusId;//项目分红ID

    public String getRewardTypeName() {
        return rewardTypeName;
    }

    public void setRewardTypeName(String rewardTypeName) {
        this.rewardTypeName = rewardTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType == null ? null : rewardType.trim();
    }

    public String getInvestNo() {
        return investNo;
    }

    public void setInvestNo(String investNo) {
        this.investNo = investNo == null ? null : investNo.trim();
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }

    public Double getAssignAmt() {
        return assignAmt;
    }

    public void setAssignAmt(Double assignAmt) {
        this.assignAmt = assignAmt;
    }

    public String getGetUser() {
        return getUser;
    }

    /**
     * @return the bonusState
     */
    public String getBonusState() {
        return bonusState;
    }

    /**
     * @param bonusState the bonusState to set
     */
    public void setBonusState(String bonusState) {
        this.bonusState = bonusState;
    }

    public void setGetUser(String getUser) {
        this.getUser = getUser == null ? null : getUser.trim();
    }

    public String getPayUser() {
        return payUser;
    }

    public void setPayUser(String payUser) {
        this.payUser = payUser == null ? null : payUser.trim();
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Double getInvestAmt() {
        return investAmt;
    }

    public void setInvestAmt(Double investAmt) {
        this.investAmt = investAmt;
    }

    public String getInvestor() {
        return investor;
    }

    public void setInvestor(String investor) {
        this.investor = investor == null ? null : investor.trim();
    }

    public String getInvitorType() {
        return invitorType;
    }

    public void setInvitorType(String invitorType) {
        this.invitorType = invitorType == null ? null : invitorType.trim();
    }

    public String getGetUserLike() {
        return getUserLike;
    }

    public void setGetUserLike(String getUserLike) {
        this.getUserLike = getUserLike;
    }

    public Date getAssignStartTime() {
        return assignStartTime;
    }

    public void setAssignStartTime(Date assignStartTime) {
        this.assignStartTime = assignStartTime;
    }

    public Date getAssignEndTime() {
        return assignEndTime;
    }

    public void setAssignEndTime(Date assignEndTime) {
        this.assignEndTime = assignEndTime;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public String getLoanUser() {
        return loanUser;
    }

    public void setLoanUser(String loanUser) {
        this.loanUser = loanUser;
    }

    public String getLoanProvince() {
        return loanProvince;
    }

    public void setLoanProvince(String loanProvince) {
        this.loanProvince = loanProvince;
    }

    public String getLoanCity() {
        return loanCity;
    }

    public void setLoanCity(String loanCity) {
        this.loanCity = loanCity;
    }

/*	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}*/

    /**
     * @return the bonusNum
     */
    public Integer getBonusNum() {
        return bonusNum;
    }

    /**
     * @param bonusNum the bonusNum to set
     */
    public void setBonusNum(Integer bonusNum) {
        this.bonusNum = bonusNum;
    }

    /**
     * @return the loanBonusId
     */
    public String getLoanBonusId() {
        return loanBonusId;
    }

    /**
     * @param loanBonusId the loanBonusId to set
     */
    public void setLoanBonusId(String loanBonusId) {
        this.loanBonusId = loanBonusId;
    }
    
    
}