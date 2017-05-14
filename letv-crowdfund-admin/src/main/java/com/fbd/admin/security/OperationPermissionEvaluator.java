package com.fbd.admin.security;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 新版权限模型 功能权限许可解析器
 * 
 * @since 2014-10-23
 */
public class OperationPermissionEvaluator implements PermissionEvaluator {
    private static Logger logger = LoggerFactory.getLogger(OperationPermissionEvaluator.class);

    private String actionPrefix = "security.operation.";
    public static String TARGETID_ACCOUNT = "1";// 账号

    /**
     * 页面调用方式,后两个参数 <security:authorize
     * access="hasPermission(null, 'security.operation.org_add')"> 需过滤的页面操作
     * </security:authorize>
     * 
     * java代码使用方法,方法头标注
     * 
     * @PreAuthorize("hasPermission(null,'security.operation.audit_mq')")
     */
    public boolean hasPermission(Authentication authentication,
            Object targetDomainObject, Object permission) {
        logger.debug("permission value:" + permission.toString());
        if (null == permission) {
            return false;
        }

        // 应用
        if (targetDomainObject != null) {
//           
        }

        String permissionStr = permission.toString();
        // 获取资源唯一值
        String targetAction = permissionStr.substring(permissionStr.indexOf(actionPrefix) + actionPrefix.length());

        // 读取用户加载好的权限信息, 判断用户是否有此操作的权限
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority instanceof AdminRoleGrantedAuthority) {
                AdminRoleGrantedAuthority roleAuthority = (AdminRoleGrantedAuthority) authority;
                if (targetAction.equalsIgnoreCase(roleAuthority.getResourceCode())) {
                    return true;
                }
            }
        }
        logger.debug("\"" + authentication.getName() + "\"" + " do not have permission to operate this resource:\""+ permissionStr + "\"");
        return false;
    }

    // 同类型身份，权限　取最大
    public boolean hasPermission(Authentication authentication,Serializable targetId, String erserverDn, Object permission) {
        return false;
    }
}
