package com.fbd.core.app.reconciliation.model;

import java.util.Date;

public class ReconciliationRechargeModel {
    
     
	private String id;
    //对账单日 
    private String orderDate;
    //对账单号
    private String reconciliationOrderId;
    //支付订单 
    private String orderId;
    //支付金额
    private Double amount;
    //银行 
    private String cardMode;
    //订单状 
    private String orderState;
    //订单描述
    private String orderDesc;
    //mac
    private String mac;
    //支付平台
    private String payPlatform;
    //本地支付订单状 
    private String localOrderState;
    //本地金额
    private Double localAmount;
    //对账结果
    private String state;
    //对账时间
    private Date createTime;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
 
    /**
     * @return the orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }
    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    /**
     * @return the reconciliationOrderId
     */
    public String getReconciliationOrderId() {
        return reconciliationOrderId;
    }
    /**
     * @param reconciliationOrderId the reconciliationOrderId to set
     */
    public void setReconciliationOrderId(String reconciliationOrderId) {
        this.reconciliationOrderId = reconciliationOrderId;
    }
    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }
    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }
    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    /**
     * @return the cardMode
     */
    public String getCardMode() {
        return cardMode;
    }
    /**
     * @param cardMode the cardMode to set
     */
    public void setCardMode(String cardMode) {
        this.cardMode = cardMode;
    }
    /**
     * @return the orderState
     */
    public String getOrderState() {
        return orderState;
    }
    /**
     * @param orderState the orderState to set
     */
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
    /**
     * @return the orderDesc
     */
    public String getOrderDesc() {
        return orderDesc;
    }
    /**
     * @param orderDesc the orderDesc to set
     */
    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }
    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }
    /**
     * @param mac the mac to set
     */
    public void setMac(String mac) {
        this.mac = mac;
    }
    /**
     * @return the payPlatform
     */
    public String getPayPlatform() {
        return payPlatform;
    }
    /**
     * @param payPlatform the payPlatform to set
     */
    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }
    /**
     * @return the localOrderState
     */
    public String getLocalOrderState() {
        return localOrderState;
    }
    /**
     * @param localOrderState the localOrderState to set
     */
    public void setLocalOrderState(String localOrderState) {
        this.localOrderState = localOrderState;
    }
    /**
     * @return the localAmount
     */
    public Double getLocalAmount() {
        return localAmount;
    }
    /**
     * @param localAmount the localAmount to set
     */
    public void setLocalAmount(Double localAmount) {
        this.localAmount = localAmount;
    }
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
     
    
    
    
    
 
}