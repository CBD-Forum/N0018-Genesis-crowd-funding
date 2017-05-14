package com.fbd.core.common.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fbd.core.app.crowdfunding.service.ICrowdFundTransferJobService;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.utils.LogUtil;
import com.fbd.core.util.spring.SpringUtil;

public class InvestTransferFinanceEndJob implements Job{

	/** 
	 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
	 * 
	 * Description:债权转让结束调度 
	 *
	 * @author wuwenbin
	 * @version 1.0
	 *
	 */
	    private static final Logger logger = LoggerFactory.getLogger(InvestTransferFinanceEndJob.class);
	    private static ICrowdFundTransferJobService crowdfundTransferJobService;
	    
	    public void execute(JobExecutionContext arg0) throws JobExecutionException {
	        Date startTime = new Date();
	        String transferId = arg0.getJobDetail().getJobDataMap().getString(QuartzJobConstants.PARAM_INVESTTRANSFER_FINANCE_END);
	        logger.info("======债权转让【"+transferId+"】支持结束调度开始=====");
	        try{
	        	getService().update(transferId);
	            
	            LogUtil.addSuccessLog(transferId,QuartzJobConstants.JOB_INVESTTRANSFER_FINANCE_END, startTime, "["+transferId+"]债权转让结束调度",
	                    "债权转让结束调度执行成功，转让编号为"+transferId);

	        }catch(Exception e){
	            e.printStackTrace();
	            LogUtil.addFailLog(transferId,QuartzJobConstants.JOB_INVESTTRANSFER_FINANCE_END, startTime, "["+transferId+"]债权转让结束调度",
	                    "债权转让结束调度执行失败，转让编号为"+transferId+",失败原因："+e.getMessage());
	        }
	        logger.info("======债权转让【"+transferId+"】债权转让结束调度结束=====");
	    }
	    
	    public static ICrowdFundTransferJobService getService(){
	        if(crowdfundTransferJobService == null){
	            crowdfundTransferJobService = (ICrowdFundTransferJobService)SpringUtil.getBean("crowdfundTransferJobService");
	        }
	        return crowdfundTransferJobService;
	    }
}
