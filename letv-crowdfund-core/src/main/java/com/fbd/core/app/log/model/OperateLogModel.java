package com.fbd.core.app.log.model;

import java.util.Date;


public class OperateLogModel {

    private String id;
    //用户名
    private String userId;
    
    //用户类型
    private String userType;
    
    //操作时间
    private Date operateTime;
    
    //ip地址
    private String ipAddress;
    
    //操作类型
    private String operateType;
    
    //操作模块
    private String operateModel;
    
    //操作结果
    private String operateResult;
    
    //异常信息
    private String resInfo;
    
    public OperateLogModel(){
        super();
    }

    public OperateLogModel(String id, String userId, String userType,
            Date operateTime, String ipAddress, String operateType,
            String operateModel, String operateResult, String resInfo) {
        super();
        this.id = id;
        this.userId = userId;
        this.userType = userType;
        this.operateTime = operateTime;
        this.ipAddress = ipAddress;
        this.operateType = operateType;
        this.operateModel = operateModel;
        this.operateResult = operateResult;
        this.resInfo = resInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(String operateModel) {
        this.operateModel = operateModel;
    }

    public String getOperateResult() {
        return operateResult;
    }

    public void setOperateResult(String operateResult) {
        this.operateResult = operateResult;
    }

    public String getResInfo() {
        return resInfo;
    }

    public void setResInfo(String resInfo) {
        this.resInfo = resInfo;
    }
    
}