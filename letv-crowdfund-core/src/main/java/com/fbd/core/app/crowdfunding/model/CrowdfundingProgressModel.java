package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import java.util.List;
import com.fbd.core.base.BaseModel;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:众筹进展 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class CrowdfundingProgressModel extends BaseModel{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String loanNo;
    
    private String enterUser;

    private Date enterTime;

    private String state;

    private String auditor;

    private Date auditTime;

    private String enterContent;
    
    private String enterNode;
    
    private String imgFiles;
    
    private String vedioFiles;
    
    private Date timeNode;
    
    private List<CrowdfundingProgressFilesModel> imgFileList;
    
    private List<CrowdfundingProgressFilesModel> vedioFileList;
    

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

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
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

    public String getEnterContent() {
        return enterContent;
    }

    public void setEnterContent(String enterContent) {
        this.enterContent = enterContent == null ? null : enterContent.trim();
    }

    public String getEnterUser() {
        return enterUser;
    }

    public void setEnterUser(String enterUser) {
        this.enterUser = enterUser;
    }

    public String getEnterNode() {
        return enterNode;
    }

    public void setEnterNode(String enterNode) {
        this.enterNode = enterNode;
    }

    public String getImgFiles() {
        return imgFiles;
    }

    public void setImgFiles(String imgFiles) {
        this.imgFiles = imgFiles;
    }

    public String getVedioFiles() {
        return vedioFiles;
    }

    public void setVedioFiles(String vedioFiles) {
        this.vedioFiles = vedioFiles;
    }

    public Date getTimeNode() {
        return timeNode;
    }

    public void setTimeNode(Date timeNode) {
        this.timeNode = timeNode;
    }

    public List<CrowdfundingProgressFilesModel> getImgFileList() {
        return imgFileList;
    }

    public void setImgFileList(List<CrowdfundingProgressFilesModel> imgFileList) {
        this.imgFileList = imgFileList;
    }

    public List<CrowdfundingProgressFilesModel> getVedioFileList() {
        return vedioFileList;
    }

    public void setVedioFileList(List<CrowdfundingProgressFilesModel> vedioFileList) {
        this.vedioFileList = vedioFileList;
    }
    
    
    
    
}