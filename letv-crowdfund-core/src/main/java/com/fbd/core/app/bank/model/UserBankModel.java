package com.fbd.core.app.bank.model;

import java.util.Date;

public class UserBankModel {
    private String id;

    private String userId;

    private String name;//开户支行名称

    private String bankNo;//银行编号

    private String bankAccount;//银行卡号

    private String bank;//银行名称

    private String ownerName;//持卡人姓名

    private String openAcctBank;

    private String bankType;

    private String bankCity;

    private String bankProvince;
    
    private String bankCityName;
    
    private String bankProvinceName;

    private Date createTime;

    private String state;
    
    private String thirndBankId;//第三方返回银行卡ID
    
    private String bankShortName;
    
    private String thirdBindType;//第三方类型
    
    private Date updateTime;  //更新时间 
    
    
    private String bankName;  //银行名称

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo == null ? null : bankNo.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getOpenAcctBank() {
        return openAcctBank;
    }

    public void setOpenAcctBank(String openAcctBank) {
        this.openAcctBank = openAcctBank == null ? null : openAcctBank.trim();
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType == null ? null : bankType.trim();
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity == null ? null : bankCity.trim();
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince == null ? null : bankProvince.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return the thirndBankId
     */
    public String getThirndBankId() {
        return thirndBankId;
    }

    /**
     * @param thirndBankId the thirndBankId to set
     */
    public void setThirndBankId(String thirndBankId) {
        this.thirndBankId = thirndBankId;
    }

    /**
     * @return the bankCityName
     */
    public String getBankCityName() {
        return bankCityName;
    }

    /**
     * @param bankCityName the bankCityName to set
     */
    public void setBankCityName(String bankCityName) {
        this.bankCityName = bankCityName;
    }

    /**
     * @return the bankProvinceName
     */
    public String getBankProvinceName() {
        return bankProvinceName;
    }

    /**
     * @param bankProvinceName the bankProvinceName to set
     */
    public void setBankProvinceName(String bankProvinceName) {
        this.bankProvinceName = bankProvinceName;
    }

    /**
     * @return the bankShortName
     */
    public String getBankShortName() {
        return bankShortName;
    }

    /**
     * @param bankShortName the bankShortName to set
     */
    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    /**
     * @return the thirdBindType
     */
    public String getThirdBindType() {
        return thirdBindType;
    }

    /**
     * @param thirdBindType the thirdBindType to set
     */
    public void setThirdBindType(String thirdBindType) {
        this.thirdBindType = thirdBindType;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the backName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param backName the backName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
}