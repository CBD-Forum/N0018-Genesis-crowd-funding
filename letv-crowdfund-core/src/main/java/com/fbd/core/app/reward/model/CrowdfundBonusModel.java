package com.fbd.core.app.reward.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class CrowdfundBonusModel extends BaseModel{
    private String id;

    private String loanNo;

    private Date bonusTime;

    private String bonusState;

    private Integer bonusNum;

    private String bonusAuditState;

    private Double bonusAmt;

    private String bonusAuditUser;

    private Date bonusAuditTime;
    
    private String realName;
    
    private String loanName;

    private String getBonusState;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }

    public Date getBonusTime() {
        return bonusTime;
    }

    public void setBonusTime(Date bonusTime) {
        this.bonusTime = bonusTime;
    }

    public String getBonusState() {
        return bonusState;
    }

    public void setBonusState(String bonusState) {
        this.bonusState = bonusState == null ? null : bonusState.trim();
    }

    public Integer getBonusNum() {
        return bonusNum;
    }

    public void setBonusNum(Integer bonusNum) {
        this.bonusNum = bonusNum;
    }

    public String getBonusAuditState() {
        return bonusAuditState;
    }

    public void setBonusAuditState(String bonusAuditState) {
        this.bonusAuditState = bonusAuditState == null ? null : bonusAuditState.trim();
    }

    public Double getBonusAmt() {
        return bonusAmt;
    }

    public void setBonusAmt(Double bonusAmt) {
        this.bonusAmt = bonusAmt;
    }

    public String getBonusAuditUser() {
        return bonusAuditUser;
    }

    public void setBonusAuditUser(String bonusAuditUser) {
        this.bonusAuditUser = bonusAuditUser == null ? null : bonusAuditUser.trim();
    }

    public Date getBonusAuditTime() {
        return bonusAuditTime;
    }

    public void setBonusAuditTime(Date bonusAuditTime) {
        this.bonusAuditTime = bonusAuditTime;
    }

    /**
     * @return the realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName the realName to set
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return the loanName
     */
    public String getLoanName() {
        return loanName;
    }

    /**
     * @param loanName the loanName to set
     */
    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    /**
     * @return the getBonusState
     */
    public String getGetBonusState() {
        return getBonusState;
    }

    /**
     * @param getBonusState the getBonusState to set
     */
    public void setGetBonusState(String getBonusState) {
        this.getBonusState = getBonusState;
    }
    
    
}