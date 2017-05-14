package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹支持
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface ICrowdfundingSupportDao extends BaseDao<CrowdfundingSupportModel>{
    
    public List<Map<String,Object>> getList(CrowdfundingSupportModel model);
    public long getCount(CrowdfundingSupportModel model);
    public CrowdfundingSupportModel getByOrderId(String orderId);
    public List<CrowdfundingSupportModel> getByLoanNo(String loanNo);
    public Map<String,Object> getSupportDataByLoanNo(String loanNo);
    public Map<String,Object> getSupportAmtAndBuyAmt(String userId);
    
    /**
     * 查询未放款的总金额
     * Description: 
     * @param 
     * @return double
     * @throws 
     * Create Date: 2016-8-26 下午2:15:39
     */
    public Map<String,Object> selectNoLendAmtByLoanNo(String loanNo);
//    public double selectNoLendAmtByLoanNo(String loanNo);
    
    /**
     * 
     * Description: 统计总的服务费
     *
     * @param 
     * @return double
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 上午10:53:40
     */
    public double getTotalP2pFee(String loanNo);
    
    /**
     * 
     * Description: 查询是否存在待发货的支持
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-16 下午7:28:01
     */
    public long getNoSendSupportListCount(String loanNo);
    
    /**
     * 
     * Description:获得支付成功的认购份数 
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-26 下午2:46:49
     */
    public long getPayedParts(String loanNo);
    
    public List<Map<String,Object>> getOrderSupportList(CrowdfundingSupportModel model);
    public long getOrderSupportCount(CrowdfundingSupportModel model);
    
    /**
     * 
     * Description:查询支持数
     *  
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-6-13 下午7:25:21
     */
    public long getSupportUserCounts(String loanNo);
	public List<Map<String, Object>> getLeaderSupportList(
			CrowdfundingSupportModel model);
	public long getLeaderSupportCount(CrowdfundingSupportModel model);
	
	
	 /**
     * 
     * Description:查询领投记录 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-7-15 下午8:14:38
     */
    public List<Map<String,Object>> getLeadSupportList(CrowdfundingSupportModel model);
    /**
     * Description: 查询可挂牌列表
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-10 下午4:26:20
     */
    
    public List<Map<String, Object>> getCrowdfundingInvestTransferList(
            CrowdfundingSupportModel model);
    /**
     * Description: 查询可挂牌列表统计
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-10 下午4:26:25
     */
    public long getCrowdfundingInvestTransferCount(CrowdfundingSupportModel model);
    /**
     * Description: 查询每个项目支持份数列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-14 上午11:08:04
     */
    
    public List<Map<String, Object>> getSupportPartsDetailList(
            CrowdfundingSupportModel model);
    /**
     * Description: 统计每个项目支持份数列表
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-14 上午11:08:33
     */
    
    public long getSupportPartsDetailCount(CrowdfundingSupportModel model);
    
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
     * Description: 查询该项目一共投资分数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-1-25 下午4:22:29
     */
    
    public long selectSumBuyNum(CrowdfundingSupportModel model);
    
    /**
     * Description: 保存抽奖编号
     *
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-10 下午5:04:44
     */
    
    public List<CrowdfundingSupportModel> getPaySucessUser(String loanNo,String backNo);
    /**
     * Description: 查询可转让产品
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-11 下午4:37:25
     */
    
    public List<Map<String, Object>> getCanTransferList(
            CrowdfundingSupportModel model);
    /**
     * Description: 统计可转让产品
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-11 下午4:37:45
     */
    
    public long getCanTransferCount(CrowdfundingSupportModel model);
    /**
     * Description: 查询可转让详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 下午1:41:32
     */
    
    public Map<String, Object> getCanTransferDetail(
            CrowdfundingSupportModel model);
    /**
     * Description: 通过订单号更新
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-27 上午11:34:25
     */
    
    public void updateByOrderNo(CrowdfundingSupportModel model);
    /**
     * Description: 通过意向金尾款订单号查询
     *
     * @param 
     * @return CrowdfundingSupportModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-27 下午12:42:48
     */
    
    public CrowdfundingSupportModel getByIntentionNo(String orderId);
    /**
     * 
     * Description: 查询投资信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin
     */
    public Map<String, Object> getSupportInfo(String orderId);
    
    /**
     * Description: 获取投资第二日待收益投资
     *
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-31 上午10:58:25
     */
    
    public List<CrowdfundingSupportModel> getDailyIncomeSupportList();
    /**
     * Description: 统计个人中心资产
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-9-1 上午11:17:09
     */
    
    public Map<String, Object> getCountInvestAll(String userId);
    

    public List<Map<String,Object>> selectUserSupportByLoanNo(String loanNo);
    
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
     * 支付每日收益
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月17日 下午2:46:10
     */
    public List<Map<String,Object>>selectDailyIncomeData(Map<String,Object>param);
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
    
    /**
     * 查询合同签署情况
     * Description: 
     * @param 
     * @return long
     * @throws 
     * Create Date: 2016-11-9 下午2:37:08
     */
    public long selectContractSignNum(CrowdfundingSupportModel model);
    
    /**
     * 查询项目是否还有未完成尾款支付的投资记录
     * Description: 
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * Create Date: 2016-11-22 下午2:02:47
     */
    public List<CrowdfundingSupportModel> selectNoComplateListByLoanNo(String loanNo);
    
    /**
     * 查询已过期但未支付尾款的支持记录
     * Description: 
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * Create Date: 2016-11-23 上午11:23:45
     */
    public List<CrowdfundingSupportModel>selectOverDueNoComplateList(CrowdfundingSupportModel model);
    
}