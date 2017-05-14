package com.fbd.admin.app.seo.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.seo.service.ISeoTdkService;
import com.fbd.core.app.seo.model.SeoTdkModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;

@Controller
@Scope("prototype")
@RequestMapping("/seotdk")
public class SeoTdkAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 4074193845853989542L;
    
    @Resource
    private ISeoTdkService seoTdkService;
    
    
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<SeoTdkModel> getSeoTdkPage(SeoTdkModel seoTdk){
        SearchResult<SeoTdkModel> seoTdks = seoTdkService.getSeoTdkPage(seoTdk);
        return seoTdks;
    }
    
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveSeoTdk(SeoTdkModel seoTdk){
        int num = seoTdkService.saveSeoTdk(seoTdk);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_TDK, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS,"TDK："+seoTdk.getCode()+",添加成功。");
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
    public @ResponseBody SeoTdkModel getSeoTdkById(String id){
        return seoTdkService.getSeoTdkById(id);
    }
    
    @RequestMapping(value = "/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifySeoTdk(SeoTdkModel seoTdk){
        int num = seoTdkService.modifySeoTdk(seoTdk);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_TDK, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS,"TDK："+seoTdk.getCode()+",修改成功。");
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/remove.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeSeoTdk(String id){
        SeoTdkModel tdk = seoTdkService.getSeoTdkById(id);
        int num = seoTdkService.removeSeoTdk(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_TDK, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS,"TDK："+tdk.getCode()+",被删除。");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }

}
