/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: WithDrawAction.java 
 *
 * Created: [2015-1-27 上午10:30:00] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.letvPay.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.letvPay.utils.VerifyClient;
import com.fbd.core.app.thirdtrusteeship.common.TrusteeshipConstants;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.web.app.auth.service.IAuthService;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.letvPay.service.ILetvPayWithDrawService;
import com.fbd.web.app.todo.service.ITodoService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;
import com.fbd.web.app.withdraw.service.IWithDrawService;

/** 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description: 提现
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/letvPay/withdraw")
public class LetvPayWithDrawAction extends BaseAction{
    
    private static final Logger logger = LoggerFactory.getLogger(LetvPayWithDrawAction.class);
    
    @Resource
    private ILetvPayWithDrawService letvPayWithDrawService ;
    
    @Resource
    private ITodoService todoService ;
    
    //资源
    @Resource
    private IAuthService authService ;
    
    @Resource
    private IVerifyCodeService verifyCodeService ;
    
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService ;
    
    
    @RequestMapping(value = "/checkWithdraw.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> checkWithdraw(HttpServletRequest request,String verifyCode,WithDrawModel model){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            //验证手机验证码
            this.verifyCodeService.checkVerifyCode(verifyCode, 
                    FbdConstants.NODE_TYPE_WITHDRAW_REMIND, userId);
            letvPayWithDrawService.checkWithDraw(model);
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch(Exception e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"验证失败");
        }
        return resultMap;
    } 
    
    /**
     * 
     * Description:提交提现申请 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin<br/>
     */
    @RequestMapping(value = "/saveWithdrawOrder.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> saveWithdrawOrder(HttpServletRequest request,WithDrawModel model){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            letvPayWithDrawService.saveWithDraw(model);
            String link = authService.getLinkByCode(FbdCoreConstants.permissionCode.WITHDRAW_AUDIT);
            todoService.saveTodo(FbdCoreConstants.withDrawState.auditing.getValue(), model.getOrderId(), 
                    userId+"提交提现申请："+model.getOrderId(), link, 
                    FbdCoreConstants.todoPost.finance);
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch(Exception e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"提现申请失败");
        }
        return resultMap;
    } 
    
    
    @RequestMapping(value = "/receiveRechargeAdminS2S.html", method = RequestMethod.POST)
    public void receiveRechargeAdminS2S(HttpServletRequest request,HttpServletResponse response) {
        logger.info("=============USP充值接口异步回调开始调用============");
        Map<String,String> map = new HashMap<String, String>();
    	map.putAll(this.getRequestParam());
        String result = this.receiveRechargeAdminCallback(request,map);
        try {
            response.getWriter().write("ok");
        } catch (IOException e) {
             logger.error(e.getMessage());
            logger.error("USP充值异步调用时，输出信息出现异常");
        }
        logger.info("USP充值接口异步回调调用结束，返回的结果为：:" + result);
    }

	private String receiveRechargeAdminCallback(HttpServletRequest request,
			Map<String, String> map) {
		String status = "";
		String  handlerResult = FbdConstants.RESULT_TRUE;
		try {
			Boolean verifyBasic = VerifyClient.verifyBasic(LetvPayConstants.INPUTCHARSET, map);
			if(verifyBasic==true){//提现成功
                status = TrusteeshipConstants.Status.PASSED;
            }else{
                status = TrusteeshipConstants.Status.REFUSED;
            }
			trusteeshipOperationService.updateOperationModel(map.get(Param.outer_trade_no), 
                    LetvPayConstants.BusiType.withdraw, 
                    null, LetvPayConstants.THIRD_ID, status, map.get(Param.deposit_status));
			
            if(map.get(Param.withdrawal_status).equals(LetvPayConstants.withDrawState.WITHDRAWAL_SUCCESS)){//提现成功
            	letvPayWithDrawService.updateUserWithDraw(map.get(Param.outer_trade_no));
            }else if(map.get(Param.withdrawal_status).equals(LetvPayConstants.withDrawState.WITHDRAWAL_FAIL)){
                throw new ApplicationException("提现失败");
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
