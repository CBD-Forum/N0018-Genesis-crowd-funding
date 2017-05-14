/**
 * 
 */
package com.fbd.admin.security;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 认证服务抽象类, 用于完成用户认证服务，并组装Authentication对象.
 *
 * @author dongzhongwei
 * @version 1.0
 *
 */

public abstract class AbstractAdminAuthenticationProvider implements AuthenticationProvider {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private UserDetailsService userDetailsService = null;

  public AbstractAdminAuthenticationProvider() {
    super();
  }

  /**
   * @param userDetailsService
   */
  public AbstractAdminAuthenticationProvider(UserDetailsService userDetailsService) {
    super();
    this.userDetailsService = userDetailsService;
  }

  public UserDetailsService getUserDetailsService() {
    return userDetailsService;
  }

  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.springframework.security.authentication.AuthenticationManager#authenticate
   * (org.springframework.security.core.Authentication)
   */
  @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    
    // 1. Check username and password
    try {
        doLogin(authentication);
    } catch (Exception e) {
        if (e instanceof AuthenticationException) {
            throw (AuthenticationException)e;
        }
        logger.error("failure to doLogin", e);
//      throw new BadCredentialsException(I18NMessageUtils.getMessage("Security.systemError"));
        }
    
    // 2. Get UserDetails
    UserDetails userDetails = null;
    try {
        userDetails = this.userDetailsService.loadUserByUsername(authentication.getName());
    } catch (Exception e) {
        if (e instanceof AuthenticationException) {
            throw (AuthenticationException)e;
        }
        logger.error("failure to get user detail", e);
//      throw new BadCredentialsException(I18NMessageUtils.getMessage("Security.systemError"));
        }
    
    // 3. Check and get all of admin roles and contexts.
    Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
    if (authorities != null && !authorities.isEmpty()) {
        AdminAuthenticationToken token = new AdminAuthenticationToken(authentication.getName(), authentication.getCredentials(), authorities);
        token.setDetails(userDetails);
      return token;
    }
    throw new BadCredentialsException("没有分配权限");
  }

  /**
   * Check username and password. When passed, return UserDetails.
   * 
   * @param authentication
   * @return 
   * @throws AuthenticationException  Failure to login
   */
  protected abstract void doLogin(Authentication authentication) throws AuthenticationException;

}
