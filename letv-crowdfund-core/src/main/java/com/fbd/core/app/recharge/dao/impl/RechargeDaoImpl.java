/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RechargeDaoImpl.java 
 *
 * Created: [2014-12-22 上午10:44:52] by haolingfeng
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

package com.fbd.core.app.recharge.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.base.BaseDaoImpl;
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
@Repository("rechargeDao")
public class RechargeDaoImpl extends BaseDaoImpl<RechargeModel> implements
        IRechargeDao {

    /**
     * 
     * Description: 根据订单号查询充值单
     * 
     * @param
     * @return RechargeModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午2:49:26
     */
    public RechargeModel selectByOrderId(String orderId) {
        return this.selectOneByField("selectByOrderId", orderId);
    }

    /**
     * 
     * Description:查询前台充值列表
     * 
     * @param
     * @return List<RechargeModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午6:12:54
     */
    public List<Map<String,Object>> getRechargeList(RechargeModel model) {
        return this.selectMapByFields("selectRechargeList", model);
    }
     
    /**
     * Description:查询前台充值列表条数
     * 
     * @param
     * 
     * @return List<RechargeModel>
     * 
     * @throws
     * 
     * @Author haolingfeng Create Date: 2014-12-24 下午6:12:54
     */
    public long getRechargeListCount(RechargeModel model) {
        return this.getCount("selectRechargeCounts", model);
    }

    /**
     * Description: 查询用户充值列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 上午11:48:00
     */
    public SearchResult<Map<String, Object>> getRechargePage(Map<String, Object> param) {
        SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(getRechargeList4Admin(param));
        result.setTotal(getRechargeCount4Admin(param));
        return result;
    }
    
    public List<Map<String, Object>> getRechargeList4Admin(Map<String, Object> param) {
        return this.selectMapByFields("getRecharges4Admin", param);
    }
    
    public long getRechargeCount4Admin(Map<String, Object> param) {
        return this.getCount("getRechargeCount4Admin", param);
    }
    public long updateByOrderId(RechargeModel model){
        return this.update("updateByOrderId", model);
    }
    /**
     * 区块链成功
     */
    public List<Map<String, Object>> selectBlockChainRechargeData(Map<String, Object> param) {
        return this.selectMapByFields("selectBlockChainRechargeData", param);
    }
    /**
     *充值成功
     */
    public List<Map<String, Object>> selectRechargeData(Map<String, Object> param) {
        return this.selectMapByFields("selectRechargeData", param);
    }

    /**
     * Description: 查询交易中和交易失败的充值记录
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-9-1 下午5:36:28
     */
    public List<Map<String, Object>> getUserRechargeList(RechargeModel model) {
        return this.selectMapByFields("selectUserRechargeList", model);
    }

    /**
     * Description: 统计交易中和交易失败的充值记录
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-9-1 下午5:36:45
     */
    public long getUserRechargeCount(RechargeModel model) {
        return this.getCount("selectUserRechargeCount", model);
    }

    /**
     * 
     * Description:查询交易中和交易失败的充值记录详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    public Map<String, Object> getUserRechargeDetail(RechargeModel model) {
        return this.selectOneMapByField("selectUserRechargeDetail", model);
    }
    
    /**
     * 根据model查询
     * Description: 
     *
     * @param 
     * @return RechargeModel
     * @throws 
     * Create Date: 2016-9-19 下午8:15:43
     */
    public List<RechargeModel> selectListByModel(RechargeModel model){
        return this.selectByField("selectListByModel", model);
    }
    
}
