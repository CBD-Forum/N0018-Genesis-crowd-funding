/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ResultHander.java 
 *
 * Created: [2014-12-26 上午10:48:02] by haolingfeng
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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.sxyPay.utils.SAXParser;
import com.fbd.core.app.sxyPay.utils.SxyPayMd5;
import com.fbd.core.exception.ApplicationException;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:首信易接口返回结果处理
 * 
 * @author wuwenbin
 * @version 1.0
 * 
 */

public class ResultHandler {
    
    

    private static final Logger logger = LoggerFactory
            .getLogger(ResultHandler.class);

    public static Map<String, String> convert2Map(HttpServletRequest request) {
        String respCode = "";
        String respDesc = "";
        Map<String, String> paramMap = new HashMap<String, String>();
        try {
            request.setCharacterEncoding("UTF-8");
            
            StringBuffer sb1 = new StringBuffer();
            sb1.append(request.getParameter(SyxParam.v_oid));
            sb1.append(request.getParameter(SyxParam.v_pstatus));
            sb1.append(java.net.URLEncoder.encode(request.getParameter(SyxParam.v_pstring), "utf-8"));
            sb1.append(java.net.URLEncoder.encode(request.getParameter(SyxParam.v_pmode), "utf-8"));
            
            
            StringBuffer sb2 = new StringBuffer();
            sb2.append(request.getParameter(SyxParam.v_amount));
            sb2.append(request.getParameter(SyxParam.v_moneytype));
            
            String v_md5info = request.getParameter(SyxParam.v_md5info);
            String v_md5money = request.getParameter(SyxParam.v_md5money);
            // 签名验证
            if (v_md5info.equals(SxyPayMd5.createMd5(sb1.toString())) && 
                    v_md5money.equals(SxyPayMd5.createMd5(sb2.toString()))) {
                respCode = request.getParameter(SyxParam.v_pstatus);
                respDesc = request.getParameter(SyxParam.v_pstring);
            } else {// 签名验证失败
                throw new ApplicationException("签名验证失败！");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            respDesc = "请求参数字符编码转换出错";
        } catch (Exception e) {
            e.printStackTrace();
            respDesc = e.getMessage();
        }
        paramMap.put("respCode", respCode);
        paramMap.put("respDesc", respDesc);
        return paramMap;
    }

    
    public static Map<String, String> convert2MapS2S(HttpServletRequest request) {
        String respCode = "";
        String respDesc = "";
        Map<String, String> paramMap = new HashMap<String, String>();
        try {
            request.setCharacterEncoding("UTF-8");
            
            StringBuffer sb1 = new StringBuffer();
            sb1.append(request.getParameter(SyxParam.v_oid));
            sb1.append(java.net.URLEncoder.encode(request.getParameter(SyxParam.v_pmode), "utf-8"));
            sb1.append(request.getParameter(SyxParam.v_pstatus));
            sb1.append(java.net.URLEncoder.encode(request.getParameter(SyxParam.v_pstring), "utf-8"));
            sb1.append(request.getParameter(SyxParam.v_count));
            
            
            
            
            StringBuffer sb2 = new StringBuffer();
            sb2.append(request.getParameter(SyxParam.v_amount));
            sb2.append(request.getParameter(SyxParam.v_moneytype));
            
            String v_mac = request.getParameter(SyxParam.v_mac);
            String v_md5money = request.getParameter(SyxParam.v_md5money);
            
//            String aa = SxyPayMd5.createMd5(sb1.toString());
//            System.out.print(aa);
            // 签名验证
            if (v_mac.equals(SxyPayMd5.createMd5(sb1.toString())) && 
                    v_md5money.equals(SxyPayMd5.createMd5(sb2.toString()))) {
                respCode = request.getParameter(SyxParam.v_pstatus);
                respDesc = request.getParameter(SyxParam.v_pstring);
            } else {// 签名验证失败
                throw new ApplicationException("签名验证失败！");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            respDesc = "请求参数字符编码转换出错";
        } catch (Exception e) {
            e.printStackTrace();
            respDesc = e.getMessage();
        }
        paramMap.put("respCode", respCode);
        paramMap.put("respDesc", respDesc);
        return paramMap;
    }


    
    public static Map<String, String> convert2Str(String resultStr) {
        String respCode = "";
        String respDesc = "";
        Map<String, String> paramMap = new HashMap<String, String>();
        try {
            StringBuffer sb1 = new StringBuffer();
            sb1.append(SAXParser.SAXParseNodeValue(resultStr, "mid"));
            sb1.append(SAXParser.SAXParseNodeValue(resultStr, "cardno"));
            sb1.append(SAXParser.SAXParseNodeValue(resultStr, "idtype"));
            sb1.append(SAXParser.SAXParseNodeValue(resultStr, "idnumber"));
            sb1.append(SAXParser.SAXParseNodeValue(resultStr, "idname"));
            sb1.append(SAXParser.SAXParseNodeValue(resultStr, "phone"));
            sb1.append(SAXParser.SAXParseNodeValue(resultStr, "verifystatus"));
            String v_md5info = SAXParser.SAXParseNodeValue(resultStr, "sign");
            // 签名验证
            if (v_md5info.equals(SxyPayMd5.createMd5(sb1.toString()))){
                respCode = SAXParser.SAXParseNodeValue(resultStr, "verifystatus");
                respDesc = SAXParser.SAXParseNodeValue(resultStr, "statusdesc");
                if(respDesc!=null){
                    respDesc = java.net.URLDecoder.decode(respDesc, "UTF-8");
                    logger.info("====================解码后的字符串-respDesc:"+respDesc);
                }
            } else {// 签名验证失败
                throw new ApplicationException("签名验证失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            respDesc = e.getMessage();
        }
        paramMap.put("respCode", respCode);
        paramMap.put("respDesc", respDesc);
        return paramMap;
    }
    
}
