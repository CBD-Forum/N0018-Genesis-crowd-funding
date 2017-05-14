

package com.fbd.web.app.letvPay.action;

import java.util.Date;
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
import com.fbd.web.app.letvPay.service.ILetvPayTransferService;
import com.fbd.web.app.user.service.IUserService;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System..
 * 
 * Description:用户实名认证
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/letvPay/account")
public class LetvPayAccountAction extends BaseAction{
    
    /**
     * 
     */
    private static final long serialVersionUID = 583651072172660381L;

    private static final Logger logger = LoggerFactory
            .getLogger(LetvPayAccountAction.class);
    
    @Resource
    private ILetvPayAccountService letvPayAccountService;
    
    @Resource
    private IUserService userService;
    
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    
    /**
     * 
     * Description: 实名认证前验证用户信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/checkUserInfo.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> checkUserInfo(String verifyCode,UserModel user,HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            user.setUserId(userId);
            userService.checkUserInfo(user);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "验证失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:个人开户
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/createUserAccount.html", method = RequestMethod.POST)
    public ModelAndView createUserAccount(UserModel user,HttpServletRequest request,HttpServletResponse response){
        logger.info("=============USP实名认证接口开始调用============");
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("titleMsg", "实名认证");
        String result = "";
        ModelAndView mav = null;
        try{
            String userId = this.getUserId(request);
            user.setUserId(userId);
            user.setAuthTime(new Date());
            Map<String,Object> createPersonMap = this.letvPayAccountService.createPersonalMember(user);
            //用户开户
            if(createPersonMap.get(Param.is_success).equals("T")){
            	System.out.print("===个人开户成功===="+createPersonMap);
            	if(createPersonMap.get(Param.is_thirnd) == null){
            		user.setThirdAccount(createPersonMap.get(Param.member_id).toString());
            		this.letvPayAccountService.updateThirdAccount(user);
            	}
            	//用户实名认证
            	Map<String,Object> authAccountMap = this.letvPayAccountService.sendAuthAccountRequest(user);
            	if(authAccountMap.get(Param.is_success).equals("T") && authAccountMap.get(Param.resultCode).equals("00")){
            		resultData.put("successMsg", "恭喜，您已经认证成功");
            		user.setIsAuth(FbdCoreConstants.IS);
            		user.setUPSAuth(FbdCoreConstants.IS);
            		this.letvPayAccountService.updateThirdAccount(user);
            		request.getSession().setAttribute(FbdCoreConstants.SESSION_UPS_AUTH,FbdCoreConstants.IS);
            		request.getSession().setAttribute(FbdCoreConstants.SESSION_IS_AUTH,FbdCoreConstants.IS);
                   /* //用户区块链开户
	                try{
	                  	boolean flag = this.blockChainService.createUserAccount(userId);
	                  	if(flag){
	                  		request.getSession().setAttribute(FbdCoreConstants.SESSION_BLOCKCHAINSTATE,"1");
	                  		flag = this.blockChainService.activeUserAccount(userId);
	                  		if(flag){
	                  			//已激活
	                  			request.getSession().setAttribute(FbdCoreConstants.SESSION_BLOCKCHAINSTATE,"2");
	                  		}else{
	                  			logger.info("==================区块链开户激活失败=======================");
	                  		}
	                  	}else{
	                  		logger.info("==================区块链开户激活失败=======================");
	                  	}
	              	}catch(Exception e){
	              		logger.info("=================用户区块链开户失败："+e.getMessage());
	              	}*/
            		
                    System.out.print("===个人实名认证成功===="+createPersonMap);
                    
                    mav = new ModelAndView("realNameRZTG");
                    
            	}else if(createPersonMap.get(Param.is_success).equals("F")){
                	resultData.put("errorMsg", createPersonMap.get(Param.error_message).toString());
                	mav = new ModelAndView("realNameRZ");
                }
            }else if(createPersonMap.get(Param.is_success).equals("F")){
            	resultData.put("errorMsg", createPersonMap.get(Param.error_message).toString());
            	mav = new ModelAndView("realNameRZ");
            }
            
            
        }catch(ApplicationException e){
           logger.error(e.getMessage());
            resultData.put("errorMsg", e.getMessage());
        }catch(SysException e){
           logger.error(e.getMessage());
            resultData.put("errorMsg",e.getMessage());
        }catch(Exception e){
           logger.error(e.getMessage());
            resultData.put("errorMsg","系统错误，请联系客服");
        }
        logger.info("=============USP实名认证接口结束调用，调用结果============"+result);
         
        mav.addObject("result", resultData);
        return mav;
    }
    
    
    
    
    
    /**
     * 
     * Description: 实名认证
     *
     * @param 
     * @return void
     * @throws 
     * @Author WUWENBIN<br/>
     */
    @RequestMapping(value = "/authAccount.html", method = RequestMethod.POST)
    public ModelAndView authAccount(UserModel user,HttpServletRequest request,HttpServletResponse response){
        logger.info("=============USP实名认证接口开始调用============");
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("titleMsg", "实名认证");
        String result = "";
        try{
//            String userId = this.getUserId(request);
//            user.setUserId(userId);
            this.letvPayAccountService.sendAuthAccountRequest(user);
        }catch(ApplicationException e){
           logger.error(e.getMessage());
            resultData.put("errorMsg", e.getMessage());
        }catch(SysException e){
           logger.error(e.getMessage());
            resultData.put("errorMsg",e.getMessage());
        }catch(Exception e){
           logger.error(e.getMessage());
            resultData.put("errorMsg","系统错误，请联系客服");
        }
        logger.info("=============USP实名认证接口结束调用，调用结果============"+result);
        ModelAndView mav = new ModelAndView("letvPayResult");
        mav.addObject("result", resultData);
        return mav;
    }
    
}
