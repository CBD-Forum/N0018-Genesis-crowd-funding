/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: PerHeepayConstants.java 
 *
 * Created: [2015-3-25 上午11:18:45] by haolingfeng
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

package com.fbd.core.app.letvPay.common;

import com.fbd.core.util.SpringPropertiesHolder;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: UPS支付
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public class LetvPayConstants {
    
    public static final String THIRD_ID="huifubao";
    /**
     * 区块链
     */
    public static final String THIRE_BLOCK_CHAIN_ID="block_chain_id";
    
    public static final String SIGNTYPE = "RSA";
    public static final String INPUTCHARSET = "UTF-8";
    public static final String VERSION = "1.0";
    
    
    public static class Config{
        /**合作者ID**/
        public static final String PARENT_ID = SpringPropertiesHolder
                .getProperty("PARENT_ID");
        /** 商户USP公钥**/
        public static final String USP_PUBLIC_KEY = SpringPropertiesHolder
                .getProperty("USP_PUBLIC_KEY");
        
        /** 18888公钥**/
        public static final String PUBLIC_KEY = SpringPropertiesHolder
                .getProperty("PUBLIC_KEY");
        
        /** 18888私钥**/
        public static final String PRIVATE_KEY = SpringPropertiesHolder
                .getProperty("PRIVATE_KEY");
        
        /** MD5**/
        public static final String MD5KEY = SpringPropertiesHolder
                .getProperty("MD5KEY");
        
        
        /**会员网关地址**/
        public static final String HUIYUAN = SpringPropertiesHolder
                .getProperty("HUIYUAN");
        
        /**收单网关地址**/
        public static final String SHOUDAN = SpringPropertiesHolder
                .getProperty("SHOUDAN");
        
        /**
         * 用户平台的回调地址前缀
         */
        public static final String PRE_RESPONSE_WEB_URL = SpringPropertiesHolder
                .getProperty("PRE_RESPONSE_RAIN_WEB_URL");
        
        /**
         * 管理员平台的回调地址前缀
         */
        public static final String PRE_RESPONSE_ADMIN_URL = SpringPropertiesHolder
                .getProperty("PRE_RESPONSE_RAIN_ADMIN_URL");
        
        
        //#################首信易######################
        
        /**首信易商户编号**/
        public static final String SXY_SHBH = SpringPropertiesHolder
                .getProperty("SXY_SHBH");
        
        /**
         * 用户平台的回调地址前缀
         */
        public static final String SXY_PRE_RESPONSE_RAIN_WEB_URL = SpringPropertiesHolder
                .getProperty("SXY_PRE_RESPONSE_RAIN_WEB_URL");
        
    }
    
    public class BusiType{
        //hch start
        /**
         * 购买转让(前端)
         */
        public static final String TRANSFER_WEB="transfer_web";
        //hch end
        
        /**
         * 绑定用户
         */
        public static final String bindAccount = "bindAccount";
        /**
         * 实名认证
         */
        public static final String authAccount = "authAccount";
        
        /**
         * 充值
         */
        public static final String recharge = "recharge";
        
        /**
         * 投标
         */
        public static final String invest = "invest";
        
        /**
         * 流转标
         */
        public static final String transferInvest = "transferInvest";
        
        /**
         * 放款(项目方)
         */
        public static final String lend_loan = "lend_loan";
        
        /**
         * 放款(平台)
         */
        public static final String lend_platform = "lend_platform";
        
        /**
         * 分红
         */
        public static final String bonus = "bonus";
        
        /**
         * 撤标
         */
        public static final String cancel = "cancel";
        
        /**
         * 提现
         */
        public static final String withdraw = "withdraw";
        /**
         * 退款  项目方审核
         */
        public static final String refund_loan = "refund_loan";
        /**
         * 退款 平台审核通过
         */
        public static final String refund_passed = "refund_passed";
        
        /**
         * 退款 平台审核拒绝
         */
        public static final String refund_refuse = "refund_refuse";
        
        /**
         * 项目分红
         */
        public static final String loan_bonus = "loan_bonus";
        /**
         * 项目分红（投资人）
         */
        public static final String investor_bonus = "investor_bonus";
        
        /**
         * 分红拒绝
         */
        public static final String bonus_refuse = "bonus_refuse";
        
        /**
         * 平台结算用户利息
         */
        public static final String refund_interest = "refund_interest";

    }
    
    /**
     * 标识类型
     */
    public  class IdentityType {
        /**
         * 平台用户id
         */
        public static final String UID = "UID";
        
        /**
         * 钱包会员号
         */
        public static final String MEMBER_ID = "MEMBER_ID";
        
        /**
         * 手机号或者邮箱号
         */
        public static final String MOBILE = "MOBILE";
        
        /**
         * 平台登录名
         */
        public static final String LOGIN_NAME = "LOGIN_NAME";
        
        /**
         * 平台用户公司id
         */
        public static final String COMPANY_ID = "COMPANY_ID";
        
    }
    
    
    /**
     * 买家账户类型
     */
    public  class MJHType {
        /**
         * 基本户
         */
        public static final String JBH = "101";
        
        /**
         *  信用户
         */
        public static final String XYH = "102";
        
        /**
         *  企业基本户
         */
        public static final String QYJBH = "201";
        
    }
    
    /**
     * 业务状态 S, P, F
     */
    public  class bizStatus {
        /**
         *交易成功
         */
        public static final String S = "S";
        
        /**
         *  处理中
         */
        public static final String P = "P";
        
        /**
         * 交易失败
         */
        public static final String F = "F";
        
    }
    
    
    
    /**
     * 充值方式
     */
    public  class rechargeType {
        /**
         * （POS支付） 
         */
        public static final String pos = "pos";
        
        /**
         *  (网银支付)
         */
        public static final String online_bank = "online_bank";
        
        /**
         * (快捷支付)
         */
        public static final String qpay = "qpay";
    }
    
    /**
     * 验证模式
     */
    public  class authMode {
        /**
         * 银行卡实名认证
         */
        public static final String CARD_CERT = "CARD_CERT";
        
        /**
         * 身份认证
         */
        public static final String IDENTITY_CERT = "IDENTITY_CERT";
    }
    
    /**
     * 验卡
     */
    public  class checkCard {
        /**
         * 用来验卡
         */
        public static final String checkCard = "0";
        
        /**
         * 实名认证
         */
        public static final String userAuth = "1";
    }
    
    /**
     * 充值状态
     */
    public  class rechargeState {
        /**
         * 成功
         */
        public static final String DEPOSIT_SUCCESS = "DEPOSIT_SUCCESS";
        
        /**
         * 失败
         */
        public static final String DEPOSIT_FAIL = "DEPOSIT_FAIL";
    }
    
    
    /**
     * 提现状态
     */
    public  class withDrawState {
        
        
        /**
         * 已提交银行
         */
        public static final String WITHDRAWAL_SUBMITTED = "WITHDRAWAL_SUBMITTED";
        
        /**
         * 提现成功
         */
        public static final String WITHDRAWAL_SUCCESS = "WITHDRAWAL_SUCCESS";
        
        /**
         * 提现失败
         */
        public static final String WITHDRAWAL_FAIL = "WITHDRAWAL_FAIL";
        
        /**
         * 提现退票
         */
        public static final String RETURN_TICKET = "RETURN_TICKET";
    }
    
    /**
     * 第三方类型
     */
    public  class thirdType {
        /**
         * 首信易
         */
        public static final String SXY = "SXY";
        
        /**
         * UPS
         */
        public static final String UPS = "UPS";
    }
    
    /**
     * 接口名称
     */
    public  class Service {
        /**
         * 实名认证
         */
        public static final String bind_identity = "bind_identity";
        
        /**
         * 开个人账户接口
         */
        public static final String create_personal_member = "create_personal_member";
        
        /**
         * 新增银行卡接口
         */
        public static final String create_bank_card = "create_bank_card";
        
        /**
         * 解绑银行卡接口
         */
        public static final String unbundling_bank_card = "unbundling_bank_card";
        
        /**
         * 查询银行卡接口
         */
        public static final String query_bank_card = "query_bank_card";
        
        /**
         * 充值网关接口
         */
        public static final String create_deposit = "create_deposit";
        
        /**
         * 付款到卡网关接口
         */
        public static final String payment_to_card = "payment_to_card";
        
        /**
         * 支付推进接口
         */
        public static final String advance_instant_pay = "advance_instant_pay";
        
        /**
         * 即时到账接口
         */
        public static final String create_instant_trade = "create_instant_trade";
        /**
         * 开企业户接口
         * 
         */
        public static final String create_enterprise_member ="create_enterprise_member";
        
        
    }
    
    /**
     * 支付属性
     */
    public  class payAttribute {
        /**
         * 大快捷
         */
        public static final String qpay = "qpay";
        
        /**
         * 普通提现卡
         */
        public static final String normal = "normal";
        
        /**
         *代扣卡
         */
        public static final String withhold = "withhold";
        
    }
    
    /**
     * 卡类型
     */
    public  class cardType {
        /**
         *借记
         */
        public static final String DEBIT = "DEBIT";
        
        /**
         * 贷记（信用卡）
         */
        public static final String CREDIT = "CREDIT";
        
        /**
         *存折
         */
        public static final String PASSBOOK = "PASSBOOK";
        
        /**
         *其它
         */
        public static final String OTHER = "OTHER";
        
    }
    
    
    
    
    /**
     * 卡属性
     */
    public  class cardAttribute {
        /**
         *对私
         */
        public static final String C = "C";
        
        /**
         * 对公
         */
        public static final String B = "B";
        
        
    }
    
    /**
     * 交易列表 (自定义参数)
     */
    public  class TraderList {
        /**
         * 交易订单号
         */
        public static final String traderNo = "traderNo";
        
        /**
         * 商品名称
         */
        public static final String commodityName = "commodityName";
        
        /**
         * 商品单价
         */
        public static final String commodityPrice = "commodityPrice";
        
        /**
         * 商品数量
         */
        public static final String commodityNum = "commodityNum";
        
        /**
         * 交易金额 =（商品单价×商品数量）,这里的交易金额是商品的原价，既这个价格是扣除订金金额之前的价格
         */
        public static final String traderAmt = "traderAmt";
        
        /**
         * 交易金额分润账号集 
         */
        public static final String royalty_parameters = "royalty_parameters";
        
        /**
         * 卖家标示ID
         */
        public static final String sellerId = "sellerId";
        
        /**
         * 卖家标示ID类型
         */
        public static final String sellerType = "sellerType";
        
        /**
         * 卖家手机号
         */
        public static final String sellerMobile = "sellerMobile";
        
        
        /**
         * 商品描述
         */
        public static final String productDescription = "productDescription";
        
        /**
         * 商品展示URL 
         */
        public static final String commodityShowUrl = "commodityShowUrl";
        
        /**
         * 商户订单提交时间
         */
        public static final String subOrderTime = "subOrderTime";
        
        /**
         * 服务器异步通知页面路径
         */
        public static final String notifyUrl = "notifyUrl";
        
        /**
         * 支付过期时间
         */
        public static final String paymentExpires = "paymentExpires";
        
        /**
         *店铺名称
         */
        public static final String storeName = "storeName";
        
        /**
         * 卖家账户类型
         */
        public static final String sellerAccountType = "sellerAccountType";
        
    }
    
    public  class SyxParam {
        /**
         * 商户编号
         */
        public static final String v_mid = "v_mid";
        
        /**
         * 订单编号
         */
        public static final String v_oid = "v_oid";
        
        /**
         * 收货人姓名
         */
        public static final String v_rcvname = "v_rcvname";
        
        /**
         * 收货人地址
         */
        public static final String v_rcvaddr = "v_rcvaddr";
        
        /**
         * 收货人电话
         */
        public static final String v_rcvtel = "v_rcvtel";
        
        
        /**
         *收货人邮政编码
         */
        public static final String v_rcvpost = "v_rcvpost";
        
        /**
         * 订单总金额
         */
        public static final String v_amount = "v_amount";
        
        /**
         * 订单产生日期
         */
        public static final String v_ymd = "v_ymd";
        
        /**
         * 商户配货状态
         */
        public static final String v_orderstatus = "v_orderstatus";
        
        /**
         * 订货人姓名
         */
        public static final String v_ordername = "v_ordername";
        
        /**
         * 支付币种
         */
        public static final String v_moneytype = "v_moneytype";
        
        /**
         * 返回商户页面地址
         */
        public static final String v_url = "v_url";
        
        /**
         * 订单数字指纹
         */
        public static final String v_md5info = "v_md5info";
    }
    
    
    public  class Param {
        
        /**
         * 接口名称
         */
        public static final String service = "service";
        
        /**
         * 版本
         */
        public static final String version = "version";
        
        /**
         * 合作者身份ID
         */
        public static final String partner_id = "partner_id";
        
        /**
         * 参数编码字符集
         */
        public static final String _input_charset = "_input_charset";
        
        /**
         * 签名
         */
        public static final String sign = "sign";
        
        /**
         * 签名方式
         */
        public static final String sign_type = "sign_type";
        
        /**
         * 会员标识号
         */
        public static final String identity_no = "identity_no";
        /**
         * 标识类型
         */
        public static final String identity_type = "identity_type";
        /**
         * 业务唯一请求号
         */
        public static final String request_no = "request_no";
        
        /**
         * 验证模式 CARD_CERT：银行卡实名认证；IDENTITY_CERT：身份认证；
         */
        public static final String auth_mode = "auth_mode";
        
        /**
         * 银行编号
         */
        public static final String bank_code = "bank_code";
        
        /**
         * 银行名称
         */
        public static final String bank_name = "bank_name";
        
        /**
         * 支行名称
         */
        public static final String bank_branch = "bank_branch";
        
        /**
         * 银行卡号
         */
        public static final String bank_account_no = "bank_account_no";
        
        
        /**
         * 身份证号
         */
        public static final String cert_num = "cert_num";
        
        /**
         * 手机号
         */
        public static final String mobile_num = "mobile_num";
        
        /**
         * 支付属性
         */
        public static final String pay_attribute = "pay_attribute";
        
        /**
         * 省份
         */
        public static final String province = "province";
        
        
        /**
         * 城市
         */
        public static final String city = "city";
        
        /**
         * 卡类型
         */
        public static final String card_type = "card_type";
        
        /**
         * 卡属性
         */
        public static final String card_attribute = "card_attribute";
        
        
        /**
         * 银行卡号
         */
        public static final String bank_no = "bank_no";
        
        /**
         * 商户网站唯一订单号
         */
        public static final String outer_trade_no = "outer_trade_no";
        
        /**
         * 交易订单号
         */
        public static final String inner_trade_no = "inner_trade_no";
        
        /**
         * 充值状态
         */
        public static final String deposit_status = "deposit_status";
        
        /**
        * 提现状态
        */
       public static final String withdrawal_status = "withdrawal_status";
        
        
        
        /**
         * 充值金额
         */
        public static final String deposit_amount = "deposit_amount";
        
        
        /**
         * 账户姓名
         */
        public static final String account_name = "account_name";
        
        /**
         * 证件号码
         */
        public static final String identity_num = "identity_num";
        
        /**
         * 手机号
         */
        public static final String mobile = "mobile";
        
        /**
         * 信用卡cvv2
         */
        public static final String cvv2 = "cvv2";
        
        /**
         * 信用卡有效期
         */
        public static final String end_date = "end_date";
        
        /**
         * 验卡 0：用来验卡；1：用来实名认证（默认）
         */
        public static final String check_card = "check_card";
        
        /**
         * 备注
         */
        public static final String memo = "memo";
        
        
        /**
         * 会员号 钱包会员号
         */
        public static final String member_id = "member_id";
        
        
        /**
         * 邮箱号
         */
        public static final String email = "email";
        
        /**
         * 外部系统用户ID 平台用户id(字母或数字)
         */
        public static final String uid = "uid";
        
        

        /**
         *真实姓名
         */
        public static final String real_name = "real_name";
        
        /**
         * 身份证号
         */
        public static final String id_card_no = "id_card_no";
        
        /**
         * 会员名称 用户昵称(平台个人会员登录名)
         */
        public static final String member_name = "member_name";
        
        /**
         * 是否激活会员 取值：T/F。默认为T，激活会员
         */
        public static final String is_active = "is_active";
        
        /**
         * 职业
         */
        public static final String career = "career";
        
        /**
         * 认证状态00-认证成功，01-鉴权成功，98-鉴权失败，99-认证失败，1-处理失败
         */
        public static final String resultCode = "resultCode";
        
        /**
         * 响应描述
         */
        public static final String returnMessage = "returnMessage";
        
        

        /**
         * 成功标识 T （成功） F（失败）
         */
        public static final String is_success = "is_success";
        
        /**
         * 充值状态
         */
        public static final String biz_status = "biz_status";
        
        /**
         * 推进凭证号
         */
        public static final String advance_voucher_no = "advance_voucher_no";
        
        /**
         * 动态码
         */
        public static final String validation_code = "validation_code";
        
        
        /**
         * 银行卡ID
         */
        public static final String bank_id = "bank_id";
        
        

        /**
         * 是否成功开户
         */
        public static final String is_thirnd = "is_thirnd";
        
        /**
         * 是否成功实名认证
         */
        public static final String is_auth = "is_auth";
        
        /**
         * 返回错误码
         */
        public static final String error_code = "error_code";
        

        /**
         * 返回错误原因
         */
        public static final String error_message = "error_message";
        
        
        /**
         * 网站唯一订单号
         */
        public static final String orig_trade_no = "orig_trade_no";
        
        /**
         * 交易列表
         */
        public static final String trade_list = "trade_list";
        
        
        /**
         * 买家ID
         */
        public static final String buyer_id  = "buyer_id";
        
        /**
         * 买家ID类型
         */
        public static final String buyer_id_type  = "buyer_id_type";
        
        /**
         *买家账户类型
         */
        public static final String buyer_account_type = "buyer_account_type";
        
        /**
         * 买家手机号
         */
        public static final String buyer_mobile = "buyer_mobile";
        
        /**
         * 用户在商户平台下单时候的ip地址
         */
        public static final String buyer_ip = "buyer_ip";
        
        /**
         * 充值金额
         */
        public static final String amount = "amount";
        
        /**
         * 异步回调地址
         */
        public static final String notify_url = "notify_url";
        
        /**
         * 身份证号
         */
        public static final String cert_no = "cert_no";
        
        
        /**
         * 支付方式 
         */
        public static final String pay_method = "pay_method";
        
        /**
         * 是否转收银台标识 
         */
        public static final String go_cashie = "go_cashie";
        
        /**
         * 是否是WEB访问
         */
        public static final String is_web_access = "is_web_access";
        
        /**
         * 是否匿名支付（跳转收银台的场景使用）
         */
        public static final String is_anonymous = "is_anonymous";
        
        /**
         * 收银台地址
         */
        public static final String cashier_url = "cashier_url";
        
        /**
         * 机构订单号
         */
        public static final String inst_order_no = "inst_order_no";
        
        /**
         *  同步回调地址
         */
        public static final String return_url = "return_url";
        
        /**
         *  是否转收银台标识 
         */
        public static final String go_cashier = "go_cashier";
        
    }
    /**
     * 企业用户
     * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 
     *
     * @author haolingfeng
     * @version 1.0
     *
     */
    public class Enterprisemember{
        public static final String login_name ="login_name";
        public static final String uid ="uid";
        public static final String enterprise_name ="enterprise_name";
        public static final String member_name="member_name";
        public static final String legal_person ="legal_person";
        public static final String legal_person_phone ="legal_person_phone";
        public static final String website ="website";
        public static final String address ="address";
        public static final String license_no ="license_no";
        public static final String license_address ="license_address";
        public static final String license_expire_date ="license_expire_date";
        public static final String business_scope ="business_scope";
        public static final String telephone ="telephone";

        public static final String organization_no ="organization_no";
        public static final String summary ="summary";
        public static final String is_active ="is_active";

    }
    
    public class PersonalMember{
        public static final String mobile ="mobile";
        public static final String uid ="uid";
        public static final String email ="email";
        public static final String id_card_no="id_card_no";
        public static final String real_name ="real_name";
        public static final String member_name ="member_name";
        public static final String is_active ="is_active";
        public static final String career ="career";
    }
    //绑定用户
    public static final String BIND_ACCOUNT_RETURN_URL = "account/receiveBindAccountWeb.html";
    public static final String BIND_ACCOUNT_WAP_RETURN_URL = "account/receiveBindAccountWebWap.html";
    public static final String BIND_ACCOUNT_NOTIFY_URL = "account/receiveBindAccountWebS2S.html";
    
    //充值
    public static final String RECHARGE_NOTIFY_URL = "recharge/receiveRechargeS2S.html";
    public static final String RECHARGE_RETURN_URL = "recharge/receiveRechargeWeb.html";
    
    //后台审核提现 异步通知
    public static final String WITHDRAW_RETURN_ADMIN_URL = "/sxyPay/withDraw/withDrawNotify.html";
    
    
    //首信易充值
    public static final String SXY_RECHARGE_NOTIFY_URL = "recharge/receiveRechargeS2S.html";
    public static final String SXY_RECHARGE_RETURN_URL = "recharge/receiveRechargeWeb.html";
    
    //满标投资
    public static final String INVEST_RETURN_URL = "invest/receiveInvestWeb.html";
    public static final String INVEST_WAP_RETURN_URL = "invest/receiveInvestWebWap.html";
    public static final String INVEST_NOTIFY_URL = "invest/receiveInvestWebS2S.html";
    
    //领头
    public static final String ORDER_INVEST_RETURN_URL = "orderInvest/receiveInvestWeb.html";
    public static final String ORDER_INVEST_WAP_RETURN_URL = "orderInvest/receiveInvestWebWap.html";
    public static final String ORDER_INVEST_NOTIFY_URL = "orderinvest/receiveInvestWebS2S.html";
    
    //满标支付首款尾款
    public static final String LOANPAY_RETURN_URL = "loanPay/receiveInvestWeb.html";
    public static final String LOANPAY_NOTIFY_URL = "loanPay/receiveInvestWebS2S.html";
    
    
    //积分商城支付运费
    public static final String INTEGRAL_RETURN_URL = "integral/receiveInvestWeb.html";
    public static final String INTEGRAL_NOTIFY_URL = "integral/receiveInvestWebS2S.html";
    
    //流转标投资
    public static final String TRANSFER_INVEST_RETURN_URL = "transferInvest/receiveTransferInvestWeb.html";
    public static final String TRANSFER_INVEST_NOTIFY_URL = "transferInvest/receiveTransferInvestWebS2S.html";
    
    //满标撤标
    public static final String CANCEL_NOTIFY_URL = "cancel/receiveCancelWebS2S.html";
    
    //提现
    public static final String WITHDRAW_RETURN_URL = "withdraw/receiveWithDrawWeb.html";
    public static final String WITHDRAW_RETURN_WAP_URL = "withdraw/receiveWithDrawWebWap.html";
    public static final String WITHDRAW_NOTIFY_URL = "withdraw/receiveWithDrawWebS2S.html";
    
    
    public static final String MOBILE_FLAG="phoneSDK";
}
