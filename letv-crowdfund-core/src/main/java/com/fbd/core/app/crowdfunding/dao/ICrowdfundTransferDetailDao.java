package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferDetailModel;
import com.fbd.core.base.BaseDao;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹挂牌详情
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
public interface ICrowdfundTransferDetailDao extends BaseDao<CrowdfundTransferDetailModel>{

    /**
     * Description: 统计已经成功购买的份数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-13 下午2:49:21
     */
    
    long selectCountBuyParts(String transferNo);

    /**
     * Description: 根据购买订单号 查询Model
     *
     * @param 
     * @return CrowdfundTransferDetailModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-13 下午4:04:56
     */
    
    CrowdfundTransferDetailModel getByBuyOrderId(String orderId);

    /**
     * 
     * Description:查询成功购买的用户
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    
    List<Map<String, Object>> getCrowdfundTransferUserPayed(
            CrowdfundTransferDetailModel model);
    
}