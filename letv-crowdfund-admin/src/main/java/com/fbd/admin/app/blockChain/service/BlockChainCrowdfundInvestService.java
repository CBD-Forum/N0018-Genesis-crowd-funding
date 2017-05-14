package com.fbd.admin.app.blockChain.service;




 
public interface BlockChainCrowdfundInvestService {
	
	
	/**
	 * 项目放款 
	 * @param loanNo
	 */
	public void sendLendTrans(String loanNo); 
	
	/**
	 * 项目放款(转账到平台收款账户)
	 * @param loanNo
	 */
	public void sendLendTransForPlatform(String loanNo);

/*	*//**
	 * 平台手续费转账异步回调处理
	 * @param loanNo
	 *//*
    public void dealWithSystemTransaction(String loanNo);
    
    *//**
	 * 发起人异步回调处理
	 * @param loanNo
	 *//*
    public void dealWithOrganiserTransaction(String loanNo);
    */

}
