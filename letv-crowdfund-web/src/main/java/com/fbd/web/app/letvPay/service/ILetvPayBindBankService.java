package com.fbd.web.app.letvPay.service;

import java.util.Map;

import com.fbd.core.app.bank.model.UserBankModel;

public interface ILetvPayBindBankService {

	/**
     * 
     * Description:绑定银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	Map<String, Object> createBindBnak(UserBankModel model);

	/**
     * 
     * Description:解绑银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	Map<String, Object> unbundlingBank(UserBankModel model);

	
	/**
     * 
     * Description:查询银行卡列表
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	Map<String, Object> getBankListByThirnd(UserBankModel model);

	/**
     * 
     * Description:查询用户是否已绑定该卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	UserBankModel getBnakByBankAccount(String bankAccount);

}
