/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
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

package com.fbd.admin.app.verifycode.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.verifycode.service.IVerifyCodeService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.util.ValidateUtils;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 短信验证码
 *
 * @author dongzhongwei
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
    private static final long serialVersionUID = -435637953567371445L;
    @Resource
    private IVerifyCodeService verifyCodeService;
    
    
    /**
     * Description: 发送短信验证码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-27 下午12:47:42
     */
    @RequestMapping(value = "/sendVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendVerifyCode(String messageNodeCode,String mobileNumber,String userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(mobileNumber)) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "请填写手机号码");
            return resultMap;
        }
        if (!ValidateUtils.validateMobile(mobileNumber)) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "请填写有效的手机号码");
            return resultMap;
        }
        try {
            verifyCodeService.sendVerifyCode(messageNodeCode,mobileNumber,userId);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 发送邮件验证码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-31 下午6:17:32
     */
    @RequestMapping(value = "/sendEmailVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendEmailVerifyCode(String messageNodeCode,String email,String userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(email)) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "请填写邮箱");
            return resultMap;
        }
        if (!ValidateUtils.validateEmail(email)) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "请填写有效的邮箱");
            return resultMap;
        }
        try {
            verifyCodeService.sendEmailVerifyCode(messageNodeCode,email,userId);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }

}
