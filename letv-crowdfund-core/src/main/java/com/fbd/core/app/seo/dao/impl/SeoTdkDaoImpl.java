package com.fbd.core.app.seo.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.seo.dao.ISeoTdkDao;
import com.fbd.core.app.seo.model.SeoTdkModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

@Repository
public class SeoTdkDaoImpl extends BaseDaoImpl<SeoTdkModel> implements ISeoTdkDao {

    public SearchResult<SeoTdkModel> getSeoTdkPage(SeoTdkModel seoTdk) {
        SearchResult<SeoTdkModel> searchResult = new SearchResult<SeoTdkModel>();
        searchResult.setTotal(getSeoTdkCount(seoTdk));
        searchResult.setRows(getSeoTdkList(seoTdk));
        return searchResult;
    }
    
    public List<SeoTdkModel> getSeoTdkList(SeoTdkModel seoTdk){
        return this.selectByField("select", seoTdk);
    }
    
    public long getSeoTdkCount(SeoTdkModel seoTdk){
        return this.getCount(seoTdk);
    }

    public int saveSeoTdk(SeoTdkModel seoTdk) {
        return this.save(seoTdk);
    }

    public SeoTdkModel getTDKBycode(String code) {
        return this.selectOneByField("selectByCode", code);
    }

    public SeoTdkModel getSeoTdkById(String id) {
        return this.selectByPrimaryKey(id);
    }

    public int modifySeoTdk(SeoTdkModel seoTdk) {
        return this.update(seoTdk);
    }

    public int removeSeoTdk(String id) {
        return this.deleteByPrimaryKey(id);
    }

}
