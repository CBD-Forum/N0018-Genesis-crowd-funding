package com.fbd.core.common.quartz;

import javax.annotation.Resource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job {
    
    @Resource
    //private IUserService userService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Quartz的任务调度！！！");
        System.out.println("======="+context.getJobDetail().getJobDataMap().getString("par1"));
        
    }
}
