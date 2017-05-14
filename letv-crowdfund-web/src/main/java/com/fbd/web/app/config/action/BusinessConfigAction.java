package com.fbd.web.app.config.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.web.app.config.service.IBusinessConfigService;

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
    
  
    
    @RequestMapping(value = "/getByDisplayName.html", method = RequestMethod.POST)
    public @ResponseBody BusinessConfigModel getBusConfigById(String displayName){
         
    	BusinessConfigModel config = this.businessConfigService.getByDisplayName(displayName);
        
        return config;
    }
      
}
