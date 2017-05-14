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

import java.util.List;
import java.util.Map;

import com.fbd.core.app.crowdfunding.model.CrowdfundAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundRefundAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingCommentModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.StockBackSetModel;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹项目
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface ICrowdfundingService {

    /**
     * 
     * Description:保存众筹项目信息 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:09:05
     */
    public void saveCrowdfunding(String operator,CrowdfundingModel model);
    
    /**
     * 
     * Description: 查询众筹项目明细
     *
     * @param 
     * @return CrowdfundingModel
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:37:32
     */
    public CrowdfundingModel getCrowdfundByLoanNo(String id);
    
    /**
     * 
     * Description: 更新众筹项目
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:44:03
     */
    public void updateCrowdFund(CrowdfundingModel model);
    
    /**
     * 
     * Description:分页查询众筹项目 
     *
     * @param 
     * @return SearchResult<CrowdfundingModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getPageList(CrowdfundingModel model);
    
    /**
     * 
     * Description:保存回报设置
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午10:46:02
     */
    public void saveCrowdfundingBackSet(CrowdfundingBackSetModel model);
    
    /**
     * 
     * Description:查询众筹项目 设置
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getBackSetList(CrowdfundingBackSetModel model);
    
    
    /**
     * 查询众筹项目当前回报已经设置的总金额
     * @param model
     * @return
     */
    public double getBackSetTotalAmt(String loanNo);
    
    /**
     * 
     * Description:删除回报设置 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:58:24
     */
    public void deleteBackSet(String id);
    /**
     * 
     * Description: 编辑回报设置
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:57:19
     */
    public void updateBackSet(CrowdfundingBackSetModel model);
    /**
     * 
     * Description: 查询众筹项目回报设置
     *
     * @param 
     * @return CrowdfundingModel
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:37:32
     */
    public CrowdfundingBackSetModel getBackSetById(String id);
    
    /**
     * 
     * Description: 查询项目详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午3:02:02
     */
    public Map<String,Object> getCrowdFundDetail(String loanNo);
    
    /**
     * 
     * Description:查询众筹项目进展
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getProgessList(CrowdfundingProgressModel model);
    
    /**
     * 
     * Description:查询众筹项目评论
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getCommentList(CrowdfundingCommentModel model);
    
    /**
     * 
     * Description:查询众筹支持
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getSpportList(CrowdfundingSupportModel model);
    
    /**
     * 
     * Description:根据项目查询支付成功的支持记录 
     *
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-30 下午3:48:21
     */
    public List<CrowdfundingSupportModel> getSupportList(String loanNo);
    
    /**
     * 
     * Description: 审核
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 下午3:22:17
     */
    public void updateCrowdFund(CrowdfundingModel model,String operator,String auditState);
    
    
    /**
     * 
     * Description: 发布众筹项目
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:44:03
     */
    public void updateCrowdFundAfterRelease(CrowdfundingModel model);
    
    /**
     * 
     * Description: 审核评论
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-11 下午2:57:23
     */
    public void updateComment(CrowdfundingCommentModel model);
    
    /**
     * 
     * Description:查询用户的资金统计
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getUserCapitalList(CrowdfundingModel model);
    
    /**
     * 
     * Description:删除项目
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:58:24
     */
    public void deleteCrowdFunding(String loanNo);
    
    /**
     * 
     * Description: 到预热截止日期时变更项目状态
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-11 下午12:02:31
     */
    public void updateCrowdFundPreheated(String loanNo);
    
    /**
     * 
     * Description: 预热项目
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-21 上午10:34:23
     */
    public void updateCrowdFundAfterPreheat(CrowdfundingModel model);
    
    /**
     * 
     * Description: 删除评论
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-11 下午2:57:23
     */
    public void delComment(String id);
    
    /**
     * 
     * Description: 更新众筹项目(延期)
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:44:03
     */
    public void updateCrowdFundAfterDelay(CrowdfundingModel model);
    
    
    /**
     * 
     * Description:更新项目详情 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-21 下午2:13:32
     */
    public void updateLoanDetail(CrowdfundDetailModel detail);
    
    /**
     * 
     * Description: 查询众筹项目股权回报设置
     *
     * @param 
     * @return CrowdfundingModel
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:37:32
     */
    public StockBackSetModel getStockBackSet(String loanNo);
    
    /**
     * 
     * Description:查询众筹的关注记录
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getAttentionList(CrowdfundingAttentionModel model);
    
    
    /**
     * 
     * Description: 插入审核记录
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-30 上午11:14:25
     */
    public void addLoanAudit(String operator, String loanNo, String auditState,
            String auditOpinion, String loanState);
    
    
    /**
     * 
     * Description:查询众筹的审核记录
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<CrowdfundAuditModel> getAuditList(CrowdfundAuditModel model);
    /**
     * 
     * Description: 股权回报设置修改
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateEquityBackSet(StockBackSetModel model);

    /**
     * 
     * Description: 修改项目等级
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateLoanLevel(CrowdfundingModel model);

	public void saveBonus(String loanNo, double money);

	/**
     * 
     * Description: 结束分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateEndBonus(CrowdfundingModel model);

	/**
     * 
     * Description: 发布众筹项目
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void saveCrowdfunding(CrowdfundingModel model);

	/**
     * 
     * Description: 查询是否设置回报
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Boolean getBackByLoanNo(String loanNo);

	/**
     * 
     * Description: 查询项目审核列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getUserPrizeList(
			CrowdfundUserPrizeModel model);

	/**
     * 
     * Description: 审核产品项目申请退款
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateRefundState(CrowdfundRefundAuditModel model);



	/**
     * 
     * Description: 查询产品项目申请退款列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getLoanAuditRefundPage(
			CrowdfundRefundAuditModel model);


	/**
     * 
     * Description: 查询用户购买转让记录表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getBuyTransferListPage(
			CrowdfundProductTransferModel model);

	/**
     * 
     * Description:项目转让审核
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateProductTransferAudit(CrowdfundProductTransferModel model);
	
    /**
     * 意向金支付未支付
     * Description: 
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年11月8日 下午7:22:19
     */
    public List<CrowdfundingSupportModel> selectIntentionsByLoanNo(String loanNo);
}
