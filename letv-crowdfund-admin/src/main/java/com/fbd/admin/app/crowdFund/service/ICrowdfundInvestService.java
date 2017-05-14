/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICrowdfundInvestService.java 
 *
 * Created: [2015-5-25 下午7:52:26] by haolingfeng
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
 * ProjectName: crowdfund-admin 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.crowdFund.service;

import java.util.List;
import java.util.Map;

import com.fbd.core.app.crowdfunding.model.CrowdfundLeadInvestorModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.InterviewRecordModel;
import com.fbd.core.app.user.model.UserUploadFileModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface ICrowdfundInvestService{
    
    
    /**
     * 
     * Description: 后台支持
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-17 下午3:10:54
     */
    public void saveSupportByBack(CrowdfundingSupportModel invest,String loanType);
    
    /**
     * 
     * Description:查询众筹的约谈记录
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getInterviewList(InterviewRecordModel model);
    
    public List<CrowdfundUserStuffModel> getByUserId(CrowdfundUserStuffModel model);
    
    public List<Map<String,Object>> getUserUploadFiles(String userId);
    
    
    /**
     * 
     * Description:申请认证列表 
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-31 下午6:40:41
     */
    public SearchResult<Map<String,Object>> getApplyAuthList(CrowdfundUserStuffModel stuff);
    
    /**
     * 
     * Description:审核领投人认证
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-31 下午7:25:27
     */
    public void updateLeadAuthState(CrowdfundUserStuffModel stuff);
    
    /**
     * 
     * Description:审核投资人认证
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-31 下午7:25:27
     */
    public void updateInvestorAuthState(CrowdfundUserStuffModel stuff);
    
    
    /**
     * 
     * Description: 放款前验证项目状态
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2015-1-9 下午12:28:41
     */
    public void checkBeforeLend(String loanNo);
    
    
    /**
     * 
     * Description:放款操作 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-1 上午11:46:25
     */
    public void updateAfterLoan(CrowdfundingModel loan,String operator);
    
    /**
     * 
     * Description:流标操作 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-1 上午11:46:25
     */
    public void updateAfterFlow(CrowdfundingModel loan,String operator);
    
    
    public void updateInterviewState(InterviewRecordModel model);
    
    /**
     * 处理项目进展
     */
    public void updateCrowdfundProgress(CrowdfundingProgressModel model);
    
    /**
     * 保存项目进展
     */
    public void saveCrowdfundProgress(CrowdfundingProgressModel model);
    
    
    /**
     * 
     * Description:查询众筹的认购列表
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getOrderSupportList(CrowdfundingSupportModel model);
    
    
    
    /**
     * 
     * Description:设为领投人 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-8 下午2:35:26
     */
     public void saveLoanLeader(CrowdfundLeadInvestorModel model);
     
     
     /**
      * 
      * Description:取消领投人 
      *
      * @param 
      * @return void
      * @throws 
      * @Author haolingfeng
      * Create Date: 2015-6-8 下午2:35:08
      */
     public void deleteLoanLeader(String id);
     
     
     /**
      * 
      * Description:查询领投人
      *
      * @param 
      * @return SearchResult<CrowdfundingBackSetModel>
      * @throws 
      * @Author haolingfeng<br/>
      * Create Date: 2015-3-27 下午5:11:45
      */
     public List<Map<String,Object>> getLoanLeader(String loanNo);
     
     /**
      * 
      * Description: 查询未放款的列表
      *
      * @param 
      * @return List<CrowdfundingSupportModel>
      * @throws 
      * @Author haolingfeng
      * Create Date: 2015-6-11 上午11:22:40
      */
     public List<CrowdfundingSupportModel> getNoLendSupportList(String loanNo);
     
     /**
      * 
      * Description:放款后更新投资 
      *
      * @param 
      * @return void
      * @throws 
      * @Author haolingfeng
      * Create Date: 2015-6-11 上午11:26:33
      */
     public void updateSupportAfterLend(CrowdfundingSupportModel support,CrowdfundingModel loan);
     
     
     public CrowdfundingModel getByLoanNo(String loanNo);
     
     /**
      * 
      * Description:流标请求
      *
      * @param 
      * @return void
      * @throws 
      * @Author haolingfeng
      * Create Date: 2015-6-11 上午11:26:33
      */
     public void sendSupportFlow(CrowdfundingSupportModel support,CrowdfundingModel loan);
    
     
     /**
      * 
      * Description: 手动停止项目
      *
      * @param 
      * @return void
      * @throws 
      * @Author haolingfeng
      * Create Date: 2015-6-23 下午6:52:45
      */
     public void updateAfterHandEndLoan(String loanNo,String operator);
     /**
      * 
      * Description:查询支付成功的份数 
      *
      * @param 
      * @return long
      * @throws 
      * @Author haolingfeng
      * Create Date: 2015-12-23 下午5:52:05
      */
     public long getTotalBuyNum(String loanNo);

     
 
	/**
     * 
     * Description:查询项目的认购列表是否有领投人
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Boolean getSupportListIsLeader(CrowdfundingSupportModel model);

	/**
     * Description: 修改公司认证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author 武文斌
     */
	public void modifyCompany(UserUploadFileModel model);
	/**
     * 
     * Description:机构领投人认证审核 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin	
     */
	public void auditOrgLeadorAuth(CrowdfundUserStuffModel model);

	/**
     * 
     * Description:机构跟投投人认证审核 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin	
     */
	public void auditOrgInvestorAuth(CrowdfundUserStuffModel model);

	/**
     * 
     * Description:查询业务参数服务费比列
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String,Object> getChargeFeeScale();
	/**
	 * 流标成功以后处理
	 * @param loanNo
	 */
	 public void sendFlowSuccess(CrowdfundingModel loan);
	 /**
		 * 转账异步回调处理
		 * @param loanNo
		 */
   public void dealWithSystemTransaction(String loanNo);
   /**
	 * 发起人异
	 * 步回调处理
	 * @param loanNo
	 */
    public void dealWithOrganiserTransaction(String loanNo);
}
