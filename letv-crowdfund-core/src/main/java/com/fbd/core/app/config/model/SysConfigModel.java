package com.fbd.core.app.config.model;

import com.fbd.core.base.BaseModel;

public class SysConfigModel extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -6026452094547646732L;

    private String id;

    private String displayName;

    private String code;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
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