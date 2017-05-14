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
import com.fbd.core.app.crowdfunding.service.ICrowdFundProductTransferJobService;
import com.fbd.core.app.user.service.ICrowdfundUserPrizeService;
import com.fbd.core.app.user.service.impl.CrowdfundUserPrizeServiceImpl;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.utils.LogUtil;
import com.fbd.core.util.spring.SpringUtil;


/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 产品转让
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public class CrowdfundProductTransferJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundProductTransferJob.class);
    private static ICrowdFundProductTransferJobService crowdFundProductTransferJobService;

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        Date startTime = new Date();
        String transferNo = arg0.getJobDetail().getJobDataMap().getString(QuartzJobConstants.PARAM_CROWDFUND_TRANSFER_FINANCE_END);
        logger.info("======产品转让【"+transferNo+"】支持结束调度开始=====");
        try{
            getCrowdfundUserPrizeService().updateTransferEnd(transferNo);
            
            LogUtil.addSuccessLog(transferNo,QuartzJobConstants.JOB_CROWDFUND_TRANSFER_FINANCE_END, startTime, "["+transferNo+"]产品转让结束调度",
                    "产品转让结束调度执行成功，项目编号为"+transferNo);

        }catch(Exception e){
            e.printStackTrace();
            LogUtil.addFailLog(transferNo,QuartzJobConstants.JOB_CROWDFUND_TRANSFER_FINANCE_END, startTime, "["+transferNo+"]产品转让结束调度",
                    "产品转让结束调度执行失败，项目编号为"+transferNo+",失败原因："+e.getMessage());
        }
        logger.info("======产品转让【"+transferNo+"】产品转让结束调度结束=====");
    }
    
    public static ICrowdFundProductTransferJobService getCrowdfundUserPrizeService(){
        if(crowdFundProductTransferJobService == null){
            crowdFundProductTransferJobService = (ICrowdFundProductTransferJobService)SpringUtil.getBean("crowdFundProductTransferJobService");
        }
        return crowdFundProductTransferJobService;
    }
    

}
