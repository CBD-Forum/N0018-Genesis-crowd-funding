package com.fbd.core.app.reconciliation.model;

import java.util.Date;

public class ReconciliationRecordModel {
    private String id;

    private String status;

    private Date time;

    private String type;
    
    public ReconciliationRecordModel(){
        super();
    }

    public ReconciliationRecordModel(String id, String status, Date time,String type) {
        super();
        this.id = id;
        this.status = status;
        this.time = time;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}