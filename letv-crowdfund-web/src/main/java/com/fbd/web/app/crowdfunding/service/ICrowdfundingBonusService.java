package com.fbd.web.app.crowdfunding.service;

import java.util.Timer;

import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.reward.model.RewardAssignModel;

public interface ICrowdfundingBonusService {
	
	/**
	 * 分红审核拒绝后续操作
	 * @param model
	 */
	public void bonusAuditRefuseAfter(CrowdfundBonusModel model,String requestID,Timer timer,boolean notifyFlag);
	
	
	/**
	 * 用户分红成功后操作
	 * @param model
	 * @param timer
	 * @param notifyFlag
	 */
	public void bonusAuditPassedAfter(RewardAssignModel model,String requestID,Timer timer,boolean notifyFlag);

}
