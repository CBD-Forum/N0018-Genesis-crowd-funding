package com.fbd.core.app.user.dao;

import com.fbd.core.app.user.model.UserSecurityLevelModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 安全级别
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface IUserSecurityLevelDao extends BaseDao<UserSecurityLevelModel>{
    
    /**
     * 
     * Description: 根据用户id查询用户安全级别
     *
     * @param 
     * @return UserSecurityLevelModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-14 上午11:17:33
     */
    public UserSecurityLevelModel getByUserId(String userId,String userType);
    /**
     * 
     * Description: 查询安全级别信息，信息比较多
     *
     * @param 
     * @return UserSecurityLevelModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-14 上午11:32:24
     */
    public UserSecurityLevelModel getByModel(String userId,String userType);
    
}