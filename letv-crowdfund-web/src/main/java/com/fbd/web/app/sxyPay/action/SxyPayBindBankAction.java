

package com.fbd.web.app.sxyPay.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.sxyPay.common.ResultHandler;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.SysException;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.sxyPay.service.ISxyPayBindBankService;
import com.fbd.web.app.user.service.IUserService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;

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
@RequestMapping("/sxyPay/bindBnak")
public class SxyPayBindBankAction extends BaseAction{
    
    /**
     * 
     */
    private static final long serialVersionUID = 583651072172660381L;

    private static final Logger logger = LoggerFactory
            .getLogger(SxyPayBindBankAction.class);

    @Resource
    private ISxyPayBindBankService sxyPayBindBankService;
    
    @Resource
    private IUserService userService;
    
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    
    @Resource
    private IVerifyCodeService verifyCodeService;
    
    /**
     * 
     * Description:绑定银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/createUserAccount.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> createUserAccount(UserBankModel model,HttpServletRequest request){
        logger.info("=============绑卡接口开始调用============");
        Map<String, Object> resultData = new HashMap<String, Object>();
        try{
        	
        	 String userId = this.getUserId(request);
             model.setUserId(userId);
        	//验证手机验证码
            String verifyCode = request.getParameter("verifyCode");
            this.verifyCodeService.checkVerifyCode(verifyCode, 
                    FbdConstants.NODE_TYPE_BIND_BANK_CARD, userId);
            UserBankModel bankModel = this.sxyPayBindBankService.getBnakByUserAndBankAccount(model);
            if(null != bankModel){
            	if(FbdCoreConstants.USER_BANK_STATE_BIND.equals(bankModel.getState())){
            		throw new ApplicationException("该银行卡已存在");
            	}
            	model.setId(bankModel.getId());
            	this.sxyPayBindBankService.updateUserBank(model);
            	resultData.put(SUCCESS, true);
            	return resultData;
            }else{
            	Map<String, String> handerResultMap=null;
            	if(FbdCoreConstants.BLOCK_USER_IS_MOCK){
            		handerResultMap=new HashMap<String,String>();
            		handerResultMap.put("respCode", "00");
            		handerResultMap.put("respDesc", "成功");
            	}else{
            		logger.info("----------------->开始调用首信易支付银行卡签权---->绑定银行卡");
            		String str = this.sxyPayBindBankService.createUserBank(model);
            		logger.info("----------------->开始调用首信易支付银行卡签权返回参数："+str);
                    handerResultMap = ResultHandler.convert2Str(str);
                    logger.info("----------------->开始调用首信易支付银行卡签权返回参数-handerResultMap："+handerResultMap);
            	}
                 //用户开户
                 String resultCode = handerResultMap.get("respCode");
                 String respDesc = handerResultMap.get("respDesc");
                 
                 if(resultCode.equals("00")){//绑定银行卡成功
                 	sxyPayBindBankService.saveUserBank(model);
                 	resultData.put(SUCCESS, true);
                 }else{
//                	 respDesc = "绑卡失败,请联系客服！";
                     resultData.put(SUCCESS, false);
                     if(StringUtils.isEmpty(respDesc)){
                         respDesc = "绑卡失败,请联系客服！";
                         resultData.put(SUCCESS, false);
                     }
                     throw new ApplicationException(respDesc);
                 }
            }
        }catch(ApplicationException e){
            logger.error(e.getMessage());
            resultData.put(MSG, e.getMessage());
            resultData.put(SUCCESS, false);
        }catch(SysException e){
           logger.error(e.getMessage());
            resultData.put(SUCCESS, false);
            resultData.put(MSG,e.getMessage());
        }catch(Exception e){
           logger.error(e.getMessage());
           resultData.put(SUCCESS, false);
            resultData.put(MSG,"系统错误，请联系客服");
        }
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
    public @ResponseBody Map<String,Object> unbundlingBank(UserBankModel model,HttpServletRequest request){
        logger.info("=============解绑银行卡接口开始调用============");
        Map<String, Object> resultData = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            this.sxyPayBindBankService.unbundlingBank(model);
            resultData.put(SUCCESS, true);
        }catch(ApplicationException e){
        	resultData.put(MSG, e.getMessage());
        	resultData.put(SUCCESS, false);
           logger.error(e.getMessage());
        }
        return resultData;
    }
    
}
