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

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fbd.core.app.user.service.ICrowdfundUserPrizeService;
import com.fbd.core.app.user.service.impl.CrowdfundUserPrizeServiceImpl;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.utils.LogUtil;
import com.fbd.core.util.spring.SpringUtil;


/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 系统定时查询待抽奖项目
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public class CrowdfundUserPrizeJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundUserPrizeJob.class);
    private static ICrowdfundUserPrizeService crowdfundUserPrizeService;

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        Date startTime = new Date();
        String loanNo = arg0.getJobDetail().getJobDataMap().getString(QuartzJobConstants.PARAM_CROWDFUND_PRIZE_LOAN_NO);
        String backNo = arg0.getJobDetail().getJobDataMap().getString(QuartzJobConstants.PARAM_CROWDFUND_PRIZE_BACK_NO);
        logger.info("======抽奖【"+loanNo+"】支持结束调度开始=====");
        try{
            getCrowdfundUserPrizeService().saveCrowdfundUserPrize(loanNo,backNo);
            
            LogUtil.addSuccessLog(loanNo,QuartzJobConstants.JOB_CROWDFUND_PRIZE_FINANCE_END, startTime, "["+loanNo+"]抽奖结束调度",
                    "抽奖结束调度执行成功，项目编号为"+loanNo);

        }catch(Exception e){
            e.printStackTrace();
            LogUtil.addFailLog(loanNo,QuartzJobConstants.JOB_CROWDFUND_PRIZE_FINANCE_END, startTime, "["+loanNo+"]抽奖结束调度",
                    "抽奖结束调度执行失败，项目编号为"+loanNo+",失败原因："+e.getMessage());
        }
        logger.info("======抽奖【"+loanNo+"】抽奖结束调度结束=====");
    }
    
    public static ICrowdfundUserPrizeService getCrowdfundUserPrizeService(){
        if(crowdfundUserPrizeService == null){
            //汇付
            crowdfundUserPrizeService = (CrowdfundUserPrizeServiceImpl)SpringUtil.getBean("crowdfundUserPrizeServiceImpl");
        }
        return crowdfundUserPrizeService;
    }
    

}
