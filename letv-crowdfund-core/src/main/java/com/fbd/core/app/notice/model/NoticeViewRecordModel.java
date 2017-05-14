package com.fbd.core.app.notice.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class NoticeViewRecordModel extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String noticeType;

    private String parentId;

    private Date latestNoticeTime;

    private Date latestClickTime;
    
    private String state;

    private Date createTime;

    private Date updateTime;

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

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType == null ? null : noticeType.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Date getLatestNoticeTime() {
        return latestNoticeTime;
    }

    public void setLatestNoticeTime(Date latestNoticeTime) {
        this.latestNoticeTime = latestNoticeTime;
    }

    public Date getLatestClickTime() {
        return latestClickTime;
    }

    public void setLatestClickTime(Date latestClickTime) {
        this.latestClickTime = latestClickTime;
    }
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
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
}