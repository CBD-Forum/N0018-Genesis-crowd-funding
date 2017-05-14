package com.fbd.core.common.quartz.util;

import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.impl.StdScheduler;

public class QuartzUtils {

    public static void saveJob(StdScheduler scheduler, String jobClass,
            String cronExpression, String parameterNames[],
            String parameterValues[]) {
        JobDetail jobDetail = new JobDetail();
        jobDetail.setName("fbdJob1");
        jobDetail.setGroup("fbdJobs");
        try {

            jobDetail.setJobClass(Class
                    .forName("com.fbd.admin.common.quartz.QuartzJob"));
            boolean replace = true;
            scheduler.addJob(jobDetail, replace);

            CronTrigger trigger = null;

            trigger = new CronTrigger("fbdTrigger", "fbdTriggers", "fbdJob1",
                    "fbdJobs", "0/15 * * ? * *");

            trigger.setDescription("测试");

            trigger.setStartTime(new Date());

            trigger.setVolatility(false);

            scheduler.scheduleJob(trigger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Description: 
     *
     * @param scheduler 调度
     * @param jobName job的名称
     * @param jobClass job的业务类全路径
     * @param parameter job的参数
     * @param cronTriggerName 触发器的名称
     * @param cronExpression 执行时间表达式
     * @param triggerDescription trigger描述
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-20 上午9:27:48
     */
    public static void saveJob(StdScheduler scheduler,String jobName, String jobClass,Map<String, String> parameter,String cronTriggerName,
            String cronExpression,String triggerDescription) {
        JobDetail jobDetail = new JobDetail();
        jobDetail.setName(jobName);
        jobDetail.setGroup("fbdJobs");
        try {

            jobDetail.setJobClass(Class.forName(jobClass));
            
            if (parameter!=null) {
                jobDetail.getJobDataMap().putAll(parameter);
            }
            boolean replace = true;
            scheduler.addJob(jobDetail, replace);

            CronTrigger trigger = null;

            trigger = new CronTrigger(cronTriggerName, "fbdTriggers", jobName,"fbdJobs", cronExpression);

            trigger.setStartTime(new Date());
            
            if (!StringUtils.isEmpty(triggerDescription)) {
                trigger.setDescription(triggerDescription);
            }

            trigger.setVolatility(false);

            scheduler.scheduleJob(trigger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
