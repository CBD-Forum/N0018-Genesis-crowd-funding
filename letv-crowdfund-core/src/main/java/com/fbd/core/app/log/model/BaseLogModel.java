package com.fbd.core.app.log.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class BaseLogModel extends BaseModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1478783422807876390L;

    private String id;

    private String userId;

    private Date loginTime;

    private String ipAddress;

    private String loginResult;

    private String resInfo;

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

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult == null ? null : loginResult.trim();
    }

    public String getResInfo() {
        return resInfo;
    }

    public void setResInfo(String resInfo) {
        this.resInfo = resInfo == null ? null : resInfo.trim();
    }
}
