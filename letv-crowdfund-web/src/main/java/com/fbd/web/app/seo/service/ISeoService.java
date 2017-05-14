package com.fbd.web.app.seo.service;

import com.fbd.core.app.seo.model.SeoTdkModel;

public interface ISeoService {
    
    /**
     * Description: 根据code查询TDK的值
     *
     * @param 
     * @return SeoTdkModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-26 下午12:14:45
     */
    SeoTdkModel getTDKBycode(String code);

}
