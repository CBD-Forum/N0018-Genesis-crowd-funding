package com.fbd.core.app.node.model;

import com.fbd.core.base.BaseModel;

public class NodeTypeModel extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 2811594879249713343L;

    private String id;

    private String code;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}