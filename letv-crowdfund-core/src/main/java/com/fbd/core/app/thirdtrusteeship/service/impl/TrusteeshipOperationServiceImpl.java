/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: TrusteeshipOperationServiceImpl.java 
 *
 * Created: [2014-12-18 上午10:31:32] by haolingfeng
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

package com.fbd.core.app.thirdtrusteeship.service.impl;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.thirdtrusteeship.common.TrusteeshipConstants;
import com.fbd.core.app.thirdtrusteeship.dao.ITrusteeshipOperationDao;
import com.fbd.core.app.thirdtrusteeship.model.TrusteeshipOperationModel;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.exception.ApplicationException;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service("trusteeshipOperationService")
public class TrusteeshipOperationServiceImpl implements
        ITrusteeshipOperationService {
    @Resource
    private ITrusteeshipOperationDao trusteeshipOperationDao;

    /**
     * 
     * Description: 返回时更新操作数据
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-17 下午6:31:05
     */
    public void updateOperationModel(String operationId, String status,
            String resInfo) {
        TrusteeshipOperationModel model = trusteeshipOperationDao.selectByPrimaryKey(operationId);
        if (model == null) {
            throw new ApplicationException("操作编号为" + operationId + "的接口调用信息不存在");
        }
        if (model.getStatus().equals(TrusteeshipConstants.Status.PASSED)) {// 接口日志状态已经为"passed"
            return;
        }
        model.setStatus(status);
        model.setResponseTime(new Date());
        model.setResInfo(resInfo);
        this.trusteeshipOperationDao.update(model);
    }
    
    /**
     * 
     * Description: 返回时更新操作数据
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-17 下午6:31:05
     */
    public void updateOperationModel(String markId, String type,String operator,
            String thirdId,String status,String resInfo) {
        TrusteeshipOperationModel model = trusteeshipOperationDao.find(markId, 
                type, operator, thirdId);
        if (model == null) {
            throw new ApplicationException("markId["+markId+"],type["+type+
                    "],operator["+operator+"],thirdId["+thirdId+"]的接口调用数据不存在");
        }
        if (model.getStatus().equals(TrusteeshipConstants.Status.PASSED)) {// 接口日志状态已经为"passed"
            return;
        }
        model.setStatus(status);
        model.setResponseTime(new Date());
        model.setResInfo(resInfo);
        this.trusteeshipOperationDao.update(model);
    }

    /**
     * 
     * Description: 插入操作数据
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-17 下午5:46:52
     */
    @SuppressWarnings("deprecation")
    public void saveOperationModel(String operationId, String userId,
            String markId, String type, String params) {
        TrusteeshipOperationModel operateModel = new TrusteeshipOperationModel();
        operateModel.setId(operationId);
        operateModel.setMarkId(markId);
        operateModel.setOperator(userId);
        operateModel.setRequestUrl("");
        operateModel.setRequestData(params);
        operateModel.setStatus(TrusteeshipConstants.Status.UN_SEND);
        operateModel.setType(type);
        operateModel.setTrusteeship("");
        operateModel.setRequestTime(new Date());
        operateModel.setStatus(TrusteeshipConstants.Status.SENDED);
        this.trusteeshipOperationDao.save(operateModel);
    }
    
    /**
     * 
     * Description: 插入操作数据(资金池)
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-17 下午5:46:52
     */
    @SuppressWarnings("deprecation")
    public void saveOperationModel(String operationId, String userId,
    		String markId, String type, String params, String requestUrl) {
    	TrusteeshipOperationModel operateModel = new TrusteeshipOperationModel();
    	operateModel.setId(operationId);
    	operateModel.setMarkId(markId);
    	operateModel.setOperator(userId);
    	operateModel.setRequestUrl(requestUrl);
    	operateModel.setRequestData(params);
    	operateModel.setStatus(TrusteeshipConstants.Status.UN_SEND);
    	operateModel.setType(type);
    	operateModel.setTrusteeship("");
    	operateModel.setRequestTime(new Date());
    	operateModel.setStatus(TrusteeshipConstants.Status.SENDED);
    	this.trusteeshipOperationDao.save(operateModel);
    }
    
    /**
     * 
     * Description: 插入操作数据(汇付宝)
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-17 下午5:46:52
     */
    @SuppressWarnings("deprecation")
    public void saveOperationModel(String operationId, String userId,
            String markId, String type,String requestUrl, String thirdId,String params) {
        TrusteeshipOperationModel operateModel = new TrusteeshipOperationModel();
        operateModel.setId(operationId);
        operateModel.setMarkId(markId);
        operateModel.setOperator(userId);
        operateModel.setRequestUrl(requestUrl);
        operateModel.setRequestData(params);
        operateModel.setType(type);
        operateModel.setTrusteeship(thirdId);
        operateModel.setRequestTime(new Date());
        operateModel.setStatus(TrusteeshipConstants.Status.SENDED);
        this.trusteeshipOperationDao.save(operateModel);
    }
}
