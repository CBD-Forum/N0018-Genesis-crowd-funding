package com.fbd.admin.app.auth.service;

import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fbd.core.app.auth.model.PermissionModel;
import com.fbd.core.app.auth.model.RoleModel;

public interface IAuthService {

    int savePermission(PermissionModel permissionModel);

    int modifyPermission(PermissionModel permissionModel);

    int removePermission(String id);

    List<PermissionModel> getPermissionList(PermissionModel permissionModel);

    @PreAuthorize("hasPermission(null, 'security.operation.authority_role_add')")
    int saveRole(RoleModel roleModel);

    @PreAuthorize("hasPermission(null, 'security.operation.authority_role_modify')")
    int modifyRole(RoleModel roleModel);

    @PreAuthorize("hasPermission(null, 'security.operation.authority_role_delete')")
    int removeRole(String id);

    List<RoleModel> getRoleList(RoleModel roleModel);

    int savePermission4Role(String roleId, String permissionId,String permissionChecked);

    int removePermission4Role(String roleId);

    List<PermissionModel> getPermission4Role(String roleId);

    @PreAuthorize("hasPermission(null, 'security.operation.user_admin_configRole')")
    int saveRole4User(String userId, String roleId);

    int removeRole4User(String id);

    List<RoleModel> getRole4User(String userId);

    List<PermissionModel> getPermissionListByPid(String pid);

    /**
     * Description: 排序权限列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-9 上午9:33:11
     */
    int modifyOrderByPermission(String targetId, String sourceId, String point);
    
    public String getLinkByCode(String code);
    
    
    /**
     * 
     * Description: 根据角色查询用户
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-12 下午6:48:59
     */
    public List<Map<String,Object>> getUserByRole(String roleId);

}
