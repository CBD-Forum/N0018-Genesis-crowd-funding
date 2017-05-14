package com.fbd.web.app.crowdfunding.service;

import java.util.Timer;

import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.recharge.model.RechargeModel;



public interface ICrowdfundingLendService {
	
	
	
	
    /**
     * 放款区块链项目方转账成功后的后续操作
     * @param orderId
     */
    public void lendProjectTransferAfter(String requestID,Timer timer,boolean notifyFlag);

    
    
    
	
    /**
     * 放款区块链平台服务费转账成功后的后续操作
     * @param orderId
     */
    public void lendPlatformTransferAfter(String requestID,Timer timer,boolean notifyFlag);
    
    
	public void loanSendLendAlter(String loanNo,CrowdfundingModel crowd,String type);
	 
	
}
