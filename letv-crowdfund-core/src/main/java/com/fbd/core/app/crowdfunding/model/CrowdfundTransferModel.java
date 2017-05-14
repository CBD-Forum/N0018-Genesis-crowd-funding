package com.fbd.core.app.crowdfunding.model;

import java.math.BigDecimal;
import java.util.Date;
import com.fbd.core.base.BaseModel;

public class CrowdfundTransferModel extends BaseModel{
    private String id;

    private String transferNo;

    private String orderNo;

    private Date applyTime;

    private Date deadline;

    private Double transferMoney;

    private Double corpus;

    private Double interest;

    private Double transferRate;

    private Double paidCorpus;

    private Double paidInterest;

    private String status;

    private String userId;

    private BigDecimal paidPenalty;

    private Integer transferDay;

    private Double transferFee;

    private Integer totalParts;

    private Double perAmt;

    private Integer transferParts;

    private Integer transferAmt;

    private Double oldPartsMoney;

    private Double partMoney;

    private String transferType;

    private Double actualTransferMoney;

    private Double transferCorpus;

    private Date transferTime;
    
    private String auditState;
    
    private Date auditTime;
    
    private String auditStateAll;
    
    private String transferStateAll;
    
    private String loanNo;
    
    private String loanName;
    
    private Date startTime;
    
    private String isAgree;
    
    private String auditOpinion;
    
    private String isAgreeNull;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Double getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(Double transferMoney) {
        this.transferMoney = transferMoney;
    }

    public Double getCorpus() {
        return corpus;
    }

    public void setCorpus(Double corpus) {
        this.corpus = corpus;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(Double transferRate) {
        this.transferRate = transferRate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public BigDecimal getPaidPenalty() {
        return paidPenalty;
    }

    public void setPaidPenalty(BigDecimal paidPenalty) {
        this.paidPenalty = paidPenalty;
    }


    public Double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(Double transferFee) {
        this.transferFee = transferFee;
    }

    public Integer getTotalParts() {
        return totalParts;
    }

    public void setTotalParts(Integer totalParts) {
        this.totalParts = totalParts;
    }

    public Double getPerAmt() {
        return perAmt;
    }

    public void setPerAmt(Double perAmt) {
        this.perAmt = perAmt;
    }

    public Integer getTransferParts() {
        return transferParts;
    }

    public void setTransferParts(Integer transferParts) {
        this.transferParts = transferParts;
    }

    public Integer getTransferAmt() {
        return transferAmt;
    }

    public void setTransferAmt(Integer transferAmt) {
        this.transferAmt = transferAmt;
    }

    public Double getOldPartsMoney() {
        return oldPartsMoney;
    }

    public void setOldPartsMoney(Double oldPartsMoney) {
        this.oldPartsMoney = oldPartsMoney;
    }

    public Double getPartMoney() {
        return partMoney;
    }

    public void setPartMoney(Double partMoney) {
        this.partMoney = partMoney;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType == null ? null : transferType.trim();
    }

    public Double getActualTransferMoney() {
        return actualTransferMoney;
    }

    public void setActualTransferMoney(Double actualTransferMoney) {
        this.actualTransferMoney = actualTransferMoney;
    }

    public Double getTransferCorpus() {
        return transferCorpus;
    }

    public void setTransferCorpus(Double transferCorpus) {
        this.transferCorpus = transferCorpus;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Integer getTransferDay() {
        return transferDay;
    }

    public void setTransferDay(Integer transferDay) {
        this.transferDay = transferDay;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public String getAuditStateAll() {
        return auditStateAll;
    }

    public void setAuditStateAll(String auditStateAll) {
        this.auditStateAll = auditStateAll;
    }

    public String getTransferStateAll() {
        return transferStateAll;
    }

    public void setTransferStateAll(String transferStateAll) {
        this.transferStateAll = transferStateAll;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(String isAgree) {
        this.isAgree = isAgree;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getIsAgreeNull() {
        return isAgreeNull;
    }

    public void setIsAgreeNull(String isAgreeNull) {
        this.isAgreeNull = isAgreeNull;
    }
    
}