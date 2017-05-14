package com.fbd.admin.app.seo.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.seo.service.ISeoTdkService;
import com.fbd.core.app.seo.dao.ISeoTdkDao;
import com.fbd.core.app.seo.model.SeoTdkModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Service
public class SeoTdkServiceImpl implements ISeoTdkService {
    
    @Resource
    private ISeoTdkDao seoTdkDao;

    public SearchResult<SeoTdkModel> getSeoTdkPage(SeoTdkModel seoTdk) {
        return seoTdkDao.getSeoTdkPage(seoTdk);
    }

    public int saveSeoTdk(SeoTdkModel seoTdk) {
        seoTdk.setId(PKGenarator.getId());
        return seoTdkDao.saveSeoTdk(seoTdk);
    }

    public SeoTdkModel getTDKBycode(String code) {
        return seoTdkDao.getTDKBycode(code);
    }

    public SeoTdkModel getSeoTdkById(String id) {
        return seoTdkDao.getSeoTdkById(id);
    }

    public int modifySeoTdk(SeoTdkModel seoTdk) {
        return seoTdkDao.modifySeoTdk(seoTdk);
    }

    public int removeSeoTdk(String id) {
        return seoTdkDao.removeSeoTdk(id);
    }

}
