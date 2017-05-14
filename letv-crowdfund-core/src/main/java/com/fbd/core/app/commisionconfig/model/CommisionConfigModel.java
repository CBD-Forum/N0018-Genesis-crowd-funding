package com.fbd.core.app.commisionconfig.model;

import com.fbd.core.base.BaseModel;

public class CommisionConfigModel extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2966614025836104506L;

	private String id;

    private String type;

    private Double minMoney;

    private Double maxMoney;

    private Double commisionRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Double minMoney) {
        this.minMoney = minMoney;
    }

    public Double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Double getCommisionRate() {
        return commisionRate;
    }

    public void setCommisionRate(Double commisionRate) {
        this.commisionRate = commisionRate;
    }
}