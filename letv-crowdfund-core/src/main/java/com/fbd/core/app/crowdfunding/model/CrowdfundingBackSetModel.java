package com.fbd.core.app.crowdfunding.model;

import com.fbd.core.base.BaseModel;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹回报设置
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class CrowdfundingBackSetModel extends BaseModel{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String backNo;

    private String loanNo;

    private Double amt;

    private Integer numberLimits;

    private String backContent;

    private Integer backTime;

    private Integer transFee;

    private String backType;

    private String state;
    
    private String photoUrl1;
    
    private String photoUrl2;
    
    private String photoUrl3;
    
    private String isDelivery;
    
    private Double transferLockPeriod;
    
    private Double dailyProfit;

    private String backLable;
    
    private String prizeDrawFlag;  //参与抽奖标识
    
    //start
    /**
     * 中奖名额
     */
    private Integer prizeNum;
    /**
     * 购买上限
     */
    private Integer prizeInvestNum;
    /**
     * 激活抽奖份数限制
     */
    private Integer prizeFullNum;
    //end
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBackNo() {
        return backNo;
    }

    public void setBackNo(String backNo) {
        this.backNo = backNo == null ? null : backNo.trim();
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Integer getNumberLimits() {
        return numberLimits;
    }

    public void setNumberLimits(Integer numberLimits) {
        this.numberLimits = numberLimits;
    }

    public String getBackContent() {
        return backContent;
    }

    public void setBackContent(String backContent) {
        this.backContent = backContent == null ? null : backContent.trim();
    }


    public Integer getBackTime() {
        return backTime;
    }

    public void setBackTime(Integer backTime) {
        this.backTime = backTime;
    }

    public Integer getTransFee() {
        return transFee;
    }

    public void setTransFee(Integer transFee) {
        this.transFee = transFee;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType == null ? null : backType.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getPhotoUrl1() {
        return photoUrl1;
    }

    public void setPhotoUrl1(String photoUrl1) {
        this.photoUrl1 = photoUrl1;
    }

    public String getPhotoUrl2() {
        return photoUrl2;
    }

    public void setPhotoUrl2(String photoUrl2) {
        this.photoUrl2 = photoUrl2;
    }

    public String getPhotoUrl3() {
        return photoUrl3;
    }

    public void setPhotoUrl3(String photoUrl3) {
        this.photoUrl3 = photoUrl3;
    }

    public String getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        this.isDelivery = isDelivery;
    }

    /**
     * @return the transferLockPeriod
     */
    public Double getTransferLockPeriod() {
        return transferLockPeriod;
    }

    /**
     * @param transferLockPeriod the transferLockPeriod to set
     */
    public void setTransferLockPeriod(Double transferLockPeriod) {
        this.transferLockPeriod = transferLockPeriod;
    }

    /**
     * @return the dailyProfit
     */
    public Double getDailyProfit() {
        return dailyProfit;
    }

    /**
     * @param dailyProfit the dailyProfit to set
     */
    public void setDailyProfit(Double dailyProfit) {
        this.dailyProfit = dailyProfit;
    }

    /**
     * @return the backLable
     */
    public String getBackLable() {
        return backLable;
    }

    /**
     * @param backLable the backLable to set
     */
    public void setBackLable(String backLable) {
        this.backLable = backLable;
    }

    /**
     * @return the prizeDrawFlag
     */
    public String getPrizeDrawFlag() {
        return prizeDrawFlag;
    }

    /**
     * @param prizeDrawFlag the prizeDrawFlag to set
     */
    public void setPrizeDrawFlag(String prizeDrawFlag) {
        this.prizeDrawFlag = prizeDrawFlag;
    }

    /**
     * @return the prizeNum
     */
    public Integer getPrizeNum() {
        return prizeNum;
    }

    /**
     * @param prizeNum the prizeNum to set
     */
    public void setPrizeNum(Integer prizeNum) {
        this.prizeNum = prizeNum;
    }

    /**
     * @return the prizeInvestNum
     */
    public Integer getPrizeInvestNum() {
        return prizeInvestNum;
    }

    /**
     * @param prizeInvestNum the prizeInvestNum to set
     */
    public void setPrizeInvestNum(Integer prizeInvestNum) {
        this.prizeInvestNum = prizeInvestNum;
    }

    /**
     * @return the prizeFullNum
     */
    public Integer getPrizeFullNum() {
        return prizeFullNum;
    }

    /**
     * @param prizeFullNum the prizeFullNum to set
     */
    public void setPrizeFullNum(Integer prizeFullNum) {
        this.prizeFullNum = prizeFullNum;
    }
}