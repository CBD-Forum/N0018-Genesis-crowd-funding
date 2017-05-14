package com.fbd.core.app.user.dao;

import com.fbd.core.app.user.model.UserSignaturePersonModel;
import com.fbd.core.base.BaseDao;

public interface IUserSignaturePersonDao  extends BaseDao<UserSignaturePersonModel>{
    
    /**
     * 查询
     * Description: 
     */
    public UserSignaturePersonModel selectByModel(UserSignaturePersonModel model);
  
}