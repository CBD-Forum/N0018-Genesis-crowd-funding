/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IUserSecurityLevelService.java 
 *
 * Created: [2015-2-14 上午10:41:47] by haolingfeng
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

import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSecurityLevelModel;

/** 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description: 用户安全级别
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IUserSecurityLevelService {
    /**
     * 
     * Description: 保存用户安全级别信息(注册成功后调用，默认最初级别为2)
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-14 上午11:03:33
     */
    public void save(String userId);
    
    /**
     * 
     * Description:更新用户安全级别 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-14 上午11:10:22
     */
    public void update(UserSecurityLevelModel model);
    
    /**
     * 
     * Description: 查询安全级别信息
     *
     * @param 
     * @return UserSecurityLevelModel
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-14 下午2:42:12
     */
    public UserSecurityLevelModel getByModel(String userId,String userType);
    
    /**
     * 
     * Description: 更新紧急人
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-14 下午4:08:47
     */
    public void updateEmergencyInfo(UserModel user);
    /**
     * 
     * Description: 更新住址信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-14 下午4:08:47
     */
    public void updateAddress(UserModel user);
    
    /**
     * 
     * Description: 获取用户的安全信息
     *
     * @param 
     * @return UserSecurityLevelModel
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-26 下午7:29:44
     */
    public UserSecurityLevelModel getUserSecurity(String userId);
    
    /**
     * 
     * Description: 认证手机
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 上午10:34:22
     */
    public void updateAuthMobile(String userId,String mobile);
    
    /**
     * 
     * Description: 解绑手机号
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 下午2:50:34
     */
    public void updateUnbindMobile(String userId,String mobile);
    
    /**
     * 
     * Description:邮箱认证 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 下午3:35:08
     */
    public void updateAuthEmail(String userId,String email);

}
