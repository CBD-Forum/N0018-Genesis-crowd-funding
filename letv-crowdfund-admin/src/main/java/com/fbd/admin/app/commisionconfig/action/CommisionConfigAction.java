package com.fbd.admin.app.commisionconfig.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.admin.app.commisionconfig.service.ICommisionConfigService;
import com.fbd.core.app.commisionconfig.model.CommisionConfigModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.helper.PKGenarator;

@Controller
@RequestMapping("/commisionConfig")
public class CommisionConfigAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8460457935246053763L;
	
	@Resource
    private ICommisionConfigService commisionConfigService;
	
	@RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<CommisionConfigModel> getbusConfigList(CommisionConfigModel commisionConfigModel){
        SearchResult<CommisionConfigModel> commisionConfigList = commisionConfigService.getCommisionConfigPage(commisionConfigModel);
        return commisionConfigList;
        
    }

	
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveCommisionConfig(CommisionConfigModel commisionConfigModel){
    	commisionConfigModel.setId(PKGenarator.getId());
        int num = commisionConfigService.saveCommisionConfig(commisionConfigModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_SYSCONFIG, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "类型："+commisionConfigModel.getType()+",最小："+commisionConfigModel.getMinMoney()+",最大"+commisionConfigModel.getMaxMoney()+",比例："+commisionConfigModel.getCommisionRate());
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
    public @ResponseBody CommisionConfigModel getCommisionConfigById(String id){
        return commisionConfigService.getCommisionConfigById(id);
    }
    
    @RequestMapping(value = "/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyCommisionConfig(CommisionConfigModel commisionConfigModel){
        int num = commisionConfigService.modifyCommisionConfig(commisionConfigModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_SYSCONFIG, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "类型："+commisionConfigModel.getType()+",最小："+commisionConfigModel.getMinMoney()+",最大"+commisionConfigModel.getMaxMoney()+",比例："+commisionConfigModel.getCommisionRate());
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/remove.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeCommisionConfig(String id){
    	CommisionConfigModel commisionConfigModel = getCommisionConfigById(id);
        int num = commisionConfigService.removeCommisionConfig(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_SYSCONFIG, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "类型："+commisionConfigModel.getType()+",最小："+commisionConfigModel.getMinMoney()+",最大"+commisionConfigModel.getMaxMoney()+",比例："+commisionConfigModel.getCommisionRate());
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    } 
}
