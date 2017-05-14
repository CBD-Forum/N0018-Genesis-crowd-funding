package com.fbd.core.app.user.dao;

import com.fbd.core.app.user.model.UserSignatureOrgModel;
import com.fbd.core.app.user.model.UserSignaturePersonModel;
import com.fbd.core.base.BaseDao;

public interface IUserSignatureOrgDao  extends BaseDao<UserSignatureOrgModel> {
    /**
     * 查询
     * Description: 
     */
    public UserSignatureOrgModel selectByModel(UserSignatureOrgModel model);
}