package com.fbd.admin.app.log.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.log.model.BaseLogModel;
import com.fbd.core.app.log.service.ILogService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.quartz.QuartzJobConstants;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 记录日志
 *
 * @author dongzhongwei
 * @version 1.0
 *
 */

@Controller
@Scope("prototype")
@RequestMapping("/log")
public class LogAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -5939310348652341926L;
    
    //管理员审计日志
    @Resource
    private ILogService logService;
    
    
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<? extends BaseLogModel> getLogPage(String logType,BaseLogModel logModel) {
        
        SearchResult<? extends BaseLogModel> logPage =logService.getLogPage(logType,logModel);
        return logPage;
    }
    
    /**
     * Description: 操作日志列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-24 下午10:04:22
     */
    @RequestMapping(value = "/getOperateLoglist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getOperateLogPage() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(this.getRequestParam());
        params.putAll(this.getPageParam());
        SearchResult<Map<String,Object>> operateLogPage =logService.getOperateLogPage(params);
        return operateLogPage;
    }
    
    /**
     * 
     * Description: 调度日志查询
     *
     * @param 
     * @return SearchResult<JobOperationLogModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-28 下午3:28:31
     */
    @RequestMapping(value = "/getJobLoglist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getJobLogPage() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(this.getRequestParam());
        params.putAll(this.getPageParam());
        SearchResult<Map<String,Object>> jobLogPage =logService.getJobLogPage(params);
        return jobLogPage;
    }
    /**
     * 
     * Description: 自动还款日志查询
     *
     * @param 
     * @return SearchResult<JobOperationLogModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-28 下午3:28:31
     */
    @RequestMapping(value = "/getAutoRepayLoglist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object> > getAutoRepayLoglist() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(this.getRequestParam());
        params.putAll(this.getPageParam());
        params.put("operateType", QuartzJobConstants.JOB_LOAN_AUTO_REPAY);
        SearchResult<Map<String,Object> > jobLogPage =logService.getJobLogPage(params);
        return jobLogPage;
    }

}
