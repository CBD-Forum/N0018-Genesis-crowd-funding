package com.fbd.web.app.sxyPay.service;

import java.util.Map;

import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.user.model.UserModel;

public interface ISxyPayAccountService {

	/**
     * 
     * Description:开户
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	String createUserAccount(UserModel user);

	/**
     * 
     * Description:修改用户认证信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateUserInfo(UserModel user);


}
