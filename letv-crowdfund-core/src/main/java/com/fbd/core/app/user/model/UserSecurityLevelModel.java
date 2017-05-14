package com.fbd.core.app.user.model;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户安全级别
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class UserSecurityLevelModel {
    private String id;

    private Integer securityLevel;

    private String emailState;

    private String mobileState;
    
    private String userId;
    
    private String userType;
    
    private String thirdAccount;
    
    private String thirdAccountState;
    
    private String realName;
    
    private String certificateNo;
    
    private String certificateNoState;
    
    private String homeAddress;
    
    private String homeAddressState;
    
    private String emergencyContact;
    
    private String emergencyState;
    
    private String emergencyPhone;
    
    private String emergencyRelation;
    
    private String postCode;
    
    private String email;
    
    private String mobile;
    
    private String levelDes;
    
    private String mobileDes;
    
    private String emailDes;
    
    private int currentHour;//当前小时
    
    //提现密码认证
    private String cashPwdState;
    
    private String isAuth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(Integer securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getEmailState() {
        return emailState;
    }

    public void setEmailState(String emailState) {
        this.emailState = emailState == null ? null : emailState.trim();
    }

    public String getMobileState() {
        return mobileState;
    }

    public void setMobileState(String mobileState) {
        this.mobileState = mobileState == null ? null : mobileState.trim();
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
        this.userType = userType;
    }

    public String getThirdAccount() {
        return thirdAccount;
    }

    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getEmergencyContact() {
    	String result = "";
    	String name = emergencyContact;
    	if(StringUtils.isEmpty(name)){
    		result = name;
    	} else if(name.length() == 1) {
    		result = "*";
    	} else if(name.length() == 2){
    		result = name.substring(0, 1) + "*";
    	} else if(name.length() == 3){
    		result = name.substring(0, 1) + "*" + name.substring(name.length() - 1);
    	} else {
    		String s = "";
    		for (int i = 0; i < name.length() - 2; i++) {
				s += "*";
			}
    		result = name.substring(0, 1) + s + name.substring(name.length() - 1);
    	}
        return result;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getEmergencyRelation() {
        return emergencyRelation;
    }

    public void setEmergencyRelation(String emergencyRelation) {
        this.emergencyRelation = emergencyRelation;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getThirdAccountState() {
        return thirdAccountState;
    }

    public void setThirdAccountState(String thirdAccountState) {
        this.thirdAccountState = thirdAccountState;
    }

    public String getCertificateNoState() {
        return certificateNoState;
    }

    public void setCertificateNoState(String certificateNoState) {
        this.certificateNoState = certificateNoState;
    }

    public String getHomeAddressState() {
        return homeAddressState;
    }

    public void setHomeAddressState(String homeAddressState) {
        this.homeAddressState = homeAddressState;
    }

    public String getEmergencyState() {
        return emergencyState;
    }

    public void setEmergencyState(String emergencyState) {
        this.emergencyState = emergencyState;
    }

    public String getLevelDes() {
        return levelDes;
    }

    public void setLevelDes(String levelDes) {
        this.levelDes = levelDes;
    }

    public String getMobileDes() {
        return mobileDes;
    }

    public void setMobileDes(String mobileDes) {
        this.mobileDes = mobileDes;
    }

    public String getEmailDes() {
        return emailDes;
    }

    public void setEmailDes(String emailDes) {
        this.emailDes = emailDes;
    }

    public int getCurrentHour() {
        return currentHour;
    }

    public void setCurrentHour(int currentHour) {
        this.currentHour = currentHour;
    }

    public String getCashPwdState() {
        return cashPwdState;
    }

    public void setCashPwdState(String cashPwdState) {
        this.cashPwdState = cashPwdState;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }
}