/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundJobService.java 
 *
 * Created: [2015-6-9 下午3:26:48] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.crowdfunding.service;

import java.util.List;
import java.util.Map;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:每日收益结算
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public interface IDailyIncomeJobService {

    /**
     * Description: 每日收益
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-31 上午10:33:19
     */
    
    void sendDailyIncome();
    /**
     * 每日收益异步回调
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月10日 下午2:56:31
     */
    public void DailyIncomeSuccess(String requestId);

}
