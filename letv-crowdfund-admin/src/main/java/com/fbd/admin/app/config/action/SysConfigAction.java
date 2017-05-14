package com.fbd.admin.app.config.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.classic.Logger;

import com.fbd.admin.app.config.service.ISysConfigService;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.common.utils.SysConfigCache;

@Controller
@Scope("prototype")
@RequestMapping("/sysconfig")
public class SysConfigAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -7248137987221260044L;
    @Resource
    private ISysConfigService sysConfigService;
    
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<SysConfigModel> getbusConfigList(SysConfigModel sysConfigModel){
        SearchResult<SysConfigModel> sysConfigList = sysConfigService.getSysConfigPage(sysConfigModel);
        return sysConfigList;
        
    }
    
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveSysConfig(SysConfigModel sysConfigModel){
        int num = sysConfigService.saveSysConfig(sysConfigModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_SYSCONFIG, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "名称："+sysConfigModel.getDisplayName()+",值："+sysConfigModel.getCode());
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
    public @ResponseBody SysConfigModel getSysConfigById(String id){
        SysConfigModel sysConfigModel = new SysConfigModel();
        sysConfigModel.setId(id);
        List<SysConfigModel> list = sysConfigService.getSysConfigList(sysConfigModel);
        if (list.size()>0) {
            return list.get(0);
        }
        return null;
    }
    
    @RequestMapping(value = "/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyBusConfig(SysConfigModel sysConfigModel){
        int num = sysConfigService.modifySysConfig(sysConfigModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_SYSCONFIG, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "名称："+sysConfigModel.getDisplayName()+",值："+sysConfigModel.getCode());
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    @PreAuthorize("hasPermission(null, 'security.operation.system_sysConfig_delete1')")
    @RequestMapping(value = "/remove.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeBusConfig(String id){
        SysConfigModel sysConfigModel = getSysConfigById(id);
        int num = sysConfigService.removeSysConfig(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_SYSCONFIG, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "名称："+sysConfigModel.getDisplayName()+",值："+sysConfigModel.getCode());
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 重新加载系统配置缓存
     * @return
     */
    @RequestMapping(value = "/reloadCache.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> reloadCache(){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(SUCCESS, false);
        
        try {
			SysConfigCache.getInstance().reloadAll();
			resultMap.put(SUCCESS, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return resultMap;
    } 

}
