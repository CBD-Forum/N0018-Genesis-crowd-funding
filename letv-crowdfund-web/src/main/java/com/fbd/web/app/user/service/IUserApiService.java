package com.fbd.web.app.user.service;

import java.util.List;

import com.fbd.core.app.user.model.UserModel;

public interface IUserApiService {
    /**
     * 对外提供API查询用户列表 
     * Description: 
     * @param 
     * @return List<UserModel>
     * @throws 
     * Create Date: 2016-12-5 下午3:40:36
     */
    public List<UserModel>selectUserListForApi(UserModel user);
    
    /**
     * 对外提供API查询用户信息 
     * Description: 
     * @param 
     * @return List<UserModel>
     * @throws 
     * Create Date: 2016-12-5 下午3:40:36
     */
    public UserModel selectUserModelForApi(UserModel user);
}
