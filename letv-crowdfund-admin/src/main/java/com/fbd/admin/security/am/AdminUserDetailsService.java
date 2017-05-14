package com.fbd.admin.security.am;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.fbd.admin.app.admin.service.IAdminService;
import com.fbd.admin.security.AbstractAdminUserDetailsService;
import com.fbd.admin.security.AdminRoleGrantedAuthority;
import com.fbd.core.app.admin.model.AdminModel;
import com.fbd.core.app.user.model.AdminUserDetails;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户详细信息获取实现
 *
 * @author dongzhongwei
 * @version 1.0
 *
 */

public class AdminUserDetailsService extends AbstractAdminUserDetailsService {
    
    private Logger logger = LoggerFactory.getLogger(AdminUserDetailsService.class);

    private IAdminService adminService;

    public void setAdminService(IAdminService adminService) {
        this.adminService = adminService;
    }


    /** {@inheritDoc} */
    protected void loadAuthoritiesIntoUserDetails(String username,
            AdminUserDetails userDetails) throws UsernameNotFoundException {
        
        List<AdminRoleGrantedAuthority> authorities = this.adminService.getAuthorityByUserId(username);
        
        
        if (authorities == null || authorities.size() == 0) {
            if (isCommonUserRequest()) {
          AdminRoleGrantedAuthority authority = 
                new AdminRoleGrantedAuthority(AdminRoleGrantedAuthority.ADMIN_ROLE_TYPE_COMMON_USER);
          userDetails.getAuthorities().add(authority);
            } else {
                logger.warn("person authorities is empty, personId is [{}]", username);
            }
            return;
        }
        userDetails.getAuthorities().addAll(authorities);
        
    }


    /** {@inheritDoc} */
    protected void loadBasicInfoIntoUserDetails(String username,
            AdminUserDetails userDetails) throws UsernameNotFoundException {
        AdminModel adminModel = adminService.getAdminByAdminId(username);
        try {
            PropertyUtils.copyProperties(userDetails, adminModel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 判断是否为普通用户请求
     * @return
     */
    private boolean isCommonUserRequest() {
//        String requestUrl = MyRequestContextHolder.getCurrentRequest().getRequestURL().toString();
//        if (requestUrl.indexOf("/fbd-admin") >= 0) {
//            return true;
//        }
//        return false;
        return true;
    }

}
