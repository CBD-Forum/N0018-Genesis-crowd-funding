/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserServiceImpl.java 
 *
 * Created: [2014-12-3 10:45:30] by haolingfeng
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

package com.fbd.web.app.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.crowdfunding.dao.ICrowdfundUserStuffDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.dao.IUserSecurityDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSecurityModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.LoginException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.HashCrypt;
import com.fbd.core.util.ValidateUtils;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.invitecode.service.IInviteCodeService;
import com.fbd.web.app.user.service.IUserSecurityLevelService;
import com.fbd.web.app.user.service.IUserSecurityService;
import com.fbd.web.app.user.service.IUserService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:用户信息
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service(value = "userService")
public class UserServiceImpl implements IUserService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Resource
    private IUserDao userDao;
    @Resource
    private IUserSecurityDao userSecurityDao;
    @Resource
    private IUserSecurityService userSecurityService;
    @Resource
    private IInviteCodeService inviteCodeService;
    
    @Resource
    private IUserSecurityLevelService userSecurityLevelService;
    
    @Resource
    private ICrowdfundUserStuffDao crowdfundUserStuffDao;
    
    

    /**
     * 
     * Description: 用户注册
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午6:17:43
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(UserModel user, UserModel inviteUser) {
        user.setPassword(HashCrypt.getDigestHash(user.getPassword(),
                FbdConstants.ENCODE_TYPE_MD5));
        user.setInviteCode(user.getUserId());
        user.setId(PKGenarator.getId());
        user.setCreateTime(new Date());
        //非借款人
        user.setIsBorrower(FbdCoreConstants.NOT);
        user.setBlockChainState("0");  //区块链状态默认为0（未开户）
        user.setBalance(0.0);
        user.setFrozenAmt(0.0);
        userDao.save(user);
        // 保存用户安全信息
        this.buildUserSecurityModel(user.getUserId(),
                FbdCoreConstants.userStatus.normal,
                FbdCoreConstants.userType.P);

        // 保存邀请码关系
        if (null != inviteUser) {
            this.inviteCodeService.addDirectInviteReleation(inviteUser, 
                    user.getUserId(), user.getUserId());
        }
        this.userSecurityLevelService.save(user.getUserId());
        //发送注册短信
        this.sendRegisterSuccessMsg(user);
    }
    
    /**
     * 发送注册成功的短信
     * @param user
     */
    private void sendRegisterSuccessMsg(UserModel user){
        Map<String, String> params = new HashMap<String,String>();
    	try{
            logger.info("发送注册成功手机短信开始");
            String nodeCode = FbdConstants.NODE_TYPE_MOBILE_REGISTER_SUCCESS;
            SendMessageUtil.sendMessageToMobile(nodeCode, user.getMobile(), params);
            logger.info("发送注册成功手机短信结束");
        }catch(Exception e){
            logger.error("发送注册成功手机短信失败，"+e.getMessage());
        }
    }
    /**
     * 
     * Description: 用户注册--同步信息
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午6:17:43
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertSync(UserModel user, UserModel inviteUser) {
        user.setPassword(user.getPassword());
        user.setInviteCode(user.getUserId());
        user.setId(PKGenarator.getId());
        user.setCreateTime(new Date());
        //非借款人
        user.setIsBorrower(FbdCoreConstants.NOT);
        userDao.save(user);
        // 保存用户安全信息
        this.buildUserSecurityModel(user.getUserId(),
                FbdCoreConstants.userStatus.normal,
                FbdCoreConstants.userType.P);

        // 保存邀请码关系
        if (null != inviteUser) {
            this.inviteCodeService.addDirectInviteReleation(inviteUser, 
                    user.getUserId(), user.getUserId());
        }
        this.userSecurityLevelService.save(user.getUserId());
        
        //保存用户资料表
        CrowdfundUserStuffModel userStuff = new CrowdfundUserStuffModel();
        userStuff.setId(PKGenarator.getId());
        userStuff.setUserId(user.getUserId());
        userStuff.setUserLevel(CrowdfundCoreConstants.UserLevel.common);
        this.crowdfundUserStuffDao.save(userStuff);
    }
    
  
    /**
     * 
     * Description: 构建用户安全对象
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-10 下午3:16:11
     */
    private void buildUserSecurityModel(String userId, String status,
            String userType) {
        UserSecurityModel userSecurity = new UserSecurityModel();
        userSecurity.setId(PKGenarator.getId());
        userSecurity.setUserId(userId);
        userSecurity.setStatus(status);
        userSecurity.setUserType(userType);
        userSecurity.setLastLoginTime(new Date());
        userSecurity.setLoginFailedNum(0);
        this.userSecurityDao.save(userSecurity);
    }

    /**
     * 
     * Description: 验证用户是否存在
     * 
     * @param
     * @return boolean
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 上午9:48:36
     */
    public boolean isExistUser(String userId) {
        UserModel user = this.userDao.findByUserId(userId);
        if (null != user) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * Description: 根据userId查询用户
     * 
     * @param
     * @return UserModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-10 上午9:47:18
     */
    public UserModel getUser(String userId) {
        UserModel user = this.userDao.findByUserId(userId);
        return user;
    }

    /**
     * 
     * Description: 用户登录校验
     * 
     * @param
     * @return UserModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午6:11:04
     */
    public UserModel valiUserByUserId(String userId) {
        UserModel user = null;
    	long count = userDao.selectIsRegister(userId);
    	if(count<=0)
    		throw new ApplicationException("该手机号还没有注册，请先注册!");
        if (StringUtils.isEmpty(userId)) {
            throw new ApplicationException("用户名或密码错误");
        }
        if (ValidateUtils.validateMobile(userId)) {// 手机号登录
            user = this.userDao.findUserByMobile(userId);
        } else if (ValidateUtils.validateEmail(userId)) {// 邮箱登录
            user = this.userDao.findByEmail(userId);
        } else {// 用户名登录
            user = this.userDao.findByUserId(userId);
        }
        if (null == user) {
            throw new ApplicationException("用户名或密码错误");
        } else {
        	/*UserSecurityModel userSecurity = this.userSecurityDao.findByUserId(
                    userId, FbdCoreConstants.userType.P);
            if (userSecurity.getStatus().equals(// 用户状态不是正常
                    FbdCoreConstants.userStatus.lock)) {
                throw new LoginException("用户已经被锁定，请24小后再登录");
            } else if (userSecurity.getStatus().equals(// 用户状态不是正常
                    FbdCoreConstants.userStatus.disable)) {
                throw new LoginException("用户已经被禁用，请与管理员联系");
            }*/
            UserSecurityModel userSecurity = this.userSecurityDao.findByUserId(
            		user.getUserId(), FbdCoreConstants.userType.P);
            String status = userSecurity.getStatus();
            if (status.equals(// 用户状态不是正常
                    FbdCoreConstants.userStatus.disable)) {
                throw new LoginException("用户已经被禁用，请与管理员联系");
            } else {
            	boolean flag = status.equals(FbdCoreConstants.userStatus.lock);
				Date loginFailedTime = userSecurity.getLoginFailedTime();
				//上次登录失败时间不为空并且大于24小时并且是锁定状态
				if(flag && null != loginFailedTime && DateUtil.diffMilli(userSecurity.getLoginFailedTime()) > (24 * 60 * 60 * 1000)){
					userSecurityService.updatePwdErrorCount0(user.getUserId(), FbdCoreConstants.userType.P);
				} else if (flag){//锁定状态
					throw new LoginException("用户已经被锁定，请24小后再登录");
				}
            }
            
        }
        return user;
    }

    /**
     * 
     * Description: 注册时，验证用户名
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 上午10:09:52
     */
    public void validateUserId(String userId) {
        if (null == userId || userId.length() == 0) {
            throw new ApplicationException("用户名为空");
        }
//        if (!ValidateUtils.validateUserId(userId)) {
//            throw new ApplicationException("必须为长度为6～16个字符的英文字母，数字或下划线");
//        }
//        if (ValidateUtils.validateUserIdData(userId)) {
//            throw new ApplicationException("用户名不能全为数字");
//        }
//        if (ValidateUtils.validateMobile(userId)) {
//            throw new ApplicationException("用户名不能为手机格式");
//        }
//        if (ValidateUtils.validateEmail(userId)) {
//            throw new ApplicationException("用户名不能为邮箱格式");
//        }
        if (this.isExistUser(userId)) {
            throw new ApplicationException("用户名已经被使用");
        }
    }
    
    
    public void validateMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            throw new ApplicationException("手机号不能为空");
        }
        if (this.userDao.findUserByMobile(mobile) != null) {
            throw new ApplicationException("手机号已经被使用");
        }
    }
    
    
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
    public UserModel findUserByMobile(String mobile) {
        UserModel user = this.userDao.findUserByMobile(mobile);
        return user;
    }

    /**
     * 
     * Description: 验证密码
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 上午11:50:42
     */
    public void validatePwd(String pwd1, String pwd2) {
        if (StringUtils.isEmpty(pwd1)) {
            throw new ApplicationException("密码1不能为空");
        }
        if (StringUtils.isEmpty(pwd2)) {
            throw new ApplicationException("密码2不能为空");
        }
        if (!pwd1.equals(pwd2)) {
            throw new ApplicationException("两次密码输入不一致");
        }
    }

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
    public void updatePwd(String codeType, String pwd, String userId) {
        UserModel user = this.userDao.findByUserId(userId);
        // if (codeType.equals(FbdConstants.VERIFY_CODE_TYPE_MOBILE_PWD)) {//
        // 手机号
        // user = this.userDao.findUserByMobile(sendTarget);
        // } else if (codeType.equals(FbdConstants.VERIFY_CODE_TYPE_EMAIL_PWD))
        // {// 邮箱
        // user = this.userDao.findByEmail(sendTarget);
        // }
        user.setPassword(HashCrypt.getDigestHash(pwd,
                FbdConstants.ENCODE_TYPE_MD5));
        this.userDao.update(user);
        
        //发送修改密码站内信
        
        this.sendUpdatePwdMsg(user);
    }
    
    
    private void sendUpdatePwdMsg(UserModel user){
    	 Map<String, String> params = new HashMap<String,String>();
         params.put("time",DateUtil.dateTime2Str(new Date(), null));
    	 try{
             logger.info("发送修改密码站内信开始");
             String nodeCode = FbdCoreConstants.NODE_TYPE_UPDATE_PASSWORD_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_SYS_MSG,user.getUserId(), params);
             logger.info("发送修改密码站内信结束");
         }catch(Exception e){
             logger.error("发送修改密码站内信失败，"+e.getMessage());
         }
    }

    /**
     * 
     * Description: 验证邀请码
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午12:47:52
     */
    public UserModel getUserByInviteCode(String inputInviteCode) {
        UserModel user = null;
        if (null == inputInviteCode || inputInviteCode.length() == 0) {
            return null;
        }
//        if (ValidateUtils.validateUserId(inputInviteCode)) {// 验证是否为用户名格式,根据用户id查询用户
//            user = this.userDao.findByUserId(inputInviteCode);
//        } else if (ValidateUtils.validateMobile(inputInviteCode)) {// 验证是否为手机号格式,根据手机号查询用户
//            user = this.userDao.findUserByMobile(inputInviteCode);
//        }
        user = this.userDao.findUserByMobile(inputInviteCode);
        if (null == user) {
            throw new ApplicationException("邀请人手机号不正确!");
        }
        return user;
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
        this.userDao.updateByUserId(user);
    }
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
    public void updateUserPhoto(String userId,String path){
        UserModel user = this.userDao.findByUserId(userId);
        user.setPhoto(path);
        this.userDao.update(user);
    }

	public long getUserCount(UserModel user) {
		return userDao.getUserCount(user);
	}
	
	public UserModel findByUserId(String userId){
		return this.userDao.findByUserId(userId);
	}
	
	
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
	public void checkUserInfo(UserModel user){
	    //验证身份证：
	    UserModel user1 = userDao.findUserByCertNo(user.getCertificateNo());
	    if(user1!=null && !user1.getUserId().equals(user.getUserId())){
	        throw new ApplicationException("身份证号已经存在");
	    }
	}
	
	/**
     * 
     * Description: 查询用户列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getUserList(UserModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(this.userDao.selectUserList(model));
		result.setTotal(this.userDao.selectUserCouont(model));
		return result;
	}
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
	public void updateUserStuffInfo(CrowdfundUserStuffModel model) {
		this.crowdfundUserStuffDao.updateByUserId(model);
	}
	/**
     * 
     * Description:查询用户 材料信息表
     *
     * @param 
     * @return CrowdfundUserStuffModel
     * @throws 
     * @Author wuwenbin<br/>
     */
	public CrowdfundUserStuffModel findUserStuffByUserId(String userId) {
		return this.crowdfundUserStuffDao.findUserStuffByUserId(userId);
	}
	
    /**
     * 统计网站用户数量
     * Description: 
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-12 上午10:06:54
     */
    public long getWebUserCount(UserModel model){
    	List<Map<String,Object>> userList = this.userDao.userStatistics(model);
    	return userList.size();
    }
	@Override
	public long selectIsRegister(String mobile) {
		// TODO Auto-generated method stub
		return userDao.selectIsRegister(mobile);
	}
    public List<UserModel> selectList(UserModel user){
    	return this.userDao.selectList(user);
    }
    
    /**
     * 测试验证数据库锁
     */
    public void saveDataSourceLock(String userId){
    	
    	//查询用户
    	UserModel user = this.userDao.findUserLockByUserId(userId);
    	Random random = new Random();
    	int balance_symbol = random.nextInt(2)>0?-1:1;
    	int balance = random.nextInt(20)*balance_symbol;
    	int frozenAmt_symbol = random.nextInt(2)>0?-1:1;
    	int frozenAmt = random.nextInt(10)*frozenAmt_symbol;
    	System.out.println(new Date()+":-balance:"+balance+",frozenAmt:"+frozenAmt);
    	user.setBalance(Double.valueOf(balance));
    	user.setFrozenAmt(Double.valueOf(frozenAmt));
    	this.userDao.updateUserAccount(user);
    }
}
