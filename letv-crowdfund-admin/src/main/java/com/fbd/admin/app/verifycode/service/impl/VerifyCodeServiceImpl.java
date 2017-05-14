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

package com.fbd.admin.app.verifycode.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.verifycode.service.IVerifyCodeService;
import com.fbd.core.app.admin.dao.IAdminDao;
import com.fbd.core.app.admin.model.AdminModel;
import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.app.verifycode.dao.IVerifyCodeDao;
import com.fbd.core.app.verifycode.model.VerifyCodeModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.MessageUtils;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.securityCode.SecurityCode;

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
    private IVerifyCodeDao verifyCodeDao;
    
    @Resource
    private IAdminDao adminDao;
    
    @Resource
    private ISysConfigDao sysConfigDao;
    
    /**
     * Description: 发送验证码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-27 下午12:47:42
     */
    public void sendVerifyCode(String messageNodeCode,String mobileNumber,String userId) {
        // 生成验证码
        String securityCode = SecurityCode.getVerifyCode();
        //发送短信验证码
        Map<String, String> params = new HashMap<String, String>();
        params.put("authCode", securityCode);
        boolean send = MessageUtils.sendSMS(messageNodeCode, params, mobileNumber);
        // 保存验证码
        if (send) {
            //查询管理员对象
            AdminModel admin = new AdminModel();
            if (!StringUtils.isEmpty(userId)) {
                admin = adminDao.getAdminByUserId(userId);
            }else{
                admin = adminDao.getAdminByMobile(mobileNumber);
            }
            //保存验证码到数据库
            VerifyCodeModel verifyCode = new VerifyCodeModel();
            verifyCode.setId(PKGenarator.getId());
            verifyCode.setUserId(admin.getAdminId());
            verifyCode.setUserType(FbdCoreConstants.userType.A);
            verifyCode.setVerifyCode(securityCode);
            verifyCode.setVerifyType(messageNodeCode);
            verifyCode.setSendTarget(mobileNumber);
            Calendar calendar = Calendar.getInstance();
            //设置生成时间
            verifyCode.setGenTime(calendar.getTime());
            //设置失效时间
            String timeOut = ((SysConfigModel)sysConfigDao.getByDisplayName("verifycodeDeadLimit")).getCode();
            if (StringUtils.isEmpty(timeOut)) {
                timeOut="10";
            }
            calendar.add(Calendar.MINUTE, Integer.parseInt(timeOut));
            verifyCode.setDeadTime(calendar.getTime());
            verifyCode.setSendResult("success");
            verifyCode.setStatus("valid");
            verifyCodeDao.save(verifyCode);
        }
    }

    /**
     * Description: 根据userId、userType和verifyType查询有效的验证码
     *
     * @param 
     * @return VerifyCodeModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午11:19:36
     */
    public VerifyCodeModel getVerifyCodeByUserAndVerifyType(String userId, String userType,String sendTarget,String verifyType) {
        return verifyCodeDao.getVerifyCodeByUserAndVerifyType(userId,userType,sendTarget,verifyType);
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
    public void sendEmailVerifyCode(String messageNodeCode, String email,String userId) {
        // 生成验证码
        String securityCode = SecurityCode.getVerifyCode();
        //发送短信验证码
        Map<String, String> params = new HashMap<String, String>();
        params.put("authCode", securityCode);
        boolean send = MessageUtils.sendEmail(messageNodeCode, params, email, "验证码");
        // 保存验证码
        if (send) {
            //查询管理员对象
            AdminModel admin = adminDao.getAdminByUserId(userId);
            //保存验证码到数据库
            VerifyCodeModel verifyCode = new VerifyCodeModel();
            verifyCode.setId(PKGenarator.getId());
            verifyCode.setUserId(admin.getAdminId());
            verifyCode.setUserType(FbdCoreConstants.userType.A);
            verifyCode.setVerifyCode(securityCode);
            verifyCode.setVerifyType(messageNodeCode);
            verifyCode.setSendTarget(email);
            Calendar calendar = Calendar.getInstance();
            //设置生成时间
            verifyCode.setGenTime(calendar.getTime());
            //设置失效时间
            String timeOut = ((SysConfigModel)sysConfigDao.getByDisplayName("verifycodeDeadLimit")).getCode();
            if (StringUtils.isEmpty(timeOut)) {
                timeOut="10";
            }
            calendar.add(Calendar.MINUTE, Integer.parseInt(timeOut));
            verifyCode.setDeadTime(calendar.getTime());
            verifyCode.setSendResult("success");
            verifyCode.setStatus("valid");
            verifyCodeDao.save(verifyCode);
        }
    }
}
