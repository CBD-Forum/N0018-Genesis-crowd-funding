package com.fbd.web.app.letvPay.service;

import java.util.Map;
import java.util.Timer;

import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;

public interface ILetvPayInvestService {

	/**
     * 
     * Description: 支持前验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	void checkBeforeSupport(CrowdfundingSupportModel model,boolean validateBalanceFlag);

	/**
     * 
     * Description: 用户全额投资
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String,Object> createFullInvest(CrowdfundingSupportModel model);

	/**
     * 
     * Description: 更新用户投资
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateInvest(Map<String, Object> map,String type);

	
	/**
     * 
     * Description:用户领头前验证
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void checkBeforeLendSupport(CrowdfundingSupportModel model,boolean validateBalanceFlag);

	/**
     * 
     * Description: 用户支付意向金尾款
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	Map<String, Object> createInvestByIntentionEndAmt(
			CrowdfundingSupportModel model);

	/**
     * 
     * Description: 意向金尾款支付
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateInvestIntentionEndAmt(Map<String, Object> map);

	String saveUserSupport(CrowdfundingSupportModel model);

	/**
     * 
     * Description:投资失败 区块链从中间账户转账到用户账户
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateInvestFail(Map<String, Object> map);

	/**
     * 
     * Description:投资尾款失败 从区块链中间账户转账给投资人
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateInvestIntentionFail(Map<String, Object> map);

	void checkBeforeIntentionSupport(CrowdfundingSupportModel model);
	
	
	/**
	 * 检测项目是否在区块链上开户，如果未开户需要从新开户
	 * @param loanNo
	 */
	public void checkLoanAccountForBlockChain(String loanNo);
	/**
	 * 投资后续处理
	 * @param orderId
	 * @param resultMap
	 */
	public void investPayAfter(String orderId,String tranId,Timer timer);
	/**
     * 意向金成功以后处理
     */
	public void IntentionAsynSucess(String requestId,BlockAsynTranModel model);

	/**
	 * 检测意向金支付
	 * @param model
	 * @return
	 */
	public boolean checkBeforeIntentionPay(CrowdfundingSupportModel model) ;
	
	/**
	 * 判断是否可以使用意向金支付
	 */
	public boolean checkIsIntentionPay(CrowdfundingModel model) ;
	
	//回报支持满额
    public boolean BackSetisFull(CrowdfundingSupportModel model);
}
