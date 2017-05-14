

package com.fbd.web.app.letvPay.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.thirdtrusteeship.common.TrusteeshipConstants;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.SysException;
import com.fbd.core.util.HttpServletUtils;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.letvPay.service.ILetvPayAccountService;
import com.fbd.web.app.letvPay.service.ILetvPayBindBankService;
import com.fbd.web.app.letvPay.service.ILetvPayTransferService;
import com.fbd.web.app.user.service.IUserService;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System..
 * 
 * Description:用户绑定银行卡
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/letvPay/bindBnak")
public class LetvPayBindBankAction extends BaseAction{
    
    /**
     * 
     */
    private static final long serialVersionUID = 583651072172660381L;

    private static final Logger logger = LoggerFactory
            .getLogger(LetvPayBindBankAction.class);
    
    @Resource
    private ILetvPayBindBankService letvPayBindBankService;
    
    @Resource
    private IUserService userService;
    
    /**
     * 
     * Description:绑定银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/createBindBnak.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> createBindBnak(UserBankModel model,HttpServletRequest request,HttpServletResponse response){
        logger.info("=============USP绑定银行卡接口开始调用============");
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("titleMsg", "绑定银行卡");
        String result = "";
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            
            
            UserBankModel bankModel = this.letvPayBindBankService.getBnakByBankAccount(model.getBankAccount());
            if(null != bankModel){
            	model.setId(bankModel.getId());
            	resultData.put(SUCCESS, false);
            	resultData.put(MSG,"您已绑定该卡");
            	return resultData;
            }
//            model.setBank("招商银行");
//            model.setBankNo("CMB");
//            model.setBankAccount("6225880170703632");
//            model.setName("北京招商银行");
            
            Map<String,Object> bindBnakmap = this.letvPayBindBankService.createBindBnak(model);
            
            if(bindBnakmap.get(Param.is_success).equals("T") && bindBnakmap.get(Param.resultCode).equals("00")){
        		resultData.put(SUCCESS, true);
            }else{
            	resultData.put(MSG, bindBnakmap.get(Param.error_message).toString());
            	resultData.put(SUCCESS, false);
            }
        }catch(ApplicationException e){
           logger.error(e.getMessage());
        }
        logger.info("=============USP绑定银行卡接口结束调用，调用结果============"+result);
        return resultData;
    }
    
    /**
     * 
     * Description:解绑银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/unbundlingBank.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> unbundlingBank(UserBankModel model,HttpServletRequest request,HttpServletResponse response){
        logger.info("=============USP绑定银行卡接口开始调用============");
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("titleMsg", "绑定银行卡");
        String result = "";
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
//            model.setThirndBankId("99784");
            Map<String,Object> bindBnakmap = this.letvPayBindBankService.unbundlingBank(model);
            if(bindBnakmap.get(Param.is_success).equals("T") && bindBnakmap.get(Param.resultCode).equals("00")){
        		resultData.put(SUCCESS, true);
            }else{
            	resultData.put(MSG, bindBnakmap.get(Param.error_message).toString());
            	resultData.put(SUCCESS, false);
            }
        }catch(ApplicationException e){
           logger.error(e.getMessage());
        }
        logger.info("=============USP绑定银行卡接口结束调用，调用结果============"+result);
        return resultData;
    }
    
    /**
     * 
     * Description:查询银行卡列表
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getBankListByThirnd.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> getBankList(UserBankModel model,HttpServletRequest request,HttpServletResponse response){
        logger.info("=============USP绑定银行卡接口开始调用============");
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("titleMsg", "绑定银行卡");
        String result = "";
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            Map<String,Object> bindBnakmap = this.letvPayBindBankService.getBankListByThirnd(model);
        }catch(ApplicationException e){
           logger.error(e.getMessage());
        }
        logger.info("=============USP绑定银行卡接口结束调用，调用结果============"+result);
        return resultData;
    }
    
    
    
}
