/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: LoanFinanceEndJob.java 
 *
 * Created: [2015-1-20 下午3:13:37] by haolingfeng
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
 * ProjectName: fbd-admin 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.common.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fbd.core.app.crowdfunding.service.IDailyIncomeJobService;
import com.fbd.core.app.crowdfunding.service.impl.DailyIncomeJobServiceImpl;
import com.fbd.core.app.withdraw.service.IWithDrawQueryService;
import com.fbd.core.app.withdraw.service.impl.WithDrawQueryServiceImpl;
import com.fbd.core.util.spring.SpringUtil;


/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 每日收益结算调度任务
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public class DailyIncomeJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(DailyIncomeJob.class);
    private static IDailyIncomeJobService dailyIncomeJobService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
         
        //查询提现是否成功
        getDailyIncomeService().sendDailyIncome();
        
    }
     
    public static IDailyIncomeJobService getDailyIncomeService(){
        if(dailyIncomeJobService == null){
            dailyIncomeJobService = (DailyIncomeJobServiceImpl)SpringUtil.getBean("dailyIncomeJobServiceImpl");
        }
        return dailyIncomeJobService;
    }

}
