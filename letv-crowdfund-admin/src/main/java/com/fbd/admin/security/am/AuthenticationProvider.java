package com.fbd.admin.security.am;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.fbd.admin.security.AbstractAdminAuthenticationProvider;


/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 如果有认证，可以使用本类
 *
 * @author dongzhongwei
 * @version 1.0
 *
 */

public class AuthenticationProvider extends AbstractAdminAuthenticationProvider {

  public AuthenticationProvider() {
    super();
  }

  public AuthenticationProvider(UserDetailsService userDetailsService) {
    super(userDetailsService);
  }
  
    /** {@inheritDoc} */
  protected void doLogin(Authentication authentication){
    
  }

  public boolean supports(Class<? extends Object> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

}
