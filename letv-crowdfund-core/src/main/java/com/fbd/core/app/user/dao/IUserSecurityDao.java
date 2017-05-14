/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserModel.java 
 *
 * Created: [2014-12-3 10:46:57] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: fbd 
 * 
 * Description: 
 * 
 * ==========================================================*/
package com.fbd.core.app.user.dao;

import com.fbd.core.app.user.model.UserSecurityModel;
import com.fbd.core.base.BaseDao;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户安全dao
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
public interface IUserSecurityDao extends BaseDao<UserSecurityModel> {
    /**
     * 
     * Description: 根据用户与用户类型查询
     * 
     * @param
     * @return UserSecurityModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午6:57:55
     */
    public UserSecurityModel findByUserId(String userId, String userType);

    /**
     * Description: 修改用户安全表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-2 下午9:41:02
     */
    public int modifyUserSecurity(UserSecurityModel userSecurity);

    /**
     * Description: 添加用户安全信息
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-7 下午12:12:06
     */
    public int saveUserSecurity(UserSecurityModel userSecurity);
}