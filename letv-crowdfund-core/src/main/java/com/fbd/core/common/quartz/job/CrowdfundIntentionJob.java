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
import com.fbd.core.app.blockChain.service.IBlockChainReturnService;
import com.fbd.core.app.crowdfunding.service.ICrowdFundProductTransferJobService;
import com.fbd.core.app.user.service.ICrowdfundUserPrizeService;
import com.fbd.core.app.user.service.impl.CrowdfundUserPrizeServiceImpl;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.utils.LogUtil;
import com.fbd.core.util.spring.SpringUtil;


/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 意向金
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public class CrowdfundIntentionJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundIntentionJob.class);
    private static IBlockChainReturnService blockChainReturnService;

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        Date startTime = new Date();
        String orderId = arg0.getJobDetail().getJobDataMap().getString(QuartzJobConstants.PARAM_CROWDFUND_INTENTION_FINANCE_END);
        logger.info("======意向金【"+orderId+"】支持结束调度开始=====");
        try{
            getCrowdfundIntentionService().intentionPayReturnTransfer(orderId);
            
            LogUtil.addSuccessLog(orderId,QuartzJobConstants.JOB_CROWDFUND_INTENTION_FINANCE_END, startTime, "["+orderId+"]意向金尾款支付结束调度",
                    "意向金尾款支付结束调度执行成功，订单编号为"+orderId);

        }catch(Exception e){
            e.printStackTrace();
            LogUtil.addFailLog(orderId,QuartzJobConstants.JOB_CROWDFUND_INTENTION_FINANCE_END, startTime, "["+orderId+"]意向金尾款支付结束调度",
                    "意向金尾款支付结束调度执行成功，订单编号为"+orderId+",失败原因："+e.getMessage());
        }
        logger.info("======意向金【"+orderId+"】支付尾款结束调度执行结束");
    }
    
    public static IBlockChainReturnService getCrowdfundIntentionService(){
        if(blockChainReturnService == null){
            blockChainReturnService = (IBlockChainReturnService)SpringUtil.getBean("blockChainReturnService");
        }
        return blockChainReturnService;
    }
}
