package com.fbd.web.app.sxyPay.service;

import java.util.Map;

import com.fbd.core.app.recharge.model.RechargeModel;

public interface ISxyPayRechargeService {

	 /**
     * 
     * Description:充值
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	String createRechargeRequest(String userId, RechargeModel model);

	
	/**
     * 
     * Description:更新充值订单为成功
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateRechargSucess(Map<String, String> resultMap);


	/**
     * 
     * Description:快捷充值
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	String createQuickRechargeRequest(String userId, RechargeModel model);
	
	
	public RechargeModel createRechangeOrderByMobile(String userId, RechargeModel model);

}
