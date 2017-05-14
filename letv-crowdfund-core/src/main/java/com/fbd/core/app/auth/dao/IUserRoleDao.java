package com.fbd.core.app.auth.dao;

import java.util.List;
import java.util.Map;


public interface IUserRoleDao {

    int saveRole4User(String userId, String roleId);

    /**
     * Description: 根据用户名删除该用户下的所有角色
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午12:10:46
     */
    int removeRole4UserByuserId(String userId);
    
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
    public List<Map<String,Object>> getUserByRoleId(String roleId);
   
}