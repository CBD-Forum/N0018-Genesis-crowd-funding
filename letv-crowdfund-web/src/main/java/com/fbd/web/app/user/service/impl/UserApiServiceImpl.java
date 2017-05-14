package com.fbd.web.app.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.web.app.user.service.IUserApiService;

@Service(value="userApiService")
public class UserApiServiceImpl implements IUserApiService {
    
	

    private static final Logger logger = LoggerFactory.getLogger(UserApiServiceImpl.class);
    
    @Resource
    private IUserDao userDao;
    /**
     * 对外提供API查询用户列表 
     * Description: 
     * @param 
     * @return List<UserModel>
     * @throws 
     * Create Date: 2016-12-5 下午3:40:36
     */
    public List<UserModel>selectUserListForApi(UserModel user){
    	return this.userDao.selectUserListForApi(user);
    }
    
    /**
     * 对外提供API查询用户信息 
     * Description: 
     * @param 
     * @return List<UserModel>
     * @throws 
     * Create Date: 2016-12-5 下午3:40:36
     */
    public UserModel selectUserModelForApi(UserModel user){
    	return this.userDao.selectUserModelForApi(user);
    }
}
