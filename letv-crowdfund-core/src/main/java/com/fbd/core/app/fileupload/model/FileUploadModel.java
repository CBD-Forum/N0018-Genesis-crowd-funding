package com.fbd.core.app.fileupload.model;

import java.util.Date;

public class FileUploadModel {
    private String id;

    private String fileName;

    private String fileSize;

    private String fileExtension;

    private String fileUrl;

    private String parentId;
    
    private String type;

    private Date createTime;

    private Date updateTime;
    
    private String tempParentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize == null ? null : fileSize.trim();
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension == null ? null : fileExtension.trim();
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
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
    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the tempParentId
     */
    public String getTempParentId() {
        return tempParentId;
    }
    /**
     * @param tempParentId the tempParentId to set
     */
    public void setTempParentId(String tempParentId) {
        this.tempParentId = tempParentId;
    }

  
}