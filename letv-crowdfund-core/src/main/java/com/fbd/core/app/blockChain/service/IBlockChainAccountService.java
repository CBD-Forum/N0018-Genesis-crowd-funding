/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BlockChainMockService.java 
 *
 * Created: [2016-8-25 下午9:01:58] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.blockChain.service;

import java.util.Date;
import java.util.Map;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IBlockChainAccountService {
    /**
     * 
     * Description: 获取项目开户区块链同步结果
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-10 上午11:03:01
     */
    public Map<String,Object> getCreateLoanAccountBlockChainResult(CrowdfundingModel crowd,String notifyURL,String opType,String opSource,BlockAsynTranModel blockAsynTran);
    /**
     * 
     * Description: 开户账号查询
     *
     * @param address:账号地址;requestId:请求码; 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-8 下午6:24:37
     */
    public String userAccountQuery(String address,String requestId);
    
    /**
     * 
     * Description: 开户账号查询
     *
     * @param address:账号地址;requestId:请求码; 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-8 下午6:24:37
     */
    public String userAccountQuery(String address,String requestId,String notifyURL);
    
    /**
     * 区块链开户(用户)
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-26 上午9:42:03
     */
    public Map<String,Object> createUserAccount(String accountId,String opSource,BlockAsynTranModel blockAsynTran);
    
    /**
     * 激活用户(用户)
     * Description: 
     * @param 
     * @return boolean
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-26 上午9:43:00
     */
    public Map<String,Object> activeUserAccount(String accountId,String opSource,BlockAsynTranModel blockAsynTran);
    /**
     * 
     * Description: 添加用户信任
     *
     * @param 
     * @return boolean
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-9 上午9:58:28
     */
    public Map<String,Object> trustUserAccount(String sourceAddress,String opSource,BlockAsynTranModel blockAsynTran); 
    
    
    /**
     * 区块链开户(项目)
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-26 上午9:42:03
     */
    public boolean createLoanAccount(String accountId,String operate);
    /**
     * 区块链开户(项目预热)
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-26 上午9:42:03
     */
    public boolean createLoanPreheatAccount(String accountId,Date preheatEndTime,String operate);
    
    /**
     * 激活用户(项目)
     * Description: 
     * @param 
     * @return boolean
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-26 上午9:43:00
     */
    public boolean activeLoanAccount(String accountId);
    /**
     * 激活用户(项目:预热)
     * Description: 
     * @param 
     * @return boolean
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-26 上午9:43:00
     */
    public boolean activeLoanPreheatAccount(String accountId);
    
    /**
     * 
     * Description: 添加项目账户信任
     *
     * @param 
     * @return boolean
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-9 上午9:58:28
     */
    public boolean trustLoanAccount(String sourceAddress); 
    
    /**
     * 
     * Description: 添加项目账户信任(预热)
     *
     * @param 
     * @return boolean
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-9 上午9:58:28
     */
    public boolean trustLoanPreheatAccount(String sourceAddress); 
    
    /**
     * 
     * Description: 用户开户修改
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 上午11:04:44
     */
    public void updateUserAccount(Map<String,String> result);
    /**
     * 
     * Description: 用户激活修改
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 上午11:04:44
     */
    public void updateUserActivationAccount(Map<String,String> result);
    /**
     * 
     * Description: 用户信任修改
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 上午11:04:44
     */
    public void updateUserTrustAccount(String userAddress);    
    /**
     * 
     * Description: 项目开户修改
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 上午11:04:44
     */
    public void updateLoanAccount(Map<String,String> result);
    /**
     * 
     * Description: 项目激活修改
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 上午11:04:44
     */
    public void updateLoanActivationAccount(Map<String,String> result);
    /**
     * 
     * Description: 项目(预热)激活修改
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 上午11:04:44
     */
    public void updateLoanPreheatActivationAccount(Map<String,String> result);
    
    /**
     * 
     * Description: 项目信任修改
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 上午11:04:44
     */
    public void updateLoanTrustAccount(String userAddress);    
    
    /**
     * 
     * Description: 项目信任修改(预热)
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 上午11:04:44
     */
    public void updateLoanPreheatTrustAccount(String userAddress); 
    
    /**
     * 
     * Description: 
     * 项目发布操作日志定时处理(废弃)
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-23 上午10:51:41
     */
    public void modifyCrowdFundOpLogTimmer(String loanNo,String operator,String operateType);
    /**
     * 
     * Description: 
     * 项目预热操作日志定时处理(废弃)
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-23 上午10:51:41
     */
    public void modifyCrowdPreheatOpLogTimmer(String loanNo,String operator,String operateType);
    
    /**
     * 
     * Description: 获取项目信任区块链同步结果
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-10 上午11:03:01
     */
    public Map<String,Object> getTrustLoanAccountBlockChainResult(CrowdfundingModel crowd,String notifyURL,String opSource,BlockAsynTranModel blockAsynTran);
    /**
     * 
     * Description: 获取项目激活区块链同步结果
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-10 上午11:03:01
     */
    public Map<String,Object> getActiveLoanAccountBlockChainResult(CrowdfundingModel crowd,String notifyURL,String opSource,BlockAsynTranModel blockAsynTran);
    /**
     * 
     * Description: 修整项目预热记录信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-27 下午2:35:38
     */
    public void modifyLoanPreaheatLogMsg(String loanNo,String operator);
    /**
     * 
     * Description: 修整项目发起记录信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-27 下午2:35:38
     */
    public void modifyLoanFundingLogMsg(String loanNo,String operator);
}
