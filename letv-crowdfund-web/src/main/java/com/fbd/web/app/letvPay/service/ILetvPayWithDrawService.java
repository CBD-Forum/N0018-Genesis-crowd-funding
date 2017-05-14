package com.fbd.web.app.letvPay.service;

import java.util.Map;

import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.withdraw.model.WithDrawModel;

public interface ILetvPayWithDrawService {

	/**
     * 
     * Description:提交提现申请验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin<br/>
     */
	void checkWithDraw(WithDrawModel model);

	
	/**
     * 
     * Description:提交提现申请验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin<br/>
     */
	void saveWithDraw(WithDrawModel model);


	
	/**
     * 
     * Description:提现成功更新提现记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin<br/>
     */
	void updateUserWithDraw(String string);


}
