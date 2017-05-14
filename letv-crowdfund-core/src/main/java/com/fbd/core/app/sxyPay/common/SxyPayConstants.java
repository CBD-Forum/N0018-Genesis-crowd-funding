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

package com.fbd.core.app.sxyPay.common;

import com.fbd.core.util.SpringPropertiesHolder;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 首信易支付
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public class SxyPayConstants {
    
    public static final String INPUTCHARSET = "UTF-8";
    public static final String THIRD_ID="SYX";
    public static final String VERSION="1.0";
    public static final String VERTICAL_LINE="|";
    public static class Config{
        
        /**首信易商户编号**/
        public static final String SXY_SHBH = SpringPropertiesHolder
                .getProperty("SXY_SHBH");
        
        /**首信易MD5加密key**/
        public static final String SXY_MD5KEY = SpringPropertiesHolder
                .getProperty("SXY_MD5KEY");
        
        /**首信易AES**/
        public static final String SXY_AES = SpringPropertiesHolder
                .getProperty("SXY_AES");
        /**
         * 首信易支付地址
         */
        public static final String SXY_PAY_REQ_URL = SpringPropertiesHolder
                .getProperty("SXY_PAY_REQ_URL");
        
        
        /**
         * 首信易支付地址（快捷）
         */
        public static final String SXY_QUICK_PAY_REQ_URL = SpringPropertiesHolder
                .getProperty("SXY_QUICK_PAY_REQ_URL");
        
        /**
         * 首信易实名认证
         */
        public static final String SXY_ACCOUNT = SpringPropertiesHolder
                .getProperty("SXY_ACCOUNT");
        
        /**
         * 首信易提现代付
         */
        public static final String SXY_WITHDRAW = SpringPropertiesHolder
                .getProperty("SXY_WITHDRAW");
        
        /**
         * 首信易提现代付查询地址
         */
        public static final String SXY_WITHDRAW_QUERY = SpringPropertiesHolder
                .getProperty("SXY_WITHDRAW_QUERY");
        
        /**
         * 用户平台的回调地址前缀
         */
        public static final String SXY_PRE_RESPONSE_RAIN_WEB_URL = SpringPropertiesHolder
                .getProperty("SXY_PRE_RESPONSE_RAIN_WEB_URL");
        
        /**
         * 账户余额查询
         */
        public static final String SXY_BALANCE_QUERY = SpringPropertiesHolder
                .getProperty("SXY_BALANCE_QUERY");
        /**
         * 对账文件获取地址
         */
        public static final String SXY_FILE_DOWNLOAD_URL = SpringPropertiesHolder
                .getProperty("SXY_FILE_DOWNLOAD_URL");   
        /**
         * 对账文件获取随机数
         */
        public static final String SXY_FILE_DOWNLOAD_RANDOMNUM = SpringPropertiesHolder
                .getProperty("SXY_FILE_DOWNLOAD_RANDOMNUM");        
        
        
    }
    
    public class BusiType{
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
         * 放款
         */
        public static final String lend = "lend";
        
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
    }
    
    
    public  class SyxDataParam {
        /**
         * 分帐信息总行数
         */
        public static final String FZXXZHS = "FZXXZHS";
        
        /**
         * 分帐总金额
         */
        public static final String FZZJE = "FZZJE";
        
        /**
         * 批次号
         */
        public static final String PCH = "PCH";
        
        /**
         * 收方帐号
         */
        public static final String SFZH = "SFZH";
        
        /**
         * 收方帐户名
         */
        public static final String SFZHM = "SFZHM";
        
        /**
         * 收方开户行
         */
        public static final String SFKHH = "SFKHH";
        
        /**
         * 收方省份
         */
        public static final String SFSF = "SFSF";
        
        /**
         * 收方城市
         */
        public static final String SFCS = "SFCS";
        
        /**
         * 付款金额
         */
        public static final String FKJE = "FKJE";
        
        /**
         * 客户标识
         */
        public static final String KHBS = "KHBS";
        
        /**
         * 联行号
         */
        public static final String LHH = "LHH";
    }
    
    
    public  class SyxParam {
        /**
         * 商户编号
         */
        public static final String v_mid = "v_mid";
        
        /**
         * 批量代付数据
         */
        public static final String v_data = "v_data";
        
        /**
         * 商户编号
         */
        public static final String v_branch = "v_branch";
        
        /**
         * 订单编号
         */
        public static final String v_oid = "v_oid";
        
        /**
         * 支付状态
         */
        public static final String v_pstatus = "v_pstatus";
        
        /**
         * 支付状态
         */
        public static final String v_count = "v_count";
        
        /**
         * 支付状态
         */
        public static final String v_pstring = "v_pstring";
        
        /**
         * 支付方式
         */
        public static final String v_pmode = "v_pmode";
        
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
         * 银行卡号
         */
        public static final String v_cardno = "v_cardno";
        
        /**
         * 证件类型
         */
        public static final String v_idtype = "v_idtype";
        
        /**
         * 证件号码
         */
        public static final String v_idnumber = "v_idnumber";
        
        /**
         * 客户姓名
         */
        public static final String v_idname = "v_idname";
        
        /**
         * 手机号码
         */
        public static final String v_phone = "v_phone";
        
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
         * 返回商户页面地址
         */
        public static final String v_userref = "v_userref";
        
        
        /**
         * 订单数字指纹
         */
        public static final String v_md5info = "v_md5info";
        
        /**
         *数字签名
         */
        public static final String v_mac = "v_mac";
        
        /**
         *数字签名
         */
        public static final String v_version = "v_version";        
        /**
         * 数字签名
         */
        public static final String v_md5money = "v_md5money";
        
        
    }
    
    
    
    
    
    //首信易充值
    public static final String SXY_RECHARGE_NOTIFY_URL = "recharge/receiveRechargeS2S.html";
    public static final String SXY_RECHARGE_RETURN_URL = "recharge/receiveRechargeWeb.html";
    
  //首信易充值
    public static final String SXY_QUICK_RECHARGE_NOTIFY_URL = "recharge/receiveQuickRechargeS2S.html";
    public static final String SXY_QUICK_RECHARGE_RETURN_URL = "recharge/receiveQuickRechargeWeb.html";
    
}
