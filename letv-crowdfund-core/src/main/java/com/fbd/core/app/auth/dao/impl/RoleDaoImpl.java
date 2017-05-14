package com.fbd.core.app.auth.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.auth.dao.IRoleDao;
import com.fbd.core.app.auth.model.RoleModel;
import com.fbd.core.base.BaseDaoImpl;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<RoleModel> implements IRoleDao {

    public int saveRole(RoleModel roleModel) {
        return this.save(roleModel);
    }

    public List<RoleModel> getRoleList(RoleModel roleModel) {
        return this.selectByModel("select", roleModel);
    }

    public List<RoleModel> getRole4User(String userId) {
        return this.selectByField("getRole4User", userId);
    }

    public int modifyRole(RoleModel roleModel) {
        return this.update(roleModel);
    }

    public int removeRole(String id) {
        return this.deleteByPrimaryKey(id);
    }
}
