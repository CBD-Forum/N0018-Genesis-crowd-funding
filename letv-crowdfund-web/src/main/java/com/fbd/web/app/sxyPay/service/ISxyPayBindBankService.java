package com.fbd.web.app.sxyPay.service;

import java.util.Map;

import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.user.model.UserModel;

public interface ISxyPayBindBankService {

	/**
     * 
     * Description:开户
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	String createUserBank(UserBankModel user);

	/**
     * 
     * Description:保存银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void saveUserBank(UserBankModel model);

	/**
     * 
     * Description:解绑银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void unbundlingBank(UserBankModel model);

	/**
     * 
     * Description:通过卡号查询银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	UserBankModel getBnakByBankAccount(String bankAccount);
	
	
	
	/**
     * 
     * Description:查询银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	UserBankModel getBnakByUserAndBankAccount(UserBankModel model);

	/**
     * 
     * Description:更新银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateUserBank(UserBankModel model);



}
