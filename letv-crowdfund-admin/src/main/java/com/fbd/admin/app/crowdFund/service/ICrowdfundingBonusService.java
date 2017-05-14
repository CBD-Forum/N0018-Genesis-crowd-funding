package com.fbd.admin.app.crowdFund.service;

import java.util.Map;

import com.fbd.core.app.crowdfunding.model.CrowdfundBonusAuditModel;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.common.model.SearchResult;

public interface ICrowdfundingBonusService {
	
	
	/**
     * 
     * Description: 查询分红项目待审核列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getLoanBonusList(
			CrowdfundBonusModel model);

	/**
     * 
     * Description:项目分红审核
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateLoanBonusState(CrowdfundBonusAuditModel model);
	
	/**
     * 
     * Description:补发分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void sendRereissueBonus(String id);
	
	


	/**
     * 
     * Description: 奖励发放明细
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:51:35
     */
	public SearchResult<Map<String,Object>> getRewardPageList(RewardAssignModel rewardModel);

	
	
	/**
     * 
     * Description: 查询分红项目    审核记录 列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getLoanAuditBonusPage(CrowdfundBonusAuditModel model);
	

}
