/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserSecurityDaoImpl.java 
 *
 * Created: [2014-12-10 下午3:00:12] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.user.dao.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.user.dao.IUserSecurityDao;
import com.fbd.core.app.user.model.UserSecurityModel;
import com.fbd.core.base.BaseDaoImpl;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("userSecurityDao")
public class UserSecurityDaoImpl extends BaseDaoImpl<UserSecurityModel>
        implements IUserSecurityDao {
    /**
     * 
     * Description: 根据用户与用户类型查询
     * 
     * @param
     * @return UserSecurityModel
     * @throws
     * @Author haolingfeng 
     * Create Date: 2014-12-13 下午6:57:55
     */
    public UserSecurityModel findByUserId(String userId, String userType) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("userType", userType);
        return super.selectOneByField("selectByUserId", paramMap);

    }

    /**
     * Description: 修改用户安全表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-2 下午9:41:02
     */
    public int modifyUserSecurity(UserSecurityModel userSecurity) {
        return this.update("updateByUserId", userSecurity);
    }

    /**
     * Description: 添加用户安全信息
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-7 下午12:12:06
     */
    public int saveUserSecurity(UserSecurityModel userSecurity) {
        return this.save(userSecurity);
    }
}
