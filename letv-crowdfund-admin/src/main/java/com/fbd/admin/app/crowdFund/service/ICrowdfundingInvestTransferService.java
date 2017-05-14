/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICrowdfundingService.java 
 *
 * Created: [2015-3-27 下午4:56:07] by haolingfeng
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
 * ProjectName: rain-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.crowdFund.service;

import java.util.Map;

import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 股权众筹 -挂牌
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public interface ICrowdfundingInvestTransferService {

    /**
     * 
     * Description:查询股权项目挂牌列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	SearchResult<Map<String, Object>> getCrowdfundingInvestTransferList(
			CrowdfundingSupportModel model);

    /**
     * 
     * Description:保存股权项目挂牌
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	void saveCrowdFundTransfer(CrowdfundTransferModel model);

    /**
     * 
     * Description:查询股权项目挂牌列表(【审核中】【已审核】) 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	SearchResult<Map<String, Object>> getCrowdfundingTransferAuditList(
			CrowdfundTransferModel model);

    /**
     * 
     * Description:查询股权项目挂牌列表(【挂牌中】【挂牌完成】) 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	SearchResult<Map<String, Object>> getCrowdfundingTransferInfoList(
			CrowdfundTransferModel model);

    /**
     * 
     * Description:审核转让
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	void updateTransfer(CrowdfundTransferModel model);

    /**
     * 
     * Description:查询挂牌详情
     *
     * @param 
     * @return CrowdfundTransferModel
     * @throws 
     * @Author wuwenbin<br/>
     */
	CrowdfundTransferModel getCrowdfundingTransferDetial(String transferNo);

    /**
     * 
     * Description:跟新股权项目挂牌
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateCrowdFundTransfer(CrowdfundTransferModel model);

    /**
     * 
     * Description:查询每个项目支持份数列表
     *
     * @param 
     * @return SearchResult <Map<String,Object>>
     * @throws 
     * @Author wuwenbin<br/>
     */
	SearchResult<Map<String, Object>> getSupportPartsDetailList(
			CrowdfundingSupportModel model);

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
