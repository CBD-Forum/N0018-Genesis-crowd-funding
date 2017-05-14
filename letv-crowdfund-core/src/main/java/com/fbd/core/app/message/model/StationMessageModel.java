package com.fbd.core.app.message.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class StationMessageModel extends BaseModel{
    private String id;

    private String userId;

    private String messageType;

    private String messageChildType;

    private String content;

    private Date sendTime;

    private Date readTime;

    private String status;
    
    private String messageChildTypeName;

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

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }

    public String getMessageChildType() {
        return messageChildType;
    }

    public void setMessageChildType(String messageChildType) {
        this.messageChildType = messageChildType == null ? null : messageChildType.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMessageChildTypeName() {
        return messageChildTypeName;
    }

    public void setMessageChildTypeName(String messageChildTypeName) {
        this.messageChildTypeName = messageChildTypeName;
    }
    
    
}