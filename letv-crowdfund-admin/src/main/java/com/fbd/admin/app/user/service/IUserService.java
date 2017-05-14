package com.fbd.admin.app.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSecurityModel;
import com.fbd.core.common.model.SearchResult;

public interface IUserService {

    /**
     * Description: 查询普通用户列表(投资用户管理、借款人管理)
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    List<Map<String, Object>> getUserList(UserModel user);

    /**
     * Description: 查询普通用户列表(投资用户管理、借款人管理)的总条数
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    long getUserCount(UserModel user);

    /**
     * Description: 分页查询普通用户列表(投资用户管理、借款人管理)
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    SearchResult<Map<String, Object>> getUserPage(UserModel user);

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
     * Description: 修改用户安全表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-2 下午9:41:02
     */
    int modifyUserSecurity(UserSecurityModel userSecurity);

    /**
     * Description: 查询用户详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-3 上午10:06:32
     */
    Map<String, Object> getUserDetail(String userId);

    /**
     * Description: 重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 上午10:24:48
     */
    @PreAuthorize("hasPermission(null, 'security.operation.webuser_resetPwd')")
    int modifyUserPassword(String id);

    /**
     * Description: 根据Id查询用户实体信息
     *
     * @param 
     * @return UserModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 上午10:16:03
     */
    UserModel getUserById(String id);

    /**
     * Description: 修改用户信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 上午10:39:34
     */
    @PreAuthorize("hasPermission(null, 'security.operation.webuser_modify')")
    int modifyUser(UserModel user);

    /**
     * Description: 分页查询用户的资金统计
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    public SearchResult<Map<String, Object>> getUserCaptialPage(UserModel user);

    /**
     * Description:根据邮箱查询用户对象 
     *
     * @param 
     * @return UserModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-7 下午4:24:59
     */
    UserModel getUserByEmail(String email);

    /**
     * Description: 根据手机号查询用户对象
     *
     * @param 
     * @return UserModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-7 下午4:25:11
     */
    UserModel getUserByMobile(String mobile);

 
    /**
     * Description: 网站统计-重点客户统计
     * 
     * @param
     * @return List<UserModel>
     * @throws
     */
    public SearchResult<Map<String, Object>> getVipCustomerPage(Map<String, Object> paramMap);
    
    /**
     * Description: 网站统计-潜在客户统计
     * 
     * @param
     * @return List<UserModel>
     * @throws
     */
    public SearchResult<Map<String, Object>> getPotentialCustomerPage(Map<String, Object> paramMap);
    
    /**
     * Description: 网站统计-按投资年龄范围统计
     * 
     * @param
     * @return List<UserModel>
     * @throws
     */
    public SearchResult<Map<String, Object>> getInvestAge(Map<String, Object> paramMap);

    /**
     * Description: 用户充值统计
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-5-7 上午10:48:56
     */
    SearchResult<Map<String, Object>> getRechargStatisticlist(Map<String, Object> param);
    
    /**
     * Description: 分页查询众筹用户列表
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    public SearchResult<Map<String, Object>> getCrowdfundUserPage(UserModel user) ;
    
    /**
     * Description: 查询众筹用户详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-3 上午10:06:32
     */
    public Map<String, Object> getCrowdfundUserDetail(String userId);
    /**
     * Description: 删除用户
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	int removeUser(String id);

	/**
     * Description: 修改用户等级
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author 武文斌
     */
	void modifyUserGrade(CrowdfundUserStuffModel model);

	void modifyUserIsCelebrity(UserModel model);
	
	
    /**
     * Description: 查询认证信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 下午3:18:47
     */
    
    public CrowdfundUserStuffModel getCrowdfundUserAuth(
            CrowdfundUserStuffModel model);
    
    /**
     * 查询所有用户列表
     * Description: 
     * @param 
     * @return List<UserModel>
     * @throws 
     * Create Date: 2016-11-14 下午2:11:54
     */
    public List<UserModel> selectAllUser(UserModel user);

}
