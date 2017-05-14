/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IUserLogService.java 
 *
 * Created: [2014-12-10 下午6:38:27] by haolingfeng
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

import java.util.Date;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IUserLogService {
    /**
     * 
     * Description: 保存正常日志
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-10 下午6:54:31
     */
    public void addSuccessLog(String userId, String ipAddress);

    /**
     * 
     * Description: 保存失败日志
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-10 下午7:04:15
     */
    public void addFailLog(String userId, String ipAddress, String resInfo);
    
    /**
     * 
     * Description: 获得上次登录时间
     *
     * @param 
     * @return Date
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-26 下午7:06:25
     */
    public Date getLastLoginTime(String userId);

}
