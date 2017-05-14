/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IRechargeDao.java 
 *
 * Created: [2014-12-22 上午10:44:01] by haolingfeng
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

package com.fbd.core.app.recharge.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.base.BaseDao;
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

public interface IRechargeDao extends BaseDao<RechargeModel> {
    /**
     * 
     * Description: 根据订单号查询充值单
     * 
     * @param
     * @return RechargeModel
     * @throws
     * @Author haolingfeng 
     * Create Date: 2014-12-22 下午2:49:26
     */
    public RechargeModel selectByOrderId(String orderId);

    /**
     * 
     * Description:查询前台充值列表
     * 
     * @param
     * @return List<RechargeModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午6:12:54
     */
    public List<Map<String,Object>> getRechargeList(RechargeModel model);

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
    public long getRechargeListCount(RechargeModel model);

    /**
     * Description: 查询用户充值列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 上午11:48:00
     */
    public SearchResult<Map<String, Object>> getRechargePage(Map<String, Object> param);
    /*
     * 根据orderId修改充值
     */
    public long updateByOrderId(RechargeModel model);
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
    public List<Map<String, Object>> selectRechargeData(Map<String, Object> param) ;

    /**
     * Description: 查询交易中和交易失败的充值记录
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-9-1 下午5:36:28
     */
    public List<Map<String, Object>> getUserRechargeList(RechargeModel model);

    /**
     * Description: 统计交易中和交易失败的充值记录
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-9-1 下午5:36:45
     */
    
    public long getUserRechargeCount(RechargeModel model);

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
     * 根据model查询
     * Description: 
     *
     * @param 
     * @return RechargeModel
     * @throws 
     * Create Date: 2016-9-19 下午8:15:43
     */
    public List<RechargeModel> selectListByModel(RechargeModel model); 
    /**
     * 区块链成功
     */
    public List<Map<String, Object>> selectBlockChainRechargeData(Map<String, Object> param) ;
}
