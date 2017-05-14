/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IUserSecurityService.java 
 *
 * Created: [2014-12-10 下午2:54:15] by haolingfeng
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

package com.fbd.web.app.user.service;

import com.fbd.core.app.user.model.UserSecurityModel;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:用户安全信息
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IUserSecurityService {
    /**
     * 
     * Description: 登录成功后，更新上次登录日期以及登录失败次数归为0
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:55:38
     */
    public void loginSucess(String userId, String userType);
    /**
     * 
     * Description: 24小时后,用户再次登录时,登录失败次数归0
     * 
     * @param
     * @return void
     * @throws
     */
    public void updatePwdErrorCount0(String userId, String userType);

    /**
     * 
     * Description: 获得当前登录失败次数并更新
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:18:25
     */
    public int updatePwdErrorCount(String userId, String userType);

    /**
     * 
     * Description: 锁定用户，变更登录失败日期以及用户状态为锁定
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:30:12
     */
    public void lockUser(String userId, String userType);

    /**
     * 
     * Description: 获得用户当日登录失败的次数
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:06:28
     */
    public UserSecurityModel getUserSecurity(String userId, String userType);

    /**
     * 
     * Description: 更新
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 上午10:14:21
     */
    public void update(UserSecurityModel model);

}
