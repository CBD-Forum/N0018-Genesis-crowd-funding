package com.fbd.core.app.todo.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class TodoModel extends BaseModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1156705026459496728L;

    private String id;

    private String event;

    private String eventObj;

    private String detail;

    private String link;

    private String post;
    
    private String[] posts;

    private Date createTime;

    private Date manageTime;

    private String manager;
    
    private String manageResult;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }

    public String getEventObj() {
        return eventObj;
    }

    public void setEventObj(String eventObj) {
        this.eventObj = eventObj == null ? null : eventObj.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }
    
    public String[] getPosts() {
        return posts;
    }

    public void setPosts(String[] posts) {
        this.posts = posts;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getManageTime() {
        return manageTime;
    }

    public void setManageTime(Date manageTime) {
        this.manageTime = manageTime;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getManageResult() {
        return manageResult;
    }

    public void setManageResult(String manageResult) {
        this.manageResult = manageResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}