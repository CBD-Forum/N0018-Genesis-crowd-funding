package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.base.BaseDao;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹挂牌
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
public interface ICrowdfundTransferDao extends BaseDao<CrowdfundTransferModel>{

    /**
     * Description: 通过转让编号查询model
     *
     * @param 
     * @return CrowdfundTransferModel
     * @throws 
     * @Author wuwenbin
     */
    
    CrowdfundTransferModel selectModelByTransferNo(String transferNo);

    /**
     * Description: 查询股权项目挂牌列表(【审核中】【已审核】) 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 上午10:33:57
     */
    
    List<Map<String, Object>> getCrowdfundingTransferAuditList(
            CrowdfundTransferModel model);

    /**
     * Description: 统计股权项目挂牌列表(【审核中】【已审核】) 
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 上午10:34:02
     */
    
    long getCrowdfundingTransferAuditCount(CrowdfundTransferModel model);

    /**
     * Description: 查询股权项目挂牌列表(【挂牌中】【挂牌完成】) 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 下午1:28:29
     */
    
    List<Map<String, Object>> getCrowdfundingTransferInfoList(
            CrowdfundTransferModel model);

    /**
     * Description: 统计股权项目挂牌列表(【挂牌中】【挂牌完成】) 
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 下午1:28:34
     */
    
    long getCrowdfundingTransferInfoCount(CrowdfundTransferModel model);

    /**
     * Description: 查询首页挂牌列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 下午9:14:12
     */
    
    List<Map<String, Object>> getCrowdfundTransferDetailList(
            CrowdfundTransferModel model);

    /**
     * Description: 统计
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 下午9:14:45
     */
    long getCrowdfundTransferDetailCount(CrowdfundTransferModel model);

    /**
     * 
     * Description:查询挂牌详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    
    Map<String, Object> getCrowdfundTransferDetail(CrowdfundTransferModel model);

    /**
     * 查询可转让份数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     */
    
    long getCanTransferParts(String orderNo);

}