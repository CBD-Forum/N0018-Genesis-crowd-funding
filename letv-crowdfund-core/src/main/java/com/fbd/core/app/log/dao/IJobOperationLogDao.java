package com.fbd.core.app.log.dao;

import java.util.Map;
import com.fbd.core.app.log.model.JobOperationLogModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 调度任务执行日志
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface IJobOperationLogDao extends BaseDao<JobOperationLogModel>{
    public SearchResult<Map<String,Object>> getLogPage(Map<String,Object> params);
}