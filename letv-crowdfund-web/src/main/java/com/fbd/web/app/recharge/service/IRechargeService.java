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

package com.fbd.web.app.recharge.service;

import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IRechargeService {
    /**
     * 
     * Description:充值列表查询
     * 
     * @param
     * @return SearchResult<RechargeModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午6:03:36
     */
    public SearchResult<Map<String,Object>> getRechargeList(RechargeModel model);
    /**
     * 投资方充值查询
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-25 下午2:33:47
     */
    public List<Map<String, Object>> pushRechargeData(Map<String, Object> param) ;
    /**
     * 
     * Description:查询交易中和交易失败的充值记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getUserRechargeList(
			RechargeModel model);
	/**
     * 
     * Description:查询交易中和交易失败的充值记录详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getUserRechargeDetail(RechargeModel model);
	
    
    /**
     * 根据orderId查询充值
     * @param orderId
     * @return
     */
    public RechargeModel selectByOrderId(String orderId);
	
    /**
     * 区块链转账成功后的后续操作
     * @param orderId
     */
    public void rechargeTransferAfter(String orderId,String requestID,RechargeModel recharge,Timer timer,boolean notifyFlag);

}
