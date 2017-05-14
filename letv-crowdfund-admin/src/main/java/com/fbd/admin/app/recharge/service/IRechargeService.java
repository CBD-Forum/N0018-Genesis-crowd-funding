/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IRechargeService.java 
 *
 * Created: [2014-12-22 下午2:32:01] by haolingfeng
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

package com.fbd.admin.app.recharge.service;

import java.util.Map;

import com.fbd.core.app.recharge.model.RechargeModel;
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

public interface IRechargeService {

    /**
     * Description: 查询用户充值记录
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 上午11:48:00
     */
    SearchResult<Map<String, Object>> getRechargePage(Map<String, Object> param);
    
    /**
     * Description: 查询用户充值记录（非平台）
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 上午11:48:00
     */
    SearchResult<Map<String, Object>> getUserRechargePage(Map<String, Object> param);
    
    /**
     * Description: 平台手续费账户充值
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 上午11:48:00
     */
    SearchResult<Map<String, Object>> getFeeRechargePage(Map<String, Object> param);

    /**
     * Description: 查询用户充值列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     */
	SearchResult<Map<String, Object>> getRechargelist(RechargeModel recharge);

	/**
     * Description: 审核充值记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	void updateAuditState(RechargeModel recharge);
	  /*
     * 根据orderId修改充值
     */
    public long updateByOrderId(RechargeModel model);
    
    
    /**
     * 区块链转账成功后的后续操作
     * @param orderId
     */
    public void rechargeTransferAfter(String orderId,Map<String,String> resultMap);
    
    /**
     * 充值处理任务
     */
    public void rechargeHandleTask();
    
    /**
     * 根据orderId查询充值
     * @param orderId
     * @return
     */
    public RechargeModel selectByOrderId(String orderId);

}
