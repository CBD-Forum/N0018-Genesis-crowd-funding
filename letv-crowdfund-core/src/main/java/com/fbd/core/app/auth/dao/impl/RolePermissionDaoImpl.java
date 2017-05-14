package com.fbd.core.app.auth.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.auth.dao.IRolePermissionDao;
import com.fbd.core.app.auth.model.RolePermissionModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.helper.PKGenarator;

@Repository
public class RolePermissionDaoImpl extends BaseDaoImpl<RolePermissionModel> implements IRolePermissionDao {

    public int savePermission4Role(String roleId, String permissionId) {
        List<RolePermissionModel> list = new ArrayList<RolePermissionModel>();
        String permissionIds[] = permissionId.split(",");
        for (int i = 0 ,length = permissionIds.length; i < length; i++) {
            RolePermissionModel rolePermissionModel= new RolePermissionModel();
            rolePermissionModel.setId(PKGenarator.getId());
            rolePermissionModel.setRoleId(roleId);
            rolePermissionModel.setPermissionId(permissionIds[i]);
            list.add(rolePermissionModel);
        }
        return this.sqlSession.insert("savePermission4Role", list);
    }

    public int removePermission4Role(String roleId) {
        return this.sqlSession.delete("deleteByRoleId", roleId);
    }

}
