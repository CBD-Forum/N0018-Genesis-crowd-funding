package com.fbd.admin.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class PermissionEvaluatorWrapper implements PermissionEvaluator {
  
  private Map<String, PermissionEvaluator> permissionEvaluators = new HashMap<String, PermissionEvaluator>();
  
  /**
   * 前缀表达式解析器
   */
  private Map<String, PermissionEvaluator> prefixPermissionEvaluators = new HashMap<String, PermissionEvaluator>();

  public PermissionEvaluatorWrapper() {
    super();
  }

  public PermissionEvaluatorWrapper(Map<String, PermissionEvaluator> permissionEvaluators) {
    super();
    this.permissionEvaluators = permissionEvaluators;
  }

  public Map<String, PermissionEvaluator> getPermissionEvaluators() {
    return permissionEvaluators;
  }

  public void setPermissionEvaluators(Map<String, PermissionEvaluator> permissionEvaluators) {
    this.permissionEvaluators = permissionEvaluators;
  }

  public Map<String, PermissionEvaluator> getPrefixPermissionEvaluators() {
        return prefixPermissionEvaluators;
    }

    public void setPrefixPermissionEvaluators(
            Map<String, PermissionEvaluator> prefixPermissionEvaluators) {
        this.prefixPermissionEvaluators = prefixPermissionEvaluators;
    }

    /* (non-Javadoc)
   * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.lang.Object, java.lang.Object)
   */
  public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
    if (permission != null && this.permissionEvaluators.containsKey(permission)) {
       return this.permissionEvaluators.get(permission).hasPermission(authentication, targetDomainObject, permission);
    }
    if (permission != null && this.prefixPermissionEvaluators != null) {
        for (String prefix : prefixPermissionEvaluators.keySet()) {
            if (permission.toString().startsWith(prefix)) {
                return this.prefixPermissionEvaluators.get(prefix).hasPermission(authentication, targetDomainObject, permission);
            }
        }
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.io.Serializable, java.lang.String, java.lang.Object)
   */
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
    if (permission != null && this.permissionEvaluators.containsKey(permission)) {
      return this.permissionEvaluators.get(permission).hasPermission(authentication, targetId, targetType, permission);
    }
    if (permission != null && this.prefixPermissionEvaluators != null) {
        for (String prefix : prefixPermissionEvaluators.keySet()) {
            if (permission.toString().startsWith(prefix)) {
          return this.prefixPermissionEvaluators.get(prefix).hasPermission(authentication, targetId, targetType, permission);
            }
        }
    }
    return true;
  }
}