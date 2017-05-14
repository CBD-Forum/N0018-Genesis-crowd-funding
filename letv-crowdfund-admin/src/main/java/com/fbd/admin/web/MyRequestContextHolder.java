package com.fbd.admin.web;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import com.fbd.core.app.user.model.AdminUserDetails;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 请求上下文钩子
 * 
 * @author dongzhongwei
 * @version 1.0
 * 
 */

public class MyRequestContextHolder {
    private static Logger logger = LoggerFactory.getLogger(MyRequestContextHolder.class);
    /** 国际化处理器 */
    private static LocaleResolver localeResolver;
    
    /**
     * 获取当前http请求
     * @return http请求对象或NULL
     */
    public static HttpServletRequest getCurrentRequest () {
        RequestAttributes requestAttributes = null;
        try {
            requestAttributes = RequestContextHolder.currentRequestAttributes();
        } catch (IllegalStateException e) {
            logger.debug("failure to get currentRequestAttributes, cause by {}", e.getMessage());
        }
        if (requestAttributes == null) {
            logger.debug("requestAttributes is null");
            return null;
        }
        Object request = requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        return (request != null && request instanceof HttpServletRequest) ? (HttpServletRequest) request : null;
    }
    
    /**
     * 获取当前http session
     * @return http session对象或NULL
     */
    public static HttpSession getCurrentSession () {
        RequestAttributes requestAttributes = null;
        try {
             requestAttributes = RequestContextHolder.currentRequestAttributes();
        } catch (Exception e) {
            logger.debug("currentRequestAttributes is ERROR"+e);
        }
        if (requestAttributes == null) {
            logger.debug("requestAttributes is null");
            return null;
        }
        Object session = requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        return (session != null && session instanceof HttpSession) ? (HttpSession) session : null;
    }
    
    /**
     * 获取当前请求的语言环境
     * @return
     */
    public static Locale getCurrentLocale () {
        HttpServletRequest httpRequest = getCurrentRequest();
        if (httpRequest == null) {
            logger.debug("httpRequest is null");
            return Locale.getDefault();
        }
        return localeResolver.resolveLocale(httpRequest);
    }

    public void setLocaleResolver(LocaleResolver localeResolver) {
        MyRequestContextHolder.localeResolver = localeResolver;
    }
    


    /**
     * 获取当前用户
     * @return
     */
    public static AdminUserDetails getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object details = authentication.getDetails();
        if (!(details instanceof AdminUserDetails)) {
            return null;
        }
        return (AdminUserDetails) details;
    }

    /**
     * 取得Authentication, 如当前SecurityContext为空时返回null.
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        Authentication authentication = context.getAuthentication();
        return authentication;
    }
    
}
