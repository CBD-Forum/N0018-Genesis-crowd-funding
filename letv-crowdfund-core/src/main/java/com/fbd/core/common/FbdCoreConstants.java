/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: FbdCoreConstants.java 
 *
 * Created: [2014-12-22 上午11:26:48] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.common;

import java.util.HashMap;
import java.util.Map;
import com.fbd.core.util.SpringPropertiesHolder;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public class FbdCoreConstants {
    
    /*--------------------------- 系统配置 ---------------------------*/
    /** 系统自动执行，操作人id **/
    public static final String SYSTEM_OPERATOR = SpringPropertiesHolder
            .getProperty("sysUserId");
    
    public static final String SYSTEM_ID = "admin";
    
    public static final String isClientEnvironment = SpringPropertiesHolder
               .getProperty("is_client_environment");
    
    
    
    /*----------------------------电子签章信息--------------------------------*/
    public static final String esp_apiId = SpringPropertiesHolder
            .getProperty("esp_apiId");
    public static final String esp_apiSecret = SpringPropertiesHolder
            .getProperty("esp_apiSecret");
    public static final String esp_baseUrl = SpringPropertiesHolder
            .getProperty("esp_baseUrl");
    public static final String esp_userUrl = SpringPropertiesHolder
            .getProperty("esp_userUrl");
    public static final String esp_HttpClientProvider = SpringPropertiesHolder
            .getProperty("esp_HttpClientProvider");
    
    
    /*----------------------------时间戳信息-----------------------------*/
    public static final String tsa_cert_keystorePwd = SpringPropertiesHolder
            .getProperty("tsa_cert_keystorePwd");
    public static final String tsa_cert_pwd = SpringPropertiesHolder
            .getProperty("tsa_cert_pwd");
    public static final String tsa_server_url = SpringPropertiesHolder
            .getProperty("tsa_server_url");
    public static final String tsa_server_userName = SpringPropertiesHolder
            .getProperty("tsa_server_userName");
    public static final String tsa_server_password = SpringPropertiesHolder
            .getProperty("tsa_server_password");
    public static final String tsa_seal_numb = SpringPropertiesHolder
            .getProperty("tsa_seal_numb");    
    public static final String tsa_seal_pwd = SpringPropertiesHolder
            .getProperty("tsa_seal_pwd"); 
    
    public static final String signature_server_url =  SpringPropertiesHolder
            .getProperty("signature_server_url"); 
    public static final String signature_product_code =  SpringPropertiesHolder
            .getProperty("signature_product_code"); 
    
    
    /*------------------------------区块链参数-----------------------------*/
    
    //请求地址
    public static final String BLOCK_CHAIN_URL = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_URL");
    
    //异步通知地址开头
    public static final String BLOCK_CHAIN_NOTIFY_URL = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_NOTIFY_URL");
    
    //产品编码
    public static final String BLOCK_CHAIN_PRODUCTCODE = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_PRODUCTCODE"); 
 
    //激活账户备付金金额
    public static final String BLOCK_CHAIN_ACTIVATED_AMT = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_ACTIVATED_AMT"); 
    
    //平台应收账户
    public static final String BLOCK_CHAIN_PLATFORM_IN_ACCOUNT = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_PLATFORM_IN_ACCOUNT"); 
    
    //平台应收账户KEY
    public static final String BLOCK_CHAIN_PLATFORM_IN_KEY = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_PLATFORM_IN_KEY");    
    
    //平台中间账户
    public static final String BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT"); 
    
    //平台中间账户KEY
    public static final String BLOCK_CHAIN_PLATFORM_MIDDLE_KEY = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_PLATFORM_MIDDLE_KEY");     
    
    //平台出账户
    public static final String BLOCK_CHAIN_PLATFORM_OUT_ACCOUNT = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_PLATFORM_OUT_ACCOUNT"); 
    
    //平台出账户KEY
    public static final String BLOCK_CHAIN_PLATFORM_OUT_KEY = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_PLATFORM_OUT_KEY"); 
    
    //区块链手续费账户
    public static final String BLOCK_CHAIN_PLATFORM_INCOME_ACCOUNT = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_PLATFORM_INCOME_ACCOUNT");
    
    //区块链手续费账户KEY
    public static final String BLOCK_CHAIN_PLATFORM_INCOME_KEY = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_PLATFORM_INCOME_KEY");
    
    //区块链发行账户
    public static final String BLOCK_CHAIN_PLATFORM_ISSUE_ACCOUNT = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_PLATFORM_ISSUE_ACCOUNT"); 
    
    //区块链是否测试环境
    public static final boolean BLOCK_CHAIN_IS_MOCK = Boolean.parseBoolean(SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_IS_MOCK")); 
    
    
    /**
     * 不走开户区块链 false:走区块链
     */
    public static final boolean BLOCK_CHAIN_USER_IS_MOCK = Boolean.parseBoolean(SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_USER_IS_MOCK")); 
    
    public static final boolean BLOCK_CHAIN_LOAN_IS_MOCK = Boolean.parseBoolean(SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_LOAN_IS_MOCK")); 
    
    /**
     * 不 使用第三方
     */
    public static final boolean BLOCK_USER_IS_MOCK = Boolean.parseBoolean(SpringPropertiesHolder
            .getProperty("BLOCK_USER_IS_MOCK"));
    
    //区块链是否使用测试账户
    public static final String BLOCK_CHAIN_IS_TEST = SpringPropertiesHolder
            .getProperty("BLOCK_CHAIN_IS_TEST");
    
  
    
    
    /*---------------------------软证书信息参数------------------------------*/
    public static final String RA_URL = SpringPropertiesHolder
            .getProperty("RA_URL");  
    public static final String RA_AccountHash = SpringPropertiesHolder
            .getProperty("RA_AccountHash");  
    public static final String RA_Password = SpringPropertiesHolder
            .getProperty("RA_Password");  
    /*--------------------------- 执行结果 ---------------------------*/
    /** 成功**/
    public static final String RESULT_SUCCESS = "success";
    /** 失败**/
    public static final String RESULT_FAIL = "fail";

    /*--------------------------- 是否的对应值 ---------------------------*/
    public static final String IS = "1";
    public static final String NOT = "0";
    /*--------------------------- session中的属性 ---------------------------*/
    public static final String SESSION_USERID = "userId";
    
    public static final String SESSION_REALNAME = "realName";
    
    public static final String SESSION_ISBORROWER = "isBorrower";
    
    public static final String SESSION_THIRDNO = "thirdNo";
    
    public static final String LAST_LOGIN_TIME = "lastLoginTime";
    
    public static final String SESSION_IS_AUTH = "isAuth"; 
    
    public static final String SESSION_UPS_AUTH = "UPSAuth"; 
    
    public static final String SESSION_SXY_AUTH = "SXYAuth"; 
    
    public static final String SESSION_BLOCKCHAINSTATE = "blockChainState"; 
    
    public static final String SESSION_IS_NICKNAME = "nickName"; 

    public static final String SESSION_USERPHOTO = "userphoto";
    
    public static final String SESSION_USERMOBILE = "userMobile";
    /*---------------------------充值方式 ---------------------------*/
    public static final String RECHARGE_WAY_HUIFU = "huifu";

    /*--------------------------- 内存锁前缀 ---------------------------*/
    /** 充值单内存锁前缀 **/
    public static final String LOCK_RECHARGE = "recharge_";
    /** 投资内存锁前缀 **/
    public static final String LOCK_INVEST = "invest_";
    /** 流转标投资内存锁前缀 **/
    public static final String LOCK_TRANSFER_INVEST = "transferInvest_";
    /** 提现内存锁前缀 **/
    public static final String LOCK_WITHDRAW = "withdraw_";
    /** 转账内存锁前缀 **/
    public static final String LOCK_SYS_TRANSFER = "systransfer_";
    /** 还款内存锁前缀 **/
    public static final String LOCK_REPAY = "repay_";
    /** 生利宝转入转出内存锁前缀 **/
    public static final String LOCK_RSSTRANS = "fsstrans_";

    /*---------------------------投资相关--------------------------------*/
    /** 是否为自动投标:否 */
    public static final String ISAUTOINVEST_FALSE = "0";
    
    /*---------------------------计息方案相关--------------------------------*/
    /** 计算不满一月的利息 单位日 365 */
    public static final int COMPUTE_INTEREST_DAY_365 = 365;
    /** 计息单位:月 */
    public static final String COMPUTE_INTEREST_UNIT_MONTH = "月";
    /** 计息单位:日 */
    public static final String COMPUTE_INTEREST_UNIT_DAY = "日";

    /*---------------------------金额相关--------------------------------------*/
    /** 金额从元转为分 */
    public static final int AMT_CHANGE_CENT = 100;
    /*---------------------------优惠券相关--------------------------------------*/
    /** 优惠券状态:未使用 */
    public static final String REWARD_STATE_UNUSED = "unused";
    /** 优惠券状态:已使用 */
    public static final String REWARD_STATE_USED = "used";

    /** 优惠券状态:过期 */
    public static final String REWARD_STATE_OVERDUE = "overdue";
    
    /** 导出文件模板存放路径 */
    public final static String EXPORT_TEMP_PATH = "/report/";
    
    /** 用户银行卡状态：绑定 */
    public static final String USER_BANK_STATE_BIND = "bind";
    /** 用户银行卡状态：解绑 */
    public static final String USER_BANK_STATE_UNBIND = "unbind";

    // 交易金额的格式
    public static final String TRADEAMT_FORMAT_TWO = "#0.00";
    
    
    /*---------------------------导出配置文件路径-------------------------------*/
    /** 还款提醒报表配置文件 */
    public static final String REPAY_EXPORT_CONFIG = "export_repay_excel.xml";
    /** 还款提醒报表文件名 */
    public static final String REPAY_EXPORT_FILE_NAME = "还款提醒";
    /** 奖励发放明细报表配置文件 */
    public static final String REWARDASSIGN_EXPORT_CONFIG = "export_rewardassign_excel.xml";
    /** 奖励发放明细报表文件名 */
    public static final String REWARDASSIGN_EXPORT_FILE_NAME = "奖励发放明细";
    
    
    /*---------------------------还款相关--------------------------------------*/
    /** 提前还款违约金投资所得比例*/
    public static final String PREPAY_FINE_INVEST_SPLIT_RATIO = "prepayFineInvestSplitRatio";
    /** 提前还款违约金平台所得比例*/
    public static final String PREPAY_FINE_SYS_SPLIT_RATIO = "prepayFineSysSplitRatio";
    /** 逾期罚金投资所得比例*/
    public static final String OVERDUE_FINE_INVEST_SPLIT_RATIO = "overdueFineInvestSplitRatio";
    /** 逾期罚金平台所得比例*/
    public static final String OVERDUE_FINE_SYS_SPLIT_RATIO = "overdueFineSysSplitRatio";
    
    
    /*---------------------------站内信-------------------------------*/
    /** 站内信状态:已读 */
    public static final String STATION_MSG_STATUS_READ = "1";
    /** 站内信状态:未读 */
    public static final String STATION_MSG_STATUS_NOREAD = "0";
    
    
    /** 站内信消息类型:充值提现 */
    public static final String STATION_MSG_TYPE_RECHARGE_WITHDRAW = "recharge_withdraw";
    /** 站内信消息类型:项目消息 */
    public static final String STATION_MSG_TYPE_LOAN_MSG = "loan_msg";
    /** 站内信消息类型:项目消息子类型-约谈 */
    public static final String STATION_MSG_CHILD_TYPE_LOAN_MSG = "interview_loan_msg";
    /** 消息节点类型: 充值站内信*/
    public static final String NODE_TYPE_RECHARGE_MSG = "recharge_msg";
    /** 消息节点类型: 充值手机短信*/
    public static final String NODE_TYPE_RECHARGE_MOBILE = "recharge_mobile";
    
    
    /** 消息节点类型: 提现站内信*/
    public static final String NODE_TYPE_WITHDRAW_MSG = "withdraw_msg";
    /** 消息节点类型: 提现申请站内信*/
    public static final String NODE_TYPE_WITHDRAW_APPLY_MSG = "withdrawApply_msg";
    /** 消息节点类型: 提现申请手机短信*/
    public static final String NODE_TYPE_WITHDRAW_APPLY_MOBILE = "withdrawApply_mobile";
    /** 消息节点类型: 提现拒绝站内信*/
    public static final String NODE_TYPE_WITHDRAW_REFUSE_MSG = "withdraw_refuse_msg";
    /** 消息节点类型: 提现通过手机短信*/
    public static final String NODE_TYPE_WITHDRAW_PASS_MOBILE = "withdraw_pass_mobile";
    /** 消息节点类型: 提现失败手机短信*/
    public static final String NODE_TYPE_WITHDRAW_FAIL_MOBILE = "withdraw_fail_mobile";
    /** 消息节点类型: 提现拒绝手机短信*/
    public static final String NODE_TYPE_WITHDRAW_REFUSE_MOBILE = "withdraw_refuse_mobile";
    /** 消息节点类型: 提现审核通过站内信*/
    public static final String NODE_TYPE_WITHDRAW_AUDITPASS_MSG = "withdrawAuditPass_msg";
    /** 消息节点类型: 提现审核通过站内信*/
    public static final String NODE_TYPE_WITHDRAW_FAIL_MSG = "withdraw_fail_msg";
    /** 消息节点类型: 提现审核拒绝站内信*/
    public static final String NODE_TYPE_WITHDRAW_AUDITREFUSE_MSG = "withdrawAuditRefuse_msg";
    
    
    
    
    
    /** 消息节点类型: 投标放款手机短信*/
    public static final String NODE_TYPE_INVEST_CASH_MOBILE = "invest_cash_mobile";
    /** 消息节点类型: 投标放款站内信*/
    public static final String NODE_TYPE_INVEST_CASH_MSG = "invest_cash_msg";
    
    /** 消息节点类型: 项目发布手机短信*/
    public static final String NODE_TYPE_LOAN_RELEASE_MOBILE = "loan_release_mobile";
    /** 消息节点类型: 项目发布站内信*/
    public static final String NODE_TYPE_LOAN_RELEASE_MSG = "loan_release_msg";
    
    /** 消息节点类型: 项目发布手机短信*/
    public static final String NODE_TYPE_LOAN_ATTENTION_MOBILE = "loan_attention_mobile";
    /** 消息节点类型: 项目发布站内信*/
    public static final String NODE_TYPE_LOAN_ATTENTION_MSG = "loan_attention_msg";    
    
    
    /** 消息节点类型: 项目申请发布手机短信*/
    public static final String NODE_TYPE_LOAN_APPLY_MOBILE = "loan_apply_mobile";
    /** 消息节点类型: 项目申请发布站内信*/
    public static final String NODE_TYPE_LOAN_APPLY_MSG = "loan_apply_msg";
    
    /** 消息节点类型: 项目回款站内信*/
    public static final String NODE_TYPE_LOAN_REPAY_MSG = "loan_repay_msg";
    /** 消息节点类型: 项目提前回款站内信*/
    public static final String NODE_TYPE_LOAN_PREREPAY_MSG = "loan_prerepay_msg";
    /** 消息节点类型: 项目提前回款手机消息*/
    public static final String NODE_TYPE_LOAN_PREREPAY_MOBILE = "loan_prerepay_mobile";
    /** 消息节点类型: 项目回款手机消息*/
    public static final String NODE_TYPE_LOAN_REPAY_MOBILE = "loan_repay_mobile";
    
    /** 站内信消息类型:系统消息 */
    public static final String STATION_MSG_TYPE_SYS_MSG = "sys_msg";
    
    /** 消息节点类型: 项目放款站内信*/
    public static final String NODE_TYPE_LOAN_CASH_MSG = "loan_cash_msg";
    /** 消息节点类型: 项目筹款到50%站内信*/
    public static final String NODE_TYPE_LOAN_PROGRESS_MSG = "loan_progress_msg";
    
    /** 消息节点类型: 项目放款手机消息*/
    public static final String NODE_TYPE_LOAN_CASH_MOBILE = "loan_cash_mobile";
    
    /** 消息节点类型: 还款成功站内信*/
    public static final String NODE_TYPE_REPAYSUCCESS_MSG = "repaysuccess_msg";
    /** 消息节点类型: 还款成功手机消息*/
    public static final String NODE_TYPE_REPAYSUCCESS_MOBILE = "repaysuccess_mobile";
    
    
    /** 消息节点类型: 购买特卖手机短信*/
    public static final String NODE_TYPE_BUY_GOODS_MOBILE = "buy_goods_mobile";
    /** 消息节点类型: 购买特卖站内信*/
    public static final String NODE_TYPE_BUY_GOODS_MSG = "buy_goods_msg";
    
    /** 消息节点类型: 众筹投标放款手机短信*/
    public static final String NODE_TYPE_SUPPORT_CASH_MOBILE = "support_cash_mobile";
    /** 消息节点类型: 众筹投标放款站内信*/
    public static final String NODE_TYPE_SUPPORT_CASH_MSG = "support_cash_msg";
    
    /** 消息节点类型: 众筹投标流标手机短信*/
    public static final String NODE_TYPE_LOAN_FLOW_MOBILE = "loan_flow_mobile";
    /** 消息节点类型: 众筹投标流标站内信*/
    public static final String NODE_TYPE_LOAN_FLOW_MSG = "loan_flow_msg";
    
    
    /** 消息节点类型: 众筹项目放款项目方合同未签署通知手机短信*/
    public static final String NODE_TYPE_LOAN_CONTRACT_SIGN_MOBILE = "loan_contract_sign_mobile";
    /** 消息节点类型: 众筹项目放款项目方合同未签署通知站内信*/
    public static final String NODE_TYPE_LOAN_CONTRACT_SIGN_MSG = "loan_contract_sign_msg";
    
    /** 消息节点类型: 投资奖励发放手机消息*/
    public static final String REWARD_ASSIGN_INVEST = "reward_assign_invest";
    /** 消息节点类型: 直接和间接推荐奖励手机消息*/
    public static final String REWARD_ASSIGN_RECOMMEND = "reward_assign_recommend";
    
    
    /** 消息节点类型: 退款申请站内信*/
    public static final String NODE_TYPE_REFUND_APPLICATION_MSG = "refund_application_msg";
    
    /** 消息节点类型:发送项目方审核投资申请退款站内信息*/
    public static final String NODE_TYPE_LOANAUDITREFUND_APPLICATION_MSG = "loan_refund_application_msg";
    /** 消息节点类型:发货申请站内信息*/
    public static final String NODE_TYPE_APPLICATION_DELIVERY_MSG = "application_delivery_msg";

    /** 消息节点类型：充值审核拒绝信息*/
    public static final String NODE_TYPE_RECHARGE_AUDIT_REFUSE_MSG = "recharge_audit_refuse_msg";
    /** 消息节点类型：充值审核拒绝短信*/
    public static final String NODE_TYPE_RECHARGE_AUDIT_REFUSE_MOBILE = "recharge_audit_refuse_mobile";
    /** 消息节点类型：充值审核通过信息*/
    public static final String NODE_TYPE_RECHARGE_AUDIT_PASSED_MSG = "recharge_audit_passed_msg";
    /** 消息节点类型：充值审核通过短信*/
    public static final String NODE_TYPE_RECHARGE_AUDIT_PASSED_MOBILE = "recharge_audit_passed_mobile";
   
    /** 消息节点类型: 线上支付成功站内信*/
    public static final String NODE_TYPE_PAY_MSG = "invest_cash_msg";
    /** 消息节点类型: 线上支付成功手机短信*/
    public static final String NODE_TYPE_PAY_MOBILE = "invest_cash_mobile";
    /** 消息节点类型: 手机短信登录，发送手机密码短信*/
    public static final String NODE_TYPE_DEFAULT_PASSWORD = "login_password";
    
    
    /** 消息节点类型: 项目分红审核确认站内信*/
    public static final String NODE_TYPE_BONUS_CONFIRM_MSG = "bonus_confirm_msg";
    /** 消息节点类型: 项目分红审核确认手机消息*/
    public static final String NODE_TYPE_BONUS_CONFIRM_MOBILE = "bonus_confirm_mobile";    
    
    /** 消息节点类型: 项目分红审核拒绝站内信*/
    public static final String NODE_TYPE_BONUS_REFUSE_LOAN_MSG = "bonus_refuse_loan_msg";
    /** 消息节点类型: 项目分红审核拒绝手机消息*/
    public static final String NODE_TYPE_BONUS_REFUSE_LOAN_MOBILE = "bonus_refuse_loan_mobile";
    
    /** 消息节点类型: 项目分红审核通过项目方站内信*/
    public static final String NODE_TYPE_BONUS_PASSED_LOAN_MSG = "bonus_passed_loan_msg";
    /** 消息节点类型: 项目分红审核通过项目方手机消息*/
    public static final String NODE_TYPE_BONUS_PASSED_LOAN_MOBILE = "bonus_passed_loan_mobile";
    
    /** 消息节点类型: 项目分红审核通过投资人站内信*/
    public static final String NODE_TYPE_BONUS_PASSED_INVEST_MSG = "bonus_passed_invest_msg";
    /** 消息节点类型: 项目分红审核通过投资人手机消息*/
    public static final String NODE_TYPE_BONUS_PASSED_INVEST_MOBILE = "bonus_passed_invest_mobile";
    

    
    //抽奖 消息类型 start
    /**
     * 中奖幸运编号产生-项目方(站内信)
     */
    public static final String NODE_PRIZE_LOAN_USER_MSG = "prize_loan_user_msg";
    
    /**
     * 中奖幸运编号产生-中奖用户(短信)
     */
    public static final String NODE_PRIZE_USER_MOBILE = "prize_user_mobile";
    /**
     * 中奖幸运编号产生-中奖用户(站内信)
     */
    public static final String NODE_PRIZE_USER_MSG = "prize_user_msg";
    
    /**
     * 中奖幸运编号产生-未中奖用户(短信)
     */
    public static final String NODE_PRIZE_NO_USER_MOBILE = "prize_no_user_mobile";
    /**
     * 中奖幸运编号产生-未中奖用户(站内信)
     */
    public static final String NODE_PRIZE_NO_USER_MSG = "prize_no_user_msg";
    //抽奖 消息类型 end
    
    /**消息节点，站内信子类型 - 产品转让****/
    public static final String NODE_TYPE_PRODUCT_TRANSFER = "product_transfer";    
    /** 消息节点类型: 转让产品提交成功*/
    public static final String NODE_TYPE_TRANSFER_SUBMIT_MSG = "transfer_submit_msg";
    /**消息节点，产品战让购买人支付成功站内信****/
    public static final String NODE_TYPE_TRANSFER_PAY_BUY_MSG = "transfer_pay_buy_msg";  
    /**消息节点，产品战让购买人支付成功转让人站内信****/
    public static final String NODE_TYPE_TRANSFER_PAY_TRANSFE_MSG = "transfer_pay_transfer_msg";
    /** 消息节点类型: 产品转让审核拒绝短信*/
    public static final String NODE_TYPE_TRANSFERR_EFUSE_TRANSFER_MOBILE = "transfer_refuse_transfer_mobile";
    /** 消息节点类型: 产品转让审核拒绝转让人站内信*/
    public static final String NODE_TYPE_TRANSFER_EFUSE_TRANSFER_MSG = "transfer_refuse_transfer_msg";    
    /** 消息节点类型: 产品转让审核拒绝购买人站内信*/
    public static final String NODE_TYPE_TRANSFER_EFUSE_BUY_MSG = "transfer_refuse_buy_msg";   
    
    /** 消息节点类型: 产品转让审核通过转让人站内信*/
    public static final String NODE_TYPE_TRANSFER_PASSED_TRANSFER_MSG = "transfer_passed_transfer_msg";    
    /** 消息节点类型: 产品转让审核通过购买人站内信*/
    public static final String NODE_TYPE_TRANSFER_PASSED_BUY_MSG = "transfer_passed_buy_msg";       
    
    
    
    
    /** 消息节点类型: 债权转让站内信*/
    public static final String NODE_TYPE_INVESTTRANSFER_MSG = "invest_transfer_msg";
    /** 站内信消息类型:成功 */
    public static final String STATION_MSG_TYPE_INVEST_TRANSFER_MSG = "invest_transfer_msg_sucess";
    /** 消息节点类型: 债权转让站内信*/
    public static final String NODE_TYPE_PRODUCT_TRANSFER_MSG = "product_transfer_msg";
    /** 消息节点类型: 债权转让手机短信*/
    public static final String NODE_TYPE_PRODUCT_TRANSFER_MOBILE = "product_transfer_mobile";
    /** 消息节点类型: 转让结束站内信*/
    public static final String NODE_TYPE_PRODUCT_TRANSFER_END_MSG = "product_transfer_end_msg";
    
    
    /** 消息节点类型: 认证通过短信*/
    public static final String NODE_TYPE_AUTHPASS_MOBILE = "auth_pass_mobile";
    /** 消息节点类型: 认证拒绝短信*/
    public static final String NODE_TYPE_AUTHREFUSE_MOBILE = "auth_refuse_mobile";
    /** 消息节点类型: 认证通过站内信*/
    public static final String NODE_TYPE_AUTHPASS_MSG = "auth_pass_msg";
    /** 消息节点类型: 认证拒绝站内信*/
    public static final String NODE_TYPE_AUTHREFUSE_MSG = "auth_refuse_msg";
    
 
    /** 消息节点类型: 项目审核拒绝短信*/
    public static final String NODE_TYPE_LOANREFUSE_MOBILE = "loan_refuse_mobile";
    /** 消息节点类型: 项目审核拒绝站内信*/
    public static final String NODE_TYPE_LOANREFUSE_MSG = "loan_refuse_msg";

    
    
    
    /** 消息节点类型: 修改密码成功站内信*/
    public static final String NODE_TYPE_UPDATE_PASSWORD_MSG = "update_password_msg";
    
    
    /** 消息节点类型: 众筹支持发货通知短信*/
    public static final String NODE_TYPE_SUPPORT_SENDING_MOBILE = "support_sending_mobile";    
    /** 消息节点类型: 众筹支持发货通知站内信*/
    public static final String NODE_TYPE_SUPPORT_SENDING_MSG = "support_sending_msg";
    /** 消息节点类型: 众筹支持发货结束转让通知站内信*/
    public static final String NODE_TYPE_SENDING_CANCEL_TRANSFER_MSG = "sending_cancel_transfer_msg";
    
    
    
    
    
    /**
     * 项目展示属性的配置
     * @author Administrator
     *
     */
    public static enum loanConfigType {
        /** 项目录入表单展示 ************/
        list("list"),
        /** 项目列表属性展示 ************/
        form("form");

        private String value;

        private loanConfigType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }
    
    public static enum loanAuditAction {
        /** 新建 ************/
        add("add"),
        /** 修改 ************/
        modify("modify"),
        /** 提交 ************/
        submit("submit"),
        /** 初审通过 ************/
        audit_pass("audit_pass"),
        /** 初审拒绝 ************/
        audit_refuse("audit_refuse"),
        /** 复审通过 ************/
        review_pass("review_pass"),
        /** 复审拒绝 ************/
        review_refuse("review_refuse"),
        /** 发布 ************/
        release("release"),
        /** 发布拒绝 ************/
        release_refuse("release_refuse"),
        /** 放款(满标)初审通过 ************/
        cash_pass("cash_pass"),
        /** 放款 ************/
        cash("cash"),
        /** 流标 ************/
        flow("flow"),
        /** 延期 ************/
        delay("delay"),
        /** 还款************/
        repay("repay"),
        /** 还款完成 ************/
        repayed("repayed");

        private String value;

        private loanAuditAction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 项目状态
     * 
     * @author haolingfeng
     * @version 1.0
     * 
     */

    public static enum loanState {
        /** 新建 ************/
        add("new"),
        /** 等待审核 ************/
        auditing("auditing"),
        /** 拒绝 ************/
        refuse("refuse"),
        /** 等待复核 ************/
        reviewing("reviewing"),
        /** 复审通过 ************/
        re_passed("re_passed"),
        /** 复审拒绝 ************/
        re_refuse("re_refuse"),
        /** 投标中 ************/
        funding("funding"),
        /** 即将投标 ************/
        onfunding("onfunding"),
        /** 投标完成 ************/
        funded("funded"),
        /** 投标截止初审通过 *********/
        repaying_passed("repaying_passed"),
        /** 还款中 ************/
        repaying("repaying"),
        /** 流标 ************/
        flow("flow"),
        /** 还款完成 ************/
        repayed("repayed"),
        /** 逾期 ************/
        overdue("overdue"),
        /** 坏账 ************/
        baddebt("baddebt"),
        /** 删除 ************/
        delete("delete");
        
        private String value;

        private loanState(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 众筹状态
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public static enum crowdFundingState {
        /** 草稿 ************/
        add("new"),
        /** 提交 ************/
        submit("submit"),
        /** 拒绝 ************/
        refuse("refuse"),
        /** 通过************/
        passed("passed"),
        /** 众筹中 ************/
        funding("funding"),
        /** 预热 ************/
        preheat("preheat"),
        /** 众筹完成 ************/
        funded("funded"),
        /** 流标 ************/
        flow("flow"),
        /** 放款完成 ************/
        lended("lended"),
        /** 结束 ************/
        end("end");
        private String value;

        private crowdFundingState(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 充值状态
     * 
     * @author haolingfeng
     * @version 1.0
     * 
     */

    public static enum rachargeState {
        /** 等待充值 ************/
        recharging("recharging"),
        /** 充值成功 ************/
        recharged("recharged"),
        /** 充值成功审核中************/
        recharge_success_auditing("recharge_success_auditing"),
        /**运营审核拒绝************/
        recharge_yy_refuse("recharge_yy_refuse"),
        /**财务审核拒绝************/
        recharge_cw_refuse("recharge_cw_refuse"),
        /**充值审核成功 ************/
       recharge_completed_success("recharge_completed_success"),
        /**充值成功*/
        recharge_success("recharge_success");
        
        private String value;

        private rachargeState(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        private static final Map<String, String> status = new HashMap<String, String>();
        public static Map<String, String> getRachargeState() {
            if (status.size() == 0) {
                status.put("recharging", "等待充值");
                status.put("recharged", "充值成功");
            }
            return status;
        }
    }
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 转账状态
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public static enum transferState {
        /** 等待充值 ************/
        transfering("transfering"),
        /** 充值成功 ************/
        transfered("transfered");
        private String value;

        private transferState(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 交易方向
     * 
     * @author haolingfeng
     * @version 1.0
     * 
     */
    public static enum tradeDirection {
        /** 收入 ************/
        in("in"),
        /** 支出 ************/
        out("out"),
        /** 冻结 ************/
        freeze("freeze"),
        /** 解冻 ************/
        unfreeze("unfreeze"),
        /** 转账 ************/
        transfer("transfer");
        private String value;

        private tradeDirection(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 交易类型
     * 
     * @author haolingfeng
     * @version 1.0
     * 
     */
    public static enum userTradeType {
        /** 转让手续费 ************/
        TRANSFER_FEE("transfer_fee"),
        /** 转让解冻************/
        TRANSFER_UNFREEZE("transfer_unFreeze"),
        /** 充值 ************/
        recharge("recharge"),
        /** 充值 冻结************/
        recharge_freeze("recharge_freeze"),    
        /** 充值审核解冻************/
        recharge_unFreeze("recharge_unFreeze"), 
        /** 转让 ************/
        saleTransfer("sale_transfer"),
        /** 购买债权转让 ************/
        buyTransfer("buy_transfer"),
        /** 充值手续费 ************/
        rechargeFee("recharge_fee"),
        /** 投资************/
        invest("invest"),
        /** 投资运费************/
        invest_transFee("invest_transFee"),
        /**投资意向金支付尾款************/
        Intention("Intention"),
        /**退款************/
        refund("refund"),
        /**退款运费************/
        refund_transFee("refund_transFee"),
        /** 投资冻结 ************/
        investFreeze("invest_freeze"),
        /** 流标 ************/
        investFlow("invest_flow"),
        /** 投资放款 ************/
        investCash("invest_cash"),
        /** 还款 ************/
        repay("repay"),
        /** 借款服务费 ************/
        borrowFee("borrow_fee"),
        /** 取现冻结 ************/
        withdrawFreeze("withdraw_freeze"),
        /** 购买转让冻结 ************/
        buyTransferFreeze("buy_transfer_freeze"),
        /** 取现 ************/
        withdraw("withdraw"),
        /** 拒绝取现 ************/
        withdrawRefuse("withdraw_refuse"),
        /** 提现失败 ************/
        withdrawFail("withdraw_fail"),
        /** 取现手续费 ************/
        withdrawFee("withdraw_fee"),
        /** 平台返利 ************/
        platformReward("platform_reward"),
        /** 推荐提成 ************/
        recommendReward("recommend_reward"),
        /** 直接推荐奖励 ************/
        directRecommendReward("direct_recommend_reward"),
        /** 间接推荐奖励 ************/
        indirectRecommendReward("indirect_recommend_reward"),
        /** 调账 ************/
        reviseBill("revise_bill"),
        /** 每日收益 ************/
        dailyIncome("dailyIncome"),
        /** 购买商品 ************/
        buyGoods("buy_goods"),
        /** 卖出商品 ************/
        saleGoods("sale_goods"),
        /** 配资保证金 ************/
        capitalGuaranteeMoney("capital_guarantee_money"),
        /** 配资保证金 ************/
        freeCapitalGuaranteeMoney("free_capital_guarantee_money"),
        /** 补仓 ************/
        capitalCoverMoney("capital_cover_money"),
        
        /** 项目分红 ************/
        loan_share("loan_share"),
        
        /** 项目分红冻结金额 ************/
        loan_bonus_freeze("loan_bonus_freeze"),
        
        /** 项目分红审核失败解冻金额 ************/
        loan_bonus_unfreeze("loan_bonus_unfreeze"),
        
        /** 项目分红审核通过扣除金额 ************/
        loan_bonus_out("loan_bonus_out"),
        
        /** 提取盈利 ************/
        capitalDrawInterestMoney("capital_draw_interest_money"),
        
        /** 退款冻结 ************/
        refund_freeze("refund_freeze"),
        /** 退款成功************/
        refund_success("refund_success"),
        /** 退款失败************/
        refund_fail("refund_fail");

        
        private String value;

        private userTradeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 平台交易类型
     * 
     * @author haolingfeng
     * @version 1.0
     * 
     */
    public static enum systemTradeType {
        /** 转让手续费 ************/
        TRANSFER_FEE("transfer_fee"),
        
        /** 垫付手续费 ************/
        advanceFee("advance_fee"),
        /** 调账 ************/
        reviseBill("revise_bill"),
        /** 准备金还款 ************/
        reserverRepay("reserve_repay"),
        /** 借款服务费 ************/
        borrowFee("borrow_fee"),
        /** 债权转让服务费 ************/
        transferFee("transfer_fee"),
        /** 放款服务费 ************/
        lendFee("lend_fee"),
        /** 流转标服务费 ************/
        transferInvestFee("transfer_invest_fee"),
        /** 活动支出 ************/
        activityPay("activity_pay"),
        /** 商户充值 ************/
        recharge("recharge"),
        /** 项目分红************/
        loan_share("loan_share"),
        /** 每日收益************/
        dailyIncome("dailyIncome"),
        /** 商户转账 ************/
        transfer("transfer");
        private String value;

        private systemTradeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 投资状态
     * 
     * @author haolingfeng
     * @version 1.0
     * 
     */
    public static enum investState {
        /** 等待支付 ************/
        paying("paying"),
        /** 投资成功 ************/
        investSuccess("success"),
        /** 投资失败 ************/
        investFail("fail"),
        /** 流标 ************/
        flow("flow"),
        /** 还款中 ************/
        repaying("repaying"),
        /** 放款失败 ************/
        cashFail("cash_fail"),
        /** 结束 ************/
        completed("completed");

        private String value;

        private investState(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        
        private static final Map<String, String> status = new HashMap<String, String>();
        public static Map<String, String> getInvestState() {
            if (status.size() == 0) {
                status.put("paying", "等待支付");
                status.put("success", "投标成功");
                status.put("fail", "投标失败");
                status.put("flow", "流标");
                status.put("repaying", "还款中");
                status.put("cash_fail", "还款失败");
                status.put("completed", "结束");
            }
            return status;
        }
        
    }
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 投资状态
     * 
     * @author wuwenbin
     * @version 1.0
     * 
     */
    public static enum investTransferState {
        /** 等待支付 ************/
        paying("paying"),
        /** 投资成功 ************/
        investSuccess("success"),
        /** 投资失败 ************/
        investFail("fail"),
        /** 流标 ************/
        flow("flow"),
        /** 还款中 ************/
        repaying("repaying"),
        /** 放款失败 ************/
        cashFail("cash_fail"),
        /** 结束 ************/
        completed("completed");

        private String value;

        private investTransferState(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        
        private static final Map<String, String> status = new HashMap<String, String>();
        public static Map<String, String> getInvestState() {
            if (status.size() == 0) {
                status.put("paying", "等待支付");
                status.put("success", "投标成功");
                status.put("fail", "投标失败");
                status.put("flow", "流标");
                status.put("repaying", "还款中");
                status.put("cash_fail", "还款失败");
                status.put("completed", "结束");
            }
            return status;
        }
        
    }

    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 还款状态
     * 
     * @author haolingfeng
     * @version 1.0
     * 
     */
    public static enum repayState {
        /** 还款中 ************/
        repaying("repaying"),
        /** 正常还款结束 ************/
        repayed("repayed"),
        /** 逾期还款 ************/
        overdueRepayed("overdue_repayed"),
        /** 提前还款************/
        preRepayed("pre_repayed");

        private String value;

        private repayState(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        private static final Map<String, String> status = new HashMap<String, String>();
        public static Map<String, String> getRepayState() {
            if (status.size() == 0) {
                status.put("repaying", "还款中");
                status.put("repayed", "还款完成");
                status.put("overdue_repayed", "逾期还款");
                status.put("pre_repayed", "提前还款");
            }
            return status;
        }
    }
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 提现状态
     * 
     * @author haolingfeng
     * @version 1.0
     * 
     */
    public static enum withDrawState {
        /** 等待处理 ************/
        add("new"),
        /** 等待审核 ************/
        auditing("auditing"),
        /** 审核通过 ************/
        passed("withdraw_passed"),
        /** 审核不通过 ***********/
        refuse("refuse"),
        /** 提现失败 ***********/
        fail("withdraw_fail"),
        /** 取现成功 ***********/
        success("success");

        private String value;

        private withDrawState(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        
        private static final Map<String, String> status = new HashMap<String, String>();
        public static Map<String, String> getWithDrawState() {
            if (status.size() == 0) {
                status.put("new", "等待处理");
                status.put("auditing", "等待审核");
                status.put("passed", "审核不通过");
            }
            return status;
        }
    }
    /** 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 用户类型
     *
     * @author dongzhongwei
     * @version 1.0
     *
     */
    
    public class userType {
        /**
         * 普通用户
         */
        public static final String P = "P";
        /**
         * 入驻商户
         */
        public static final String M = "M";
        /**
         * 担保公司
         */
        public static final String G = "G";
        /**
         * 管理员
         */
        public static final String A = "A";
        
    }
    
    
    /** 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 用户状态
     *
     * @author dongzhongwei
     * @version 1.0
     *
     */
    
    public class userStatus {
        /**
         * 正常
         */
        public static final String normal = "normal";
        /**
         * 锁定
         */
        public static final String lock = "lock";
        /**
         * 禁用
         */
        public static final String disable = "disable";
        
    }
    
    
    public class userSecurityState {
        /**
         * 未绑定
         */
        public static final String nobind = "未绑定";
        /**
         * 未认证
         */
        public static final String noAuth = "未认证";
        
        /**
         * 未开通
         */
        public static final String noopen = "未开通";
        /**
         * 已认证
         */
        public static final String authed = "已认证";
        
    }
    
    public class applyLoanStatus {
        /**
         * 申请中
         */
        public static final String applying = "applying";
        /**
         * 流标
         */
        public static final String flow = "flow";
        /**
         * 通过
         */
        public static final String passed = "passed";
    }
    
    public class messageNodeStatus {
        /**
         * 启用
         */
        public static final String on = "开启";
        /**
         * 不启用
         */
        public static final String off = "不启用";
    }
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 优惠券节点
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class rewardNode {
        /**
         * 注册
         */
        public static final String register = "1";
        /**
         * 投资
         */
        public static final String invest = "2";
        /**
         * 充值
         */
        public static final String recharge = "3";
        /**
         * 抽奖
         */
        public static final String lucky = "4";
    }
    //优惠券节点状态
    public class rewardNodeStatus {
        /**
         * 可用
         */
        public static final String use = "use";
        /**
         * 不可用
         */
        public static final String unuse = "unuse";
    }
    
    //奖励发放类型
    public class rewardAssignType {
        /**
         * 投标奖励
         */
        public static final String invest = "invest";
        /**
         * 推荐奖励
         */
        public static final String recommend = "recommend";
        
        /**
         * 分红
         */
        public static final String loan_share = "loan_share";
    }
    
    //推荐人类型
    public class invitorType{
        /** 推荐人类型: 直接 */
        public static final String direct = "direct";
        /** 推荐人类型: 间接  */
        public static final String indirect = "indirect";
    }
    
    //待办状态
    public class todoStatus{
        /** 已办 */
        public static final String untodo = "untodo";
        /** 待办  */
        public static final String todoed = "todoed";
    }
    
    //待办岗位
    public class todoPost{
        /** 项目录入员 */
        public static final String xmlry = "xmlry";
        /** 项目初审员 */
        public static final String xmcsy = "xmcsy";
        /** 项目复审员 */
        public static final String xmfsy = "xmfsy";
        /** 项目发布员 */
        public static final String xmfby = "xmfby";
        /** 项目放款初审员 */
        public static final String xmfkcsy = "xmfkcsy";
        /** 财务 */
        public static final String finance = "finance";
        
        /*************************众筹待办岗位*******************************************/
        /** 众筹项目审核员*/
        public static final String zcsh = "zcsh";
        /** 众筹项目复审员*/
        public static final String zcfs = "zcfs";
        
        /** 众筹项目预热审核员 */
        public static final String yrsh = "yrsh";
        
        /** 众筹项目预热发布员 */
        public static final String yrfb = "yrfb";
        
        /** 众筹项目发布员 */
        public static final String zcfb = "zcfb";
        /** 众筹项目放款员 */
        public static final String zcfk = "zcfk";
        
        
        /*************************特卖待办岗位*******************************************/
        /** 特卖项目审核员*/
        public static final String tmsh = "tmsh";
        /** 特卖项目发布员 */
        public static final String tmfb = "tmfb";
        
    }
    
    //待办事项
    public class todoEvent{
        public static final String withdrawApply="withdrawApply";
    }
    
    
    //邀请类型
    public class inviteType{
        /** 邀请类型:注册 */
        public static final String register = "register";
        /** 邀请类型:投资 */
        public static final String invest = "invest";
    }
    //调账申请状态
    public class adjustAccountState{
        /**审核中*/
        public static final String auditing = "auditing";
        /**审核通过*/
        public static final String passed = "passed";
        /**审核拒绝*/
        public static final String refused = "refused";
    }
    //平台第三方账户类型
    public class sysThirdAccount{
        /**基本账户*/
        public static final String basedt = "BASEDT";
        /**专属账户*/
        public static final String mdt000001 = "MDT000001";
        /**保证金账户*/
        public static final String dep000001 = "DEP000001";
    }
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 回报设置状态
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class crowdFundBackState{
        /**已满额*/
        public static final String FULL = "full";
        /**未满额*/
        public static final String NOTFULL = "notfull";
    }
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 退款状态
     *
     * @author wuwenbin
     * @version 1.0
     *
     */
    public class refundState{
        
        /**项目方待确认*/
        public static final String waitConfirm = "waitConfirm";
        /**项目方审核拒绝*/
        public static final String loanRefuse = "loanRefuse";
        /**项目方同意退款*/
        public static final String loanPassed = "loanPassed";
        /**审核中(项目方同意)*/
        public static final String auditing = "auditing";
        /**通过(提交成功)*/
        public static final String passed = "passed";
        /**拒绝*/
        public static final String refuse = "refuse";
        /**退款成功(真正处理成功)*/
        public static final String refund_success = "refund_success";
        /**提交处理中第三方状态(项目方提交)（不作为平台处理数据）*/
        public static final String submitProcess = "submitProcess";
        /**提交处理中第三方状态(平台方提交)（不作为平台处理数据）*/
        public static final String auditSubmitProcess = "auditSubmitProcess";
        /**交易失败*/
        public static final String fail = "fail";
    }
    
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 转让审核状态
     *
     * @author wuwenbin
     * @version 1.0
     *
     */
    public class transferAuditState{
        /**审核中*/
        public static final String auditing = "auditing";
        //hch start
        /** 审核通过(等待)  */
        public static final String auditing_passed="auditing_passed";
        /** 审核拒绝  (等待) */
        public static final String auditing_refuse="auditing_refuse";
        //hch end
        /**拒绝*/
        public static final String refuse = "refuse";
        /**通过*/
        public static final String passed = "passed";
    }
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 分红审核状态
     *
     * @author wuwenbin
     * @version 1.0
     *
     */
    public class bonusAuditState{
        
        /**创建*/
        public static final String add = "add";
        /**提交处理中*/
        public static final String submitProcess = "submitProcess";
        /**项目方确认**/
        public  static final String confirm = "confirm";
       
        /**分红成功*/
        public static final String success = "success";
        /**取消分红*/
        public static final String cancel = "cancel";
        
        
        /**等待审核**/
        public static final String waitAudit = "waitAudit";      
        /**审核中(区块链转账处理成功-项目方冻结资金成功)*/
        public static final String auditing = "auditing";
        /**拒绝*/
        public static final String refuse = "refuse";
        /**通过*/
        public static final String passed = "passed";

    }
    
    public class bonusState{
        
        /**等待分红*/
        public static final String add = "add";
        
        /**等待分红*/
        public static final String wait_bonus = "wait_bonus";
        
        /**分红提交进行中*/
        public static final String submitProcess = "submitProcess";        
        
        /**分红成功*/
        public static final String bonus_success = "bonus_success";
        
        /**分红失败*/
        public static final String bonus_fail = "bonus_fail";
    }
    
    public class crowdFundAuditState{
        /**审核中*/
        public static final String auditing = "auditing";
        /**审核通过*/
        public static final String passed = "passed";
        /**拒绝*/
        public static final String refused = "refused";
    }
    
    public class crowdFundPayState{
        /**已支付*/
        public static final String payed = "payed";
        /**未支付*/
        public static final String noPay = "noPay";
        /**已放款*/
        public static final String lend = "lend";
        /**已撤标*/
        public static final String cancel = "cancel";
    }
    
    public class crowdFundOrderState{
        /**已发货*/
        public static final String sending = "sending";
        /**待发货*/
        public static final String  processing = "processing";
        /**已收货*/
        public static final String sended = "sended";
    }
    public class goodsState{
        /**新加*/
        public static final String add = "add";
        /**审核通过*/
        public static final String passed = "passed";
        /**拒绝*/
        public static final String refused = "refused";
        /**发布*/
        public static final String canBuy = "canBuy";
        /**结束*/
        public static final String end = "end";
    }
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description:特卖订单状态 
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class goodsOrderState{
        /**等待处理*/
        public static final String processing = "processing";
        /**已发货*/
        public static final String sending = "sending";
        /**已收货*/
        public static final String sended = "sended";
    }
    
    public class payState{
        /**已支付*/
        public static final String payed = "payed";
        /**未支付*/
        public static final String noPay = "noPay";
    }
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description:抽奖发放状态 
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class drawReleaseState{
        /**已发放*/
        public static final String released = "released";
        /**未发放*/
        public static final String norelease = "norelease";
    }
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description:抽奖状态 
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class drawState{
        /**中奖*/
        public static final String draw = "draw";
        /**未中奖*/
        public static final String  nodraw= "nodraw";
    }
    
    /** 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 配资状态类型
     *
     * @author dongzhongwei
     * @version 1.0
     *
     */
    
    public class CapitalStatus {
        /**
         * 审批通过
         */
        public static final String passed = "passed";
        /**
         * 审批拒绝
         */
        public static final String refuse = "refuse";
        
        /**
         * 等待支付
         */
        public static final String paying = "paying";
        /**
         * 取消订单
         */
        public static final String cancel = "cancel";
        
        /**
         * 审核中
         */
        public static final String auditing = "auditing";
        /**
         * 借款项目审核
         */
        public static final String loanAuditing = "loanAuditing";
        
        /**
         * 筹款中
         */
        public static final String funding = "funding";
        /**
         * 筹款完成
         */
        public static final String funded = "funded";
        /**
         * 操盘中
         */
        public static final String tradering = "tradering";
        /**
         * 操盘结算
         */
        public static final String tradered = "tradered";
        /**
         * 补仓
         */
        public static final String cover = "cover";
        /**
         * 续约
         */
        public static final String renewal = "renewal";
        
    }
    
    /** 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 配资规则编号
     *
     * @author dongzhongwei
     * @version 1.0
     *
     */
    
    public class CapitalCode {
        /**
         * 免费体验
         */
        public static final String free = "tiyan";
        /**
         * 前台公共用户
         */
        public static final String publicUser = "shangchaoguPublicAdmin"; 
        
        /**
         * 按天计息单位
         */
        public static final String day = "day";
        /**
         * 按月计息单位
         */
        public static final String month = "month";
    }
    
    
    
    /**
     * 资源ID URL
     *
     */
    public class permissionCode {
        /**提交后,等待初审*/
        public static final String PROJECT_LOAN_FIRSTAUDIT = "project_loan_firstAudit";
        /**初审后,等待复审*/
        public static final String PROJECT_LOAN_SECONDAUDIT = "project_loan_secondAudit";
        /**复审后,等待发布*/
        public static final String PROJECT_LOAN_ISSURE = "project_loan_issure";
        /**初审、复审、发布拒绝,打回给录入员,等待提交*/
        public static final String PROJECT_LOAN_NEWLIST = "project_loan_newList";
        /**放款(满标)初审通过,交给项目放款员复审,等待放款复审（终审）*/
        public static final String PROJECT_LOAN_REPAYING = "project_loan_fundedLoan";
        /**前台 新手指引***/
        public static final String FRESHER = "fresher";
        
        
        
        /******************************众筹项目管理*************************************************/
        /**提交后,等待审核*/
        public static final String CROWDFUND_AUDIT = "crowdfund_auditing";
        /**等待复审*/
        public static final String CROWDFUND_REAUDIT = "crowdfund_secondAuditing";
        
        /**股权审核后,等预热申请审核*/
        public static final String CROWDFUND_APPLYPREHEAT = "crowdfund_applyPreheat";
        
        /**股权预热申请审核后,预热列表发布*/
        public static final String CROWDFUND_PREHEAT = "crowdfund_preheat";
        
        /**审核后,等待发布*/
        public static final String CROWDFUND_RELEASE = "crowdfund_releasing";
        /**发布后,等待放款*/
        public static final String CROWDFUND_CASH = "crowdfund_lending";
        
        
        /******************************特卖管理*************************************************/
        /**提交后,等待审核*/
        public static final String GOODS_AUDIT = "goodsManange_auditing";
        /**审核后,等待发布*/
        public static final String GOODS_RELEASE = "goodsManange_releasing";
        
        
        /******************************提现管理*************************************************/
        /**提现申请提交后,等待审核*/
        public static final String WITHDRAW_AUDIT = "finance_rechargeAndwithdraw_withdraw_auditing";
    }
    
    //平台向发起人收取的服务费比例
    public static final String CROWDFUND_SERVICEFEERATIO_CONFIG = "crowdfundServiceFeeRatio";
    //平台向投标人收取的充值费比例
    public static final String CROWDFUND_RECHARGEFEERATIO_CONFIG = "crowdfundRechargeFeeRatio";
    //平台收取的取现手续费
    public static final String WITHDRAW_FEE = "WITHDRAW_FEE";
}
