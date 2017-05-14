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
import java.util.Timer;

import com.fbd.core.app.crowdfunding.model.CrowdfundDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundRefundAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingCommentModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingPraiseModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.StockBackSetModel;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.app.user.model.UserUploadFileModel;
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
	 * 查询用户是否点赞过或者收藏过该项目
	 * @param userId
	 * @param LoanNo
	 * @return
	 */
	public Map<String,Object> getUserOperateLoan(String userId,String loanNo);

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
    public void saveCrowdfunding(CrowdfundingModel model,CrowdfundDetailModel crowdfundDetail );
    
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
    public CrowdfundingModel getCrowdfundByLoanNo(String loanNo);
    
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
    public SearchResult<Map<String,Object>> getPageList(CrowdfundingModel model,String loaginUserId);
    
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
    public SearchResult<Map<String,Object>> getBackSetList(CrowdfundingBackSetModel model,String userId);
    
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
    public Map<String,Object> getCrowdFundDetail(String loanNo,String userId);
    
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
     * 查询项目评论数量
     * @param model
     * @return
     */
    public long getCommentCount(CrowdfundingCommentModel model);
    /**
     * 
     * Description: 评论
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-31 上午11:03:12
     */
    public void saveComment(CrowdfundingCommentModel model);
    
    /**
     * 
     * Description:用户的总支持金额 
     *
     * @param 
     * @return double
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 上午11:35:07
     */
    public Map<String,Object> getSupportAmtAndBuyAmt(String userId);
    
    /**
     * 
     * Description:点赞保存 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午4:26:07
     */
    public void savePraise(CrowdfundingPraiseModel model);
    
    /**
     * 
     * Description: 更新支持
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-8 下午7:06:36
     */
    public void updateSupport(CrowdfundingSupportModel model);
    
    /**
     * 
     * Description: 更新支持的发货信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-8 下午7:06:36
     */
    public String updateSupportSendInfo(CrowdfundingSupportModel model);
    
    /**
     * 
     * Description:关注 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-9 下午8:40:25
     */
    public void saveAttention(CrowdfundingAttentionModel model);
    
    /**
     * 
     * Description:分页查询关注列表 
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-10 上午10:02:14
     */
    public SearchResult<Map<String,Object>> getAttentionList(CrowdfundingAttentionModel model);
    
    /**
     * 
     * Description:取消关注
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:58:24
     */
    public void deleteAttention(String id);
    
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
    public CrowdfundingModel getCrowdfundById(String id);
    
    
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
    public CrowdfundingSupportModel getSupportById(String id);
    
    /**
     * 
     * Description:更新众筹发起人的资料 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-19 下午3:13:50
     */
    public void updateCrowdfundUserStuff(CrowdfundUserStuffModel userStuff);
    
    /**
     * 
     * Description: 上传项目图片
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-19 下午3:56:54
     */
    public void updateLoanPhoto(String loanPhotos,String loanNo);
    
    /**
     * 
     * Description: 更新股权回报设置
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-19 下午8:22:38
     */
    public void updateStockBackSet(StockBackSetModel model);
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
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-28 上午11:38:19
     */
    public void saveUserUploadFile(UserUploadFileModel model);

	public Map<String, Object> getCrowdPhotos(String loanNo);

	public StockBackSetModel getStockBack(String loanNo);
    
    /**
     * 
     * Description:删除项目 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-9 下午3:12:09
     */
    public void deleteCrowdfund(String loanNo);
    
    /**
     * 
     * Description:删除支持列表
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-9 下午3:12:09
     */
    public void deleteSupport(String id);

	public SearchResult<Map<String, Object>> getLeaderSupportList(
			CrowdfundingSupportModel model);
	
	/**
     * 
     * Description: 平台统计
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-12-11 下午12:24:40
     */
	public Map<String,Object> getStatsData();

	/**
     * 
     * Description: 统计前台展示数据(已投资总额；项目总数；投资人总数)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getCountInfo();

	/**
     * 
     * Description: 取消关注
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
	public void deleteAttention(CrowdfundingAttentionModel model);

	/**
     * 
     * Description: 保存用户认证信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void saveUserAuth(CrowdfundUserStuffModel model);

	/**
     * 
     * Description: 查询用户中奖列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getUserPrizeList(
			CrowdfundUserPrizeModel model);

	public CrowdfundUserStuffModel getCrowdfundUserAuth(CrowdfundUserStuffModel model);
	
	
	public CrowdfundUserStuffModel selectByModel(CrowdfundUserStuffModel model);
	

	/**
     * 
     * Description: 查询服务费比列
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getServiceFeeScale();

	/**
     * 
     * Description: 产品投资申请退款前验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void checkBeforeRefundApplication(String orderId);
	
	/**
     * 
     * Description: 产品投资申请退款
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateRefundApplication(CrowdfundingSupportModel model);

	/**
     * 
     * Description: 分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public String saveBonus(String loanNo, double money);

	/**
     * 
     * Description: 项目方审核投资申请退款
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateLoanAuditRefund(CrowdfundRefundAuditModel model);

	
	/**
     * 
     * Description: 查询项目方审核投资申请退款记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public List<Map<String, Object>> getLoanAuditRefundList(
			CrowdfundRefundAuditModel model);

	/**
     * 
     * Description: 查询回报设置backNo
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Object getBackSetByBackNo(String loanNo,String backNo);

	/**
     * 
     * Description: 申请发货
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateApplicationDelivery(String supportNo);

	/**
     * 
     * Description: 查询抽奖编号
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public CrowdfundUserPrizeModel getUserPrize(String loanNo, String supportUser);

	/**
     * 
     * Description: 奖励发放明细
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:51:35
     */
	public SearchResult<Map<String,Object>> getRewardPageList(RewardAssignModel rewardModel);

	/**
     * 
     * Description: 用户是否同意分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void saveBonus(String state, String loanBonusId);


	 /**
     * 募集失败数据推送
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-26 下午1:46:29
     */
    public List<Map<String, Object>> selectLoanFail(Map<String, Object> param);
    
    /**
     * 投资划款
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-26 下午5:57:00
     */
    public List<Map<String, Object>> selectIsnvestmentFunds(Map<String, Object> param);

	/**
     * 
     * Description: 获取投资所需要配置参数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin
     */
	public Map<String,Object> getDeployMap();

	/**
     * 
     * Description: 查询投资信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin
     */
	public  Map<String,Object> getSupportInfo(String orderId);
	/**
     * 
     * Description: 分红前验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin
     */
	public void checkBeforeBonus(String loanNo,double money);
	
    /**
     * 查询用户项目统计
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * Create Date: 2016-8-31 下午7:33:49
     */
    public  Map<String, Object>  selectUserProjectInfo(String userId);
    
    public CrowdfundingModel getByloanNo(String loanNo);
    
	/**
	 * 项目方同意退款成功的后续操作
	 * @param params
	 */
	public void loanAuditRefundAfter(String tranId,String requestID,Timer timer,boolean notifyFlag);
	
	/**
	 * 平台审核退款后续操作 
	 * @param params
	 */
	@SuppressWarnings("unchecked")
	public void platformAuditRefundAfter(String tranId,String requestID,String auditStatus,Timer timer,boolean notifyFlag);
	
	/**
	 * 项目方分红后续操作
	 * @param id
	 */
	public void loanBonusAfter(String id,String requestID,Timer timer,boolean notifyFlag);
	 
    /**
     * 意向金尾款支付是否成功
     * Description: 
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月11日 下午5:57:16
     */
    public long selectIntentionEndIsSucess(String orderId);
    /**
     * 意向金支付成功
     * Description: 
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月11日 下午5:57:50
     */
    public long selectPayIntentionIsSucess(String orderId);
    /**
     * 全额付款是否成功
     * Description: 
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月11日 下午5:58:17
     */
    public long selectPayIsSuccess(String orderId);
    /**
     * 正在支付的分数
     * Description: 
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月20日 下午7:23:08
     */
    public long selectPayingParts(String loanNo);

    
    public List<CrowdfundingModel> selectByAll(CrowdfundingModel model);

}
