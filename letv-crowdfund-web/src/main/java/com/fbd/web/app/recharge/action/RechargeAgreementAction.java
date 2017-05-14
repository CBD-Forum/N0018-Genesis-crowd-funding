package com.fbd.web.app.recharge.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.base.BaseAction;
import com.fbd.core.exception.ApplicationException;
import com.fbd.web.app.recharge.service.IRechargeAgreementService;


@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@RequestMapping("/rechargeAgreement")
public class RechargeAgreementAction extends BaseAction {
	
    private static final Logger logger = LoggerFactory.getLogger(RechargeAgreementAction.class);
    
    @Resource
    private IRechargeAgreementService rechargeAgreementService;
    
    @SuppressWarnings("unused")
	@RequestMapping(value = "/getRechargeAgreement.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> getRechargeAgreement(String orderId,HttpServletRequest request,HttpServletResponse response){
    	
    	logger.info("--------------获取充值协议模板orderId："+orderId+"-----");
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
			String userId = this.getUserId(request);
    		String path = rechargeAgreementService.signRechargeAgreement(orderId, request, response);
    		resultMap.put("path",path);	
    		resultMap.put(SUCCESS,true);
    	}catch(ApplicationException e){
    		resultMap.put(MSG, e.getMessage());
    		resultMap.put(SUCCESS,false);
    	}catch(Exception e){
    		resultMap.put(MSG, e.getMessage());
    		resultMap.put(SUCCESS,false);
    	}
    	return resultMap;
    }
}
