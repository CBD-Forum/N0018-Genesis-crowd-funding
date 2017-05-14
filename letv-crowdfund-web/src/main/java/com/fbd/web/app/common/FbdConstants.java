/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: FbdConstants.java 
 *
 * Created: [2014-12-9 下午5:01:38] by haolingfeng
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
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.common;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 0BUG常量类
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public class FbdConstants {

    /*----------------------------返回的消息key--------------------------------*/
    public static final String JSON_MSG_KEY = "msg";

    /*----------------------------session key--------------------------------*/
    /** session中保存的验证码属性名 */
    public static final String CAPTCHA_SESSION = "captchaSession";
    /** 是否显示验证码属性名 */
    public static final String JSON_IS_SHOW_VERIFYCODE = "isShowCode";
    /** 是否已开户属性名 */
    public static final String IS_OPEN_ACCT = "isOpenAcct";

    public static final String RESULT_TRUE = "success";

    public static final String RESULT_FALSE = "fail";

    /*----------------------------用户登录--------------------------------*/
    /** 连续登录失败的次数属性 */
    public static final String LOGIN_FAIL_COUNT_NAME = "loginFailCount";
    /** 登录失败时是否显示验证码属性 */
    public static final String IS_SHOW_VERIFYCODE = "isShowVerifyCode";
    /** 密码错误的次数属性 */
    public static final String PASSWORD_ERROR_COUNT_NAME = "pwdErrorCount";

    /*----------------------------用户注册--------------------------------*/
    /** 密码加密方式 ：MD5 */
    public static final String ENCODE_TYPE_MD5 = "MD5";
    /** 密码加密方式 ：SHA */
    public static final String ENCODE_TYPE_SHA = "SHA";
    

    /*----------------------------消息节点类型--------------------------------*/
    /** 消息节点类型: 邮箱找密码 */
    public static final String NODE_TYPE_EMAIL_PWD = "email_pwd";
    /** 消息节点类型: 手机注册*/
    public static final String NODE_TYPE_MOBILE_REGISTER = "register_by_mobile";
    
    /** 消息节点类型: 注册成功验证码*/
    public static final String NODE_TYPE_MOBILE_REGISTER_SUCCESS = "register_success_mobile";
    
    /** 消息节点类型: 手机wap端支付短信验证码*/
    public static final String NODE_TYPE_MOBILE_PAY = "pay_by_mobile";
    
    /** 消息节点类型: 手机短信登录*/
    public static final String NODE_TYPE_MOBILE_LOGIN = "login_by_mobile";
    /** 消息节点类型: 手机找密码 */
    public static final String NODE_TYPE_MOBILE_PWD = "mobile_pwd";
    /** 消息节点类型: 绑定手机 */
    public static final String NODE_TYPE_MOBILE_BIND = "mobile_bind";
    /** 消息节点类型: 取现验证码 */
    public static final String NODE_TYPE_WITHDRAW_REMIND = "withdraw_remind";
    /** 消息节点类型: 手机验证*/
    public static final String NODE_TYPE_MOBILE_VERIFY = "mobile_verify";
    
    /** 消息节点类型: 手机验证*/
    public static final String NODE_TYPE_BIND_BANK_CARD= "bind_bank_card";    
    
    
    /** 消息节点类型: 手机验证*/
    public static final String NODE_TYPE_MOBILE_VERIFY_OLD = "mobile_verify_old";
    
    /** 消息节点类型: 手机验证*/
    public static final String NODE_TYPE_MOBILE_VERIFY_NEW = "mobile_verify_new";
    
    /** 消息节点类型: 激活邮箱*/
    public static final String NODE_TYPE_EMAIL_AUTH = "email_auth";
    /** 消息节点类型: 发送邀请码*/
    public static final String NODE_TYPE_INVITECODE_MOBILE = "invitecode_mobile";
    
    /** 消息节点类型: 转让商品*/
    public static final String NODE_TYPE_TRANSFER_MOBILE = "transfer_by_mobile";

    
    
    /** 消息节点类型: 转让商品*/
    public static final String NODE_TYPE_REFUND_MODEL = "refund_by_mobile";
    
    /** 消息节点类型: 激活邮箱  主题*/
    public static final String EMAIL_SUBJECT_EMAIL_AUTH = "激活邮箱邮件";
    
    /** 验证码状态：有效 */
    public static final String VERIFY_CODE_STATUS_VALID = "valid";
    /** 验证码状态：无效 */
    public static final String VERIFY_CODE_STATUS_INVALID = "invalid";

    /*----------------------------系统参数配置--------------------------------*/
    /** 是否保存用户日志开关code名 */
    public static final String USERLOG_SWITCH_CODE = "userlogSwitch";
    /** 是否保存商户日志开关code名 */
    public static final String MERCHANTLOG_SWITCH_CODE = "merlogSwitch";
    /** 日志是否关闭: 是 */
    public static final String USERLOG_SWITCH_CODE_TRUE = "1";
    /** 日志是否关闭: 否 */
    public static final String USERLOG_SWITCH_CODE_FALSE = "0";
    /** 登录失败次数code名 */
    public static final String USER_LOGIN_FAIL_COUNT_CODE = "loginFailCount";
    /** 密码错误次数code名 */
    public static final String USER_PWD_ERROR_COUNT_CODE = "pwdErrorCount";
    /** 验证码失效时长(分)code名 */
    public static final String VERIFYCODE_DEAD_LIMIT = "verifycodeDeadLimit";
    
    /*----------------------------业务参数配置--------------------------------*/
    /** 是否保存用户日志开关code名 */
    public static final String NORMAL_REPAYBUTTON_DISPLAY = "displayNormalRepayButton";

    /*----------------------------用户登录日志--------------------------------*/
    /** 登录成功 */
    public static final String USER_LOG_RESULT_SUCCESS = "success";
    /** 登录失败 */
    public static final String USER_LOG_RESULT_FAIL = "fail";


    /*---------------------------信息模板--------------------------------*/
    /** 模板属性:验证码 */
    public static final String MESSAGE_TEMPLATE_PROP_VERIFYCODE = "verifyCode";
    public static final String MESSAGE_TEMPLATE_PROP_REALNAME = "realName";
    public static final String MESSAGE_TEMPLATE_PROP_INVITECODE = "inviteCode";
    
    /*---------------------------投资相关--------------------------------*/
    /** 是否为自动投标:否 */
    public static final String ISAUTOINVEST_FALSE = "0";
    /** 投资状态:等待支付 */
    public static final String INVEST_STATE_PAYING = "paying";

    /*---------------------------项目相关--------------------------------------*/
    /** 项目状态:投标中 */
    public static final String LOAN_STATE_FUNDING = "funding";
    
    /*---------------------------导出配置文件路径-------------------------------*/
    /** 交易明细报表配置文件 */
    public static final String USERBILL_EXPORT_CONFIG = "export_userbill_excel.xml";
    /** 交易明细报表文件名 */
    public static final String USERBILL_EXPORT_FILE_NAME = "收支明细";
    
    /*---------------------------合同模板类型-------------------------------*/
    /** 合同管理_合同模板类型：投资协议范文 */
    public static final String CONTRACT_TEMPLATE_INVEST_MODEL = "invest_model";
    /** 合同管理_合同模板状态：发表*/
    public static final String CONTRACT_TEMPLATE_STATUS_RELEASE = "1";
    
    /*---------------------------上传头像文件夹-------------------------------*/
    public static final String USER_PHOTO_FILE = "userPhoto/";
    
}
