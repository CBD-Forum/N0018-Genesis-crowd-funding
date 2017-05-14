package com.fbd.web.app.letvPay.service;

import java.util.Map;

import com.fbd.core.app.recharge.model.RechargeModel;


public interface ILetvPayRechargeService {

	
	/**
     * 
     * Description:充值
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	Map<String,Object>  sendRechargeRequest(RechargeModel model);

	/**
     * 
     * Description:更新充值成功
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateRecharge(String string);

	/**
     * 
     * Description:支付推进
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	Map<String, Object> sendAdvanceInstantPay(String userId,
			String validationCode, String advanceVoucherNo);

	/**
     * 
     * Description:即时到账
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	Map<String, Object> createImmediatePay(RechargeModel model);


}
