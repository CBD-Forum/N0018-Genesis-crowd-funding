package com.fbd.admin.security.am;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 注销过滤器，集成AM做注销
 * @author Wuqingming
 * @since 2012-12-6 下午3:30:28
 */

public class LogoutFilter extends
        org.springframework.security.web.authentication.logout.LogoutFilter {

    private String globalLogoutUrl = "/";

    private SimpleUrlLogoutSuccessHandler globalLogoutHandler;

    public LogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
            LogoutHandler[] handlers) {
        super(logoutSuccessHandler, handlers);
    }

    public LogoutFilter(String logoutSuccessUrl, LogoutHandler... handlers) {
        super(logoutSuccessUrl, handlers);
    }

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (requiresGlobalLogout(request, response)) {
            SimpleUrlLogoutSuccessHandler handler = getGlobalLogoutHandler();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            handler.onLogoutSuccess(request, response, auth);
            return;
        }
        super.doFilter(req, res, chain);
    }

    private SimpleUrlLogoutSuccessHandler getGlobalLogoutHandler() {
        if (globalLogoutHandler != null) {
            return globalLogoutHandler;
        }
        Assert.isTrue(
                !StringUtils.hasLength(globalLogoutUrl)
                        || UrlUtils.isValidRedirectUrl(globalLogoutUrl), globalLogoutUrl
                        + " isn't a valid redirect URL");
        SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        if (StringUtils.hasText(globalLogoutUrl)) {
            urlLogoutSuccessHandler.setDefaultTargetUrl(globalLogoutUrl);
        }
        globalLogoutHandler = urlLogoutSuccessHandler;
        return globalLogoutHandler;
    }

    /**
     * 判断是需要全局注销
     * 
     * @return
     */
    protected boolean requiresGlobalLogout(HttpServletRequest request, HttpServletResponse response) {
        if (requiresLogout(request, response)) {
            return true;
        }
        return false;
    }

    public void setGlobalLogoutUrl(String globalLogoutUrl) {
        this.globalLogoutUrl = globalLogoutUrl;
    }

}
