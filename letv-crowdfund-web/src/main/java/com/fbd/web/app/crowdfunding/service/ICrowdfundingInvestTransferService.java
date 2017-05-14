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

package com.fbd.web.app.crowdfunding.service;

import java.util.List;
import java.util.Map;

import com.fbd.core.app.crowdfunding.model.CrowdfundTransferDetailModel;
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
     * Description:是否同意
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	void updateIsAgree(CrowdfundTransferModel model);

    /**
     * 
     * Description:查询首页挂牌列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	SearchResult<Map<String, Object>> getCrowdfundTransferDetailList(
			CrowdfundTransferModel model);

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
