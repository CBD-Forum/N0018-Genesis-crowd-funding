/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BlockChain.java 
 *
 * Created: [2016-9-21 下午2:42:19] by haolingfeng
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
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.blockChain.service;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 区块链常量
 *
 * @author hanchenghe
 * @version 1.0
 *
 */

public class BlockChainCore {
    /**
     * 线程睡眠时间
     */
    public static final Long THREAD_SLEEP_TIME=2000L;
    /**
     * 请求码(用于唯一标示一个请求，在异步返回时会回填，以标示异步返回消息所属请求。)
     */
    public static final String QUERY_REQUEST_ID="queryRequestID";
    //column 业务参数外
    //column result start
    /**
     * 请求码(用于唯一标示一个请求，在异步返回时会回填，以标示异步返回消息所属请求。)
     */
    public static final String REQUEST_ID="requestID";
    /**
     * 用户标识
     */
    public static final String UID="UID";
    /**
     * 地址
     */
    public static final String ADDRESS="address";
    /**
     * 密钥
     */
    public static final String KEY="key";
    /**
     * 状态
     */
    public static final String STATUS="status";
    /**
     * 余额
     */
    public static final String AMOUNT="amount";
    /**
     * 说明文字
     */
    public static final String MESSAGE="message";
    /**
     * 转账订单号
     */
    public static final String TRANSFER_NO="transferNO";
    /**
     * 本地事务ID
     */
    public static final String TRANSACTION_ID="transactionID";
    //column result end
    
    /**
     * 
     * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 操作来源
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class BlockAsynSource{
        /**
         * 正常
         */
        public static final String NORMAL="normal";
        /**
         * 任务
         */
        public static final String JOB="job";
    }
    /**
     * 
     * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
     * 
     * Description:用户认证状态 
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class UserAuth{
        /**
         * 开户中
         */
        public static final String CREATE="-1";
        /**
         * 激活中
         */
        public static final String ACTIVE="-2";
        /**
         * 信任中
         */
        public static final String TRUST="-3";
        /**
         * 信任处理中
         */
        public static final String TRUST_OP="-4";
        /**
         * 区块链完成
         */
        public static final String END="2";
    }
    /**
     * 
     * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 返回结果状态
     *
     * @author hanchenghe
     * @version 1.0
     *
     */
    public class ResultStatus{
        /**
         *新创建数据
         */
        public static final String add="new"; 
        /**
         *服务器响应：调用失败，不表示业务成功(自定义参数,不在业务参数范围内)
         */
        public static final String ServiceFail="ServiceFail";
        
        /**
         *服务器响应：调用成功，不表示业务成功
         */
        public static final String ServiceSuccess="ServiceSuccess";     
        
        /**
         *调用成功，不表示业务成功(只表示参数调用成功)
         */
        public static final String SUCCESS="SubmitGlobalLedgerSuccess";
        /**
         *调用成功，不表示业务成功(只表示参数调用成功)
         */
        public static final String FAIL="SubmitGlobalLedgerFail";       
        /**
         *调用成功，事务进行中(目前没有此状态)
         */
        public static final String SUBMIT_PROCESS="SubmitProcess";            
        /**
         *事务成功
         */
        public static final String TRANSACTION_SUCCESS="ConsensusSuccess";
        /**
         *事务失败
         */
        public static final String TRANSACTION_FAIL="ConsensusFail";
        
        /**
         *参数错误(传入参数不正确)
         */
        public static final String PARAMETER_ERROR="ServiceParameterError";
        /**
         *服务异常(服务系统异常)
         */
        public static final String SERVICE_EXCEPTION="ServiceException";
        /**
         *产品不存在(传入产品码错误，无产品对应产品码)
         */
        public static final String PRODUCT_IS_NOT_EXIST="ServiceProductIsNotExist";
    }
    
    
    
    
    /**
     * 
     * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 操作类型
     *
     * @author hanchenghe
     * @version 1.0
     *
     */
    public class Type{
        /**
         * 用户开户
         */
        public static final String OPEN_ACCOUNT_USER="open_account_user";
        /**
         * 项目开户
         */
        public static final String OPEN_ACCOUNT_LOAN="open_account_loan";
        /**
         * 用户账户激活
         */
        public static final String ACTIVATION_ACCOUNT_USER="activation_account_user";
        /**
         * 项目账户激活
         */
        public static final String ACTIVATION_ACCOUNT_LOAN="activation_account_loan";
        
        /**
         * 用户账户信任
         */
        public static final String TRUST_ACCOUNT_USER="trust_account_user";
        /**
         * 项目账户信任
         */
        public static final String TRUST_ACCOUNT_LOAN="trust_account_loan";
        /**
         * 充值
         */
      //  public static final String recharge="recharge";
        /**
         * 转让
         */
        public static final String TRANSFER="transfer";
        /**
         * 转让审核成功
         */
        public static final String TRANSFER_AUDIT_SUCCESS="transfer_audit_success";
        /**
         * 转让审核成功(手续费)
         */
        public static final String TRANSFER_AUDIT_FEE_SUCCESS="transfer_audit_fee_success";
        /**
         * 转让审核失败
         */
        public static final String TRANSFER_AUDIT_FAIL="transfer_audit_fail";
        
        /**
         * 退款项目方审核
         */
        public static final String REFUNDLOANAUDIT = "refundLoanAudit";
        
        /**
         * 退款审核通过
         */
        public static final String REFUNDPASSEDAUDIT = "refund_passed_audit";        
        
        /**
         * 退款审核拒绝
         */
        public static final String REFUNDREFUSEAUDIT = "refund_refuse_audit";
         
        /**
         * 项目支持-领投
         */
        public static final String leader_pay = "leader_pay";  
        
        
        /**
         * 项目支持 跟投
         */
        public static final String invest_pay = "invest_pay";  
 
        /**
         * 项目支持 意向金尾款支付
         */
        public static final String intention_retainage_pay = "intention_retainage_pay";  

        /**
         * 项目支持 意向金用户退款
         */
        public static final String intention_user_return= "intention_user_return"; 
        /**
         * 项目支持 意向金系统服务费
         */
        public static final String intention_system_return_fee= "intention_system_return_fee"; 
        /**
         * 项目支持 意向金支付
         */
        public static final String intention_pay = "intention_pay";  
        
        /**
         * 放款项目方
         */
        public static final String lend_loan = "lend_loan";
        
        /**
         * 放款(平台)
         */
        public static final String lend_platform = "lend_platform";
        
        public static final String SYSTEMLEND = "system_lend";
        
        public static final String ORGANISERLEND = "organiser_lend";
        
        
        /**
         *流标
         */
        public static final String FLOW ="flow";
        
        /**
         *利息
         */
        public static final String INTEREST ="interest";
        
        /**
         * 项目分红
         */
        public static final String loan_bonus = "loan_bonus";  
        /**
         * 项目分红（投资人）
         */
        public static final String investor_bonus = "investor_bonus";
        /**
         * 项目分红拒绝
         */
        public static final String bonus_refuse = "bonus_refuse";
        /**
         * 提现申请
         */
        public static final String withdraw_apply = "withdraw_apply";
        /**
         * 提现审核
         */
        public static final String withdraw_audit = "withdraw_audit";
        
        /**
         * 提现没有通过审核
         */
        public static final String withdraw_refuse = "withdraw_refuse";
        
        /**
         * 用户提现成功操作(回收众筹令)
         */
        public static final String withdraw_user_deal = "withdraw_user_deal";
        
        
        /**
         * 用户提现失败操作(解冻用户的众筹令)
         */
        public static final String withdraw_fail_user_deal = "withdraw_fail_user_deal";
        
        /**
         * 系统提现操作手续费(平台区块链手续费)
         */
        public static final String withdraw_system_deal = "withdraw_system_deal";
        
        /**
         * 区块链提现请求
         */
        public static final String withdraw_block = "withdraw_block";
        
        /**
         * 放款平台手续费
         */
        public static final String recharge ="recharge";
        
        /**
        * 平台提现
        */
        public static final String platform_withdraw ="platform_withdraw";  
    }
}
