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
import com.fbd.core.app.withdraw.service.IWithDrawQueryService;
import com.fbd.core.app.withdraw.service.impl.WithDrawQueryServiceImpl;
import com.fbd.core.util.spring.SpringUtil;


/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 定时查询是否提现成功调度任务
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public class WithDrawQueryJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(WithDrawQueryJob.class);
    private static IWithDrawQueryService withDrawQueryService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
         
        //查询提现是否成功
        getWithDrawQueryService().updateWithDraw();
        
    }
     
    public static IWithDrawQueryService getWithDrawQueryService(){
        if(withDrawQueryService == null){
            withDrawQueryService = (WithDrawQueryServiceImpl)SpringUtil.getBean("withDrawQueryService");
        }
        return withDrawQueryService;
    }

}
