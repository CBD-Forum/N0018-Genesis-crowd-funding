package com.fbd.core.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author dongzhognwei
 * @version 1.0
 * 
 */
@JsonIgnoreProperties(value = { "page", "rows", "sort", "order", "startPage",
        "endPage", "paramMap" })
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BaseModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8817364607805079502L;

    // 开始页码
    protected int page = 1;
    // 每页显示的条数
    protected int rows;
    // 排序字段
    protected String sort;
    // 正序:"asc" 倒序："desc"
    protected String order;

    protected long startPage;

    protected long endPage;

    protected String defaultSort;// 默认的排序

    protected Map<String, Object> paramMap = new HashMap<String, Object>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page==0) {
            this.page = 1;
        }else{
            this.page = page;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public long getStartPage() {
        return (this.page - 1) * rows;
    }

    public void setStartPage(long startPage) {
        this.startPage = startPage;
    }

    public long getEndPage() {
        return this.rows;
    }

    public void setEndPage(long endPage) {
        this.endPage = endPage;
    }

    public Map<String, Object> getParamMap() {
        this.paramMap.put("sort", this.sort);
        this.paramMap.put("order", this.order);
        this.paramMap.put("startPage", this.getStartPage());
        this.paramMap.put("endPage", this.getEndPage());
        return this.paramMap;
    }

    public String getDefaultSort() {
        return defaultSort;
    }

    public void setDefaultSort(String defaultSort) {
        this.defaultSort = defaultSort;
    }

}