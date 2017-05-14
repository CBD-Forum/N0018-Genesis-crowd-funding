package com.fbd.core.app.bill.model;

import java.util.Date;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 转账
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class SysTransferAcctModel {
    private String id;

    private String orderId;

    private Double transferAmt;

    private String outUserId;

    private String outChildAcct;

    private String inUserId;

    private String inChildAcct;

    private Date createTime;

    private Date completeTime;

    private String state;
    
    private String operator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Double getTransferAmt() {
        return transferAmt;
    }

    public void setTransferAmt(Double transferAmt) {
        this.transferAmt = transferAmt;
    }

    public String getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(String outUserId) {
        this.outUserId = outUserId == null ? null : outUserId.trim();
    }

    public String getOutChildAcct() {
        return outChildAcct;
    }

    public void setOutChildAcct(String outChildAcct) {
        this.outChildAcct = outChildAcct == null ? null : outChildAcct.trim();
    }

    public String getInUserId() {
        return inUserId;
    }

    public void setInUserId(String inUserId) {
        this.inUserId = inUserId == null ? null : inUserId.trim();
    }

    public String getInChildAcct() {
        return inChildAcct;
    }

    public void setInChildAcct(String inChildAcct) {
        this.inChildAcct = inChildAcct == null ? null : inChildAcct.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    
}