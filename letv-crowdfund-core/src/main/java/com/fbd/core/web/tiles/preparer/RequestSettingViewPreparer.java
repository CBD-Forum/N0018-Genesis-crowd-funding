package com.fbd.core.web.tiles.preparer;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.ViewPreparer;
import com.fbd.core.app.seo.dao.ISeoTdkDao;
import com.fbd.core.app.seo.dao.impl.SeoTdkDaoImpl;
import com.fbd.core.app.seo.model.SeoTdkModel;
import com.fbd.core.util.SpringPropertiesHolder;
import com.fbd.core.util.spring.SpringUtil;

public class RequestSettingViewPreparer implements ViewPreparer {

    public void execute(TilesRequestContext tilesContext,AttributeContext attributeContext) {
        Map<String, Object> requestScope = tilesContext.getRequestScope();
        
        String viewName = attributeContext.getAttribute("content").toString();
        
        //前端
        if (viewName!=null && !"".equals(viewName)) {
            if (viewName.indexOf("WEB-INF")>-1) {
                viewName=viewName.substring(viewName.lastIndexOf("/")+1,viewName.lastIndexOf("."));
            }else{
                viewName=viewName.substring(6,viewName.length()-4).replace("/", ".");
            }
        }
        
        if (requestScope.get("tdk")==null) {
            ISeoTdkDao seoTdkService = (SeoTdkDaoImpl)SpringUtil.getBean("seoTdkDaoImpl");
            
            SeoTdkModel tdk=seoTdkService.getTDKBycode(viewName);
            if (tdk==null) {
                tdk = new SeoTdkModel();
            }
            if (StringUtils.isEmpty(tdk.getTitle())) {
                tdk.setTitle(SpringPropertiesHolder.getProperty("SEO.TDK.title"));
            }
            if (StringUtils.isEmpty(tdk.getDescription())) {
                tdk.setDescription(SpringPropertiesHolder.getProperty("SEO.TDK.description"));
            }
            if (StringUtils.isEmpty(tdk.getKeyword())) {
                tdk.setKeyword(SpringPropertiesHolder.getProperty("SEO.TDK.keyword"));
            }
            requestScope.put("tdk", tdk);
        }
    }

}
