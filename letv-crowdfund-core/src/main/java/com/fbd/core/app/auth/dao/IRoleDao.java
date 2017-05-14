package com.fbd.core.app.auth.dao;

import java.util.List;
import com.fbd.core.app.auth.model.RoleModel;

public interface IRoleDao {

    int saveRole(RoleModel roleModel);

    List<RoleModel> getRoleList(RoleModel roleModel);

    List<RoleModel> getRole4User(String userId);

    int modifyRole(RoleModel roleModel);

    int removeRole(String id);

}