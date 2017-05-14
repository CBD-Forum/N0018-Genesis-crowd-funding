/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: JobOperationLogDaoImpl.java 
 *
 * Created: [2015-2-28 上午11:24:34] by haolingfeng
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

package com.fbd.core.app.log.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.log.dao.IJobOperationLogDao;
import com.fbd.core.app.log.model.JobOperationLogModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 调度任务执行日志
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("jobOperationLogDao")
public class JobOperationLogDaoImpl extends BaseDaoImpl<JobOperationLogModel> 
   implements  IJobOperationLogDao{
    
    public SearchResult<Map<String,Object>> getLogPage(Map<String,Object> params) {
        SearchResult<Map<String,Object>> searchResult = new SearchResult<Map<String,Object>>();
        searchResult.setTotal(this.getCount("getCount", params));
        searchResult.setRows(getList(params));
        return searchResult;
    }

    public List<Map<String,Object>> getList(Map<String,Object> params) {
        return this.selectMapByFields("selectList", params);
    }
}
