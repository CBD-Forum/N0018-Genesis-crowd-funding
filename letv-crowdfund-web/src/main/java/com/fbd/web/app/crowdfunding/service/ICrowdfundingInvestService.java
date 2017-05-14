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

import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.InterviewRecordModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 股权众筹投资 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface ICrowdfundingInvestService {
    
    /**
     * 
     * Description:保存约谈记录 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午3:04:52
     */
    public void saveInterview(InterviewRecordModel model);
    
    /**
     * 
     * Description:众筹投资（线下付款）(房产与股权) 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午7:36:30
     */
    public void saveStockInvest(CrowdfundingSupportModel model);
    
    /**
     * 
     * Description:查询领投人/跟投人
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getInvestorList(CrowdfundUserStuffModel model);
    
    
    /**
     * 
     * Description:实物众筹支持前验证 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-30 下午2:13:50
     */
    public void checkBeforeEntitySupport(CrowdfundingSupportModel model);
    
    
    
    /**
     * 
     * Description: 查询众筹用户信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午2:50:05
     */
    public Map<String,Object> getCrowdfundUserInfo(String userId);
    
    
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
    
  
    
    /**
     * 
     * Description:认购定金前验证 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-30 下午2:13:50
     */
    public void checkBeforeOrderInvest(CrowdfundingSupportModel model);
    
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
     * Description:查询领投记录
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getLeadSpportList(CrowdfundingSupportModel model);

    /**
     * 
     * Description: 查询个人中心
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     * Create Date: 2015-4-2 下午12:34:23
     */
	public Object getUserCenterInfo(String userId);


	public Map<String, Object> getCountInvestAll(String userId);

	public String getUserInfoByAuth(CrowdfundUserStuffModel model);
	/**
	 * 转账异步回调处理
	 * @param loanNo
	 */
    public void dealWithSystemTransaction(String loanNo);
    /**
	 * 发起人异步回调处理
	 * @param loanNo
	 */
    public void dealWithOrganiserTransaction(String loanNo);
    public CrowdfundingModel getByloanNo(String loanNo);
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
     * Description:放款操作 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-1 上午11:46:25
     */
    public void updateAfterLoan(CrowdfundingModel loan,String operator);
    public void sendFlowSuccess(String requestId,Timer timer,boolean notifyFlag);

}
