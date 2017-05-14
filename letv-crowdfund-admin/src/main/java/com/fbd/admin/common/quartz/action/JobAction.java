//package com.fbd.admin.common.quartz.action;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//import javax.servlet.ServletException;
//import org.apache.commons.lang3.time.DateUtils;
//import org.quartz.CronTrigger;
//import org.quartz.JobDetail;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobListener;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.Trigger;
//import org.quartz.impl.StdScheduler;
//import org.quartz.impl.StdSchedulerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import com.fbd.core.common.quartz.helper.TriggerHelper;
//import com.fbd.core.common.quartz.model.ChooseSchedulerForm;
//import com.fbd.core.common.quartz.model.JobDetailForm;
//import com.fbd.core.common.quartz.model.ListenerDTO;
//import com.fbd.core.common.quartz.model.SchedulerDTO;
//import com.fbd.core.common.quartz.model.TriggerForm;
//import com.fbd.core.common.quartz.util.FormatUtil;
//
//@Controller
//@Scope("prototype")
//@RequestMapping("/quartz/job")
//@SuppressWarnings("all")
//public class JobAction {
//    
//    private static final Logger logger = LoggerFactory.getLogger(JobAction.class);
//
////    @Resource
////    private StdScheduler scheduler;
//
//    /**
//     * Description: 获取Job列表
//     * 
//     * @param
//     * @return List<JobDetail>
//     * @throws
//     * @Author dongzhongwei Create Date: 2014-12-25 下午2:44:14
//     */
//    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
//    public @ResponseBody
//    List<JobDetail> getJobsList() {
//        List<JobDetail> jobList = null;
//        try {
//            String[] jobGroups = scheduler.getJobGroupNames();
//            List<String> addedJobs = new ArrayList<String>(jobGroups.length);
//            jobList = new ArrayList<JobDetail>();
//            for (String groupName : jobGroups) {
//                String[] jobs = scheduler.getJobNames(groupName);
//                for (String job : jobs) {
//                    JobDetail jobDetail = scheduler
//                            .getJobDetail(job, groupName);
//                    String key = job + groupName;
//                    jobList.add(jobDetail);
//                    addedJobs.add(key);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
//        return jobList;
//    }
//
//    /**
//     * Description: 根据组和名称获取job
//     * 
//     * @param
//     * @return Map<String,Object>
//     * @throws
//     * @Author dongzhongwei Create Date: 2014-12-25 下午2:44:37
//     */
//    @RequestMapping(value = "/saveJob.html", method = RequestMethod.POST)
//    public @ResponseBody  Map<String, Object> saveJob(String group,String name, String jobClass,
//            Boolean requestsRecovery, Boolean durable,String description,
//            String parameterNames[], String parameterValues[]) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        
//        try {
//            JobDetail jobDetail = new JobDetail();
//            jobDetail.setJobClass(Class.forName(jobClass));
//            jobDetail.setGroup(group);
//            jobDetail.setName(name);
//            jobDetail.setRequestsRecovery(requestsRecovery);
//            jobDetail.setDurability(durable);
//            jobDetail.setDescription(description);
//            if (parameterNames!=null) {
//                for (int i = 0; i < parameterNames.length; i++) {
//                    if (parameterNames[i].trim().length() > 0
//                            && parameterValues[i].trim().length() > 0) {
//                        jobDetail.getJobDataMap().put(parameterNames[i].trim(),
//                                parameterValues[i].trim());
//                    }
//                }
//            }
//            boolean replace = true;
//            scheduler.addJob(jobDetail, replace);
//            resultMap.put("success", true);
//        }catch (ClassNotFoundException e) {
//            resultMap.put("success", false);
//            resultMap.put("msg", jobClass+" 类不存在。");
//        } catch (SchedulerException e) {
//            resultMap.put("success", false);
//            resultMap.put("msg", e.getMessage());
//        }
//        return resultMap;
//    }
//
//    /**
//     * Description: 根据组和名称获取job
//     * 
//     * @param
//     * @return Map<String,Object>
//     * @throws
//     * @Author dongzhongwei Create Date: 2014-12-25 下午2:44:37
//     */
//    @RequestMapping(value = "/getJob.html", method = RequestMethod.GET)
//    public @ResponseBody
//    Map<String, Object> getJobByNameAndGroup(String jobName, String jobGroup) {
//        JobDetail jobDetail = new JobDetail();
//        try {
//            jobDetail = scheduler.getJobDetail(jobName, jobGroup);
//        } catch (Exception e) {
//        }
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        resultMap.put("jobDetail", jobDetail);
//        resultMap.put("jobDataMapKeys", jobDetail.getJobDataMap().getKeys());
//
//        return resultMap;
//    }
//
//    /**
//     * Description: 立即执行
//     * 
//     * @param
//     * @return Map<String,Object>
//     * @throws
//     * @Author dongzhongwei Create Date: 2014-12-25 下午2:44:37
//     */
//    @RequestMapping(value = "/runNow.html", method = RequestMethod.GET)
//    public @ResponseBody
//    Map<String, Object> runNow(String jobName, String jobGroup) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        try {
//            scheduler.triggerJob(jobName, jobGroup);
//            resultMap.put("success", true);
//        } catch (Exception e) {
//            resultMap.put("success", false);
//        }
//        return resultMap;
//    }
//
//    /**
//     * Description: 删除触发器
//     * 
//     * @param
//     * @return Map<String,Object>
//     * @throws
//     * @Author dongzhongwei Create Date: 2014-12-25 下午2:45:44
//     */
//    @RequestMapping(value = "/unSchedule.html", method = RequestMethod.POST)
//    public @ResponseBody
//    Map<String, Object> unSchedule(String triggerName, String triggerGroup) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        try {
//            scheduler.unscheduleJob(triggerName, triggerGroup);
//            resultMap.put("success", true);
//        } catch (Exception e) {
//            resultMap.put("success", false);
//        }
//        return resultMap;
//    }
//
//    /**
//     * Description: 删除job
//     * 
//     * @param
//     * @return Map<String,Object>
//     * @throws
//     * @Author dongzhongwei Create Date: 2014-12-25 下午5:14:22
//     */
//    @RequestMapping(value = "/removeJob.html", method = RequestMethod.GET)
//    public @ResponseBody Map<String, Object> removeJob(String jobName, String jobGroup) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        try {
//            scheduler.deleteJob(jobName, jobGroup);
//            resultMap.put("success", true);
//        } catch (Exception e) {
//            resultMap.put("success", false);
//        }
//        return resultMap;
//    }
//    
//    /**
//     * Description: 添加Cron表达式
//     *
//     * @param 
//     * @return Map<String,Object>
//     * @throws 
//     * @Author dongzhongwei
//     * Create Date: 2015-3-14 上午12:00:05
//     */
//    @RequestMapping(value = "/createCronTrigger.html", method = RequestMethod.POST)
//    public @ResponseBody
//    Map<String, Object> createCronTrigger(String triggerGroup, String triggerName,
//            String description, String startTime, String stopTime,
//            String cronExpression, String jobName, String jobGroup) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        
//        boolean startTimeHasValue = ((startTime != null) && (startTime.length() > 0));
//        boolean stopTimeHasValue = ((stopTime != null) && (stopTime.length() > 0));
//        CronTrigger trigger = null;
//        try {
//            if (cronExpression.length() > 2) {
//                trigger = new CronTrigger(triggerName, triggerGroup, jobName,
//                        jobGroup, cronExpression);
//            }
//            trigger.setDescription(description);
//            String[] pattern = new String[] { "yyyy-MM", "yyyyMM", "yyyy/MM",
//                    "yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMddHHmmss",
//                    "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };
//            if (startTimeHasValue) {
//                trigger.setStartTime(DateUtils.parseDate(startTime, pattern));
//            }
//            if (stopTimeHasValue) {
//                trigger.setEndTime(DateUtils.parseDate(stopTime, pattern));
//            }
//            trigger.setVolatility(false);
//            scheduler.scheduleJob(trigger);
//            resultMap.put("success", true);
//        } catch (SchedulerException e) {
//            logger.error("SchedulerException, Could not schedule the trigger " + trigger + " " + e.getLocalizedMessage());
//            resultMap.put("success", false);
//            resultMap.put("msg", "SchedulerException, Could not schedule the trigger " + trigger + " " + e.getLocalizedMessage());
//        } catch (UnsupportedOperationException ue) {
//            logger.error("UnsupportedOperation in CronSchedule:" + ue);
//            logger.error("Could not schedule the trigger " + trigger + " "+ ue.getLocalizedMessage());
//            resultMap.put("success", false);
//            resultMap.put("msg", "Could not schedule the trigger " + trigger + " "+ ue.getLocalizedMessage());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return resultMap;
//    }
//    
//
//    /**
//     * Description: 查看Job
//     * 
//     * @param
//     * @return Map<String,Object>
//     * @throws
//     * @Author dongzhongwei Create Date: 2014-12-25 下午2:44:52
//     */
//    @RequestMapping(value = "/viewJob.html", method = RequestMethod.POST)
//    public @ResponseBody
//    Map<String, Object> view(String jobName, String jobGroup) throws Exception {
//        JobDetail jobDetail = new JobDetail();
//        JobDetailForm form = new JobDetailForm();
//
//        List<TriggerForm> jobTriggers = new ArrayList<TriggerForm>();
//        try {
//
//            if (jobDetail.getName() == null) {
//                jobDetail = scheduler.getJobDetail(jobName, jobGroup);
//            } else {
//                jobDetail = scheduler.getJobDetail(jobDetail.getName(),
//                        jobDetail.getGroup());
//            }
//        } catch (SchedulerException e) {
//            throw new Exception("When reading the jobs", e);
//        }
//        populateForm(jobDetail, form, scheduler, jobTriggers);
//
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        resultMap.put("jobDetail", jobDetail);
//        resultMap.put("jobTriggers", jobTriggers);
//        resultMap.put("jobDataMapKeys", jobDetail.getJobDataMap().getKeys());
//
//        return resultMap;
//    }
//
//    private void populateForm(JobDetail jobDetail, JobDetailForm form,
//            Scheduler scheduler, List<TriggerForm> jobTriggers)
//            throws ServletException {
//
//        Trigger[] triggers = TriggerHelper.getTriggersFromJob(scheduler,
//                jobDetail.getName(), jobDetail.getGroup());
//
//        for (Trigger trigger : triggers) {
//            TriggerForm tForm = new TriggerForm();
//
//            tForm.setDescription(trigger.getDescription());
//            tForm.setJobGroup(trigger.getJobGroup());
//            tForm.setJobName(trigger.getJobName());
//            tForm.setMisFireInstruction(trigger.getMisfireInstruction());
//            tForm.setStartTime(FormatUtil.getDateAsString(trigger
//                    .getStartTime()));
//            tForm.setStopTime(FormatUtil.getDateAsString(trigger.getEndTime()));
//            tForm.setTriggerGroup(trigger.getGroup());
//            tForm.setTriggerName(trigger.getName());
//            tForm.setNextFireTime(FormatUtil.getDateAsString(trigger
//                    .getNextFireTime()));
//            tForm.setPreviousFireTime(FormatUtil.getDateAsString(trigger
//                    .getPreviousFireTime()));
//            tForm.setType(TriggerHelper.getTriggerType(trigger));
//            if ("cron".equals(tForm.getType())) {
//                CronTrigger cronTrigger = (CronTrigger) trigger;
//                System.out.println(cronTrigger.getCronExpression());
//            }
//            jobTriggers.add(tForm);
//        }
//
//        try {
//            String[] jobListenerNames = jobDetail.getJobListenerNames();
//            for (Iterator iter = scheduler.getJobListenerNames().iterator(); iter
//                    .hasNext();) {
//                String name = (String) iter.next();
//                JobListener jobListener = scheduler.getJobListener(name);
//                for (String element : jobListenerNames) {
//                    if (jobListener.getName().equals(element)) {
//                        ListenerDTO listenerForm = new ListenerDTO();
//                        listenerForm.setName(jobListener.getName());
//                        listenerForm.setListenerClass(jobListener.getClass()
//                                .getName());
//                        form.getJobListeners().add(listenerForm);
//                    }
//                }
//            }
//        } catch (SchedulerException e) {
//        }
//    }
//
//    
//    @RequestMapping(value = "/schedule.html", method = RequestMethod.POST)
//    public @ResponseBody
//    ChooseSchedulerForm ScheduleControler(String command) {
//        ChooseSchedulerForm scheduleInfo=new ChooseSchedulerForm();
//        try {
//                if (command.equals("start")) {
//                    if (scheduler.isShutdown()) {
//                        //choosenScheduler = createSchedulerAndUpdateApplicationContext(schedulerName);
//                    }
//                    scheduler.start();
//                } else if (command.equals("stop")) {
//                    scheduler.shutdown();
//                    //choosenScheduler = StdSchedulerFactory.getDefaultScheduler();         
//                } else if (command.equals("pause")) {
//                    scheduler.standby();
//                } else if (command.equals("waitAndStopScheduler")) {
//                    scheduler.shutdown(true);
//                } else if (command.equals("pauseAll")) {
//                    scheduler.pauseAll();
//                } else if (command.equals("resumeAll")) {
//                    scheduler.resumeAll();
//                }
//                
//                this.populateSchedulerForm(scheduler, scheduleInfo);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return scheduleInfo;
//    }
//    
//    
//    private void populateSchedulerForm(Scheduler choosenScheduler, ChooseSchedulerForm form)
//            throws Exception
//        {
//        
//            Collection scheduleCollection =  new StdSchedulerFactory().getAllSchedulers();
//            Iterator itr = scheduleCollection.iterator();
//
//            form.setSchedulers(new ArrayList<Scheduler>());
//            try {
//                form.setChoosenSchedulerName(choosenScheduler.getSchedulerName());
//                
//                while (itr.hasNext()) {
//                    Scheduler scheduler  = (Scheduler) itr.next();
//                    form.getSchedulers().add(scheduler.getSchedulerName());            
//                }
//            
//            } catch (SchedulerException e) {
//                throw new Exception(e);
//            }
//
//
//            SchedulerDTO schedForm = new SchedulerDTO();
//            schedForm.setSchedulerName(choosenScheduler.getSchedulerName());
//            schedForm.setNumJobsExecuted(String.valueOf(choosenScheduler.getMetaData().getNumberOfJobsExecuted()));
//
//            if (choosenScheduler.getMetaData().jobStoreSupportsPersistence()) {
//                schedForm.setPersistenceType("database");
//            } else {
//                schedForm.setPersistenceType("memory");  // mp possible bugfix
//            }
//            schedForm.setRunningSince(String.valueOf(choosenScheduler.getMetaData().getRunningSince()));
//            if (choosenScheduler.isShutdown()) {
//                schedForm.setState("stopped");
//            } else if (choosenScheduler.isInStandbyMode()) {
//                schedForm.setState("paused");
//            } else {
//                schedForm.setState("started");
//            }
//            
//            schedForm.setThreadPoolSize(String.valueOf(choosenScheduler.getMetaData().getThreadPoolSize()));
//            schedForm.setVersion(choosenScheduler.getMetaData().getVersion());
//            schedForm.setSummary(choosenScheduler.getMetaData().getSummary());
//
//            List jobDetails = choosenScheduler.getCurrentlyExecutingJobs();
//            for (Iterator iter = jobDetails.iterator(); iter.hasNext();) {
//                JobExecutionContext job = (JobExecutionContext) iter.next();
//                JobDetail jobDetail = job.getJobDetail();
//
//                JobDetailForm jobForm = new JobDetailForm();
//                jobForm.setGroupName(jobDetail.getGroup());
//                jobForm.setName(jobDetail.getName());
//                jobForm.setDescription(jobDetail.getDescription());
//                jobForm.setJobClass(jobDetail.getJobClass().getName());
//                form.getExecutingJobs().add(jobForm);
//            }
//            String calendars[] = choosenScheduler.getCalendarNames();
//
//            List jobListeners = choosenScheduler.getGlobalJobListeners();
//            for (Iterator iter = jobListeners.iterator(); iter.hasNext();) {
//                JobListener jobListener = (JobListener) iter.next();
//                ListenerDTO listenerForm = new ListenerDTO();
//                listenerForm.setName(jobListener.getName());
//                listenerForm.setListenerClass(jobListener.getClass().getName());
//                schedForm.getGlobalJobListeners().add(listenerForm);
//            }
//            
//            form.setScheduler(schedForm);
//        }
//}
