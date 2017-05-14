package com.fbd.core.app.banner.model;

import com.fbd.core.base.BaseModel;

public class BannerPictureModel extends BaseModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2989749584954765816L;

    private String id;

    private String bannerId;

    private String title;

    private String picture;

    private Boolean isOutSite;

    private String url;

    private Integer seqNum;
    
    private String seqNumStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId == null ? null : bannerId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public Boolean getIsOutSite() {
        return isOutSite;
    }

    public void setIsOutSite(Boolean isOutSite) {
        this.isOutSite = isOutSite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public String getSeqNumStr() {
        return seqNumStr;
    }

    public void setSeqNumStr(String seqNumStr) {
        this.seqNumStr = seqNumStr;
    }
}