package com.fbd.admin.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.fbd.core.app.user.model.AdminUserDetails;


/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author dongzhongwei
 * @version 1.0
 *
 */

public abstract class AbstractAdminUserDetailsService implements UserDetailsService {

  /**
   * 
   */
  public AbstractAdminUserDetailsService() {
    super();
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
   */
  public AdminUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AdminUserDetails userDetails = new AdminUserDetails();
    userDetails.setAdminId(username);
    // 1. Load basic information & PersonDN from TIM LDAP, fill into a SIAMAdminUserDetails
    loadBasicInfoIntoUserDetails(username, userDetails);
    
    // 3. Load GrantedAuthorities (携带其所属的各个管理员角色, 以及所管辖的目标对象(例如: 应用和组织机构等))
    loadAuthoritiesIntoUserDetails(username, userDetails);
    
    return userDetails;
  }

  /**
   * 加载管理用户的权限，填充到目标对象UserDetails中.
   * 
   * @param username  查询的用户标识
   * @param userDetails  填充权限信息的目标对象.
   * @throws UsernameNotFoundException
   */
  protected abstract void loadAuthoritiesIntoUserDetails(String username, AdminUserDetails userDetails) throws UsernameNotFoundException;

  /**
   * 根据用户名加载用户基本信息和UserDN,填充到目标对象UserDetails中.
   * @param username  查询的用户标识
   * @param userDetails  填充权限信息的目标对象.
   * @throws UsernameNotFoundException  未找到对应的用户
   */
  protected abstract void loadBasicInfoIntoUserDetails(String username, AdminUserDetails userDetails) throws UsernameNotFoundException;

}
