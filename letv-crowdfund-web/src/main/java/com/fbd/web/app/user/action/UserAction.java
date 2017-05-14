/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserAction.java 
 *
 * Created: [2014-12-3 10:46:25] by haolingfeng
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
 * ProjectName: fbd 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.user.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.redis.IRedisDao;
import com.fbd.core.app.user.model.UserLoginBean;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.DesUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.LoginException;
import com.fbd.core.exception.VerifyCodeException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtils;
import com.fbd.core.util.HashCrypt;
import com.fbd.core.util.SpringPropertiesHolder;
import com.fbd.core.util.WebUtil;
import com.fbd.core.util.ticket.TicketUtil;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.config.service.IBusinessConfigService;
import com.fbd.web.app.config.service.ISysConfigService;
import com.fbd.web.app.log.service.IUserLogService;
import com.fbd.web.app.user.service.IUserSecurityService;
import com.fbd.web.app.user.service.IUserService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:普通用户登录与注册
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserAction extends BaseAction {
    private static final Logger logger = LoggerFactory.getLogger(UserAction.class);
    /**
     * 
     */
    private static final long serialVersionUID = 9089297921676327771L;
    
    @Resource
    private IRedisDao redisDao;
    @Resource
    private IUserService userService;
    @Resource
    private IUserLogService userLogService;
    @Resource
    private IUserSecurityService userSecurityService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IVerifyCodeService verifyCodeService;
    @Resource
    private IBusinessConfigService businessConfigService;
    

    /**
     * 
     * Description: 登录
     * 
     * @param
     * @return String
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午12:10:10
     */
    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> login(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String result = FbdConstants.RESULT_TRUE;
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
      //  String mobile = request.getParameter("mobile");
        UserModel user = null;
        try {
            user = this.userService.valiUserByUserId(userId);
            // 验证密码
            this.validateLoginPwd(password, user.getPassword());
            // 验证码验证
//            this.validateLoginVerifyCode(request);
            // 登录成功
            this.userSecurityService.loginSucess(user.getUserId(),FbdCoreConstants.userType.P);
            String token = PKGenarator.getId();
            resultMap.put("token", token);
            this.loginSucess(user, request,token);
            //查询用户材料信息表
//            CrowdfundUserStuffModel userStuffModle = this.userService.findUserStuffByUserId(userId);
//            if(null != userStuffModle){
//            	if(null != userStuffModle.getLendAuthPhoto() && !"".equals(userStuffModle.getLendAuthPhoto())){
//                	user.setPhoto(userStuffModle.getLendAuthPhoto());
//                }
//            }
            resultMap.put(SUCCESS, true);
            this.loginSucess(user, request,token);
        } catch (ApplicationException e) {// 用户名有误
        	result = e.getMessage();
            try {
				this.loginError(user, request);
			} catch (ApplicationException e1) {
				result = e1.getMessage();
			}
            resultMap.put(SUCCESS, false);
        } catch (LoginException e) {// 用户名有误 
            result = e.getMessage();
            resultMap.put(SUCCESS, false);
        }catch (Exception e) {// 出现其他异常
            e.printStackTrace();
            result = "登录时出现异常";
            resultMap.put(SUCCESS, false);
        }
        resultMap.put(FbdConstants.JSON_MSG_KEY, result);
        resultMap.put(FbdConstants.JSON_IS_SHOW_VERIFYCODE, request.getSession().getAttribute(FbdConstants.IS_SHOW_VERIFYCODE));
        return resultMap;
    }
    
    /**
     * 
     * Description: 登录到P2P
     * 
     * @param
     * @return String
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午12:10:10
     */
    @RequestMapping(value = "/valiLogin.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> valiLogin(HttpServletRequest request) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	String result = FbdConstants.RESULT_TRUE;
    	UserModel user = null;
    	try {
    		String userId = getUserId(request);
    		//获取配置
        	String ticket = SpringPropertiesHolder.getProperty("tianxiatou.ticket");
        	String desTicket = HashCrypt.getDigestHash(userId + ticket, FbdConstants.ENCODE_TYPE_MD5);
        	Map<String, String> params = new HashMap<String, String>();
        	params.put("userId", userId);
        	params.put("ticket", desTicket);
        	Map<String, String> ticketResult = TicketUtil.doPost(params, SpringPropertiesHolder.getProperty("tianxiatou.ticket.url") + TicketUtil.TOLOGIN_URL);
        	logger.debug("ticketResult:" + ticketResult);
        	if(null != ticketResult && FbdConstants.RESULT_TRUE.equals(ticketResult.get("msg"))){
        		//登录成功，解密sessionid
        		DesUtils des = new DesUtils();
        		String sessionid = des.decrypt(ticketResult.get("id"));
        		logger.info("解密后的sessionid：" + sessionid);
                resultMap.put("id", sessionid);
        	} else {
        		result = ticketResult.get("msg");
        	}
    	} catch (ApplicationException e) {// 用户名有误
    		result = e.getMessage();
    	} catch (LoginException e) {// 用户名有误
    		result = e.getMessage();
    	}catch (Exception e) {// 出现其他异常
    		e.printStackTrace();
    		result = "登录时出现异常";
    	}
    	resultMap.put("info", SpringPropertiesHolder.getProperty("tianxiatou.ticket.url"));
    	resultMap.put(FbdConstants.JSON_MSG_KEY, result);
    	return resultMap;
    }
   
    
    /**
     * 验证单点过来的ticket
     */
    private void validateTicket(String desTicket, String userId) {
    	if(StringUtils.isEmpty(desTicket) || StringUtils.isEmpty(userId)){
    		throw new ApplicationException("用户名或票据为空");
    	}
    	//获取配置
    	String ticket = SpringPropertiesHolder.getProperty("tianxiatou.ticket");
    	String desTicketLocal = HashCrypt.getDigestHash(userId + ticket, FbdConstants.ENCODE_TYPE_MD5);
    	logger.info("-----------ticket校验：desTicket:" + desTicket + " desTicketLocal：" + desTicketLocal);
    	if (!desTicket.equals(desTicketLocal)) {
    		logger.info("-----------ticket校验未通过");
            throw new ApplicationException("验证票据失败");
        }
    	logger.info("-----------ticket校验通过");
    }
    
//    /**
//     * 
//     * Description: 手机短信登录
//     * 
//     * @param
//     * @return String
//     * @throws
//     * @Author haolingfeng Create Date: 2014-12-13 下午12:10:10
//     */
//    @RequestMapping(value = "/mobilelogin.html", method = RequestMethod.POST)
//    public @ResponseBody
//    Map<String, Object> mobilelogin(HttpServletRequest request) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        String mobile = request.getParameter("sendTarget");
//        try {
//        	
//            UserModel user = this.userService.valiUserByUserId(mobile);
//            if(user == null){
//                throw new ApplicationException("用户不存在，请先注册");
//            }
//            this.verifyCodeService.checkVerifyCode(request.getParameter("verifyCode").toString(), 
//                    FbdConstants.NODE_TYPE_MOBILE_LOGIN, user.getUserId(), mobile, "mobile");
//            // 保存用户
////            if(user == null){
////                user = new UserModel();
////                user.setUserId(PKGenarator.getUserId());
////                user.setMobile(mobile);
////                String password = SecurityCode.get8SecurityCode();
////                user.setPassword(password);
////                this.userService.insert(user, null);
////                
////                //发送登录默认密码
////                this.sendDefaultPwdMessage(password, mobile);
////            }
//            // 登录成功
//            this.loginSucess(user, request);
//            resultMap.put(SUCCESS, true);
//        } catch (ApplicationException e) {
//            resultMap.put(SUCCESS, false);
//            resultMap.put(MSG, e.getMessage());
//        }catch (Exception e) {
//            resultMap.put(SUCCESS, false);
//            resultMap.put(MSG, e.getMessage());
//        }
//        return resultMap;
//    }
    
    

    /**
     * 
     * Description: 验证登录时的验证码
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午11:49:26
     */
    private void validateLoginVerifyCode(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(
                FbdConstants.IS_SHOW_VERIFYCODE);
        boolean isValidate = (obj == null) ? false : true;
        if (isValidate) {
            this.validateCode(request);
        }
    }

    /**
     * 
     * Description: 登录成功
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:58:57
     */
    private void loginSucess(UserModel user, HttpServletRequest request,String token) {
        this.userLogService.addSuccessLog(user.getUserId(),WebUtil.getIpAddr(request));
//        Date lastLoginTime = this.userLogService.getLastLoginTime(user.getUserId());
        String loginTime="";
//        if(lastLoginTime != null){
//            loginTime = DateUtil.dateTime2Str(lastLoginTime, null);
//        }else{
//            loginTime = DateUtil.dateTime2Str(new Date(), null);
//        }
       
        String mobileTerminalFlag = request.getHeader("mobileTerminalFlag");
        if(!StringUtils.isEmpty(mobileTerminalFlag)){  //终端请求
            UserLoginBean userBean = new UserLoginBean();
            userBean.setUserId(user.getUserId()==null?"":user.getUserId());
            userBean.setIsAuth(user.getIsAuth()==null?"0":user.getIsAuth());
            userBean.setLastLoginTime(loginTime);
            userBean.setThirdNo(user.getThirdAccount()==null?"":user.getThirdAccount());
            userBean.setRealName(user.getRealName()==null?"":user.getRealName());
            String userStr = JsonHelper.getStrFromObject(userBean);
            this.redisDao.set(token, userStr,60*30);  //session保存30分钟
            
            if(this.redisDao.exists("showVerifyCode_"+user.getMobile())){
            	this.redisDao.del("showVerifyCode_"+user.getMobile());
            }
        }else{
           request.getSession().setAttribute(FbdCoreConstants.LAST_LOGIN_TIME, loginTime);
           request.getSession().setAttribute(FbdCoreConstants.SESSION_USERID, user.getUserId()==null?"":user.getUserId());
           request.getSession().setAttribute(FbdCoreConstants.SESSION_REALNAME, user.getRealName()==null?"":user.getRealName());
           request.getSession().setAttribute(FbdCoreConstants.SESSION_ISBORROWER, user.getIsBorrower()==null?"":user.getIsBorrower());
           request.getSession().setAttribute(FbdCoreConstants.SESSION_THIRDNO,user.getThirdAccount()==null?"":user.getThirdAccount());
           request.getSession().setAttribute(FbdCoreConstants.SESSION_USERPHOTO,user.getPhoto()==null?"":user.getPhoto());
           request.getSession().setAttribute(FbdCoreConstants.SESSION_IS_AUTH,user.getIsAuth()==null?"0":user.getIsAuth());
           request.getSession().setAttribute(FbdCoreConstants.SESSION_IS_NICKNAME, user.getNickName()==null?"":user.getNickName());
           request.getSession().setAttribute(FbdConstants.IS_SHOW_VERIFYCODE, false);
           request.getSession().setAttribute(FbdCoreConstants.SESSION_USERMOBILE, user.getMobile()==null?"":user.getMobile());
        }
    }

    /**
     * 
     * Description: 验证密码正确性
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午6:32:08
     */
    private void validateLoginPwd(String pwd, String rightPwd) {
        if (StringUtils.isEmpty(pwd)) {
            throw new ApplicationException("用户名或密码错误");
        } else {
            String encodePwd = HashCrypt.getDigestHash(pwd,
                    FbdConstants.ENCODE_TYPE_MD5);
            if (!rightPwd.equals(encodePwd)) {
                throw new ApplicationException("用户名或密码错误");
            }
        }
    }

    /**
     * 
     * Description:登录失败
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午5:15:15
     */
    private void loginError(UserModel user, HttpServletRequest request) {
        int loginFailCount = this.setLoginFailCount(request);
        int maxCount = getLoginFailMaxCount();
        if (loginFailCount >= maxCount) {// 显示验证码
            request.getSession().setAttribute(
                    FbdConstants.IS_SHOW_VERIFYCODE, true);
        }

        if (null != user) {// 密码错误,需要锁定 用户
            String userId = user.getUserId();
            int pwdErrorMaxCnt = this.getPwdErrorMaxCount();
            int pwdErrorCount = userSecurityService.updatePwdErrorCount(userId, FbdCoreConstants.userType.P);
            if (pwdErrorCount >= pwdErrorMaxCnt) {// 密码错误次数大于最大次数，锁定用户
                userSecurityService.lockUser(userId, FbdCoreConstants.userType.P);
                if (pwdErrorCount == pwdErrorMaxCnt){//==5
                	throw new ApplicationException("用户已经被锁定，请24小后再登录");
                }
            } else if(pwdErrorCount >= maxCount && pwdErrorCount < pwdErrorMaxCnt){//>=3   < 5
            	throw new ApplicationException("用户名或密码错误，您还有" + (pwdErrorMaxCnt - pwdErrorCount) + "次输入机会");
            }
        }
    }

    /**
     * 
     * Description: 获得登录失败的次数
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午5:21:49
     */
    private int setLoginFailCount(HttpServletRequest request) {
        int count;
        Object obj = request.getSession().getAttribute(FbdConstants.LOGIN_FAIL_COUNT_NAME);
        if (obj == null) {
            count = 1;
        } else {
            count = Integer.parseInt(obj.toString()) + 1;
        }
        request.getSession().setAttribute(FbdConstants.LOGIN_FAIL_COUNT_NAME, count);
        return count;
    }

    /**
     * 
     * Description: 获得登录失败的最大次数
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午5:53:09
     */
    private int getLoginFailMaxCount() {
        SysConfigModel dict = sysConfigService
                .getByDisplayName(FbdConstants.USER_LOGIN_FAIL_COUNT_CODE);
        int loginFailMaxCount = Integer.parseInt(dict.getCode());
        return loginFailMaxCount;
    }

    /**
     * 
     * Description: 获得登录失败的最大次数
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午5:53:09
     */
    private int getPwdErrorMaxCount() {
        SysConfigModel dict = sysConfigService
                .getByDisplayName(FbdConstants.USER_PWD_ERROR_COUNT_CODE);
        int pwdErrorMaxCount = Integer.parseInt(dict.getCode());
        return pwdErrorMaxCount;
    }

    /**
     * 
     * Description: 用户注册
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午4:36:00
     */
    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> register(UserModel user, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String result = FbdConstants.RESULT_TRUE;
        try {
            if(user.getUserId() != null){
                this.userService.validateUserId(user.getUserId());
            }else{
                user.setUserId(PKGenarator.getUserId());
            }
            this.userService.validatePwd(user.getPassword(),
                    user.getPassword2());
            
            //验证是否输入邀请码
            BusinessConfigModel config = this.businessConfigService.getByDisplayName("inviteCode_switch");
            if(config!=null){
            	if("1".equals(config.getCode())){ //必填
            		if(StringUtil.isEmpty(user.getInputInviteCode())){
            			throw new ApplicationException("邀请码不能为空！");
            		}
            	}
            }
            UserModel inviteUser = this.userService.getUserByInviteCode(user
                    .getInputInviteCode());
            // 验证码校验
          //  this.validateCode(request);
            //验证手机验证码
            if(request.getParameter("verifyCode")!=null){
                this.verifyCodeService.checkVerifyCode(request.getParameter("verifyCode").toString(), 
                        FbdConstants.NODE_TYPE_MOBILE_REGISTER,user.getUserId(), user.getMobile(), "mobile");
            }
            // 保存用户
            this.userService.insert(user, inviteUser);
            // 登录成功
            String token = PKGenarator.getId();
            this.loginSucess(user, request,token);
            resultMap.put("token", token);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {// 自定义异常
            result = e.getMessage();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, result);
        } catch (Exception e) {
            e.printStackTrace();
            result = "注册时出现异常";
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, result);
        }
       
        return resultMap;
    }

    /**
     * 注册后，同步数据到P2P
     */
    private void syncUser(UserModel user) {
    	try {
			String ticket = SpringPropertiesHolder.getProperty("tianxiatou.ticket");
			if(StringUtil.isNotEmpty(ticket)){
				DesUtils des = new DesUtils();
				String desTicket = HashCrypt.getDigestHash(user.getUserId() + ticket, FbdConstants.ENCODE_TYPE_MD5);
	        	Map<String, String> params = new HashMap<String, String>();
	        	params.put("userId", user.getUserId());
	        	params.put("password", des.encrypt(user.getPassword()));//密码加密后提交
	        	params.put("ticket", desTicket);
	        	Map<String, String> ticketResult = TicketUtil.doPostReg(params, SpringPropertiesHolder.getProperty("tianxiatou.ticket.url") + TicketUtil.TOREGISTER_URL);
	        	logger.debug("-----用户注册信息同步结果:" + ticketResult);
	        	if(null != ticketResult && "true".equals(ticketResult.get("success"))){
	        		logger.info("-----用户信息同步成功--------");
	        	} else {
	        		logger.warn("-----用户信息同步失败，原因：" + ticketResult.get("msg"));
	        	}
			}
		} catch (Exception e) {
			logger.error("------------注册后用户数据同步到众筹错误");
			e.printStackTrace();
		}
	}
    
    /**
     * 
     * Description: 接受同步信息
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午4:36:00
     */
    @RequestMapping(value = "/toregister.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> toregister(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String result = FbdConstants.RESULT_TRUE;
        
        String userId = request.getParameter("userId");
        String desTicket = request.getParameter("ticket");
        String password = request.getParameter("password");
        try {
        	if(StringUtil.isEmpty(password)){
        		throw new ApplicationException("密码不能为空");
        	}
        	//校验ticket
        	this.validateTicket(desTicket, userId);
        	
        	UserModel user = new UserModel();
        	user.setUserId(userId);
        	DesUtils des = new DesUtils();
        	password = des.decrypt(password);
        	user.setPassword(password);
            if(user.getUserId() != null){
                this.userService.validateUserId(user.getUserId());
            }
            // 保存用户
            this.userService.insertSync(user, null);

            resultMap.put(SUCCESS, "true");
            resultMap.put(MSG, "用户数据同步成功");
        } catch (ApplicationException e) {// 自定义异常
            result = e.getMessage();
            resultMap.put(SUCCESS, "false");
            resultMap.put(MSG, result);
        } catch (Exception e) {
            e.printStackTrace();
            result = "用户信息同步时出现异常";
            resultMap.put(SUCCESS, "false");
            resultMap.put(MSG, result);
        }
       
        return resultMap;
    }
    
    /**
     * 
     * Description:手机短信登录发送手机验证码 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-17 上午10:23:11
     */
    @RequestMapping(value = "/sendMobileLoginVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendMobileLoginVerifyCode(HttpServletRequest request,String sendTarget) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if(StringUtil.isEmpty(sendTarget)){
                throw new ApplicationException("手机号为空");
            }
            UserModel user = userService.findUserByMobile(sendTarget);
            
            // 发送验证码前，先进行验证码校验
            //this.validateCode(request);
            
            if(user == null){
                throw new ApplicationException("用户不存在，请先注册");
            }
            
            //发送验证码
            this.verifyCodeService.sendVerifyCode(user.getUserId(),sendTarget,
                    FbdCoreConstants.userType.P,FbdConstants.NODE_TYPE_MOBILE_LOGIN);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "发送验证码失败");
        }
        return resultMap;
    }
    
    
    
    /**
     * 
     * Description:手机短信登录发送手机验证码 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-17 上午10:23:11
     */
    @RequestMapping(value = "/checkValidateCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> checkValidateCode(HttpServletRequest request,String sendTarget) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 发送验证码前，先进行验证码校验
            this.validateCode(request);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "验证码错误");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:注册发送手机验证码 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-17 上午10:23:11
     */
    @RequestMapping(value = "/sendRegisterVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendRegisterVerifyCode(HttpServletRequest request,String sendTarget) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //用户id不为空
//            if(StringUtils.isEmpty(userId)){
//                throw new ApplicationException("用户名不能为空");
//            }
        	//生成用户名
        	String userId = PKGenarator.getUserId();
        	
            // 发送验证码前，先进行验证码校验
            this.validateCode(request);
            //校验手机
            this.verifyCodeService.checkMobileRegister(sendTarget);
            //发送验证码
            this.verifyCodeService.sendVerifyCode(userId,sendTarget,
                    FbdCoreConstants.userType.P,FbdConstants.NODE_TYPE_MOBILE_REGISTER);
            resultMap.put(SUCCESS, true);
            resultMap.put("userId", userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 验证手机是否存在
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午2:43:26
     */
    @RequestMapping(value = "/validateMobile.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> validateMobile(String mobile) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.userService.validateMobile(mobile);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        
        return resultMap;
    }
    /**
     * 
     * Description: 验证用户是否存在
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午2:43:26
     */
    @RequestMapping(value = "/validateUserId.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> validateUserId(String userId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String result = FbdConstants.RESULT_TRUE;
        ;
        try {
            this.userService.validateUserId(userId);
        } catch (Exception e) {
            result = e.getMessage();
        }
        resultMap.put(FbdConstants.JSON_MSG_KEY, result);
        return resultMap;
    }

    /**
     * 
     * Description: 验证邀请码
     * 
     * @param
     * @return String
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午4:31:09
     */
    @RequestMapping(value = "/validateInviteCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> validateInviteCode(String inputInviteCode)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String result = FbdConstants.RESULT_TRUE;
        ;
        try {
            this.userService.getUserByInviteCode(inputInviteCode);
        } catch (Exception e) {
            result = e.getMessage();
        }
        resultMap.put(FbdConstants.JSON_MSG_KEY, result);
        return resultMap;
    }

    /**
     * 
     * Description: 验证验证码有效性
     * 
     * @param
     * @return String
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午12:25:35
     */
    @RequestMapping(value = "/validateVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> validateVerifyCode(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String result = FbdConstants.RESULT_TRUE;
        try {
            this.validateCode(request);
        } catch (Exception e) {
            result = e.getMessage();
        }
        resultMap.put(FbdConstants.JSON_MSG_KEY, result);
        return resultMap;
    }

    /**
     * 
     * Description: 找回密码
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午4:39:05
     */
    @RequestMapping(value = "/findPassword.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> findPassword(HttpServletRequest request) {
        String verifyCode = request.getParameter("verifyCode");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String userId = request.getParameter("userId");
        String sendTarget = request.getParameter("sendTarget");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String result = FbdConstants.RESULT_TRUE;
        String codeType = FbdConstants.NODE_TYPE_MOBILE_PWD;
        try {
            // 验证码的有效性验证
            this.verifyCodeService.checkVerifyCode(verifyCode, codeType, userId, sendTarget, "mobile");
            // 密码的正确性验证
            this.userService.validatePwd(password1, password2);
            // 修改密码
            this.userService.updatePwd(codeType, password1, userId);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            result = e.getMessage();
            resultMap.put(SUCCESS, false);
        } catch (Exception e) {
            e.printStackTrace();
            result = "修改密码时出错";
            resultMap.put(SUCCESS, false);
        }
        resultMap.put(MSG, result);
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 安全中心变更用户信息
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午4:39:05
     */
    @RequestMapping(value = "/updateUser.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateUser(UserModel user,HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            user.setUserId(userId);
            this.userService.updateByUserId(user);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 更新用户密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 下午3:10:23
     */
    @RequestMapping(value = "/updateUserPwd.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> updateUserPwd(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            String password1 = request.getParameter("password1");
            String password2 = request.getParameter("password2");
            String oldPwd = request.getParameter("oldPwd");
            UserModel user = this.userService.getUser(userId);
            this.validateOldPwd(oldPwd, user.getPassword());
            // 密码的正确性验证
            this.userService.validatePwd(password1, password2);
            // 修改密码
            this.userService.updatePwd(null, password1, userId);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    
    private void validateOldPwd(String oldPwd,String pwd){
        if (StringUtils.isEmpty(oldPwd)) {
            throw new ApplicationException("原密码不能为空");
        }
        String encodePwd = HashCrypt.getDigestHash(oldPwd,
                FbdConstants.ENCODE_TYPE_MD5);
        if (!pwd.equals(encodePwd)) {
            throw new ApplicationException("原密码错误");
        }
    }
//
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
    
    
    /**
     * 
     * Description: 上传头像
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-13 下午2:54:21
     */
    @RequestMapping(value = "/uploadUserPhoto.html", method = RequestMethod.POST)  
    public @ResponseBody
    Map<String, Object> handleFormUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {  
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String errorMsg = "上传图片未成功，请检查图片格式和大小!";
        //保存  
        try {  
            String userId = this.getUserId(request);
            String path = FbdConstants.USER_PHOTO_FILE + DateUtils.dateToString(new Date(), null)+"/";  
            String fileName = file.getOriginalFilename();
            if ("".equals(fileName)) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, errorMsg);
                return resultMap;
            }
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            List<String> allowSuffix = new ArrayList<String>();
            allowSuffix.add(".jpg"); 
            allowSuffix.add(".png");
            allowSuffix.add(".gif");
            allowSuffix.add(".jpeg");
            if (!allowSuffix.contains(suffix.toLowerCase())) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, errorMsg);
                return resultMap;
            }
            String picId=PKGenarator.getId();
            fileName=picId+suffix;
            boolean flag = FTPManagerFactory.getFtpManager().uploadFile(path, fileName, file.getInputStream());
            
            if (flag) {
                //上传图片
                this.userService.updateUserPhoto(userId, path+fileName);
                resultMap.put(SUCCESS,true);
                resultMap.put(MSG,path+fileName);
                request.getSession().setAttribute(FbdCoreConstants.SESSION_USERPHOTO,(path+fileName)==null?"":(path+fileName));
            }else{
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, errorMsg);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG, errorMsg);
        }  
        return resultMap;   
    }  
    
    @RequestMapping(value = "/loginOut.html", method = RequestMethod.POST)  
    public @ResponseBody
       Map<String, Object>  loginOut(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            Enumeration em = request.getSession().getAttributeNames();
            while(em.hasMoreElements()){
             request.getSession().removeAttribute(em.nextElement().toString());
            }
            resultMap.put(SUCCESS, true);
       } catch (Exception e) {
           e.printStackTrace();
           resultMap.put(SUCCESS, false);
       }
        return resultMap;
    }
    
    @RequestMapping(value = "/terminalLoginOut.html", method = RequestMethod.POST)  
    public @ResponseBody Map<String, Object>  terminalLoginOut(HttpServletRequest request){
       Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
        	  logger.info("==========用户退出===============");
             this.userLoginOut(request);
             resultMap.put(SUCCESS, true);
       } catch (Exception e) {
           e.printStackTrace();
           resultMap.put(SUCCESS, false);
       }
        return resultMap;
    }
    
    /**
     * 
     * Description: 查询用户的信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午12:08:39
     */
    @RequestMapping(value = "/getUserInfo.html", method = RequestMethod.POST)  
    public @ResponseBody
       Map<String, Object>  getUser(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            UserModel user = this.userService.getUser(userId);
            user.setPassword("*************************");
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,user);
       } catch (Exception e) {
           logger.error(e.getMessage());
           resultMap.put(SUCCESS, false);
       }
        return resultMap;
    } 
    
    /**
     * 
     * Description:更新用户 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午12:13:10
     */
    @RequestMapping(value = "/updateUserInfo.html", method = RequestMethod.POST)  
    public @ResponseBody
       Map<String, Object>  updateUser(HttpServletRequest request,UserModel user){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            user.setUserId(userId);
            user.setPassword(null);
            this.userService.updateByUserId(user);
            if(!"".equals(user.getPhoto()) && null != user.getPhoto()){
            	request.getSession().setAttribute(FbdCoreConstants.SESSION_USERPHOTO,user.getPhoto()==null?"":user.getPhoto());
            }
            resultMap.put(SUCCESS, true);
       } catch (Exception e) {
           logger.error(e.getMessage());
           resultMap.put(SUCCESS, false);
       }
        return resultMap;
    } 
    
    
    
    /**
     * 
     * Description: 发送手机登录产生的密码
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendDefaultPwdMessage(String password,String mobile){
       Map<String, String> params = new HashMap<String,String>();
       params.put("password",password);
       try{
           logger.info("发送手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_DEFAULT_PASSWORD;
//           SendMessageUtil.sendMessageToMobile(nodeCode, mobile, params);
           logger.info("发送手机短信结束");
       }catch(Exception e){
           logger.error("发送手机短信失败，"+e.getMessage());
       }
    }


    
    /**
     * 
     * Description: 查询用户列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/getUserList.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getInvestor(UserModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            SearchResult<Map<String,Object>> pageList = this.userService.getUserList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:更新用户 材料信息表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     * Create Date: 2015-3-28 下午12:13:10
     */
    @RequestMapping(value = "/updateUserStuffInfo.html", method = RequestMethod.POST)  
    public @ResponseBody
       Map<String, Object>  updateUserStuffInfo(HttpServletRequest request,CrowdfundUserStuffModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            this.userService.updateUserStuffInfo(model);
            UserModel user = new UserModel();
            user.setUserId(userId);
            user.setPhoto(model.getLendAuthPhoto());
            this.userService.updateByUserId(user);
            resultMap.put(SUCCESS, true);
       } catch (Exception e) {
           logger.error(e.getMessage());
           resultMap.put(SUCCESS, false);
       }
        return resultMap;
    } 
    
    
    
    /**
     * 
     * Description: 更新验证手机号码获取Url
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 上午10:22:15
     */
    @RequestMapping(value = "/updateMobile.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> updateMobile(HttpServletRequest request,HttpServletResponse response) {
        logger.info("============用户更新手机号码开始调用开始============");
        
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            String userId = this.getUserId(request);
            
            String newMobile = request.getParameter("newMobile").toString();
            
            String oldMobile = request.getParameter("oldMobile").toString();
            
            //验证短信验证码是否正确
            try{
                this.verifyCodeService.checkVerifyCode(request.getParameter("verifyCode1").toString(), 
                        FbdConstants.NODE_TYPE_MOBILE_VERIFY_OLD, userId,oldMobile, "mobile");
            }catch(Exception e){
            	throw new ApplicationException("原手机号-"+e.getMessage());
            }
            try{
            	this.verifyCodeService.checkVerifyCode(request.getParameter("verifyCode2").toString(), 
                        FbdConstants.NODE_TYPE_MOBILE_VERIFY_NEW,userId, newMobile , "mobile");
            }catch(Exception e){
            	throw new ApplicationException("新手机号-"+e.getMessage());
            }
            
            //查询用户信息
            UserModel user = this.userService.getUser(userId);
            
            //根据新手机号查询号码是否存在
            UserModel mobileUser = this.userService.findUserByMobile(newMobile);
            if(mobileUser != null){
            	throw new ApplicationException("手机号码已经存在！");
            }
            user.setMobile(newMobile) ;
            this.userService.updateByUserId(user);
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e1){
        	resultMap.put(SUCCESS, false);
        	resultMap.put(MSG, e1.getMessage());
        }catch (Exception e) {
        	resultMap.put(SUCCESS, false);
        	resultMap.put(MSG,"请求失败");
        }
        return resultMap;
    } 
    
    /**
     * 查询当前网站注册人数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getWebUserCount.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> getWebUserCount(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        int count = 0;
    	try{
    		UserModel user = new UserModel();
    		count = (int) this.userService.getWebUserCount(user);
    		resultMap.put(SUCCESS,true);
    		resultMap.put("count",count);
    	}catch(Exception e){
    		resultMap.put(SUCCESS,false);
    		resultMap.put("count",count);
    	}
    	return resultMap;
    }
    
    
    /**
     * 测试数据加锁
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/testDataSourceLock.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> testDataSourceLock(HttpServletRequest request,String userId) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
    	 
    		this.userService.saveDataSourceLock(userId);
    		resultMap.put(SUCCESS,true);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put(SUCCESS,false);
    	}
    	return resultMap;
    }
    
    
    /**
     * 数据加密
     * @param request
     * @param response
     * @return
     */
//    @RequestMapping(value = "/userBlockChainKeyEncode.html", method = RequestMethod.GET)
//    public @ResponseBody Map<String,Object> userBlockChainKeyEncode(HttpServletRequest request,UserModel model) {
//        Map<String,Object> resultMap = new HashMap<String,Object>();
//    	try{
//    		
//    		List<UserModel> userList = this.userService.selectList(model);
//    		DesUtils des = new DesUtils();
//    		for(UserModel user:userList){
//    			if(!StringUtil.isEmpty(user.getBlockChainSecretKey())){
//    				user.setBlockChainSecretKey(des.encrypt(user.getBlockChainSecretKey()));
//        			this.userService.updateByUserId(user);
//    			}
//    		}
//    		resultMap.put(SUCCESS,true);
//    	}catch(Exception e){
//    		e.printStackTrace();
//    		resultMap.put(SUCCESS,false);
//    	}
//    	return resultMap;
//    }
    
    
    
    /**
     * 
     * Description:更新用户安全眼状态
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午12:13:10
     */
    @RequestMapping(value = "/updateSafetyEyeFlag.html", method = RequestMethod.POST)  
    public @ResponseBody Map<String, Object>  updateSafetyEyeFlag (HttpServletRequest request,UserModel user){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            UserModel userModel = this.userService.findByUserId(userId);
            userModel.setSafetyEyeFlag(user.getSafetyEyeFlag());
            this.userService.updateByUserId(userModel);
            resultMap.put("safetyEyeFlag", user.getSafetyEyeFlag());
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
           resultMap.put(SUCCESS, false);
        }
        return resultMap;
    } 

    
}
