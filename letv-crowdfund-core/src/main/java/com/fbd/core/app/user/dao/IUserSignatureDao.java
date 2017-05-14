package com.fbd.core.app.user.dao;

import com.fbd.core.app.user.model.UserSignatureModel;
import com.fbd.core.base.BaseDao;

public interface IUserSignatureDao extends BaseDao<UserSignatureModel>{
    
    /**
     * Description: 根据用户名查询签章用户信息
     * @param 
     * @return UserSignatureModel
     * @throws 
     * Create Date: 2016-8-16 上午11:58:02
     */
    public UserSignatureModel selectByUserId(String userId);
    /**
     * Description: 根据签章用户ID查询签章用户信息
     * @param 
     * @return UserSignatureModel
     * @throws 
     * Create Date: 2016-8-16 上午11:58:02
     */
    public UserSignatureModel selectBySignUserId(String signUserId);
    
}