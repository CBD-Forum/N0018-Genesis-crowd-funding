package com.fbd.core.app.user.model;

import java.util.Date;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户关注关系表
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class UserAttentionModel {
    private String id;

    private String userId;

    private String beAttentionUser;

    private Date attentionTime;

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

    public String getBeAttentionUser() {
        return beAttentionUser;
    }

    public void setBeAttentionUser(String beAttentionUser) {
        this.beAttentionUser = beAttentionUser == null ? null : beAttentionUser.trim();
    }

    public Date getAttentionTime() {
        return attentionTime;
    }

    public void setAttentionTime(Date attentionTime) {
        this.attentionTime = attentionTime;
    }
}