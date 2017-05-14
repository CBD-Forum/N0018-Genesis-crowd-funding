package com.fbd.core.app.withdraw.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class SystemWithdrawModel extends BaseModel {
    private String id;

    private String orderId;

    private String applyPerson;

    private Date applyTime;

    private String fianacialAuditor;

    private Date fianacialAuditTime;
    private Date startApplyTime;
    private Date endApplyTime;
    private String opionion;
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

    private String thirdWtihDrawType;
    /**
     * @return the opionion
     */
    public String getOpionion() {
        return opionion;
    }

    /**
     * @param opionion the opionion to set
     */
    public void setOpionion(String opionion) {
        this.opionion = opionion;
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

    private Double amt;

    private Double fee;

    private String state;

    private String bankCardId;

    private String ower;

    private String bankName;
    
    private Date completeTime;
    

    /**
     * @return the completeTime
     */
    public Date getCompleteTime() {
        return completeTime;
    }

    /**
     * @param completeTime the completeTime to set
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

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

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson == null ? null : applyPerson.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getFianacialAuditor() {
        return fianacialAuditor;
    }

    public void setFianacialAuditor(String fianacialAuditor) {
        this.fianacialAuditor = fianacialAuditor == null ? null : fianacialAuditor.trim();
    }

    public Date getFianacialAuditTime() {
        return fianacialAuditTime;
    }

    public void setFianacialAuditTime(Date fianacialAuditTime) {
        this.fianacialAuditTime = fianacialAuditTime;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId == null ? null : bankCardId.trim();
    }

    public String getOwer() {
        return ower;
    }

    public void setOwer(String ower) {
        this.ower = ower == null ? null : ower.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }
}