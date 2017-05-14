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

public class CrowdfundCoreConstants {
    

    /*--------------------------- 内存锁前缀 ---------------------------*/
    /** 充值单内存锁前缀 **/
    public static final String LOCK_RECHARGE = "recharge_";
    /** 投资内存锁前缀 **/
    public static final String LOCK_INVEST = "invest_";
    /** 流转标投资内存锁前缀 **/
    public static final String LOCK_TRANSFER_INVEST = "transferInvest_";
    /** 投资内存锁前缀 **/
    public static final String LOCK_WITHDRAW = "withdraw_";
    /** 转账内存锁前缀 **/
    public static final String LOCK_SYS_TRANSFER = "systransfer_";
    /**
     * 转让内存所前缀
     */
    public static final String LOCK_TRANSFER="transfer_";
    
    
    /*---------------------------站内信-------------------------------*/
    /** 站内信状态:已读 */
    public static final String STATION_MSG_STATUS_READ = "1";
    /** 站内信状态:未读 */
    public static final String STATION_MSG_STATUS_NOREAD = "0";
    
    
    /** 站内信消息类型:充值提现 */
    public static final String STATION_MSG_TYPE_RECHARGE_WITHDRAW = "recharge_withdraw";
    /** 站内信消息类型:项目消息 */
    public static final String STATION_MSG_TYPE_LOAN_MSG = "loan_msg";
    /** 消息节点类型: 充值站内信*/
    public static final String NODE_TYPE_RECHARGE_MSG = "recharge_msg";
    /** 消息节点类型: 充值手机短信*/
    public static final String NODE_TYPE_RECHARGE_MOBILE = "recharge_mobile";
    /** 消息节点类型: 提现申请站内信*/
    public static final String NODE_TYPE_WITHDRAW_APPLY_MSG = "withdrawApply_msg";
    /** 消息节点类型: 提现申请手机短信*/
    public static final String NODE_TYPE_WITHDRAW_APPLY_MOBILE = "withdrawApply_mobile";
    /** 消息节点类型: 提现站内信*/
    public static final String NODE_TYPE_WITHDRAW_MSG = "withdraw_msg";
    /** 消息节点类型: 投标放款手机短信*/
    public static final String NODE_TYPE_INVEST_CASH_MOBILE = "invest_cash_mobile";
    /** 消息节点类型: 投标放款站内信*/
    public static final String NODE_TYPE_INVEST_CASH_MSG = "invest_cash_msg";
    
    /** 消息节点类型: 项目发布手机短信*/
    public static final String NODE_TYPE_LOAN_RELEASE_MOBILE = "loan_release_mobile";
    /** 消息节点类型: 项目发布站内信*/
    public static final String NODE_TYPE_LOAN_RELEASE_MSG = "loan_release_msg";
    
    /** 消息节点类型: 项目回款站内信*/
    public static final String NODE_TYPE_LOAN_REPAY_MSG = "loan_repay_msg";
    /** 消息节点类型: 项目提前回款站内信*/
    public static final String NODE_TYPE_LOAN_PREREPAY_MSG = "loan_prerepay_msg";
    /** 消息节点类型: 项目提前回款手机消息*/
    public static final String NODE_TYPE_LOAN_PREREPAY_MOBILE = "loan_prerepay_mobile";
    /** 消息节点类型: 项目回款手机消息*/
    public static final String NODE_TYPE_LOAN_REPAY_MOBILE = "loan_repay_mobile";
    
    /** 消息节点类型: 项目放款站内信*/
    public static final String NODE_TYPE_LOAN_CASH_MSG = "loan_cash_msg";
    /** 消息节点类型: 项目放款手机消息*/
    public static final String NODE_TYPE_LOAN_CASH_MOBILE = "loan_cash_mobile";
    
    /** 消息节点类型: 还款成功站内信*/
    public static final String NODE_TYPE_REPAYSUCCESS_MSG = "repaysuccess_msg";
    /** 消息节点类型: 还款成功手机消息*/
    public static final String NODE_TYPE_REPAYSUCCESS_MOBILE = "repaysuccess_mobile";
    
    /**意向金项目结束前N天 可进行支付     || 项目结束前N天意向金违约退款 **/
    public static final String INTENTION_CAN_PAY_DAY = "INTENTION_CAN_PAY_DAY";
    
    /**意向金项目 支付N天后未支付尾款  违约退款天数 **/
    public static final String INTENTION_DEFAULT_REFUND_DAY = "INTENTION_DEFAULT_REFUND_DAY";
    
    /**意向金支付比例 **/
    public static final String INTENTION_PAY_SCALE = "INTENTION_PAY_SCALE";
    
    /**转让市场转让时长 **/
    public static final String TRANSFER_PRODUCT_DAY = "TRANSFER_PRODUCT_DAY";
    
    /**每日收益利率 **/
    public static final String DAILY_INCOME_RATE = "DAILY_INCOME_RATE";
    
    /**收取服务费比列**/
    public static final String SERVICE_FEE_SCALE = "SERVICE_FEE_SCALE";
    
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
        /** 预热 ************/
        preheat("preheat"),
        /** 发布拒绝 ************/
        release_refuse("release_refuse"),
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
     * Description: 众筹状态
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public static enum crowdFundingState {
        /** 草稿 ************/
        add("add"),
        /** 提交 ************/
        submit("submit"),
        /** 拒绝 ************/
        refuse("refuse"),
        /** 通过************/
        passed("passed"),
        /** 复审通过 ************/
        re_passed("re_passed"),
        /** 复审拒绝 ************/
        re_refuse("re_refuse"),
        /** 众筹中 ************/
        funding("funding"),
        /** 发布锁定 ************/
        funding_locking("funding_locking"),
        /** 预热 ************/
        preheat("preheat"),
        /** 预热 锁定************/
        preheat_locking("preheat_locking"),
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
     * Description: 交易类型
     * 
     * @author haolingfeng
     * @version 1.0
     * 
     */
    public static enum userTradeType {
        /** 充值 ************/
        recharge("recharge"),
        /** 充值手续费 ************/
        rechargeFee("recharge_fee"),
        /** 投资************/
        invest("invest"),
        /** 流标 ************/
        investFlow("invest_flow"),
        /** 投资放款 ************/
        investCash("invest_cash"),
        /** 意向金退款 ************/
        intentionPayReturnFee("intentionPayReturnFee"),
        /** 放款服务费 ************/
        /** 借款服务费 ************/
        borrowFee("borrow_fee"),
        /** 取现冻结 ************/
        withdrawFreeze("withdraw_freeze"),
        /** 取现 ************/
        withdraw("withdraw"),
        /** 取现手续费 ************/
        withdrawFee("withdraw_fee"),
        /** 平台返利 ************/
        platformReward("platform_reward"),
        /** 购买商品 ************/
        buyGoods("buy_goods"),
        /** 卖出商品 ************/
        saleGoods("sale_goods"),
        /** 转入生利宝:从汇付余额转到生利宝 ************/
        fssTransI("fsstrans_i"),
        /** 转出生利宝:从生利宝转到汇付余额 ************/
        fssTransO("fsstrans_o");
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
        /** 垫付手续费 ************/
        advanceFee("advance_fee"),
        /** 调账 ************/
        reviseBill("revise_bill"),
        /** 准备金还款 ************/
        reserverRepay("reserve_repay"),
        /** 借款服务费 ************/
        borrowFee("borrow_fee"),
        /** 意向金退款罚金 ************/
        intentionPayReturnFee("intentionPayReturnFee"),
        /** 放款服务费 ************/
        lendFee("lend_fee"),
        /** 流转标服务费 ************/
        transferInvestFee("transfer_invest_fee"),
        /** 提现服务费 ************/
        withDrawFee("with_draw_fee"),
        /** 活动支出 ************/
        activityPay("activity_pay"),
        /** 商户充值 ************/
        recharge("recharge"),
        /** 挂牌 ************/
        buyTransfer("buyTransfer"),
        /** 挂牌转让服务费************/
        buyTransferFee("buyTransferFee"),
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
     * Description: 提现状态
     * 
     * @author haolingfeng
     * @version 1.0
     * 
     */
    public static enum withDrawState {
        /** 等待审核 ************/
        add("new"),
        /** 等待审核 ************/
        auditing("auditing"),
        /** 审核通过 ************/
        passed("passed"),
        /** 审核不通过 ***********/
        refuse("refuse"),
        /** 发送成功 ***********/
        sended("sended"),
        /** 提现完成***********/
        success("success"),
        /** 提现失败***********/
        fail("fail"),
        /** 提现区块链成功***********/
        blockchainsuccess("blockchainsuccess")
        ;

        private String value;

        private withDrawState(String value) {
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
    
    public class crowdFundAuditState{
        /**新建**/
        public static final String  add= "add";
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
        /** 支付中*/
        public static final String locking = "locking";
        /**支付失败*/
        public static final String payFail = "payFail";
    }
    
    public class crowdFundOrderState{
        
        /**新建*/
        public static final String add = "add";
        /**已放款*/
        public static final String lended = "lended";
        /**申请退款   已退款*/
        public static final String refunded = "refunded";
        /**已撤标*/
        public static final String cancel = "cancel";
        /**意向金支付取消**/
        public static final String returnback = "returnback";
        /**已发货*/
        public static final String sending = "sending";
        /**发送结束*/
//        public static final String end = "end";
        /**已收货*/
        //public static final String sended = "sended";
    }
    
    public class crowdFundSystmeWithDrawParam {
        /**平台提现银行卡号**/
        public static final String bankcardid="bankcardid";
        /**平台提现收方帐户名**/
        public static final String userName="userName";
        /**平台提现银行卡省份**/
        public static final String ProvinceName="ProvinceName";

        /**平台银行卡所在市**/
        public static final String CityName="CityName";

        /**提现银行卡联行号**/
        public static final String bankcode="bankcode";
        /**平台提现收方开户行**/
        public static final String OpenAcctBank="OpenAcctBank";

    }
    
    public class crowdFundSupportClass{
        /**已放款*/
        public static final String transfer = "transfer";
       
    }
    public class attentionType{
        /**众筹项目*/
        public static final String loan = "loan";
        /**特卖*/
        public static final String goods = "goods";
    }
    
    public static enum userCrowdfundTradeType {
        /** 充值 ************/
        recharge("recharge"),
        /** 充值手续费 ************/
        rechargeFee("recharge_fee"),
        /** 投资************/
        invest("invest"),
        /** 流标 ************/
        investFlow("invest_flow"),
        /** 投资放款 ************/
        investCash("invest_cash"),
        /** 借款服务费 ************/
        borrowFee("borrow_fee"),
        /** 取现冻结 ************/
        withdrawFreeze("withdraw_freeze"),
        /** 取现 ************/
        withdraw("withdraw"),
        /** 取现手续费 ************/
        withdrawFee("withdraw_fee"),
        /** 平台返利 ************/
        platformReward("platform_reward"),
        /** 购买商品 ************/
        buyGoods("buy_goods"),
        /** 卖出商品 ************/
        saleGoods("sale_goods");
        private String value;

        private userCrowdfundTradeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    
    public class goodsState{
        /**新加*/
        public static final String add = "add";
        /**提交 */
        public static final String submit = "submit";
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
     * Description: 挂牌状态
     * 
     * @author wuwenbin
     * @version 1.0
     * 
     */
    public static enum transferStateFbd {
        /** 转让中 ************/
        transfering("transfering"),
        /**
         * 转让中锁定
         */
        transfering_locking("transfering_locking"),
        /** 转让完成 ************/
        transfered("transfered"),
        /** 转让结束 ************/
        transferend("transferend");
        
        private String value;

        private transferStateFbd(String value) {
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
     * Description: 支持类别
     * 
     * @author wuwenbin
     * @version 1.0
     * 
     */
    public static enum supportClass {
        /** 购买转让商品 ************/
        transferSupport("transferSupport"),
        /** 普通支持 ************/
        ordinarySupport("ordinarySupport");
        
        private String value;

        private supportClass(String value) {
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
     * Description: 挂牌购买状态
     * 
     * @author wuwenbin
     * @version 1.0
     * 
     */
    public static enum transferBuyStateFbd {
        /** 支付中 ************/
        paying("paying"),
        /** 支付完成 ************/
        payed("payed"),
        /** 支付失败 ************/
        fail("fail");
        
        private String value;

        private transferBuyStateFbd(String value) {
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
     * Description: 挂牌审核状态
     * 
     * @author wuwenbin
     * @version 1.0
     * 
     */
    public static enum transferAuditState {
        /** 审核中 ************/
        auditing("auditing"),
        /** 审核通过 ************/
        audit_pass("audit_pass"),
        /** 审核拒绝 ************/
        audit_refuse("audit_refuse");
        
        private String value;

        private transferAuditState(String value) {
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
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description:众筹类别 
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class CrowdfundType{
        /**公益*/
        public static final String PUBLIC_SERVICE = "public";
        /**产品*/
        public static final String PRODUCT = "product";
        /**实物*/
        public static final String ENTITY = "entity";
        /**房产*/
        public static final String HOUSE = "house";
        /**股权*/
        public static final String STOCK = "stock";
    }
    
    public class supportWay{
        /**系统支持*/
        public static final String bySystem = "bySystem";
        /**真实支持*/
        public static final String byUser = "byUser";
    }
    public class deviceType{
        /**IOS*/
        public static final String IOS = "IOS";
        /**PC*/
        public static final String PC = "PC";
    }
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description:股权支付类型 
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class StockSupportType{
        /**领投*/
        public static final String firstInvest = "firstInvest";
        /**跟投*/
        public static final String followInvest = "followInvest";
        /**普通投*/
        public static final String commonInvest = "commonInvest";
    }
    
    
    public class UserFileState{
        /**审核中*/
        public static final String auditing = "auditing";
        /**通过*/
        public static final String passed = "passed";
        /**拒绝*/
        public static final String refused = "refused";
    }
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 众筹用户等级
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class UserLevel{
        //普通会员
        public static final String common = "common";
        
        //认证通过
        public static final String authed = "authed";
        
        //领投人
        public static final String lead = "lead";
        
    }
    
    /**
     * 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 投资类型
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class InvestType{
        //普通投资
        public static final String commonInvest = "=commonInvest";
        
        //领投
        public static final String firstInvest = "firstInvest";
        
        //跟投
        public static final String followInvest = "followInvest";
        
    }
    
    public class BuyType{
        //支持
        public static final String invest = "=invest";
        
        //认购定金
        public static final String orderBuy = "orderBuy";
    }
    
    public class ProcessState{
        /**处理中*/
        public static final String processing = "processing";
        /**处理完成*/
        public static final String processed = "processed";
    }
    
    
    
    public class InvestPayWay{
        public static final String depositPay = "depositPay";
        public static final String onePay = "onePay";
    }
    
    
    public class LoanUserPayNode{
        /**预热前*/
        public static final String pre_preheat = "pre_preheat";
        /**预热*/
        public static final String preheat = "preheat";
       
    }
    
    public class WithDrawFeeType{
        /**按每笔收取*/
        public static final String perFee = "perFee";
        /**按比例收取*/
        public static final String ratioFee = "ratioFee";
    }
    
    
    
    
    //挂牌服务费
    public static final String INVEST_TRANSFER_FEE = "INVEST_TRANSFER_FEE";
    //平台收取的取现手续费
    public static final String WITHDRAW_FEE = "WITHDRAW_FEE";
    //平台收取的取现手续费比例
    //public static final String WITHDRAW_RATIO_FEE = "WITHDRAW_RATIO_FEE";
    //平台向发起人收取的保证金比例
    public static final String CROWDFUND_DEPOSITRATIO_CONFIG = "crowdfundDepositRatio";
    //平台向发起人收取的服务费比例
    public static final String CROWDFUND_SERVICEFEERATIO_CONFIG = "crowdfundServiceFeeRatio";
    //平台向投标人收取的充值费比例
    public static final String CROWDFUND_RECHARGEFEERATIO_CONFIG = "crowdfundRechargeFeeRatio";
    
    public static final String CROWDFUNDING_PHOTO_FILE = "crowdfund/";
    public static final String GOODS_PHOTO_FILE = "goods/";
    public static final String CROWDFUNDING_CONTRACT_FILE = "crowdfund_contract/";
    public static final String CROWDFUNDING_ORG_FILE = "crowdfund_org/";
    public static final String CROWDFUNDING_AUTH_FILE = "crowdfund_auth/";
    
    
    /***************************众筹项目进展的节点****************************/
    //项目进展-项目上线
    public static final String NODE_CROWDFUND_PROGRESS_RELEASE = "crowdfund_progress_release";
    //项目进展-第一个人支持
    public static final String NODE_CROWDFUND_PROGRESS_FIRSTINVEST = "crowdfund_progress_firstInvest";
    //项目进展-50个人支持
    public static final String NODE_CROWDFUND_PROGRESS_50INVESTOR = "crowdfund_progress_50Investor";
    //项目进展-100人支持
    public static final String NODE_CROWDFUND_PROGRESS_100INVESTOR = "crowdfund_progress_100Investor";
    //项目进展-筹资达到50%
    public static final String NODE_CROWDFUND_PROGRESS_50FUND = "crowdfund_progress_50fund";
    //项目进展-融资成功
    public static final String NODE_CROWDFUND_PROGRESS_FUNDOK = "crowdfund_progress_fundok";
    //项目进展-融资结束
    public static final String CROWDFUND_PROGRESS_FUNDEND = "crowdfund_progress_fundend";
}
