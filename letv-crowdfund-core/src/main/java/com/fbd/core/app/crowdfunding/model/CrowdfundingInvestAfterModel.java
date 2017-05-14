package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import java.util.List;
import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.base.BaseModel;

public class CrowdfundingInvestAfterModel extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String loanNo;

    private String content;

    private String picUrl;

    private String dateNode;

    private String state;

    private String auditor;

    private Date auditTime;

    private Date createTime;

    private Date updateTime;

    
    private String loanName ;
    
    
    private List<FileUploadModel> fileList;
    
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getDateNode() {
        return dateNode;
    }

    public void setDateNode(String dateNode) {
        this.dateNode = dateNode == null ? null : dateNode.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
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

    /**
     * @return the loanName
     */
    public String getLoanName() {
        return loanName;
    }

    /**
     * @param loanName the loanName to set
     */
    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    /**
     * @return the fileList
     */
    public List<FileUploadModel> getFileList() {
        return fileList;
    }

    /**
     * @param fileList the fileList to set
     */
    public void setFileList(List<FileUploadModel> fileList) {
        this.fileList = fileList;
    }
    
    
}