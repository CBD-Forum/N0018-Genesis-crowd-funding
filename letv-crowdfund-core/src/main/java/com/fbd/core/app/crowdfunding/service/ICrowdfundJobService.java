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
 * Description: 众筹处理jobservice
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface ICrowdfundJobService {

    /**
     * 
     * Description: 到截止日期时变更项目状态
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2015-4-11 下午12:02:31
     */
    public void updateCrowdFundFunded(String loanNo);

    /**
     * 募集失败退款 Description:
     * 
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author haolingfeng Create Date: 2016-8-30 上午11:15:20
     */
    public List<Map<String, Object>> pushLoanFail(Map<String, Object> param);

    public List<Map<String, Object>> pushIsnvestmentFunds(
            Map<String, Object> param);

    /**
     * 募集转账
     */
    public List<Map<String, Object>> pushRaiseTransfer(Map<String, Object> param);

    /**
     * 支付收益
     */
    public List<Map<String, Object>> pushDailyIncomeData(
            Map<String, Object> param);

    /**
     * 项目结算
     */
    public List<Map<String, Object>> pushJSData(Map<String, Object> param);
}
