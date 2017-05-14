package com.fbd.core.app.user.model;

import java.util.Date;

public class UserSignatureOrgModel {
    private String id;

    private String userId;

    private String userType;

    private String identifyField;

    private String orgName;

    private String usci;

    private String orgCode;

    private String businessNum;

    private String signUserId;

    private String legalPersonName;

    private String transactorName;

    private String transactoridCardNum;

    private String transactorMobile;

    private Date createTime;

    private Date updateTime;

    private String clientSignCert;
    
    private String signImg;
    
    private String signImgState;

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
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getIdentifyField() {
        return identifyField;
    }

    public void setIdentifyField(String identifyField) {
        this.identifyField = identifyField == null ? null : identifyField.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getUsci() {
        return usci;
    }

    public void setUsci(String usci) {
        this.usci = usci == null ? null : usci.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(String businessNum) {
        this.businessNum = businessNum == null ? null : businessNum.trim();
    }
    
    public String getSignUserId() {
        return signUserId;
    }
    public void setSignUserId(String signUserId) {
        this.signUserId = signUserId;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName == null ? null : legalPersonName.trim();
    }

    public String getTransactorName() {
        return transactorName;
    }

    public void setTransactorName(String transactorName) {
        this.transactorName = transactorName == null ? null : transactorName.trim();
    }

    public String getTransactoridCardNum() {
        return transactoridCardNum;
    }

    public void setTransactoridCardNum(String transactoridCardNum) {
        this.transactoridCardNum = transactoridCardNum == null ? null : transactoridCardNum.trim();
    }

    public String getTransactorMobile() {
        return transactorMobile;
    }

    public void setTransactorMobile(String transactorMobile) {
        this.transactorMobile = transactorMobile == null ? null : transactorMobile.trim();
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

    public String getClientSignCert() {
        return clientSignCert;
    }

    /**
     * @return the signImg
     */
    public String getSignImg() {
        return signImg;
    }

    /**
     * @param signImg the signImg to set
     */
    public void setSignImg(String signImg) {
        this.signImg = signImg;
    }

    /**
     * @return the signImgState
     */
    public String getSignImgState() {
        return signImgState;
    }

    /**
     * @param signImgState the signImgState to set
     */
    public void setSignImgState(String signImgState) {
        this.signImgState = signImgState;
    }

    public void setClientSignCert(String clientSignCert) {
        this.clientSignCert = clientSignCert == null ? null : clientSignCert.trim();
    }
    
    
}