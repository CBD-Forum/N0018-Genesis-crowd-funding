package com.fbd.admin.security;

import org.springframework.security.core.GrantedAuthority;


/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author dongzhongwei
 * @version 1.0
 *
 */

public class AdminRoleGrantedAuthority implements GrantedAuthority {

    /**
     * 普通用户角色类型
     */
    public static String ADMIN_ROLE_TYPE_COMMON_USER = "commonUserRole";

    /**
     * 
     */
    private static final long serialVersionUID = -132033237174043242L;


    /**
     * 角色类型名称
     */
    private String authority = null;
    
    private String roleCode;
    
    private String roleName;
    
    private String resourceCode;
    
    private String resourceName;
    
    private String resourceUrl;
    
    private String resourcePcode;
    
    public AdminRoleGrantedAuthority() {
        super();
    }
    
    public AdminRoleGrantedAuthority(String authority) {
        super();
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourcePcode() {
        return resourcePcode;
    }

    public void setResourcePcode(String resourcePcode) {
        this.resourcePcode = resourcePcode;
    }
}