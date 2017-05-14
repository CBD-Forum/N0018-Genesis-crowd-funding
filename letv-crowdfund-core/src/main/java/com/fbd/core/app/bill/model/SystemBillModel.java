package com.fbd.core.app.bill.model;

import java.util.Date;

import com.fbd.core.base.BaseModel;

public class SystemBillModel extends BaseModel{
    private String id;

    private String userId;

    private Integer seqNum;

    private Double amt;

    private Double balance;

    private Double frozenAmt;

    private Date tradeTime;

    private String tradeType;

    private String oppositeSide;

    private String tradeDirection;

    private String tradeId;

    private String parentId;

    private String detail;
    
    private String childAcctType;
    
    private String childAcctNo;
    
    private Date tradeStartTime;
    private Date tradeEndTime;
    private String tradeIdLike;
    private String displayAmt;
    private String tradeTypeName;
    private String tradeDirectionName;
    private String childAcctName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getFrozenAmt() {
        return frozenAmt;
    }

    public void setFrozenAmt(Double frozenAmt) {
        this.frozenAmt = frozenAmt;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    public String getOppositeSide() {
        return oppositeSide;
    }

    public void setOppositeSide(String oppositeSide) {
        this.oppositeSide = oppositeSide == null ? null : oppositeSide.trim();
    }

    public String getTradeDirection() {
        return tradeDirection;
    }

    public void setTradeDirection(String tradeDirection) {
        this.tradeDirection = tradeDirection == null ? null : tradeDirection.trim();
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Date getTradeStartTime() {
        return tradeStartTime;
    }

    public void setTradeStartTime(Date tradeStartTime) {
        this.tradeStartTime = tradeStartTime;
    }

    public Date getTradeEndTime() {
        return tradeEndTime;
    }

    public void setTradeEndTime(Date tradeEndTime) {
        this.tradeEndTime = tradeEndTime;
    }

    public String getTradeIdLike() {
        return tradeIdLike;
    }

    public void setTradeIdLike(String tradeIdLike) {
        this.tradeIdLike = tradeIdLike;
    }

    public String getDisplayAmt() {
        return displayAmt;
    }

    public void setDisplayAmt(String displayAmt) {
        this.displayAmt = displayAmt;
    }

    public String getTradeTypeName() {
        return tradeTypeName;
    }

    public void setTradeTypeName(String tradeTypeName) {
        this.tradeTypeName = tradeTypeName;
    }

    public String getTradeDirectionName() {
        return tradeDirectionName;
    }

    public void setTradeDirectionName(String tradeDirectionName) {
        this.tradeDirectionName = tradeDirectionName;
    }

    public String getChildAcctType() {
        return childAcctType;
    }

    public void setChildAcctType(String childAcctType) {
        this.childAcctType = childAcctType;
    }

    public String getChildAcctNo() {
        return childAcctNo;
    }

    public void setChildAcctNo(String childAcctNo) {
        this.childAcctNo = childAcctNo;
    }

    public String getChildAcctName() {
        return childAcctName;
    }

    public void setChildAcctName(String childAcctName) {
        this.childAcctName = childAcctName;
    }
    
}