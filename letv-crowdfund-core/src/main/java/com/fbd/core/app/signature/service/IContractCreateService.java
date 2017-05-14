/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IContractCreateService.java 
 *
 * Created: [2016-8-24 下午7:35:37] by haolingfeng
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 合同创建service
 *
 * @version 1.0
 *
 */

public interface IContractCreateService {
    
    
    /**
     * Description: 根据模板创建合同
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-24 下午7:39:47
     */
    public int createCrontractPdf(String loanNo,String investNo,String tempInFilePath,String tempOutFilePath,HttpServletRequest request,HttpServletResponse response);

}
