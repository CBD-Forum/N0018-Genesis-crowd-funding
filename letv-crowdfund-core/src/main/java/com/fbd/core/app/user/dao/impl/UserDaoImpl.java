/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserDaoImpl.java 
 *
 * Created: [2014-12-3 10:44:36] by haolingfeng
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

package com.fbd.core.app.user.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
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
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<UserModel> implements IUserDao {

    /**
     * 
     * Description: 根据用户名查询用户
     * 
     * @param
     * @return UserModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 上午10:32:07
     */
    public UserModel findByUserId(String userId) {
        return super.selectOneByField("selectByUserId", userId);
    }

    /**
     * 
     * Description: 根据手机号查询user
     * 
     * @param
     * @return UserModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-16 下午4:22:35
     */
    public UserModel findUserByMobile(String mobile) {
        List<UserModel> userList = selectByField("selectUserByMobile", mobile);
        if(userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 
     * Description: 根据身份证号查询user
     * 
     * @param
     * @return UserModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-16 下午4:22:35
     */
    public UserModel findUserByCertNo(String certNo) {
        List<UserModel> userList = selectByField("selectUserByCertNo", certNo);
        if(userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }
    }

    /**
     * 
     * Description: 根据邮箱查询用户
     * 
     * @param
     * @return UserModel
     * @throws
     * @Author haolingfeng 
     * Create Date: 2014-12-13 上午10:32:30
     */
    public UserModel findByEmail(String email) {
        List<UserModel> userList = this.selectByField("selectByEmail", email);
        if (userList.size()>0) {
            return userList.get(0);
        }
        return null;
    }

    /**
     * Description: 查询普通用户列表(投资用户管理、借款人管理)
     *
     * @param 
     * @return List<UserModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-16 下午2:18:18
     */
    public List<Map<String, Object>> getUserList(UserModel user) {
        return this.selectMapByFields("select", user);
    }

    /**
     * Description: 查询普通用户列表(投资用户管理、借款人管理)的总条数
     *
     * @param 
     * @return List<UserModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-16 下午2:18:18
     */
    public long getUserCount(UserModel user) {
        return this.getCount(user);
    }
    
    /**
	 * 推荐人统计
	 * @param user
	 * @return
	 */
    public long getUserRecommendCount(UserModel user) {
    	return this.getCount("selectUserRecommendCount", user);
	}

    /**
	 * 推荐人统计
	 * @param user
	 * @return
	 */
	public List<Map<String, Object>> getUserRecommendList(UserModel user) {
		return this.selectMapByFields("selectUserRecommendList", user);
	}
    
    /**
     * Description: 查询众筹用户列表
     *
     * @param 
     * @return List<UserModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-16 下午2:18:18
     */
    public List<Map<String, Object>> getCrowdfundUserList(UserModel user) {
        return this.selectMapByFields("selectCrowdfundUser", user);
    }

    /**
     * Description:查询众筹用户的总条数
     *
     * @param 
     * @return List<UserModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-16 下午2:18:18
     */
    public long getCrowdfundUserCount(UserModel user) {
        return this.getCount("getCrowdfundUserCount",user);
    }
    
    
    public Map<String, Object> getCrowdfundUserDetail(String userId) {
        return this.selectMapByFields("selectCrowdfundUserByUserId", userId).get(0);
    }
    
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
    public void updateByUserId(UserModel user){
        this.update("updateByUserId", user);
    }

    public Map<String, Object> getUserDetail(String userId) {
        return this.selectOneMapByField("getUserDetailByUserId", userId);
    }

    /**
     * Description: 重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 上午10:24:48
     */
    public int modifyUserPassword(String id) {
        return this.update("resetPassword", id);
    }

    /**
     * Description: 根据Id查询用户实体信息
     *
     * @param 
     * @return UserModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 上午10:16:03
     */
    public UserModel getUserById(String id) {
        return this.selectByPrimaryKey(id);
    }

    /**
     * Description: 修改用户信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 上午10:39:34
     */
    public int modifyUser(UserModel user) {
        return this.updateBySelective(user);
    }
    
    /**
     * 
     * Description: 统计用户的资金
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-12 下午8:51:48
     */
    public List<Map<String, Object>> getUserCapitalList(UserModel user) {
        return this.selectMapByFields("getUserCapitalList", user);
    }
    public long getUserCapitalListCount(UserModel user) {
        return this.getCount("getUserCapitalListCount", user);
    }

    /**
     * Description: 根据第三方账户查询用户实体信息
     *
     * @param 
     * @return UserModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 上午10:16:03
     */
    public UserModel getUserByThridAccount(String thridAccount) {
        return this.selectOneByField("selectByThridAccount", thridAccount);
    }
    
    /**
     * 重点客户统计
     */
	public List<Map<String, Object>> getVipCustomerList(Map<String, Object> paramMap) {
		return this.selectMapByFields("getVipCustomerList", paramMap);
	}
	public long getVipCustomerListCount(Map<String, Object> paramMap) {
		return this.getCount("getVipCustomerListCount", paramMap);
	}
	
	/**
	 * 潜在客户统计
	 */
	public List<Map<String, Object>> getPotentialCustomerList(Map<String, Object> paramMap) {
		return this.selectMapByFields("getPotentialCustomerList", paramMap);
	}
	public long getPotentialCustomerListCount(Map<String, Object> paramMap) {
		return this.getCount("getPotentialCustomerListCount", paramMap);
	}
	
	/**
	 * 按投资年龄范围统计
	 */
	public List<Map<String, Object>> getInvestAgeList(Map<String, Object> paramMap) {
		return this.selectMapByFields("getInvestAgeList", paramMap);
	}

	/**
	 * 网站统计-投资人数
	 */
	public List<Map<String, Object>> getLendPcountList(Map<String, Object> paramMap) {
		return this.selectMapByFields("getLendPcountList", paramMap);
	}
	public long getLendPcountCount(Map<String, Object> paramMap) {
		return this.getCount("getLendPcountCount", paramMap);
	}

	/**
     * Description: 密客网 注册用户
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	public void insertUser(UserModel user) {
		// TODO Auto-generated method stub
		this.save(user);
	}

	/**
     * 
     * Description: 密客网注册
     * 
     * @param
     * @return void
     * @throws
     * @Author wuwenbin
     */
	public void insertUserMKW(UserModel user) {
		this.save(user);
	}

	/**
     * Description: 查询投资人总数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-2-16 上午10:46:43
     */
    public long getCountUser() {
        return this.getCount("selectCountUser", "");
    }

    /**
     * Description: 查询用户列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-2-16 下午3:35:36
     */
    public List<Map<String, Object>> selectUserList(UserModel model) {
        return this.selectMapByFields("selectUserList", model);
    }

    /**
     * Description: 统计用户列表
     *
     * @param 
     * @return long
     * @throws 
     * Create Date: 2016-2-16 下午3:35:44
     */
    public long selectUserCouont(UserModel model) {
        return this.getCount("selectUserCount", model);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.user.dao.IUserDao#findByBlockChainAddress(java.lang.String)
     */
    @Override
    public UserModel findByBlockChainAddress(String blockChainAddress) {
        // TODO Auto-generated method stub
        return this.selectOneByField("findByBlockChainAddress", blockChainAddress);
    }
    
    /**
     * 统计用户数量
     * Description: 
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-12 上午10:06:54
     */
    public List<Map<String,Object>> userStatistics(UserModel model){
        return this.selectMapByFields("userStatistics", model);
    }
    public long selectIsRegister(String mobile){
        return this.getCount("selectIsRegister", mobile);
    }
    
    /**
     * 查询用户信息并且加锁
     * Description: 
     * @param 
     * @return UserModel
     * Create Date: 2016-11-14 下午2:11:28
     */
    public UserModel findUserLockByUserId(String userId) {
        return super.selectOneByField("findUserLockByUserId", userId);
    }
    /**
     * 查询用户列表
     * Description: 
     * @param 
     * @return List<UserModel>
     * @throws 
     * Create Date: 2016-11-14 下午2:11:54
     */
    public List<UserModel> selectList(UserModel user){
        return this.selectByField("selectList", user);
    }
    
    /**
     * 更新用户余额和冻结金额
     * Description: 
     * @param 
     * @return int
     * @throws 
     * Create Date: 2016-11-14 下午2:12:33
     */
     public int updateUserAccount(UserModel user){
         return this.update("updateUserAccount", user);
     }
     /**
      * 查询所有用户列表
      * Description: 
      * @param 
      * @return List<UserModel>
      * @throws 
      * Create Date: 2016-11-14 下午2:11:54
      */
     public List<UserModel> selectAllUser(UserModel user){
         return this.selectByField("selectAllUser", user);
     }
     
     /**
      * 对外提供API查询接口
      * Description: 
      * @param 
      * @return List<UserModel>
      * @throws 
      * Create Date: 2016-12-5 下午3:40:36
      */
     public List<UserModel>selectUserListForApi(UserModel user){
         return this.selectByModel("selectUserListForApi", user);
     }
     
     /**
      * 对外提供API查询用户信息 
      * Description: 
      * @param 
      * @return List<UserModel>
      * @throws 
      * Create Date: 2016-12-5 下午3:40:36
      */
     public UserModel selectUserModelForApi(UserModel user){
         return this.selectOneByField("selectUserModelForApi",user);
     }
}
