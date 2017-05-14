

package com.fbd.web.app.letvPay.action;

import java.io.IOException;
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

import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.letvPay.utils.VerifyClient;
import com.fbd.core.app.notice.model.NoticeViewRecordModel;
import com.fbd.core.app.notice.service.INoticeViewRecordService;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.thirdtrusteeship.common.TrusteeshipConstants;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.HttpServletUtils;
import com.fbd.core.util.ValidateUtils;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.letvPay.service.ILetvPayRechargeService;
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
@RequestMapping("/letvPay/recharge")
public class LetvPayRechargeAction extends BaseAction{
    
    /**
     * 
     */
    private static final long serialVersionUID = 583651072172660381L;

    private static final Logger logger = LoggerFactory
            .getLogger(LetvPayRechargeAction.class);
    
    @Resource
    private ILetvPayRechargeService letvPayRechargeService;
    
    @Resource
    private IUserService userService;
	@Resource
    private IRechargeDao rechargeDao;
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
	@Resource
	private INoticeViewRecordService noticeViewRecordService;
    
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
            String isAuth = request.getSession().getAttribute(FbdCoreConstants.SESSION_UPS_AUTH).toString();
            if(!isAuth.equals(FbdCoreConstants.IS)){
                throw new ApplicationException("对不起，还未开通第三方账号");
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
    public 
    @ResponseBody Map<String,Object> createRechargeRequest(RechargeModel model,HttpServletRequest request,HttpServletResponse response){
    	logger.info("=============USP充值接口开始调用============");
    	Map<String, Object> resultData = new HashMap<String, Object>();
        //发送请求
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            model.setAddressIp(HttpServletUtils.getIpAddress(request));
            Map<String,Object> rechargemap = letvPayRechargeService.sendRechargeRequest(model);
            
            if(rechargemap.get(Param.is_success).equals("T") && rechargemap.get(Param.biz_status).equals(LetvPayConstants.bizStatus.P)){
        		resultData.put(SUCCESS, true);
        		resultData.put(MSG, rechargemap.get(Param.advance_voucher_no));
            }else{
            	resultData.put(MSG, rechargemap.get(Param.error_message).toString());
            	resultData.put(SUCCESS, false);
            }
            
        }catch(Exception e){
            logger.error(e.getMessage());
            resultData.put(SUCCESS, false);
        }
        return resultData;
    }
    
    
    
    /**
     * 
     * Description:支付推进
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/sendAdvanceInstantPay.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> sendAdvanceInstantPay(HttpServletRequest request,String validationCode,String advanceVoucherNo){
    	logger.info("=============USP充值接口开始调用============");
    	Map<String, Object> resultData = new HashMap<String, Object>();
        //发送请求
        try{
            String userId = this.getUserId(request);
            Map<String,Object> rechargemap = letvPayRechargeService.sendAdvanceInstantPay(userId,validationCode,advanceVoucherNo);
            
            if(rechargemap.get(Param.is_success).equals("T") && rechargemap.get(Param.biz_status).equals(LetvPayConstants.bizStatus.P)){
        		resultData.put(SUCCESS, true);
            }else{
            	resultData.put(SUCCESS, false);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            resultData.put(SUCCESS, false);
        }
        return resultData;
    }
    
    
    /**
     * 
     * Description:即时到账
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/createImmediatePay.html", method = RequestMethod.POST)
    public Map<String,Object> createImmediatePay(HttpServletRequest request,RechargeModel model){
    	logger.info("=============USP充值接口开始调用============");
    	Map<String, Object> resultData = new HashMap<String, Object>();
        //发送请求
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            model.setAddressIp(HttpServletUtils.getIpAddress(request));
            model.setRechargeAmt(0.01);
            model.setBankId("99788");
            Map<String,Object> rechargemap = letvPayRechargeService.createImmediatePay(model);
            
            if(rechargemap.get(Param.is_success).equals("T") && rechargemap.get(Param.biz_status).equals(LetvPayConstants.bizStatus.P)){
        		resultData.put(SUCCESS, true);
        		resultData.put(MSG, rechargemap.get(Param.advance_voucher_no));
            }else{
            	resultData.put(MSG, rechargemap.get(Param.error_message).toString());
            	resultData.put(SUCCESS, false);
            }
            
        }catch(Exception e){
            logger.error(e.getMessage());
            resultData.put(SUCCESS, false);
        }
        return resultData;
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
    	
    	
    	Map<String,String> map = new HashMap<String, String>();
    	map.putAll(this.getRequestParam());
    	
        logger.info("=============USP充值接口同步回调开始调用============");
        String result = this.receiveRechargeCallback(request,map);
        logger.info("USP充值接口同步回调调用结束，返回的结果为：:" + result);
        
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
        logger.info("=============USP充值接口异步回调开始调用============");
        Map<String,String> map = new HashMap<String, String>();
    	map.putAll(this.getRequestParam());
        String result = this.receiveRechargeCallback(request,map);
        try {
            response.getWriter().write("ok");
        } catch (IOException e) {
             logger.error(e.getMessage());
            logger.error("USP充值异步调用时，输出信息出现异常");
        }
        logger.info("USP充值接口异步回调调用结束，返回的结果为：:" + result);
    } 


	private String receiveRechargeCallback(HttpServletRequest request,Map<String,String> map) {
		String status = "";
		String  handlerResult = FbdConstants.RESULT_TRUE;
		try {
			Boolean verifyBasic = VerifyClient.verifyBasic(LetvPayConstants.INPUTCHARSET, map);
			if(verifyBasic==true){//充值成功
                status = TrusteeshipConstants.Status.PASSED;
            }else{
                status = TrusteeshipConstants.Status.REFUSED;
            }
			trusteeshipOperationService.updateOperationModel(map.get(Param.outer_trade_no), 
                    LetvPayConstants.BusiType.recharge, 
                    null, LetvPayConstants.THIRD_ID, status, map.get(Param.deposit_status));
			
            if(map.get(Param.deposit_status).equals(LetvPayConstants.rechargeState.DEPOSIT_SUCCESS)){//充值成功
            	
            	letvPayRechargeService.updateRecharge(map.get(Param.outer_trade_no));
            	
            	//添加未到账提示信息
            	try{
            		//查询当前充值用户信息
            		RechargeModel recharge = this.rechargeDao.selectByOrderId(map.get(Param.outer_trade_no));
            		String userId = recharge.getUserId();
            		//查询是否显示有一条未到账信息(充值)
                    NoticeViewRecordModel qnoticeViewRecord = this.noticeViewRecordService.queryNoticeViewRecord(userId, "recharge", null, null);
                    qnoticeViewRecord.setUpdateTime(new Date());
                    qnoticeViewRecord.setLatestNoticeTime(new Date());
                    this.noticeViewRecordService.modifyNoticeViewRecord(qnoticeViewRecord);
                    
            	}catch(Exception e){
            		logger.error(e.getMessage());
            	}
            	
            	
            }else{
                throw new ApplicationException("充值失败");
            }
		} catch (ApplicationException e) {
            handlerResult = e.getMessage();
            logger.error(e.getMessage());
       } catch (Exception e) {
           handlerResult = "充值失败,请联系客服处理";
            logger.error(e.getMessage());
       }
       return handlerResult;
	}
}
