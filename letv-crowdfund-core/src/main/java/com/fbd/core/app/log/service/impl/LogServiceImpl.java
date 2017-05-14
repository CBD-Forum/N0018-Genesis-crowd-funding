package com.fbd.core.app.log.service.impl;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import com.fbd.core.app.log.dao.IAdminLogDao;
import com.fbd.core.app.log.dao.IGuaranteeLogDao;
import com.fbd.core.app.log.dao.IJobOperationLogDao;
import com.fbd.core.app.log.dao.IMerchantLogDao;
import com.fbd.core.app.log.dao.IOperateLogDao;
import com.fbd.core.app.log.dao.IUserLogDao;
import com.fbd.core.app.log.model.AdminLogModel;
import com.fbd.core.app.log.model.BaseLogModel;
import com.fbd.core.app.log.model.GuaranteeLogModel;
import com.fbd.core.app.log.model.JobOperationLogModel;
import com.fbd.core.app.log.model.MerchantLogModel;
import com.fbd.core.app.log.model.UserLogModel;
import com.fbd.core.app.log.service.ILogService;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author dongzhongwei
 * @version 1.0
 * 
 */
@Service("logService")
public class LogServiceImpl implements ILogService {

    // 管理员审计日志
    @Resource
    private IAdminLogDao adminLogDao;
    // 担保公司审计日志
    @Resource
    private IGuaranteeLogDao guaranteeLogDao;
    // 入驻商家审计日志
    @Resource
    private IMerchantLogDao merchantLogDao;
    // 普通用户审计日志
    @Resource
    private IUserLogDao userLogDao;
    //审计日志
    @Resource
    private IOperateLogDao operateLogDao;
    
    //调度任务执行日志
    @Resource
    private IJobOperationLogDao jobOperationLogDao;
    
    
    public SearchResult<? extends BaseLogModel> getLogPage(String logType,
            BaseLogModel logModel) {
        if ("admin".equals(logType)) {
            AdminLogModel adminLogModel=new AdminLogModel();
            try {
                BeanUtils.copyProperties(adminLogModel, logModel);
            } catch (Exception e) {
                e.printStackTrace();
            } 
            SearchResult<AdminLogModel> result = adminLogDao.getLogPage(adminLogModel);
            return result;
        }else if ("guarantee".equals(logType)) {
            GuaranteeLogModel guaranteeLogModel=new GuaranteeLogModel();
            try {
                BeanUtils.copyProperties(guaranteeLogModel, logModel);
            } catch (Exception e) {
                e.printStackTrace();
            } 
            SearchResult<GuaranteeLogModel> result = guaranteeLogDao.getLogPage(guaranteeLogModel);
            return result;
        }else if ("merchant".equals(logType)) {
            MerchantLogModel merchantLogModel=new MerchantLogModel();
            try {
                BeanUtils.copyProperties(merchantLogModel, logModel);
            } catch (Exception e) {
                e.printStackTrace();
            } 
            SearchResult<MerchantLogModel> result = merchantLogDao.getLogPage(merchantLogModel);
            return result;
        }else if ("user".equals(logType)) {
            UserLogModel userLogModel=new UserLogModel();
            try {
                BeanUtils.copyProperties(userLogModel, logModel);
            } catch (Exception e) {
                e.printStackTrace();
            } 
            SearchResult<UserLogModel> result = userLogDao.getLogPage(userLogModel);
            return result;
        }
        return null;
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
    public SearchResult<Map<String, Object>> getOperateLogPage(Map<String, Object> params) {
        return this.operateLogDao.getPage("getCount", "select", params);
    }
    
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
            Date startTime,String result){
        JobOperationLogModel model = new JobOperationLogModel();
        model.setId(PKGenarator.getId());
        model.setOperateType(operateType);
        model.setOperateBeginTime(startTime);
        model.setOperateEndTime(new Date());
        model.setSummary(summary);
        model.setContent(content);
        model.setResult(result);
        model.setBusiId(busiId);
        this.jobOperationLogDao.save(model);
    }
    
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
    public SearchResult<Map<String,Object>> getJobLogPage(Map<String,Object> params){
        if(params.get("operateBeginTime")!=null){
            params.put("operateBeginTime",DateUtil.getDayMax((Date)params.get("operateBeginTime")));
        }
        if(params.get("operateEndTime")!=null){
            params.put("operateEndTime",DateUtil.getDayMax((Date)params.get("operateEndTime")));
        }
        return this.jobOperationLogDao.getLogPage(params);
    }
}
