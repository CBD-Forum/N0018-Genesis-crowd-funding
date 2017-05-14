package com.fbd.core.app.banner.model;

import com.fbd.core.base.BaseModel;

public class BannerModel extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 6263832954839449060L;

    private String id;
    
    private String code;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}