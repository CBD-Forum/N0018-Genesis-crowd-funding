/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: VerifyCodeService.java 
 *
 * Created: [2014-12-15 下午3:41:13] by haolingfeng
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

package com.fbd.web.app.verifycode.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fbd.core.app.user.model.UserModel;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:验证码
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IVerifyCodeService {
    
    /**
     * 
     * Description: 发送验证码
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 下午12:26:22
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendVerifyCode(String userId,String sendTarget, String userType,String codeType);
    
    /**
     * 
     * Description: 发送验证码
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:42:31
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendMobileVerifyCode(String userid,String userType,String codeType);
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
    public UserModel checkMobileFindPwd(String sendTarget);
    
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
    public void checkOldMobile(String userId,String sendTarget);
    
    /**
     * 
     * Description: 手机认证发送验证码参数验证
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:42:31
     */
    public void checkSendMobileAuthCode(String userId,String sendTarget);

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
            String userId);
    
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
            String userId,String sendTarget,String sFlag);
    
    /**
     * 
     * Description: 修改手机，新手机号,参数验证
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:42:31
     */
    public void checkSendMobileUpdateCodeNew(String userId,String sendTarget);
    
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
            String userType);
    
    
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
    public void sendInviteCode(String userId,String mobile);
    
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
    public void checkMobileRegister(String sendTarget);

    /**
     * 获取邀请好友模板内容
     * @return
     */
	public String getInviteContent(String nodeCode);
	
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
    public void sendMobileInviteCode(String userId,String mobile);

}
