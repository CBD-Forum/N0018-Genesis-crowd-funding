/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserSecurityServiceImpl.java 
 *
 * Created: [2014-12-10 下午2:56:35] by haolingfeng
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
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.user.service.impl;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fbd.core.app.user.dao.IUserSecurityDao;
import com.fbd.core.app.user.model.UserSecurityModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.user.service.IUserSecurityService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:用户安全信息
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service(value = "userSecurityService")
public class UserSecurityServiceImpl implements IUserSecurityService {
    @Resource
    private IUserSecurityDao userSecurityDao;

    /**
     * 
     * Description: 登录成功后，更新上次登录日期以及登录失败次数归为0
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:55:38
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void loginSucess(String userId, String userType) {
        UserSecurityModel userSecurity = this.getUserSecurity(userId, userType);
        userSecurity.setLastLoginTime(new Date());
        userSecurity.setLoginFailedNum(0);
        this.userSecurityDao.update(userSecurity);
    }

    /**
     * 
     * Description: 获得当前登录失败次数并更新
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:18:25
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePwdErrorCount(String userId, String userType) {
        UserSecurityModel userSecurity = this.getUserSecurity(userId, userType);
        int loginFailedNum = userSecurity.getLoginFailedNum() + 1;
        userSecurity.setLoginFailedNum(loginFailedNum);
        userSecurity.setLoginFailedTime(new Date());
        // 更新登录失败次数
        this.update(userSecurity);
        return loginFailedNum;
    }
    
    /**
     * 
     * Description: 24小时后,用户再次登录时,登录失败次数归0,状态更新为正常
     * 
     * @param
     * @return void
     * @throws
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePwdErrorCount0(String userId, String userType) {
    	UserSecurityModel userSecurity = this.getUserSecurity(userId, userType);
    	userSecurity.setLoginFailedNum(0);
    	userSecurity.setStatus(FbdCoreConstants.userStatus.normal);
    	// 更新登录失败次数为0
    	this.update(userSecurity);
    }

    /**
     * 
     * Description: 锁定用户，变更登录失败日期以及用户状态为锁定
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:30:12
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void lockUser(String userId, String userType) {
        UserSecurityModel userSecurity = this.getUserSecurity(userId, userType);
        userSecurity.setLoginFailedTime(new Date());
        userSecurity.setStatus(FbdCoreConstants.userStatus.lock);
        this.update(userSecurity);
    }

    /**
     * 
     * Description: 获得用户当日登录失败的次数
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:06:28
     */
    public UserSecurityModel getUserSecurity(String userId, String userType) {
        UserSecurityModel userSecurity = this.userSecurityDao.findByUserId(
                userId, userType);
        return userSecurity;
    }

    /**
     * 
     * Description: 更新
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:14:21
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(UserSecurityModel model) {
        this.userSecurityDao.update(model);
    }

}
