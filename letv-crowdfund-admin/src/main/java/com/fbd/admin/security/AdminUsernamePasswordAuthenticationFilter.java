package com.fbd.admin.security;

import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fbd.admin.app.admin.service.IAdminService;
import com.fbd.admin.app.config.service.ISysConfigService;
import com.fbd.admin.app.user.service.IUserService;
import com.fbd.core.app.admin.model.AdminModel;
import com.fbd.core.app.user.model.UserSecurityModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.util.HashCrypt;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户登录验证
 *
 * @author dongzhongwei
 * @version 1.0
 *
 */

public class AdminUsernamePasswordAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {
    public static final String VALIDATE_CODE = "validateCode";
    public static final String USERNAME = "j_username";
    public static final String PASSWORD = "j_password";
    public static final String EMPLOYEENO = "j_employeeNo";

    @Resource
    private IAdminService adminService;
    
    @Resource
    private ISysConfigService sysConfigService;
    
    @Resource
    private IUserService userService;
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: "+ request.getMethod());
        }
        
        //密码错误次数
        int pwdErrorCount = Integer.parseInt(sysConfigService.getCodeByDisplayName("admin_pwdErrorCount")==null?"0":sysConfigService.getCodeByDisplayName("admin_pwdErrorCount"));
        //登录失败次数
        int loginFailCount = Integer.parseInt(sysConfigService.getCodeByDisplayName("admin_loginFailCount")==null?"0":sysConfigService.getCodeByDisplayName("admin_loginFailCount"));
        
        String username = obtainUsername(request);
        //判断用户名是否为空
        if (StringUtils.isEmpty(username)) {
            throw new AuthenticationServiceException("{msg:'请填写账号/手机号'}");
        }
        //员工编号
        String employeeNo = obtainEmployeeNo(request);
        if (StringUtils.isEmpty(employeeNo)) {
            throw new AuthenticationServiceException("{msg:'请填写员工号'}");
        }
        String password = obtainPassword(request);
        if (StringUtils.isEmpty(password)) {
            throw new AuthenticationServiceException("{msg:'请填写登录密码'}");
        }
        //加密
        password=HashCrypt.getDigestHash(password,"MD5");
        
        AdminModel adminModel = adminService.getAdminByAdminId(username);
        
        int errorCount = 0;
        
        if (adminModel != null && FbdCoreConstants.userStatus.disable.equals(adminModel.getStatus())) {
            throw new AuthenticationServiceException("{msg:'用户已禁用'}");
        }
        
        if (adminModel != null && FbdCoreConstants.userStatus.lock.equals(adminModel.getStatus())) {
            throw new AuthenticationServiceException("{msg:'用户已锁定'}");
        }
        
        if (adminModel != null) {
            errorCount = adminModel.getLoginFailedNum()==null?0:adminModel.getLoginFailedNum();
        }
        
        // 检测验证码
        if(errorCount>=pwdErrorCount){
            StringBuilder result = new StringBuilder();
            result.append("'isShowCode':true");
            String validateCodeParameter = obtainValidateCodeParameter(request);
            if (StringUtils.isEmpty(validateCodeParameter)) {
                result.append(",'msg':'请填写验证码'");
                throw new AuthenticationServiceException("{"+result.toString()+"}");
            }
            
            HttpSession session = request.getSession();
            String sessionValidateCode = obtainSessionValidateCode(session);
            // 让上一次的验证码失效
            session.setAttribute("codeImage", null);
            if (StringUtils.isEmpty(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
                result.append(",'msg':'请填写正确的验证码'");
                throw new AuthenticationServiceException("{"+result.toString()+"}");
            }
        }

        if (adminModel == null || !password.equals(adminModel.getPassword()) || !employeeNo.equals(adminModel.getEmployeeNo())) {
            errorCount++;
            UserSecurityModel userSecurity = new UserSecurityModel();
            userSecurity.setUserType(FbdCoreConstants.userType.A);
            userSecurity.setUserId(username);
            userSecurity.setLoginFailedNum(errorCount);
            if (errorCount>=loginFailCount) {
                userSecurity.setStatus(FbdCoreConstants.userStatus.lock);
            }
            userSecurity.setLoginFailedTime(new Date());
            userService.modifyUserSecurity(userSecurity);
            StringBuilder result = new StringBuilder();
            if(errorCount>=pwdErrorCount){
                result.append("'isShowCode':true");
            }
            result.append(",msg:'用户名、员工号或密码错误！'");
            throw new AuthenticationServiceException("{"+result.toString()+"}");
        }
        
        UserSecurityModel userSecurity = new UserSecurityModel();
        userSecurity.setUserType(FbdCoreConstants.userType.A);
        userSecurity.setUserId(username);
        userSecurity.setLoginFailedNum(0);
        userSecurity.setLastLoginTime(new  Date());
        userSecurity.setStatus(FbdCoreConstants.userStatus.normal);
        userService.modifyUserSecurity(userSecurity);
        
        // UsernamePasswordAuthenticationToken实现 Authentication
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(adminModel.getAdminId(), password);

        // 允许子类设置详细属性
        setDetails(request, authRequest);

        // 运行UserDetailsService的loadUserByUsername 再次封装Authentication
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainValidateCodeParameter(HttpServletRequest request) {
        Object obj = request.getParameter(VALIDATE_CODE);
        return null == obj ? "" : obj.toString();
    }

    protected String obtainSessionValidateCode(HttpSession session) {
        Object obj = session.getAttribute("codeImage");
        return null == obj ? "" : obj.toString();
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        Object obj = request.getParameter(USERNAME);
        return null == obj ? "" : obj.toString();
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        Object obj = request.getParameter(PASSWORD);
        return null == obj ? "" : obj.toString();
    }
    
    protected String obtainEmployeeNo(HttpServletRequest request) {
        Object obj = request.getParameter(EMPLOYEENO);
        return null == obj ? "" : obj.toString();
    }

}
