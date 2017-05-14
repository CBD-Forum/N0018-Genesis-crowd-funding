package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class CrowdfundingOperateDataModel extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String loanNo;

    private Integer registerNumByDay;

    private Integer userNumByDay;

    private Double retentionByNextDay;

    private Integer registerNumByMonth;

    private Integer userNumByMonth;

    private Double retentionByNextMonth;

    private Integer payUserNum;

    private Date createTime;

    private Date updateTime;
    
    private String expectedReturn;
    
    private String tempLoanNo;

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

    /**
     * @return the registerNumByDay
     */
    public Integer getRegisterNumByDay() {
        return registerNumByDay;
    }

    /**
     * @param registerNumByDay the registerNumByDay to set
     */
    public void setRegisterNumByDay(Integer registerNumByDay) {
        this.registerNumByDay = registerNumByDay;
    }

    /**
     * @return the userNumByDay
     */
    public Integer getUserNumByDay() {
        return userNumByDay;
    }

    /**
     * @param userNumByDay the userNumByDay to set
     */
    public void setUserNumByDay(Integer userNumByDay) {
        this.userNumByDay = userNumByDay;
    }

    /**
     * @return the retentionByNextDay
     */
    public Double getRetentionByNextDay() {
        return retentionByNextDay;
    }

    /**
     * @param retentionByNextDay the retentionByNextDay to set
     */
    public void setRetentionByNextDay(Double retentionByNextDay) {
        this.retentionByNextDay = retentionByNextDay;
    }

    /**
     * @return the registerNumByMonth
     */
    public Integer getRegisterNumByMonth() {
        return registerNumByMonth;
    }

    /**
     * @param registerNumByMonth the registerNumByMonth to set
     */
    public void setRegisterNumByMonth(Integer registerNumByMonth) {
        this.registerNumByMonth = registerNumByMonth;
    }

    /**
     * @return the userNumByMonth
     */
    public Integer getUserNumByMonth() {
        return userNumByMonth;
    }

    /**
     * @param userNumByMonth the userNumByMonth to set
     */
    public void setUserNumByMonth(Integer userNumByMonth) {
        this.userNumByMonth = userNumByMonth;
    }

    /**
     * @return the retentionByNextMonth
     */
    public Double getRetentionByNextMonth() {
        return retentionByNextMonth;
    }

    /**
     * @param retentionByNextMonth the retentionByNextMonth to set
     */
    public void setRetentionByNextMonth(Double retentionByNextMonth) {
        this.retentionByNextMonth = retentionByNextMonth;
    }

    /**
     * @return the payUserNum
     */
    public Integer getPayUserNum() {
        return payUserNum;
    }

    /**
     * @param payUserNum the payUserNum to set
     */
    public void setPayUserNum(Integer payUserNum) {
        this.payUserNum = payUserNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the expectedReturn
     */
    public String getExpectedReturn() {
        return expectedReturn;
    }

    /**
     * @param expectedReturn the expectedReturn to set
     */
    public void setExpectedReturn(String expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    /**
     * @return the tempLoanNo
     */
    public String getTempLoanNo() {
        return tempLoanNo;
    }

    /**
     * @param tempLoanNo the tempLoanNo to set
     */
    public void setTempLoanNo(String tempLoanNo) {
        this.tempLoanNo = tempLoanNo;
    }
    
}