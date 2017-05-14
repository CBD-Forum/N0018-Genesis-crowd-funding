package com.fbd.admin.app.config.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.admin.app.config.service.IBusinessConfigService;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;

@Controller
@Scope("prototype")
@RequestMapping("/businessconfig")
public class BusinessConfigAction extends BaseAction {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1230390968302995888L;
    @Resource
    private IBusinessConfigService businessConfigService;
    
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<BusinessConfigModel> getbusConfigList(BusinessConfigModel businessConfigModel){
        SearchResult<BusinessConfigModel> busConfigList = businessConfigService.getBusConfigPage(businessConfigModel);
        return busConfigList;
    }
    
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveBusConfig(BusinessConfigModel businessConfigModel){
        int num = businessConfigService.saveBusConfig(businessConfigModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_BUSINESSCONFIG, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "名称："+businessConfigModel.getDisplayName()+",值："+businessConfigModel.getCode());
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
                AuditLogUtils.log(AuditLogConstants.MODEL_BUSINESSCONFIG, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_FAIL, "名称："+businessConfigModel.getDisplayName()+",值："+businessConfigModel.getCode());
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
    public @ResponseBody BusinessConfigModel getBusConfigById(String id){
        BusinessConfigModel businessConfigModel = new BusinessConfigModel();
        businessConfigModel.setId(id);
        List<BusinessConfigModel> list = businessConfigService.getBusConfigList(businessConfigModel);
        if (list.size()>0) {
            return list.get(0);
        }
        return null;
    }
    
    @RequestMapping(value = "/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyBusConfig(BusinessConfigModel businessConfigModel){
        int num = businessConfigService.modifyBusConfig(businessConfigModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_BUSINESSCONFIG, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "名称："+businessConfigModel.getDisplayName()+",值："+businessConfigModel.getCode());
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/remove.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeBusConfig(String id){
        int num = businessConfigService.removeBusConfig(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_BUSINESSCONFIG, AuditLogConstants.TYPE_DELETE, AuditLogConstants.RESULT_SUCCESS);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    } 
    
    
    @RequestMapping(value = "/getByDisplayName.html", method = RequestMethod.POST)
    public @ResponseBody BusinessConfigModel getByDisplayName(String displayName){
         
    	BusinessConfigModel config = this.businessConfigService.getByDisplayName(displayName);
        
        return config;
    }
}
