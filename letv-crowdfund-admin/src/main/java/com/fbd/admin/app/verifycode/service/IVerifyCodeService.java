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

package com.fbd.admin.app.verifycode.service;

import com.fbd.core.app.verifycode.model.VerifyCodeModel;

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
     * Description: 发送验证码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-27 下午12:47:42
     */
    void sendVerifyCode(String messageNodeCode,String mobileNumber,String userId);
    
    
    /**
     * Description: 根据userId、userType和verifyType查询有效的验证码
     *
     * @param 
     * @return VerifyCodeModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午11:19:36
     */
    VerifyCodeModel getVerifyCodeByUserAndVerifyType(String userId,String userType,String sendTarget,String verifyType);


    /**
     * Description: 发送邮件验证码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-31 下午6:17:32
     */
    void sendEmailVerifyCode(String messageNodeCode, String email,String userId);
    
}
