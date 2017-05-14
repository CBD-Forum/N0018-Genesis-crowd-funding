/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: QuartzJobConstants.java 
 *
 * Created: [2015-1-20 下午12:13:31] by haolingfeng
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

package com.fbd.core.common.quartz;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: job常量类
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public class QuartzJobConstants {

    /*********************项目停止投标调度参数***************************/
    /*jobname-项目停止投标调度*/
    public static final String JOB_LOAN_FINANCE_END = "loan_financeEndJob";
    /*Triggername-项目停止投标调度*/
    public static final String TRIGGER_LOAN_FINANCE_END = "loan_financeEndTrigger";
    /*描述-项目停止投标调度*/
    public static final String DES_LOAN_FINANCE_END = "项目投标结束调度";
    /*参数-项目停止投标调度*/
    public static final String PARAM_LOAN_FINANCE_END = "loanNo";
    /*执行类-项目停止投标调度*/
    public static final String CLASS_LOAN_FINANCE_END = "com.fbd.core.common.quartz.job.LoanFinanceEndJob";
    
    /*********************项目自动还款调度参数***************************/
    /*jobname-项目自动还款调度*/
    public static final String JOB_LOAN_AUTO_REPAY = "loan_repayAutoJob";
    /*Triggername-项目自动还款调度*/
    public static final String TRIGGER_LOAN_AUTO_REPAY = "loan_repayAutoTrigger";
    /*描述-项目自动还款调度*/
    public static final String DES_LOAN_AUTO_REPAY = "项目自动还款调度";
    /*参数-项目自动还款调度:还款编号*/
    public static final String PARAM_LOAN_AUTO_REPAY_REPAYID = "repayId";
    /*参数-项目自动还款调度：还款人*/
    public static final String PARAM_LOAN_AUTO_REPAY_REPAYUSER = "repayUser";
    /*执行类-项目自动还款调度*/
    //public static final String CLASS_LOAN_AUTO_REPAY = "com.fbd.admin.common.quartz.job.LoanAutoRepayJob";
    /*执行类-项目自动还款调度(易宝)*/
    public static final String CLASS_LOAN_AUTO_REPAY_YEEPAY = "com.fbd.core.common.quartz.job.YeepayLoanAutoRepayJob";
    
    
    
    
    
    /*********************众筹项目停止投标调度参数***************************/
    /*jobname-项目停止投标调度*/
    public static final String JOB_CROWDFUND_FINANCE_END = "crowdfund_financeEndJob";
    /*Triggername-项目停止投标调度*/
    public static final String TRIGGER_CROWDFUND_FINANCE_END = "crowdfund_financeEndTrigger";
    /*描述-项目停止投标调度*/
    public static final String DES_CROWDFUND_FINANCE_END = "众筹项目投标结束调度";
    /*参数-项目停止投标调度*/
    public static final String PARAM_CROWDFUND_FINANCE_END = "loanNo";
    /*执行类-项目停止投标调度*/
    public static final String CLASS_CROWDFUND_FINANCE_END = "com.fbd.core.common.quartz.job.CrowdfundFinanceEndJob";
    
    
    /*********************众筹产品项目抽奖调度参数***************************/
    /*jobname-项目停止投标调度*/
    public static final String JOB_CROWDFUND_PRIZE_FINANCE_END = "crowdfund_prize_financeEndJob";
    /*Triggername-项目停止投标调度*/
    public static final String TRIGGER_CROWDFUND_PRIZE_FINANCE_END = "crowdfund_prize_financeEndTrigger";
    /*描述-项目停止投标调度*/
    public static final String DES_CROWDFUND_PRIZE_FINANCE_END = "众筹产品项目抽奖调度";
    /**参数(项目编号)-项目停止投标调度*/
    public static final String PARAM_CROWDFUND_PRIZE_LOAN_NO = "loanNo";
    /**参数(回报编号)-项目停止投标调度*/
    public static final String PARAM_CROWDFUND_PRIZE_BACK_NO = "backNo";
    /*执行类-项目停止投标调度*/
    public static final String CLASS_CROWDFUND_PRIZE_FINANCE_END = "com.fbd.core.common.quartz.job.CrowdfundUserPrizeJob";
    
    
    /*********************众筹产品项目转让调度参数***************************/
    /*jobname-项目停止投标调度*/
    public static final String JOB_CROWDFUND_TRANSFER_FINANCE_END = "crowdfund_transfer_financeEndJob";
    /*Triggername-项目停止投标调度*/
    public static final String TRIGGER_CROWDFUND_TRANSFER_FINANCE_END = "crowdfund_transfer_financeEndTrigger";
    /*描述-项目停止投标调度*/
    public static final String DES_CROWDFUND_TRANSFER_FINANCE_END = "众筹产品项目转让调度";
    /*参数-项目停止投标调度*/
    public static final String PARAM_CROWDFUND_TRANSFER_FINANCE_END = "transferNo";
    /*执行类-项目停止投标调度*/
    public static final String CLASS_CROWDFUND_TRANSFER_FINANCE_END = "com.fbd.core.common.quartz.job.CrowdfundProductTransferJob";
    
    
    /*********************众筹意向金调度参数***************************/
    /*jobname-项目停止投标调度*/
    public static final String JOB_CROWDFUND_INTENTION_FINANCE_END = "crowdfund_intention_financeEndJob";
    /*Triggername-项目停止投标调度*/
    public static final String TRIGGER_CROWDFUND_INTENTION_FINANCE_END = "crowdfund_intention_financeEndTrigger";
    /*描述-项目停止投标调度*/
    public static final String DES_CROWDFUND_INTENTION_FINANCE_END = "众筹意向金调度";
    /*参数-项目停止投标调度*/
    public static final String PARAM_CROWDFUND_INTENTION_FINANCE_END = "orderId";
    /*执行类-项目停止投标调度*/
    public static final String CLASS_CROWDFUND_INTENTION_FINANCE_END = "com.fbd.core.common.quartz.job.CrowdfundIntentionJob";
    
    
    /*********************课程安排结束调度参数***************************/
    /*jobname-项目停止课程安排调度*/
    public static final String JOB_COURSE_PLAN_END = "couse_planEndJob";
    /*Triggername-项目停止课程安排调度*/
    public static final String TRIGGER_COURSE_PLAN_END = "couse_planEndTrigger";
    /*描述-项目停止课程安排调度*/
    public static final String DES_COURSE_PLAN_END = "课程安排结束调度";
    /*参数-项目停止课程安排调度*/
    public static final String PARAM_COURSE_PLAN_END = "courseId";
    /*执行类-项目停止课程安排调度*/
    public static final String CLASS_COURSE_PLAN_END = "com.fbd.core.common.quartz.job.CoursePlanEndJob";
    
    /*********************众筹项目停止预热调度参数***************************/
    /*jobname-项目停止预热调度*/
    public static final String JOB_CROWDFUND_PREHEAT_END = "crowdfund_preheatEndJob";
    /*Triggername-项目停止预热调度*/
    public static final String TRIGGER_CROWDFUND_PREHEAT_END = "crowdfund_preheatEndTrigger";
    /*描述-项目停止预热调度*/
    public static final String DES_CROWDFUND_PREHEAT_END = "众筹项目预热结束调度";
    /*参数-项目停止预热调度*/
    public static final String PARAM_CROWDFUND_PREHEAT_END = "loanNo";
    /*执行类-项目停止预热调度*/
    public static final String CLASS_CROWDFUND_PREHEAT_END = "com.fbd.core.common.quartz.job.CrowdfundPreheatEndJob";
    
    
    /*********************挂牌调度参数***************************/
    /*jobname-项目停止课程安排调度*/
    public static final String JOB_INVESTTRANSFER_FINANCE_END = "transfer_EndJob";
    /*Triggername-项目停止课程安排调度*/
    public static final String TRIGGER_INVESTTRANSFER_FINANCE_END = "transfer_EndTrigger";
    /*描述-项目停止课程安排调度*/
    public static final String DES_INVESTTRANSFER_FINANCE_END = "挂牌结束调度";
    /*参数-项目停止课程安排调度*/
    public static final String PARAM_INVESTTRANSFER_FINANCE_END = "transferNo";
    /*执行类-项目停止课程安排调度*/
    public static final String CLASS_INVESTTRANSFER_FINANCE_END = "com.fbd.core.common.quartz.job.InvestTransferFinanceEndJob";
    
    
}
