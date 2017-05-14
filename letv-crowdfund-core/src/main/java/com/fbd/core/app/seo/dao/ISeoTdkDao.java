package com.fbd.core.app.seo.dao;

import com.fbd.core.app.seo.model.SeoTdkModel;
import com.fbd.core.common.model.SearchResult;

public interface ISeoTdkDao {

    SearchResult<SeoTdkModel> getSeoTdkPage(SeoTdkModel seoTdk);

    int saveSeoTdk(SeoTdkModel seoTdk);

    SeoTdkModel getTDKBycode(String code);

    SeoTdkModel getSeoTdkById(String id);

    int modifySeoTdk(SeoTdkModel seoTdk);

    int removeSeoTdk(String id);
   
}