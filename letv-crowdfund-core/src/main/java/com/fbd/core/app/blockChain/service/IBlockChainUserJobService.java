/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IBlockChainUserJobService.java 
 *
 * Created: [2016-10-24 下午3:30:33] by haolingfeng
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

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户账单定时任务
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IBlockChainUserJobService {
    /**
     * 
     * Description: 项目开户
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-24 下午3:32:05
     */
    public void modifyLoanCreateAccount();
    /**
     * 
     * Description: 项目激活
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-25 下午4:14:59
     */
    public void modifyLoanActivationAccount();
    /**
     * 
     * Description: 项目信任
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-26 上午10:25:52
     */
    public void modifyLoanTrustAccount();
    
    
    /**
     * 
     * Description: 用户开户
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-24 下午3:32:05
     */
    public void modifyUserCreateAccount();
    /**
     * 
     * Description: 用户激活
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-25 下午4:14:59
     */
    public void modifyUserActivationAccount();
    /**
     * 
     * Description: 用户信任
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-26 上午10:25:52
     */
    public void modifyUserTrustAccount();
}
