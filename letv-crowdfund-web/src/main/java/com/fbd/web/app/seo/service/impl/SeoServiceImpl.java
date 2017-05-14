package com.fbd.web.app.seo.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.seo.dao.ISeoTdkDao;
import com.fbd.core.app.seo.model.SeoTdkModel;
import com.fbd.web.app.seo.service.ISeoService;

@Service
public class SeoServiceImpl implements ISeoService {
    
    @Resource
    private ISeoTdkDao seoTdkDao;

    /**
     * Description: 根据code查询TDK的值
     *
     * @param 
     * @return SeoTdkModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-26 下午12:14:45
     */
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
