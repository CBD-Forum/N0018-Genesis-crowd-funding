/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: VerifyCodeAction.java 
 *
 * Created: [2015-2-10 下午5:59:12] by haolingfeng
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

package com.fbd.web.app.verifycode.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.redis.IRedisDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.VerifyCodeException;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.user.service.IUserService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;

/** 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description: 发送验证码
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/verifycode")
public class VerifyCodeAction extends BaseAction{
    /**
     * 
     */
    private static final long serialVersionUID = -5502008130352584619L;
    @Resource
    private IVerifyCodeService verifyCodeService;
    @Resource
    private IUserService userService;
    @Resource
    private IRedisDao redisDao;
    /**
     * 
     * Description: 发送验证码，不需要输入手机号
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:36:37
     */
    @RequestMapping(value = "/sendUserVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendVerifyCode(HttpServletRequest request,String codeType) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
           
            this.verifyCodeService.sendMobileVerifyCode(userId,
                    FbdCoreConstants.userType.P,codeType);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    /**
     * 
     * Description: 发送验证码(注册),需要输入手机号
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:36:37
     */
    @RequestMapping(value = "/sendRegisterVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendRegisterVerifyCode(String sendTarget,String userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.verifyCodeService.checkMobileRegister(sendTarget);
            this.verifyCodeService.sendVerifyCode(userId,sendTarget,
                    FbdCoreConstants.userType.P,FbdConstants.NODE_TYPE_MOBILE_REGISTER);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    /**
     * 
     * Description: 发送验证码(找回密码),需要输入手机号
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:36:37
     */
    @RequestMapping(value = "/sendFindPwdVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendFindPwdVerifyCode(String sendTarget, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            UserModel user = this.verifyCodeService.checkMobileFindPwd(sendTarget);
            
            // 发送手机验证码前，先进行图片验证码校验
            this.validateCode(request);
            
            this.verifyCodeService.sendVerifyCode(user.getUserId(),sendTarget,
                    FbdCoreConstants.userType.P,FbdConstants.NODE_TYPE_MOBILE_PWD);
            resultMap.put(SUCCESS, true);
            resultMap.put("userId",user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    
//    /**
//     * 
//     * Description: 验证码校验
//     * 
//     * @param
//     * @return void
//     * @throws
//     * @Author haolingfeng Create Date: 2014-12-9 下午5:29:23
//     */
//    private void validateCode(HttpServletRequest request) {
//        String valiCode = request.getParameter("valiCode");
//        String verifyCodeSession = this.getVerifyCodeFromSession(request);
//        if (StringUtils.isEmpty(valiCode)
//                || !verifyCodeSession.equalsIgnoreCase(valiCode)) {
//            throw new ApplicationException("验证码错误");
//        }
//    }
    /**
     * 
     * Description: 验证码校验
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午5:29:23
     */
    private void validateInviteCode(HttpServletRequest request) {
        String valiCode = request.getParameter("valicode");
        String verifyCodeSession = this.getVerifyCodeFromSession(request);
        if (StringUtils.isEmpty(valiCode)
                || !verifyCodeSession.equalsIgnoreCase(valiCode)) {
            throw new ApplicationException("图片验证码错误");
        }
    }
    /**
     * 
     * Description: 验证码校验
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午5:29:23
     */
    private void validateCode(HttpServletRequest request) {
    	
    	String valiCode = request.getParameter("valiCode");
        String mobileTerminalFlag = request.getHeader("mobileTerminalFlag");
        if(!StringUtils.isEmpty(mobileTerminalFlag)){  //终端请求
            String imageId = request.getParameter("imageId");
            if(imageId == null || "".equals(imageId)){
            	throw new VerifyCodeException("图片验证码不能为空");
            }
            if(this.redisDao.exists(imageId)){
            	 String verifyCodeSession = this.redisDao.get(imageId);
                 if (StringUtils.isEmpty(valiCode)
                         || !verifyCodeSession.equalsIgnoreCase(valiCode)) {
                 	throw new VerifyCodeException("图片验证码错误");
                 }else{
                 	this.redisDao.del(imageId);
                 }
            }else{
            	throw new VerifyCodeException("图片验证码错误");
            }
        }else{
            String verifyCodeSession = this.getVerifyCodeFromSession(request);
            if (StringUtils.isEmpty(valiCode)
                    || !verifyCodeSession.equalsIgnoreCase(valiCode)) {
                throw new ApplicationException("图片验证码错误");
            }
        }
    }
    
    /**
     * 
     * Description: 获取session中的验证码
     * 
     * @param
     * @return String
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午5:23:27
     */
    private String getVerifyCodeFromSession(HttpServletRequest request) {
        Object verifyCodeSession = request.getSession().getAttribute(
                FbdConstants.CAPTCHA_SESSION);
        return null == verifyCodeSession ? "" : verifyCodeSession.toString();
    }
    
    @RequestMapping(value = "/checkMobileFindPwd.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> checkMobileFindPwd(String sendTarget) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            UserModel user = this.verifyCodeService.checkMobileFindPwd(sendTarget);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 发送验证码(手机认证码),需要输入手机号
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:36:37
     */
    @RequestMapping(value = "/sendMobileAuthCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendMobileAuthCode(HttpServletRequest request,String sendTarget) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            this.verifyCodeService.checkSendMobileAuthCode(userId, sendTarget);
            this.verifyCodeService.sendVerifyCode(userId,sendTarget,
                    FbdCoreConstants.userType.P,FbdConstants.NODE_TYPE_MOBILE_VERIFY);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    
   
    
    /**
     * 
     * Description: 发送邮箱认证码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-6 上午11:33:09
     */
    @RequestMapping(value = "/sendEmailAuthCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendEmailAuthCode(HttpServletRequest request,String sendTarget) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            this.verifyCodeService.sendEmailAuthEmail(sendTarget, userId, 
                    FbdCoreConstants.userType.P);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 发送邀请码
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:36:37
     */
    @RequestMapping(value = "/sendInviteCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendInviteCode(HttpServletRequest request,String mobile) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            
            UserModel u = userService.getUser(userId);
            if(null == u.getIsAuth()){
            	resultMap.put(SUCCESS, false);
                resultMap.put(MSG, "只有实名认证后才能邀请好友");
                return resultMap;
            }
            
            //按手机号查询user表，如果为0可发邀请码
            UserModel user = new UserModel();
            user.setMobile(mobile);
            long count = userService.getUserCount(user);
            if(count > 0){
            	resultMap.put(SUCCESS, false);
                resultMap.put(MSG, "对不起，您邀请的好友已注册");
                return resultMap;
            }
            
            this.verifyCodeService.sendInviteCode(userId, mobile);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 发送邀请码(资金池)
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:36:37
     */
    @RequestMapping(value = "/sendInviteCodeFundPool.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendInviteCodeFundPool(HttpServletRequest request,String mobile) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            validateInviteCode(request);
            //按手机号查询user表，如果为0可发邀请码
            UserModel user = new UserModel();
            user.setMobile(mobile);
            long count = userService.getUserCount(user);
            if(count > 0){
                resultMap.put(SUCCESS, false);
                resultMap.put(MSG, "对不起，您邀请的好友已注册");
                return resultMap;
            }
            this.verifyCodeService.sendMobileInviteCode(userId, mobile);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 获取邀请好友模板内容
     * 
     * @param
     * @return void
     * @throws
     */
    @RequestMapping(value = "/getInviteContent.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getInviteContent(HttpServletRequest request) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		String userId = this.getUserId(request);
    		String content = this.verifyCodeService.getInviteContent(FbdConstants.NODE_TYPE_INVITECODE_MOBILE);
    		UserModel user = userService.getUser(userId);
    		Map<String, String> params = new HashMap<String, String>();
            params.put(FbdConstants.MESSAGE_TEMPLATE_PROP_REALNAME, user.getRealName());
            params.put(FbdConstants.MESSAGE_TEMPLATE_PROP_INVITECODE, user.getUserId());
            for (String key : params.keySet()) {
                content = content.replace("#{" + key + "}", params.get(key));
            }
    		resultMap.put(MSG, content);
    		resultMap.put(SUCCESS, true);
    	} catch (Exception e) {
    		e.printStackTrace();
    		resultMap.put(SUCCESS, false);
    		resultMap.put(MSG, e.getMessage());
    	}
    	return resultMap;
    }
    
    
    /**
     * 
     * Description: 发送验证码(手机修改前原手机号验证),需要输入手机号
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:36:37
     */
    @RequestMapping(value = "/sendMobileUpdateCodeOld.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendMobileUpdateCodeOld(HttpServletRequest request,String sendTarget) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            this.verifyCodeService.checkOldMobile(userId, sendTarget);
            this.verifyCodeService.sendVerifyCode(userId,sendTarget,
                    FbdCoreConstants.userType.P,FbdConstants.NODE_TYPE_MOBILE_VERIFY_OLD);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 发送验证码(手机修改前新手机号验证),需要输入手机号
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:36:37
     */
    @RequestMapping(value = "/sendMobileUpdateCodeNew.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendMobileUpdateCodeNew(HttpServletRequest request,String sendTarget) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            this.verifyCodeService.checkSendMobileUpdateCodeNew(userId, sendTarget);
            this.verifyCodeService.sendVerifyCode(userId,sendTarget,
                    FbdCoreConstants.userType.P,FbdConstants.NODE_TYPE_MOBILE_VERIFY_NEW);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }

}
