package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class CrowdfundTransferDetailModel extends BaseModel{
    private String id;

    private String buyOrderNo;
    
    private String transferNo;

    private String userId;

    private Double buyMoney;

    private Integer buyParts;

    private Double buyCorpus;

    private Double buyInterest;

    private Date applyTime;

    private Double transferFee;

    private Double actualPayMoney;

    private Date transferTime;

    private String orderNo;

    private Double expectInterest;

    private Double paidCorpus;

    private Double paidInterest;

    private Double paidFee;

    private String status;
    
    
    //===========支付用到的卡号===========
    private String cardTop;//卡号前6位
    private String cardLast;//卡号后4位
    
    private String payResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTransferNo() {
        return transferNo;
    }

    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo == null ? null : transferNo.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Double getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(Double buyMoney) {
        this.buyMoney = buyMoney;
    }

    public Integer getBuyParts() {
        return buyParts;
    }

    public void setBuyParts(Integer buyParts) {
        this.buyParts = buyParts;
    }

    public Double getBuyCorpus() {
        return buyCorpus;
    }

    public void setBuyCorpus(Double buyCorpus) {
        this.buyCorpus = buyCorpus;
    }

    public Double getBuyInterest() {
        return buyInterest;
    }

    public void setBuyInterest(Double buyInterest) {
        this.buyInterest = buyInterest;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(Double transferFee) {
        this.transferFee = transferFee;
    }

    public Double getActualPayMoney() {
        return actualPayMoney;
    }

    public void setActualPayMoney(Double actualPayMoney) {
        this.actualPayMoney = actualPayMoney;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Double getExpectInterest() {
        return expectInterest;
    }

    public void setExpectInterest(Double expectInterest) {
        this.expectInterest = expectInterest;
    }

    public Double getPaidCorpus() {
        return paidCorpus;
    }

    public void setPaidCorpus(Double paidCorpus) {
        this.paidCorpus = paidCorpus;
    }

    public Double getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(Double paidInterest) {
        this.paidInterest = paidInterest;
    }

    public Double getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(Double paidFee) {
        this.paidFee = paidFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getBuyOrderNo() {
        return buyOrderNo;
    }

    public void setBuyOrderNo(String buyOrderNo) {
        this.buyOrderNo = buyOrderNo;
    }

    /**
     * @return the cardTop
     */
    public String getCardTop() {
        return cardTop;
    }

    /**
     * @param cardTop the cardTop to set
     */
    public void setCardTop(String cardTop) {
        this.cardTop = cardTop;
    }

    /**
     * @return the cardLast
     */
    public String getCardLast() {
        return cardLast;
    }

    /**
     * @param cardLast the cardLast to set
     */
    public void setCardLast(String cardLast) {
        this.cardLast = cardLast;
    }

    /**
     * @return the payResult
     */
    public String getPayResult() {
        return payResult;
    }

    /**
     * @param payResult the payResult to set
     */
    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }
    
    
}