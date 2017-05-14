package com.fbd.web.app.letvPay.service;

import java.util.Map;

import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferExtendModel;

public interface ILetvPayTransferService {

	/**
     * 
     * Description: 购买转让商品前验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	void checkBeforeBuyTransfer(CrowdfundProductTransferModel model);

	
	/**
     * 
     * Description: 购买转让商品
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void createTransferOrder(CrowdfundProductTransferModel model);


	/**
     * 
     * Description: 修改转让成功
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateTransferSuccess(Map<String, String> map);
	
	public String saveTransUserInfo(CrowdfundTransferExtendModel model);
	public CrowdfundTransferExtendModel getTransUserInfo(String id);
	/**
	 * 购买转让同步失败
	 * @param model
	 */
	void updateTransferSyncFail(Map<String, String> result);
}
