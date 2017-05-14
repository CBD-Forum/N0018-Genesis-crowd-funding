package com.fbd.core.app.auth.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.auth.dao.IUserRoleDao;
import com.fbd.core.app.auth.model.UserRoleModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.helper.PKGenarator;

@Repository
public class UserRoleDaoImpl extends BaseDaoImpl<UserRoleModel> implements IUserRoleDao {

    public int saveRole4User(String userId, String roleId) {
        List<UserRoleModel> list = new ArrayList<UserRoleModel>();
        String roleIds[] = roleId.split(",");
        for (int i = 0 ,length = roleIds.length; i < length; i++) {
            UserRoleModel userRoleModel= new UserRoleModel();
            userRoleModel.setId(PKGenarator.getId());
            userRoleModel.setUserId(userId);
            userRoleModel.setRoleId(roleIds[i]);
            list.add(userRoleModel);
        }
        return this.sqlSession.insert("saveRole4User", list);
    }

    public int update(String statementId, UserRoleModel obj) {
        // TODO Auto-generated method stub
        return 0;
    }
    /**
     * Description: 根据用户名删除该用户下的所有角色
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午12:10:46
     */
    public int removeRole4UserByuserId(String userId) {
        return this.deleteByField("deleteByUserId", userId);
    }
    
    /**
     * 
     * Description: 根据roleId查询用户列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-12 下午6:41:58
     */
    public List<Map<String,Object>> getUserByRoleId(String roleId){
        return this.selectMapByFields("selectUserByRoleId", roleId);
    }

}
