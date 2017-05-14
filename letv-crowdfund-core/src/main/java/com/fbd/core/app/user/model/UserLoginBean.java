package com.fbd.core.app.user.model;

import java.io.Serializable;

public class UserLoginBean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String lastLoginTime;
    
    private String userId;
    
    private String realName;
    
    private String userphoto;
    
    private String isAuth;
    
    private String isSealed;
    
    private String thirdNo;

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserphoto() {
		return userphoto;
	}

	public void setUserphoto(String userphoto) {
		this.userphoto = userphoto;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public String getIsSealed() {
		return isSealed;
	}

	public void setIsSealed(String isSealed) {
		this.isSealed = isSealed;
	}

	public String getThirdNo() {
		return thirdNo;
	}

	public void setThirdNo(String thirdNo) {
		this.thirdNo = thirdNo;
	}
	
}
