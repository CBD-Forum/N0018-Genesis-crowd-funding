/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICertSignService.java 
 *
 * Created: [2016-8-25 下午1:23:28] by haolingfeng
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

package com.fbd.core.app.signature.service;
import cn.com.itrus.raapi.UserInfo;


/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * Description: 
 * @version 1.0
 *
 */

public interface ICertSignService {
    
    
    /**
     * AA模式申请证书
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-25 下午1:37:16
     */
    public String applyCertAA(UserInfo userInfo,String certReqBuf,String accountHash,String userPassword);
    
    
    
    

}
