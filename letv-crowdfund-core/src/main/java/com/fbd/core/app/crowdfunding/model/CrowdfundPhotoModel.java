package com.fbd.core.app.crowdfunding.model;

import com.fbd.core.base.BaseModel;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹图片
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class CrowdfundPhotoModel extends BaseModel{
    private String id;

    private String loanNo;

    private String photoUrl;

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }
}