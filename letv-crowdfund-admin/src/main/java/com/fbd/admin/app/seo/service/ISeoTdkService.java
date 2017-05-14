package com.fbd.admin.app.seo.service;

import org.springframework.security.access.prepost.PreAuthorize;
import com.fbd.core.app.seo.model.SeoTdkModel;
import com.fbd.core.common.model.SearchResult;

public interface ISeoTdkService {

    SearchResult<SeoTdkModel> getSeoTdkPage(SeoTdkModel seoTdk);

    @PreAuthorize("hasPermission(null, 'security.operation.spread_tdk_add')")
    int saveSeoTdk(SeoTdkModel seoTdk);
    
    SeoTdkModel getTDKBycode(String code);

    SeoTdkModel getSeoTdkById(String id);

    @PreAuthorize("hasPermission(null, 'security.operation.spread_tdk_modify')")
    int modifySeoTdk(SeoTdkModel seoTdk);

    @PreAuthorize("hasPermission(null, 'security.operation.spread_tdk_delete')")
    int removeSeoTdk(String id);

}
