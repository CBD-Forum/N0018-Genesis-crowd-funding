/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IBlockChainQueryService.java 
 *
 * Created: [2016-9-19 下午8:00:26] by haolingfeng
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

package com.fbd.web.app.blockChain.service;

import java.util.Map;


/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 异步回调
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IBlockChainAsynTranscationService {
    
    /**
     * 
     * Description: 区块链事物查询异步
     *
     * @param 
     * @return String
     * @throws 
     * @Author hanchenghe
     * Create Date: 2016-9-23 上午9:55:01
     */
    public void modifyTranscationQueryAsyn(Map<String,String> result);
    
    /**
     * 
     * Description: 
     * 前台购买转让区块链定时事务处理
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-23 上午10:51:41
     */
    public void modifyTransferWebTransactionTimmer(Map<String,String> result);
    /**
     * 用户开户
     * @param result
     */
    public void modifyUserOpenAccount(Map<String,String> result);
    /**
     * 用户激活
     */
    public void modifyUserActivationAccount(Map<String,String> result);
    /**
     * 用户信任
     */
    public void modifyUserTrustAccount(Map<String,String> result);
    
    
    /**
     * 项目开户(预热)
     * @param result
     */
    public void modifyLoanPreheatOpenAccount(Map<String,String> result);
    /**
     * 项目激活(预热)
     */
    public void modifyLoanPreheatActivationAccount(Map<String,String> result);
    /**
     * 项目信任(预热)
     */
    public void modifyLoanPreheatTrustAccount(Map<String,String> result);
    
    
    /**
     * 项目开户
     * @param result
     */
    public void modifyLoanOpenAccount(Map<String,String> result);
    /**
     * 项目激活
     */
    public void modifyLoanActivationAccount(Map<String,String> result);
    /**
     * 项目信任
     */
    public void modifyLoanTrustAccount(Map<String,String> result);
    
    
    /**
     * 用户开户查询异步修改
     * @param result
     */
    public void modifyUserAccountQuery(Map<String, String> result);
}
