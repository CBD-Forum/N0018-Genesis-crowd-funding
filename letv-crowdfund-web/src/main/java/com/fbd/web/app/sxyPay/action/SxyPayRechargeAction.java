

package com.fbd.web.app.sxyPay.action;

import java.io.IOException;
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

import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.sxyPay.common.ResultHandler;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.thirdtrusteeship.common.TrusteeshipConstants;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.HttpServletUtils;
import com.fbd.core.util.ValidateUtils;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.sxyPay.service.ISxyPayRechargeService;
import com.fbd.web.app.user.service.IUserService;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System..
 * 
 * Description:用户充值
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/sxyPay/recharge")
public class SxyPayRechargeAction extends BaseAction{
    
    /**
     * 
     */
    private static final long serialVersionUID = 583651072172660381L;

    private static final Logger logger = LoggerFactory
            .getLogger(SxyPayRechargeAction.class);
    
    @Resource
    private ISxyPayRechargeService sxyPayRechargeService;
    
    @Resource
    private IUserService userService;
    
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;

    
    
    
    /**
     * 
     * Description:移动端充值 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/createRechargeOrderByMobile.html", method = RequestMethod.POST)
    public  @ResponseBody Map<String,Object> createRechargeOrderByMobile(RechargeModel recharge,HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
        	
        	String userId = this.getUserId(request);
        	UserModel user = this.userService.findByUserId(userId);
        	String isAuth = user.getIsAuth();
        	
            if(!isAuth.equals(FbdCoreConstants.IS)){
                throw new ApplicationException("对不起，您还未进行实名认证！");
            }
            String rechargeAmt = request.getParameter("rechargeAmt");
            if (StringUtils.isEmpty(rechargeAmt)) {
                throw new ApplicationException("对不起，充值金额不能为空");
            }
            if (!ValidateUtils.isNumber(rechargeAmt)) {
                throw new ApplicationException("对不起，充值金额格式有误");
            }
            recharge.setUserId(userId);
            recharge.setThirdRechargeType("SXY");
            RechargeModel model = this.sxyPayRechargeService.createRechangeOrderByMobile(userId, recharge);
            resultMap.put(SUCCESS, true);
            resultMap.put("userId", userId);  //用户名
            resultMap.put("orderId", model.getOrderId());  //订单编号
            resultMap.put("rechargeAmt", model.getRechargeAmt()); 
            resultMap.put("v_mid",SxyPayConstants.Config.SXY_SHBH);
            resultMap.put("Md5Key",SxyPayConstants.Config.SXY_MD5KEY);
            resultMap.put("AesKey",SxyPayConstants.Config.SXY_AES);
            
        }catch(ApplicationException e){
        	resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"创建订单失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:充值前的验证 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/checkCreateRecharge.html", method = RequestMethod.POST)
    public  @ResponseBody Map<String,Object> checkCreateRecharge(RechargeModel recharge,HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String isAuth = request.getSession().getAttribute(FbdCoreConstants.SESSION_IS_AUTH).toString();
            if(!isAuth.equals(FbdCoreConstants.IS)){
                throw new ApplicationException("对不起，您还未进行实名认证！");
            }
            String rechargeAmt = request.getParameter("rechargeAmt");
            if (StringUtils.isEmpty(rechargeAmt)) {
                throw new ApplicationException("对不起，充值金额不能为空");
            }
            if (!ValidateUtils.isNumber(rechargeAmt)) {
                throw new ApplicationException("对不起，充值金额格式有误");
            }
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:充值
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    
    @RequestMapping(value = "/createRechargeRequest.html", method = RequestMethod.POST)
    public void createRechargeRequest(RechargeModel model,HttpServletRequest request,HttpServletResponse response){
        logger.info("=============网关支付接口开始调用============");
        //发送请求
        String requestHtml = "";
        String result = "";
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            model.setThirdRechargeType("SXY");
            requestHtml = sxyPayRechargeService.createRechargeRequest(userId, model);
            result = SUCCESS;
        }catch(ApplicationException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            requestHtml = e.getMessage();
            result = ERROR;
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            requestHtml = "充值失败";
            result = ERROR;
        }
        HttpServletUtils.outString(response, requestHtml);
        logger.info("=============网关支付接口结束调用，调用结果"+result+"============");
    }
    
    
    
    
    
    /**
     * 
     * Description: 同步回调充值
     *
     * @param 
     * @return ModelAndView
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/receiveRechargeWeb.html", method = RequestMethod.POST)
    public ModelAndView receiveBindAccountCallbackWeb(
            HttpServletRequest request) {
    	
        logger.info("=============首信易支付充值接口同步回调开始调用============");
        String result = this.receiveRechargeCallback(request);
        logger.info("首信易支付充值接口同步回调调用结束，返回的结果为：:" + result);
        
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("titleMsg", "充值");
        if (!result.equals(FbdConstants.RESULT_TRUE)) {
            resultData.put("errorMsg", result);
        } else {
            resultData.put("successMsg", "恭喜您！充值成功！");
        }
        ModelAndView mav = new ModelAndView("letvPayResult");
        mav.addObject("result", resultData);
        return mav;
    }
    
    @RequestMapping(value = "/receiveRechargeS2S.html", method = RequestMethod.POST)
    public void receiveRechargeCallbackS2S(HttpServletRequest request,HttpServletResponse response) {
        logger.info("=============首信易充值接口异步回调开始调用============");
        String result = this.receiveRechargeCallbackS2S(request);
        try {
            response.getWriter().write("ok");
        } catch (IOException e) {
             logger.error(e.getMessage());
            logger.error("USP充值异步调用时，输出信息出现异常");
        }
        logger.info("USP充值接口异步回调调用结束，返回的结果为：:" + result);
    } 


	private String receiveRechargeCallbackS2S(HttpServletRequest request) {
		String  handlerResult = FbdConstants.RESULT_TRUE;
		try {
			Map<String, String> handerResultMap = ResultHandler.convert2MapS2S(request);
			logger.info("====================>首易信充值结果："+handerResultMap);
			String resultCode = handerResultMap.get("respCode");
            String respDesc = handerResultMap.get("respDesc");
            String result = resultCode+";"+respDesc;
            String status = "";
            if(resultCode.equals("1")){//充值成功
                status = TrusteeshipConstants.Status.PASSED;
            }else{
                status = TrusteeshipConstants.Status.REFUSED;
            }
            trusteeshipOperationService.updateOperationModel(request.getParameter(SyxParam.v_oid), 
                    SxyPayConstants.BusiType.recharge, 
                    null, SxyPayConstants.THIRD_ID, status, result);
            if(resultCode.equals("1")){//充值成功
                Map<String,String> resultMap = this.getMap(request);
                logger.info("===========>首信易支付充值成功，开始更新数据========");
                sxyPayRechargeService.updateRechargSucess(resultMap);
            }else{
                if(StringUtils.isEmpty(respDesc)){
                    respDesc = "充值失败,请联系客服！";
                }
                throw new ApplicationException(respDesc);
            }
            
            logger.info("USP充值回调返回的结果为：:" + result);
        } catch (ApplicationException e) {
            handlerResult = e.getMessage();
            logger.error(e.getMessage());
        } catch (Exception e) {
            handlerResult = "充值失败,请联系客服！";
            logger.error(e.getMessage());
        }
        return handlerResult;
	}


	private String receiveRechargeCallback(HttpServletRequest request) {
		String  handlerResult = FbdConstants.RESULT_TRUE;
		try {
			Map<String, String> handerResultMap = ResultHandler.convert2Map(request);
			String resultCode = handerResultMap.get("respCode");
            String respDesc = handerResultMap.get("respDesc");
            String result = resultCode+";"+respDesc;
            String status = "";
            if(resultCode.equals("20")){//充值成功
                status = TrusteeshipConstants.Status.PASSED;
            }else{
                status = TrusteeshipConstants.Status.REFUSED;
            }
            trusteeshipOperationService.updateOperationModel(request.getParameter(SyxParam.v_oid), 
                    SxyPayConstants.BusiType.recharge, 
                    null, SxyPayConstants.THIRD_ID, status, result);
            if(resultCode.equals("20")){//充值成功
                Map<String,String> resultMap = this.getMap(request);
                sxyPayRechargeService.updateRechargSucess(resultMap);
            }else{
                if(StringUtils.isEmpty(respDesc)){
                    respDesc = "充值失败,请联系客服！";
                }
                throw new ApplicationException(respDesc);
            }
            
            logger.info("首信易充值接口回调返回的结果为：:" + result);
        } catch (ApplicationException e) {
            handlerResult = e.getMessage();
            logger.error(e.getMessage());
        } catch (Exception e) {
            handlerResult = "充值失败,请联系客服处理";
            logger.error(e.getMessage());
        }
        return handlerResult;
	}


	private Map<String, String> getMap(HttpServletRequest request) {
		Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put(SyxParam.v_oid, request.getParameter(SyxParam.v_oid));
        return paramMap;
	}
	
	
	
	
	
	
	
	/**
     * 
     * Description:快捷充值
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    
    @RequestMapping(value = "/createQuickRechargeRequest.html", method = RequestMethod.POST)
    public void createQuickRechargeRequest(RechargeModel model,HttpServletRequest request,HttpServletResponse response){
        logger.info("=============网关支付接口开始调用============");
        //发送请求
        String requestHtml = "";
        String result = "";
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            requestHtml = sxyPayRechargeService.createQuickRechargeRequest(userId, model);
            result = SUCCESS;
        }catch(ApplicationException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            requestHtml = e.getMessage();
            result = ERROR;
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            requestHtml = "投资失败";
            result = ERROR;
        }
        HttpServletUtils.outString(response, requestHtml);
        logger.info("=============网关支付接口结束调用，调用结果"+result+"============");
    }
	
    
    
    /**
     * 
     * Description: 快捷同步回调充值
     *
     * @param 
     * @return ModelAndView
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/receiveQuickRechargeWeb.html", method = RequestMethod.GET)
    public ModelAndView receiveQuickRechargeWeb(
            HttpServletRequest request) {
    	
        logger.info("=============USP充值接口同步回调开始调用============");
        String result = this.receiveQuickRechargeCallback(request);
        logger.info("USP充值接口同步回调调用结束，返回的结果为：:" + result);
        
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("titleMsg", "充值");
        if (!result.equals(FbdConstants.RESULT_TRUE)) {
            resultData.put("errorMsg", result);
        } else {
            resultData.put("successMsg", "恭喜,你成功充值!");
        }
        ModelAndView mav = new ModelAndView("letvPayResult");
        mav.addObject("result", resultData);
        return mav;
    }
    
    private String receiveQuickRechargeCallback(HttpServletRequest request) {
		String  handlerResult = FbdConstants.RESULT_TRUE;
		try {
			Map<String, String> handerResultMap = ResultHandler.convert2Map(request);
			String resultCode = handerResultMap.get("respCode");
            String respDesc = handerResultMap.get("respDesc");
            String result = resultCode+";"+respDesc;
            String status = "";
            if(resultCode.equals("20")){//充值成功
                status = TrusteeshipConstants.Status.PASSED;
            }else{
                status = TrusteeshipConstants.Status.REFUSED;
            }
            trusteeshipOperationService.updateOperationModel(request.getParameter(SyxParam.v_oid), 
                    SxyPayConstants.BusiType.recharge, 
                    null, SxyPayConstants.THIRD_ID, status, result);
            if(resultCode.equals("20")){//充值成功
                Map<String,String> resultMap = this.getMap(request);
                sxyPayRechargeService.updateRechargSucess(resultMap);
            }else{
                if(StringUtils.isEmpty(respDesc)){
                    respDesc = "充值失败,请联系客服！";
                }
                throw new ApplicationException(respDesc);
            }
            
            logger.info("汇付宝满标投资接口回调返回的结果为：:" + result);
        } catch (ApplicationException e) {
            handlerResult = e.getMessage();
            logger.error(e.getMessage());
        } catch (Exception e) {
            handlerResult = "满标投资失败,请联系客服处理";
            logger.error(e.getMessage());
        }
        return handlerResult;
	}
    
    
    @RequestMapping(value = "/receiveQuickRechargeS2S.html", method = RequestMethod.POST)
    public void receiveQuickRechargeS2S(HttpServletRequest request,HttpServletResponse response) {
        logger.info("=============USP充值接口异步回调开始调用============");
        String result = this.receiveQuickRechargeCallbackS2S(request);
        try {
            response.getWriter().write("sent");
        } catch (IOException e) {
             logger.error(e.getMessage());
            logger.error("USP充值异步调用时，输出信息出现异常");
        }
        logger.info("USP充值接口异步回调调用结束，返回的结果为：:" + result);
    } 


	private String receiveQuickRechargeCallbackS2S(HttpServletRequest request) {
		String  handlerResult = FbdConstants.RESULT_TRUE;
		try {
			Map<String, String> handerResultMap = ResultHandler.convert2MapS2S(request);
			String resultCode = handerResultMap.get("respCode");
            String respDesc = handerResultMap.get("respDesc");
            String result = resultCode+";"+respDesc;
            String status = "";
            if(resultCode.equals("1")){//充值成功
                status = TrusteeshipConstants.Status.PASSED;
            }else{
                status = TrusteeshipConstants.Status.REFUSED;
            }
            trusteeshipOperationService.updateOperationModel(request.getParameter(SyxParam.v_oid), 
                    SxyPayConstants.BusiType.recharge, 
                    null, SxyPayConstants.THIRD_ID, status, result);
            if(resultCode.equals("1")){//充值成功
                Map<String,String> resultMap = this.getMap(request);
                sxyPayRechargeService.updateRechargSucess(resultMap);
            }else{
                if(StringUtils.isEmpty(respDesc)){
                    respDesc = "充值失败,请联系客服！";
                }
                throw new ApplicationException(respDesc);
            }
        } catch (ApplicationException e) {
            handlerResult = e.getMessage();
            logger.error(e.getMessage());
        } catch (Exception e) {
            handlerResult = "充值失败,请联系客服";
            logger.error(e.getMessage());
        }
        return handlerResult;
	}
}
