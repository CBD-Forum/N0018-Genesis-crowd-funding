package com.fbd.core.app.user.model;

import java.util.Date;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public class UserSignaturePersonModel {
    
    private String id;

    private String userId;

    private String userType;

    private String identifyField;

    private String personalName;

    private String idCardNum;

    private String passportNum;

    private String mtpNum;

    private String signUserId;

    private String mobile;

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

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName == null ? null : personalName.trim();
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum == null ? null : idCardNum.trim();
    }

    public String getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum == null ? null : passportNum.trim();
    }

    public String getMtpNum() {
        return mtpNum;
    }

    public void setMtpNum(String mtpNum) {
        this.mtpNum = mtpNum == null ? null : mtpNum.trim();
    }

 
    public String getSignUserId() {
        return signUserId;
    }
    public void setSignUserId(String signUserId) {
        this.signUserId = signUserId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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

    public void setClientSignCert(String clientSignCert) {
        this.clientSignCert = clientSignCert == null ? null : clientSignCert.trim();
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
    
}