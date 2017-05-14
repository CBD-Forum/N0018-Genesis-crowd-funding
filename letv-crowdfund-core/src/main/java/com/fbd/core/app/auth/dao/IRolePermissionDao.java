package com.fbd.core.app.auth.dao;


public interface IRolePermissionDao {

    int savePermission4Role(String roleId, String permissionId);

    int removePermission4Role(String roleId);
    
}