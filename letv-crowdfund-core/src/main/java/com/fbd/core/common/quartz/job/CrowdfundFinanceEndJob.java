/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundFinanceEndJob.java 
 *
 * Created: [2015-4-11 上午11:55:33] by haolingfeng
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
 * ProjectName: rain-admin 
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
import com.fbd.core.app.crowdfunding.service.ICrowdfundJobService;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.utils.LogUtil;
import com.fbd.core.util.spring.SpringUtil;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
 * 
 * Description:众筹项目结束调度 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public class CrowdfundFinanceEndJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundFinanceEndJob.class);
    private static ICrowdfundJobService crowdfundJobService;
    
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        Date startTime = new Date();
        String loanNo = arg0.getJobDetail().getJobDataMap().getString(QuartzJobConstants.PARAM_CROWDFUND_FINANCE_END);
        logger.info("======众筹项目【"+loanNo+"】支持结束调度开始=====");
        try{
            getCrowdfundJobService().updateCrowdFundFunded(loanNo);
            
            LogUtil.addSuccessLog(loanNo,QuartzJobConstants.JOB_CROWDFUND_FINANCE_END, startTime, "["+loanNo+"]众筹项目投标结束调度",
                    "众筹项目投标结束调度执行成功，项目编号为"+loanNo);

        }catch(Exception e){
            e.printStackTrace();
            LogUtil.addFailLog(loanNo,QuartzJobConstants.JOB_CROWDFUND_FINANCE_END, startTime, "["+loanNo+"]众筹项目投标结束调度",
                    "众筹项目投标结束调度执行失败，项目编号为"+loanNo+",失败原因："+e.getMessage());
        }
        logger.info("======众筹项目【"+loanNo+"】投标结束调度结束=====");
    }
    
    public static ICrowdfundJobService getCrowdfundJobService(){
        if(crowdfundJobService == null){
            crowdfundJobService = (ICrowdfundJobService)SpringUtil.getBean("crowdfundJobService");
        }
        return crowdfundJobService;
    }

}
