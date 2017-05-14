/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: VerifyCodeServiceImpl.java 
 *
 * Created: [2014-12-15 下午3:41:37] by haolingfeng
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

package com.fbd.web.app.verifycode.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.app.message.dao.IMessageTemplateDao;
import com.fbd.core.app.message.model.MessageTemplateModel;
import com.fbd.core.app.sms.service.impl.MessageBO;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.verifycode.dao.IVerifyCodeDao;
import com.fbd.core.app.verifycode.model.VerifyCodeModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.MessageUtils;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.ValidateUtils;
import com.fbd.core.util.securityCode.SecurityCode;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.config.service.ISysConfigService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:验证码
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements IVerifyCodeService {
    @Resource
    private IUserDao userDao;
    @Resource
    private IVerifyCodeDao verifyCodeDao;
    @Resource
    private IMessageTemplateDao messageTemplateDao;
    @Resource
    private MessageBO messageBO;
    @Resource
    private ISysConfigService sysConfigService;
    
    /**
     * 
     * Description: 发送验证码
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendVerifyCode(String userId,String sendTarget, String userType,String codeType){
        String securityCode = SecurityCode.getVerifyCode();
    	if("true".equals(FbdCoreConstants.isClientEnvironment)){
    		securityCode = "111111";
    	}
        Map<String, String> params = new HashMap<String, String>();
        params.put(FbdConstants.MESSAGE_TEMPLATE_PROP_VERIFYCODE, securityCode);
        String sendResult = FbdConstants.RESULT_TRUE;
        String sendInfo = "";
        try {
        	// 发送
	       	if(!"true".equals(FbdCoreConstants.isClientEnvironment)){
	            MessageUtils.sendSMS(codeType, params, sendTarget);
	    	}
        } catch (Exception e) {
            sendResult = FbdConstants.RESULT_FALSE;
            sendInfo = e.getMessage();
        }
        // 保存验证码
        this.saveVerifyCode(userId, sendTarget, securityCode,
                codeType, userType,sendResult,sendInfo);
    }
    
    /**
     * 
     * Description:找回密码参数验证 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 下午12:28:00
     */
    public UserModel checkMobileFindPwd(String sendTarget){
        if (StringUtils.isEmpty(sendTarget) || !ValidateUtils.validateMobile(sendTarget)){
            throw new ApplicationException("手机号不正确");
        }        
        UserModel userModel = this.userDao.findUserByMobile(sendTarget);
        if(userModel==null){
            throw new ApplicationException("手机号不正确");
        }else{
            return userModel;
        }
    }
    
    /**
     * 
     * Description:注册参数验证 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 下午12:28:00
     */
    public void checkMobileRegister(String sendTarget){
        if (StringUtils.isEmpty(sendTarget) || !ValidateUtils.validateMobile(sendTarget)){
            throw new ApplicationException("手机号不正确");
        }        
        UserModel userModel = this.userDao.findUserByMobile(sendTarget);
        if(userModel!=null){
            throw new ApplicationException("手机号已被使用");
        }
    }
    
    /**
     * 
     * Description:验证原手机号(修改手机号前)
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 下午12:28:00
     */
    public void checkOldMobile(String userId,String sendTarget){
        if (StringUtils.isEmpty(sendTarget) || !ValidateUtils.validateMobile(sendTarget)){
            throw new ApplicationException("手机号不正确");
        }        
        UserModel userModel = this.userDao.findUserByMobile(sendTarget);
        if(userModel==null){
            throw new ApplicationException("手机号不正确");
        }else{
            if(!userId.equals(userModel.getUserId())){
                throw new ApplicationException("手机号不正确");
            }
        }
    }
    /**
     * 
     * Description: 手机认证发送验证码
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:42:31
     */
    public void checkSendMobileAuthCode(String userId,String sendTarget) {
        if (StringUtils.isEmpty(sendTarget) || !ValidateUtils.validateMobile(sendTarget)){
            throw new ApplicationException("手机号不正确");
        }
        UserModel user = this.userDao.findByUserId(userId);
        if (user.getMobile()== null || !user.getMobile().equals(sendTarget)){
            UserModel userModel = this.userDao.findUserByMobile(sendTarget);
            if(userModel!=null && !userModel.getUserId().equals(userId)){
                throw new ApplicationException("手机号被使用");
            }
        }
    }
    /**
     * 
     * Description: 修改手机，新手机号,参数验证
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:42:31
     */
    public void checkSendMobileUpdateCodeNew(String userId,String sendTarget) {
        if (StringUtils.isEmpty(sendTarget) || !ValidateUtils.validateMobile(sendTarget)){
            throw new ApplicationException("手机号不正确");
        }
        UserModel userModel = this.userDao.findUserByMobile(sendTarget);
        if(userModel!=null && !userModel.getUserId().equals(userId)){
            throw new ApplicationException("手机号被使用");
        }
    }
    
    /**
     * 
     * Description: 发送邮件认证邮箱
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午4:28:38
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendEmailAuthEmail(String sendTarget, String userId,
            String userType) {
        if(StringUtils.isEmpty(sendTarget) || !ValidateUtils.validateEmail(sendTarget)){
            throw new ApplicationException("输入的邮箱不正确，请重新输入");
        }
        // 生成验证码
        String securityCode = SecurityCode.getVerifyCode();
        Map<String, String> params = new HashMap<String, String>();
        params.put(FbdConstants.MESSAGE_TEMPLATE_PROP_VERIFYCODE, securityCode);
        String sendResult = FbdConstants.RESULT_TRUE;
        String sendInfo = "";
        try {
            // 发送
            MessageUtils.sendEmail(FbdConstants.NODE_TYPE_EMAIL_AUTH,params, sendTarget,
                    FbdConstants.EMAIL_SUBJECT_EMAIL_AUTH);
        } catch (Exception e) {
            sendResult = FbdConstants.RESULT_FALSE;
            sendInfo = e.getMessage();
        }
        this.saveVerifyCode(userId, sendTarget, securityCode,
                FbdConstants.NODE_TYPE_EMAIL_AUTH, userType,sendResult,sendInfo);
    }


    /**
     * 
     * Description:验证验证码的正确性
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-16 下午5:11:31
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkVerifyCode(String sVerifyCode, String codeType,
            String userId) {
        if (StringUtils.isEmpty(sVerifyCode)) {
            throw new ApplicationException("验证码不正确");
        }
        VerifyCodeModel model = this.verifyCodeDao.selectByTypeAndTarget(
                codeType, FbdCoreConstants.userType.P,userId);
        if (model == null) {
            throw new ApplicationException("验证码不正确");
        }
        
        String code = model.getVerifyCode();
        if (!sVerifyCode.equals(code)) {
            throw new ApplicationException("验证码不正确");
        }
        //更新验证码的状态为无效
        this.updateVerifyCode(model);
    }
    
    /**
     * 
     * Description:验证验证码的正确性
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-16 下午5:11:31
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkVerifyCode(String sVerifyCode, String codeType,
            String userId,String sendTarget,String sFlag) {
    	String preStr = "";
        if(sFlag.equals("mobile")){
        	preStr = "手机";
            if (StringUtils.isEmpty(sendTarget) || !ValidateUtils.validateMobile(sendTarget)) {
                throw new ApplicationException("发送手机格式不正确");
            }
        }else if(sFlag.equals("email")){
        	preStr = "邮箱";
            if (StringUtils.isEmpty(sendTarget) || !ValidateUtils.validateEmail(sendTarget)) {
                throw new ApplicationException("发送邮箱格式不正确");
            }
        }
        
        if (StringUtils.isEmpty(sVerifyCode)) {
            throw new ApplicationException(preStr+"验证码不正确");
        }
        VerifyCodeModel model = this.verifyCodeDao.selectByTypeAndTarget(
                codeType, FbdCoreConstants.userType.P,userId);
        if (model == null) {
            throw new ApplicationException(preStr+"验证码不正确");
        }
        
     /*   if(!sendTarget.equals(model.getSendTarget())){
            throw new ApplicationException("输入"sendTarget);
        }*/
        
        String code = model.getVerifyCode();
        if (!sVerifyCode.equals(code)) {
            throw new ApplicationException(preStr+"验证码不正确");
        }
        //更新验证码的状态为无效
        this.updateVerifyCode(model);
    }
    
    
    /**
     * 
     * Description: 验证正确的验证码是否已经存在
     *
     * @param 
     * @return boolean
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-10 下午6:56:08
     */
    private  VerifyCodeModel getVerifyCodeValid(String codeType,String userId) {
        VerifyCodeModel model = this.verifyCodeDao.selectByTypeAndTarget(
                codeType,FbdCoreConstants.userType.P, userId);
        return model;
    }
    
    /**
     * 
     * Description: 保存验证码信息
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午4:13:08
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveVerifyCode(String userId, String sendTarget,
            String securityCode, String verifyType, String userType,String sendResult,String sendInfo) {
        VerifyCodeModel model = new VerifyCodeModel();
        model.setId(PKGenarator.getId());
        model.setUserId(userId);
        model.setUserType(userType);
        model.setSendTarget(sendTarget);
        model.setVerifyCode(securityCode);
        model.setVerifyType(verifyType);
        model.setGenTime(new Date());
        model.setStatus(FbdConstants.VERIFY_CODE_STATUS_VALID);
        // 改为从字典取
        SysConfigModel dict = sysConfigService
                .getByDisplayName(FbdConstants.VERIFYCODE_DEAD_LIMIT);
        int deadlineLimit = Integer.parseInt(dict.getCode());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + deadlineLimit);
        model.setDeadTime(cal.getTime());
        model.setSendResult(sendResult);
        model.setSendId(sendInfo);
        verifyCodeDao.save(model);
    }

    /**
     * 
     * Description: 更新验证码表,验证码使用后更新为无效
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-16 下午4:00:17
     */
    public void updateVerifyCode(VerifyCodeModel model) {
        model.setStatus(FbdConstants.VERIFY_CODE_STATUS_INVALID);
        this.verifyCodeDao.update(model);
    }

    /**
     * 发送验证码
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendMobileVerifyCode(String userId, String userType,
            String codeType) {
        UserModel user = this.userDao.findByUserId(userId);
        if(StringUtils.isEmpty(user.getMobile())){
            throw new ApplicationException("手机号未绑定，请先绑定手机号");
        }
        this.sendVerifyCode(userId, user.getMobile(), userType, codeType);
    }
    
    
    
    /**
     * 
     * Description: 发送邀请码
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-5 下午4:07:03
     */
    public void sendInviteCode(String userId,String mobile) {
        if(StringUtils.isEmpty(mobile)|| !ValidateUtils.validateMobile(mobile)){
            throw new ApplicationException("输入有误，请重新输入");
        }
        UserModel user = this.userDao.findByUserId(userId);
        Map<String, String> params = new HashMap<String, String>();
        params.put(FbdConstants.MESSAGE_TEMPLATE_PROP_REALNAME, user.getRealName());
        params.put(FbdConstants.MESSAGE_TEMPLATE_PROP_INVITECODE, mobile);
        try {
            // 发送
           MessageUtils.sendSMS(FbdConstants.NODE_TYPE_INVITECODE_MOBILE, params, mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 
     * Description: 发送邀请码(手机号作为邀请码)
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-5 下午4:07:03
     */
    public void sendMobileInviteCode(String userId,String mobile) {
        if(StringUtils.isEmpty(mobile)|| !ValidateUtils.validateMobile(mobile)){
            throw new ApplicationException("输入有误，请重新输入");
        }
        UserModel user = this.userDao.findByUserId(userId);
        Map<String, String> params = new HashMap<String, String>();
        
        String realName = user.getRealName();
        if(realName==null || "".equals(realName)){
        	realName = user.getMobile();
        }
        params.put(FbdConstants.MESSAGE_TEMPLATE_PROP_REALNAME,realName);
        params.put(FbdConstants.MESSAGE_TEMPLATE_PROP_INVITECODE, user.getMobile());
        try {
            // 发送
           MessageUtils.sendSMS(FbdConstants.NODE_TYPE_INVITECODE_MOBILE, params, mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public String getInviteContent(String nodeCode) {
		if(StringUtils.isEmpty(nodeCode)){
			return null;
		}
		return this.verifyCodeDao.getInviteContent(nodeCode);
	}
}
