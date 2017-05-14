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

package com.fbd.core.app.log.service;

import java.util.Date;
import java.util.Map;
import com.fbd.core.app.log.model.BaseLogModel;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author dongzhongwei
 * @version 1.0
 * 
 */

public interface ILogService {

    SearchResult<? extends BaseLogModel> getLogPage(String logType,
            BaseLogModel logModel);
    
    /**
     * Description: 操作日志列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-24 下午10:04:22
     */
    SearchResult<Map<String, Object>> getOperateLogPage(Map<String, Object> params);
    
    /**
     * 
     * Description:保存调度执行日志 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-28 上午11:36:04
     */
    public void saveJobOperationLog(String busiId,String operateType,String summary,String content,
            Date startTime,String result);
    
    /**
     * 
     * Description:分页查询调度日志 
     *
     * @param 
     * @return SearchResult<JobOperationLogModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-28 下午3:26:27
     */
    public SearchResult<Map<String,Object>> getJobLogPage(Map<String,Object> model);



}
