package com.fbd.core.app.crowdfunding.model;

/**
 * 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 转让用户记录扩展
 *
 * @author hanchenghe
 * @version 1.0
 *
 */
public class CrowdfundTransferExtendModel {
    private String id;
    /**
     * 转让编号
     */
    private String transferNo;

    private String userId;
    /**
     * 地址
     */
    private String postAddressNo;
    /**
     * 内容
     */
    private String supportContent;
    /**
     * 备注
     */
    private String supportRemark;
    /**
     * 是否使用
     * y:使用;其他非使用;
     */
    private String isUse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTransferNo() {
        return transferNo;
    }

    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo == null ? null : transferNo.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPostAddressNo() {
        return postAddressNo;
    }

    public void setPostAddressNo(String postAddressNo) {
        this.postAddressNo = postAddressNo == null ? null : postAddressNo.trim();
    }

    public String getSupportContent() {
        return supportContent;
    }

    public void setSupportContent(String supportContent) {
        this.supportContent = supportContent == null ? null : supportContent.trim();
    }

    public String getSupportRemark() {
        return supportRemark;
    }

    public void setSupportRemark(String supportRemark) {
        this.supportRemark = supportRemark == null ? null : supportRemark.trim();
    }

    /**
     * @return the isUse
     */
    public String getIsUse() {
        return isUse;
    }

    /**
     * @param isUse the isUse to set
     */
    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }
}