package com.fbd.web.app.crowdfunding.service;

import java.util.Map;

import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.common.model.SearchResult;

public interface ICrowdfundProductTransferService {

	 /**
     * 
     * Description:查询可转让产品投资
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	SearchResult<Map<String, Object>> getCanTransferList(
			CrowdfundingSupportModel model);

	/**
     * 
     * Description:保存产品项目转让
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	void saveCrowdFundTransfer(CrowdfundProductTransferModel model);

	/**
     * 
     * Description:查询市场转让列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	SearchResult<Map<String, Object>> getCrowdfundTransferDetailList(
			CrowdfundProductTransferModel model);

	/**
     * 
     * Description:查询转让详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	Map<String, Object> getCrowdfundTransferDetail(
			CrowdfundProductTransferModel model);

	/**
     * 
     * Description:查询可转让详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	Map<String, Object> getCanTransferDetail(CrowdfundingSupportModel model);

	int getTransferDay();

}
