package com.fbd.web.app.letvPay.service;

import java.util.Map;

import com.fbd.core.app.user.model.UserModel;

public interface ILetvPayAccountService {

	/**
     * 
     * Description: 实名认证
     *
     * @param 
     * @return void
     * @throws 
     * @Author WUWENBIN<br/>
     */
	Map<String,Object> sendAuthAccountRequest(UserModel user);

	/**
     * 
     * Description:个人开户
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	Map<String,Object> createPersonalMember(UserModel user);

	/**
     * 
     * Description:修改用户第三方会员号
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateThirdAccount(UserModel user);


}
