/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: Md5.java 
 *
 * Created: [2016-8-22 下午2:20:22] by haolingfeng
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

package com.fbd.core.app.sxyPay.utils;

import java.io.IOException;

import com.capinfo.crypt.Md5;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public class SxyPayMd5 {

    public static String createMd5(String value){
        Md5 md5 = new Md5 ("") ;
        String digestString = "";
        try {
            md5.hmac_Md5(value,SxyPayConstants.Config.SXY_MD5KEY) ;
            byte b[]= md5.getDigest();
            digestString = md5.stringify(b) ;
        } catch (IOException e) {
            
        }
        return digestString;
    }
}
