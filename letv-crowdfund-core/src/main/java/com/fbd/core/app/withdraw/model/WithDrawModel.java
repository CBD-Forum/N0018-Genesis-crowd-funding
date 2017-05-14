package com.fbd.core.app.withdraw.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class WithDrawModel extends BaseModel{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String bankCardId;

    private String withdrawWay;

    private Double amt;

    private Double actualMoney;
    private Double withdrawFee;
    private String memo;
    
    
    private Double fee;
    
    private Double hyFee;

    private String feeChildAcctno;

    private String feeAcctNo;

    private String thirdAcctNo;

    private Double cashFine;

    private String state;

    private Date applyTime;
    
    private Date sendTime;//发送提现申请

    private Date acceptTime;//提现申请提交
    
    private Date completeTime;//提现完成时间

    private Date auditTime;

    private String auditOpinion;

    private String auditor;
    
    private String orderId;
    
    private String stateName;
    
    private Date applyStartTime;
    
    private Date applyEndTime;
    
    private Date createStartTime;
    
    private Date createEndTime;
    
    private String feeType;
    
    private String bankName;
    //运营审核意见
    private String operatorOpinion;
    //财务审核意见
    private String financialOpinion;
    //运营审核时间
    private String operatorAuditTime;
    //财务审核时间
    private String financialAuditTime;
    //运营审核人员
    private String operator;
    //财务审核人员
    private String financialAuditor;
    //提现交易方
    private String thirdWtihDrawType;
    
    private String stateIn; //查询状态
    private Date startApplyTime;
    
    private Date endApplyTime;
    //用户提现请求事务ID
    private String withdrawTransactionId;  
    //用户提现请求区块链状态
    private String withdrawBlockChainState;  
    //审核通过事务ID
    private String auditTransactionId;  
    //审核通过后事务ID
    private String auditBlockChainState;  
    //协议签署状态  0-未下载  1-已签署
    private String agreementState; 
    //协议地址
    private String agreementUrl;  
    //审核操作类型   operator-运营审核   financial-财务审核
    private String auditType;  
    
    
    
    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo the memo to set
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * @return the withdrawFee
     */
    public Double getWithdrawFee() {
        return withdrawFee;
    }

    /**
     * @param withdrawFee the withdrawFee to set
     */
    public void setWithdrawFee(Double withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    
    /**
     * @return the operatorOpinion
     */
    public String getOperatorOpinion() {
        return operatorOpinion;
    }

    /**
     * @param operatorOpinion the operatorOpinion to set
     */
    public void setOperatorOpinion(String operatorOpinion) {
        this.operatorOpinion = operatorOpinion;
    }

    /**
     * @return the financialOpinion
     */
    public String getFinancialOpinion() {
        return financialOpinion;
    }

    /**
     * @param financialOpinion the financialOpinion to set
     */
    public void setFinancialOpinion(String financialOpinion) {
        this.financialOpinion = financialOpinion;
    }

    /**
     * @return the operatorAuditTime
     */
    public String getOperatorAuditTime() {
        return operatorAuditTime;
    }

    /**
     * @param operatorAuditTime the operatorAuditTime to set
     */
    public void setOperatorAuditTime(String operatorAuditTime) {
        this.operatorAuditTime = operatorAuditTime;
    }

    /**
     * @return the financialAuditTime
     */
    public String getFinancialAuditTime() {
        return financialAuditTime;
    }

    /**
     * @param financialAuditTime the financialAuditTime to set
     */
    public void setFinancialAuditTime(String financialAuditTime) {
        this.financialAuditTime = financialAuditTime;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @return the financialAuditor
     */
    public String getFinancialAuditor() {
        return financialAuditor;
    }

    /**
     * @param financialAuditor the financialAuditor to set
     */
    public void setFinancialAuditor(String financialAuditor) {
        this.financialAuditor = financialAuditor;
    }

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

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId == null ? null : bankCardId.trim();
    }

    public String getWithdrawWay() {
        return withdrawWay;
    }

    public void setWithdrawWay(String withdrawWay) {
        this.withdrawWay = withdrawWay == null ? null : withdrawWay.trim();
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(Double actualMoney) {
        this.actualMoney = actualMoney;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getFeeChildAcctno() {
        return feeChildAcctno;
    }

    public void setFeeChildAcctno(String feeChildAcctno) {
        this.feeChildAcctno = feeChildAcctno == null ? null : feeChildAcctno.trim();
    }

    public String getFeeAcctNo() {
        return feeAcctNo;
    }

    public void setFeeAcctNo(String feeAcctNo) {
        this.feeAcctNo = feeAcctNo == null ? null : feeAcctNo.trim();
    }

    public String getThirdAcctNo() {
        return thirdAcctNo;
    }

    public void setThirdAcctNo(String thirdAcctNo) {
        this.thirdAcctNo = thirdAcctNo == null ? null : thirdAcctNo.trim();
    }

    public Double getCashFine() {
        return cashFine;
    }

    public void setCashFine(Double cashFine) {
        this.cashFine = cashFine;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion == null ? null : auditOpinion.trim();
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Date getApplyStartTime() {
        return applyStartTime;
    }

    public void setApplyStartTime(Date applyStartTime) {
        this.applyStartTime = applyStartTime;
    }

    public Date getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(Date applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public Double getHyFee() {
        return hyFee;
    }

    public void setHyFee(Double hyFee) {
        this.hyFee = hyFee;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the thirdWtihDrawType
     */
    public String getThirdWtihDrawType() {
        return thirdWtihDrawType;
    }

    /**
     * @param thirdWtihDrawType the thirdWtihDrawType to set
     */
    public void setThirdWtihDrawType(String thirdWtihDrawType) {
        this.thirdWtihDrawType = thirdWtihDrawType;
    }

    /**
     * @return the stateIn
     */
    public String getStateIn() {
        return stateIn;
    }

    /**
     * @return the createStartTime
     */
    public Date getCreateStartTime() {
        return createStartTime;
    }

    /**
     * @param createStartTime the createStartTime to set
     */
    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    /**
     * @return the createEndTime
     */
    public Date getCreateEndTime() {
        return createEndTime;
    }

    /**
     * @param createEndTime the createEndTime to set
     */
    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    /**
     * @param stateIn the stateIn to set
     */
    public void setStateIn(String stateIn) {
        this.stateIn = stateIn;
    }

    /**
     * @return the startApplyTime
     */
    public Date getStartApplyTime() {
        return startApplyTime;
    }

    /**
     * @param startApplyTime the startApplyTime to set
     */
    public void setStartApplyTime(Date startApplyTime) {
        this.startApplyTime = startApplyTime;
    }

    /**
     * @return the endApplyTime
     */
    public Date getEndApplyTime() {
        return endApplyTime;
    }

    /**
     * @param endApplyTime the endApplyTime to set
     */
    public void setEndApplyTime(Date endApplyTime) {
        this.endApplyTime = endApplyTime;
    }

    /**
     * @return the withdrawTransactionId
     */
    public String getWithdrawTransactionId() {
        return withdrawTransactionId;
    }

    /**
     * @param withdrawTransactionId the withdrawTransactionId to set
     */
    public void setWithdrawTransactionId(String withdrawTransactionId) {
        this.withdrawTransactionId = withdrawTransactionId;
    }

    /**
     * @return the withdrawBlockChainState
     */
    public String getWithdrawBlockChainState() {
        return withdrawBlockChainState;
    }

    /**
     * @param withdrawBlockChainState the withdrawBlockChainState to set
     */
    public void setWithdrawBlockChainState(String withdrawBlockChainState) {
        this.withdrawBlockChainState = withdrawBlockChainState;
    }

    /**
     * @return the auditTransactionId
     */
    public String getAuditTransactionId() {
        return auditTransactionId;
    }

    /**
     * @param auditTransactionId the auditTransactionId to set
     */
    public void setAuditTransactionId(String auditTransactionId) {
        this.auditTransactionId = auditTransactionId;
    }

    /**
     * @return the auditBlockChainState
     */
    public String getAuditBlockChainState() {
        return auditBlockChainState;
    }

    /**
     * @param auditBlockChainState the auditBlockChainState to set
     */
    public void setAuditBlockChainState(String auditBlockChainState) {
        this.auditBlockChainState = auditBlockChainState;
    }

    /**
     * @return the agreementState
     */
    public String getAgreementState() {
        return agreementState;
    }

    /**
     * @param agreementState the agreementState to set
     */
    public void setAgreementState(String agreementState) {
        this.agreementState = agreementState;
    }

    /**
     * @return the agreementUrl
     */
    public String getAgreementUrl() {
        return agreementUrl;
    }

    /**
     * @param agreementUrl the agreementUrl to set
     */
    public void setAgreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl;
    }

    /**
     * @return the auditType
     */
    public String getAuditType() {
        return auditType;
    }

    /**
     * @param auditType the auditType to set
     */
    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }
    
}