/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IUserService.java 
 *
 * Created: [2014-12-3 10:45:51] by haolingfeng
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

package com.fbd.web.app.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:用户信息
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IUserService {
    /**
     * 
     * Description: 用户注册
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午6:17:43
     */
    public void insert(UserModel user, UserModel inviteUser);
    /**
     * 
     * Description: 用户注册--用户信息同步
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午6:17:43
     */
    public void insertSync(UserModel user, UserModel inviteUser);

    /**
     * 
     * Description: 验证用户是否存在
     * 
     * @param
     * @return boolean
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 上午9:48:36
     */
    public boolean isExistUser(String userId);

    /**
     * 
     * Description: 根据userId查询用户
     * 
     * @param
     * @return UserModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-10 上午9:47:18
     */
    public UserModel getUser(String userId);

    /**
     * 
     * Description: 用户登录校验
     * 
     * @param
     * @return UserModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午6:11:04
     */
    public UserModel valiUserByUserId(String userId);

    /**
     * 
     * Description: 注册时，验证用户名
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 上午10:09:52
     */
    public void validateUserId(String userId);

    /**
     * 
     * Description: 验证邀请码
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午12:47:52
     */
    public UserModel getUserByInviteCode(String inputInviteCode);

    /**
     * 
     * Description: 验证密码
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 上午11:50:42
     */
    public void validatePwd(String pwd1, String pwd2);

    /**
     * 
     * Description: 忘记密码时修改密码
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-16 下午5:31:43
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePwd(String codeType, String pwd, String sendTarget);
    
    /**
     * 
     * Description: 根据用户id变更用户信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-28 下午12:20:32
     */
    public void updateByUserId(UserModel user);
    
    /**
     * 
     * Description: 上传头像
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-13 下午3:04:11
     */
    public void updateUserPhoto(String userId,String path);
    
    /**
     * Description: 查询普通用户列表(投资用户管理、借款人管理)的总条数
     * 
     * @param
     * @return
     * @throws
     */
    public long getUserCount(UserModel user);
    
    /**
     * 
     * Description:实名认证时，认证用户的身份证号与手机号以及邮箱 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-15 下午6:26:44
     */
    public void checkUserInfo(UserModel user);
    
    public void validateMobile(String mobile);
    
    /**
     * 
     * Description: 根据手机查询用户
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-5 上午9:56:53
     */
    public UserModel findUserByMobile(String mobile);
    /**
     * 
     * Description: 查询用户列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getUserList(UserModel model);
	/**
     * 
     * Description:更新用户 材料信息表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     * Create Date: 2015-3-28 下午12:13:10
     */
	public void updateUserStuffInfo(CrowdfundUserStuffModel model);
	
	/**
     * 
     * Description:查询用户 材料信息表
     *
     * @param 
     * @return CrowdfundUserStuffModel
     * @throws 
     * @Author wuwenbin<br/>
     */
	public CrowdfundUserStuffModel findUserStuffByUserId(String userId);

	public UserModel findByUserId(String userId);
	
	
    /**
     * 统计网站用户数量
     * Description: 
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-12 上午10:06:54
     */
    public long getWebUserCount(UserModel model);
    /**
     * 检查用户是否注册说
     * Description: 
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月26日 下午8:11:34
     */
    public long selectIsRegister(String mobile);
    
    public void saveDataSourceLock(String userId);
    
    /**
     * 查询所有用户
     * @param user
     * @return
     */
    public List<UserModel> selectList(UserModel user);
	
}
