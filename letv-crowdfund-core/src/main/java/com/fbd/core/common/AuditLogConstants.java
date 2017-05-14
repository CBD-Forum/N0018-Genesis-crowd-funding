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

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public class AuditLogConstants {
    
    /*--------------------------- 执行结果 ---------------------------*/
    /** 成功**/
    public static final String RESULT_SUCCESS = "success";
    /** 失败**/
    public static final String RESULT_FAIL = "fail";
    
    /*--------------------------- 操作类型 ---------------------------*/
    /** 添加 **/
    public static final String TYPE_ADD = "add";
    
    /** 修改 **/
    public static final String TYPE_MODIFY = "modify";
    
    /** 删除 **/
    public static final String TYPE_DELETE = "delete";
    
    /** 重置密码 **/
    public static final String TYPE_RESETPASSWORD = "resetPassword";
    
    /** 启用 **/
    public static final String TYPE_ENABLE  = "enable";
    
    /** 禁用  **/
    public static final String TYPE_DISABLE  = "disable";
    
    /** 授信调整  **/
    public static final String TYPE_MODIFYADJUST  = "modifyAdjust";
    
    /** 修改手机号  **/
    public static final String TYPE_MODIFYMOBILE  = "modifyMobile";
    
    /** 修改手机号  **/
    public static final String TYPE_MODIFYEMAIL  = "modifyEmail";
    
    /** 延期  **/
    public static final String TYPE_DEFER  = "defer";
    
    /** 垫付  **/
    public static final String TYPE_PAY  = "pay";
    
    /** 强制还款  **/
    public static final String TYPE_COERCEREPAY  = "coerceRepay";
    
    /** 放款  **/
    public static final String TYPE_SEND  = "send";
    
    /** 流标  **/
    public static final String TYPE_FLOW  = "flow";
    
    /** 充值  **/
    public static final String TYPE_RECHARGE  = "recharge";
    
    /** 同意  **/
    public static final String TYPE_APPROVE  = "approve";
    
    /** 拒绝  **/
    public static final String TYPE_REJECT  = "reject";
    
    /** 编辑权限  **/
    public static final String TYPE_ADDPERMISSION  = "addPermission";
    
    /** 发送邮件  **/
    public static final String TYPE_SENDEMAIL  = "sendEmail";
    
    /** 发送短信 **/
    public static final String TYPE_SENDSMS  = "sendSMS";
    
    /** 发布操作**/
    public static final String TYPE_RELEASE  = "release";
    
    /** 结束操作**/
    public static final String TYPE_END  = "end";
    
    /** 预热 **/
    public static final String TYPE_PREHEAT  = "preheat";
    
    /** 提交 **/
    public static final String TYPE_SUBMIT  = "submit";
    /*--------------------------- 操作模块 ---------------------------*/
    /** 用户管理 -->> 投资用户 **/
    public static final String MODEL_INVESTUSER = "investUser";
    /** 用户管理 -->> 借款用户 **/
    public static final String MODEL_LOANTUSER = "loanUser";
    /** 用户管理 -->> 入驻商家用户 **/
    public static final String MODEL_MERCHANTUSER = "merchantUser";
    /** 用户管理 -->> 担保公司 **/
    public static final String MODEL_GUARANTEEUSER = "guaranteeUser";
    /** 用户管理 -->> 管理员 **/
    public static final String MODEL_ADMINUSER= "adminUser";
    
    
    /** 项目管理 -->> 录入项目 **/
    public static final String MODEL_LOAN= "loan";
    
    /** 项目管理 -->> 合同管理 **/
    public static final String MODEL_CONTRACT= "contract";
    
    /** 项目管理 -->> 计息方式 **/
    public static final String MODEL_INVERFORMULA= "inverFormula";
    
    /** 项目管理 -->> 优惠券基本信息 **/
    public static final String MODEL_BASEREWARD= "baseReward";
    
    /** 项目管理 -->> 优惠券发放节点 **/
    public static final String MODEL_REWARDNODE= "rewardNode";
    
    /** 财务管理 -->> 平台手续费 **/
    public static final String MODEL_PLATFORM= "platform";
    
    /** 财务管理-->> 提现审核流程 **/
    public static final String MODEL_WITHDRAWAUDIT= "withdrawaudit";
    
    /** 文案管理-->> 文章类型 **/
    public static final String MODEL_NODETYPE= "nodetype";
    
    /** 文案管理-->> 文章 **/
    public static final String MODEL_NODE= "node";
    
    /** 消息管理-->> 消息节点 **/
    public static final String MODEL_MESSAGENODE= "messageNode";
    
    /** 消息管理-->> 消息模版 **/
    public static final String MODEL_MESSAGETPL= "messageTpl";
    
    /** 权限管理-->> 角色管理 **/
    public static final String MODEL_ROLE= "role";
    
    /** 推广管理 -->> TDK管理 **/
    public static final String MODEL_TDK= "tdk";
    
    /** 推广管理-->> 群发管理 **/
    public static final String MODEL_GROUPSENDMSG= "groupSendMsg";
    
    /** 系统管理-->> 字典管理 **/
    public static final String MODEL_DICTIONAR= "dictionar";
    
    /** 系统管理-->> 系统配置 **/
    public static final String MODEL_SYSCONFIG= "sysConfig";
    
    /** 系统管理-->> 业务配置 **/
    public static final String MODEL_BUSINESSCONFIG= "businessConfig";
    
    /** 系统管理-->> 项目配置 **/
    public static final String MODEL_LOANCONFIG= "loanConfig";
    
    /** 系统管理-->> Banner管理**/
    public static final String MODEL_BANNER= "banner";
    
    /** 配资管理-->> 配资规则**/
    public static final String CAPITAL_RULE= "capitalRule";
    
    
    /** 众筹项目管理 **/
    public static final String MODEL_CROWDFUND = "crowdfund";
    /** 特卖管理 **/
    public static final String MODEL_GOODS = "goods";
    

}
