/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserSecurityLevelServiceImpl.java 
 *
 * Created: [2015-2-14 上午10:42:13] by haolingfeng
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

import java.util.Calendar;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.dao.IUserSecurityLevelDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSecurityLevelModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.user.service.IUserSecurityLevelService;

/** 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description: 用户安全级别
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("userSecurityLevelService")
public class UserSecurityLevelServiceImpl implements IUserSecurityLevelService{
    
    @Resource
    private IUserSecurityLevelDao userSecurityLevelDao;
    @Resource
    private IUserDao userDao;
    
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
    public UserSecurityLevelModel getUserSecurity(String userId){
        UserSecurityLevelModel model = userSecurityLevelDao.getByUserId(userId, FbdCoreConstants.userType.P);
        Calendar cal = Calendar.getInstance();
        int sHour = cal.get(Calendar.HOUR_OF_DAY);
        model.setCurrentHour(sHour);
        return model;
    }

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
    public void save(String userId){
        UserSecurityLevelModel model = new UserSecurityLevelModel();
        model.setId(PKGenarator.getId());
        model.setUserId(userId);
        model.setUserType(FbdCoreConstants.userType.P);
        model.setSecurityLevel(2);
        model.setMobileState(FbdCoreConstants.userSecurityState.nobind);
        model.setEmailState(FbdCoreConstants.userSecurityState.nobind);
        userSecurityLevelDao.save(model);
    }
    
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
    public void update(UserSecurityLevelModel model){
        model.setSecurityLevel(model.getSecurityLevel()+1);
        userSecurityLevelDao.update(model);
    }
    
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
    public UserSecurityLevelModel getByModel(String userId,String userType){
        UserSecurityLevelModel model = new UserSecurityLevelModel();
        model.setUserType(userType);
        model.setUserId(userId);
        return userSecurityLevelDao.getByModel(userId, userType);
    }
    
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
    public void updateEmergencyInfo(UserModel user){
        String userId = user.getUserId();
        UserModel model = userDao.findByUserId(userId);
        if(StringUtils.isEmpty(model.getEmergencyContact())){
            UserSecurityLevelModel security = this.userSecurityLevelDao.getByUserId(userId, FbdCoreConstants.userType.P);
            security.setSecurityLevel(security.getSecurityLevel()+1);
            this.userSecurityLevelDao.update(security);
        }
        model.setEmergencyContact(user.getEmergencyContact());
        model.setEmergencyPhone(user.getEmergencyPhone());
        model.setEmergencyRelation(user.getEmergencyRelation());
        userDao.update(model);
    }
    
    
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
    public void updateAddress(UserModel user){
        String userId = user.getUserId();
        UserModel model = userDao.findByUserId(userId);
        if(StringUtils.isEmpty(model.getHomeAddress())){
            UserSecurityLevelModel security = this.userSecurityLevelDao.getByUserId(userId, FbdCoreConstants.userType.P);
            security.setSecurityLevel(security.getSecurityLevel()+1);
            this.userSecurityLevelDao.update(security);
        }
        model.setHomeAddress(user.getHomeAddress());
        userDao.update(model);
    }
    
    /**
     * 
     * Description: 认证手机,变更手机绑定状态为认证
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 上午10:34:22
     */
    public void updateAuthMobile(String userId,String mobile){
        UserModel user = userDao.findUserByMobile(mobile);
        if(user!=null && !user.getUserId().equals(userId)){//手机号被使用
            throw new ApplicationException("手机号已经被使用");
        }
        if(user == null){
            UserModel userModel = userDao.findByUserId(userId);
            userModel.setMobile(mobile);
            userDao.update(userModel);
        }
        UserSecurityLevelModel model = this.getUserSecurity(userId);
        model.setMobileState(FbdCoreConstants.userSecurityState.authed);
        this.update(model);
    }
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
    public void updateUnbindMobile(String userId,String mobile){
        UserModel userModel = userDao.findByUserId(userId);
        userModel.setMobile(null);
        userDao.update(userModel);
        UserSecurityLevelModel model = this.getUserSecurity(userId);
        model.setMobileState(FbdCoreConstants.userSecurityState.nobind);
        model.setSecurityLevel(model.getSecurityLevel()-1);
        this.userSecurityLevelDao.update(model);
    }
    
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
    public void updateAuthEmail(String userId,String email){
        UserModel user = userDao.findByEmail(email);
        if(user!=null && !user.getUserId().equals(userId)){//邮箱被使用
            throw new ApplicationException("邮箱已经被使用");
        }
        if(user == null){
            UserModel userModel = userDao.findByUserId(userId);
            userModel.setEmail(email);
            userDao.update(userModel);
        }
        UserSecurityLevelModel model = this.getUserSecurity(userId);
        model.setEmailState(FbdCoreConstants.userSecurityState.authed);
        this.update(model);
    }
}
