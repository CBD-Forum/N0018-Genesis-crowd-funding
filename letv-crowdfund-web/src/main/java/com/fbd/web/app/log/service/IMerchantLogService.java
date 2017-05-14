/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IMerchantLogService.java 
 *
 * Created: [2014-12-12 下午5:13:36] by haolingfeng
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

package com.fbd.web.app.log.service;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:商户日志
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IMerchantLogService {
    /**
     * 
     * Description: 保存正常日志
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-10 下午6:54:31
     */
    public void addSuccessLog(String merchantId, String ipAddress);

    /**
     * 
     * Description: 保存失败日志
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-10 下午7:04:15
     */
    public void addFailLog(String merchantId, String ipAddress, String resInfo);
}
