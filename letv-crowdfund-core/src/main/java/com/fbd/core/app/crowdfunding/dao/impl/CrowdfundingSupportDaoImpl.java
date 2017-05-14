/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingSupportDaoImpl.java 
 *
 * Created: [2015-3-27 下午4:33:30] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹支持
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundingSupportDao")
public class CrowdfundingSupportDaoImpl extends BaseDaoImpl<CrowdfundingSupportModel>
implements ICrowdfundingSupportDao{

    public List<Map<String,Object>> getList(CrowdfundingSupportModel model){
        return this.selectMapByFields("selectList", model);
    }
    public long getCount(CrowdfundingSupportModel model){
        return this.getCount("selectCount", model);
    }
    
    
    public CrowdfundingSupportModel getByOrderId(String orderId){
        return this.selectOneByField("selectByOrderId", orderId);
    }
    
    
    /**
     * 查询合同签署情况
     * Description: 
     * @param 
     * @return long
     * @throws 
     * Create Date: 2016-11-9 下午2:37:08
     */
    public long selectContractSignNum(CrowdfundingSupportModel model){
        return this.getCount("selectContractSignNum", model);
    }
    
    /**
     * 查询未放款的支持记录
     */
    public List<CrowdfundingSupportModel> getByLoanNo(String loanNo){
        return this.selectByField("selectByLoanNo", loanNo);
    }
    
    /**
     * 查询未放款的总金额
     * Description: 
     * @param 
     * @return double
     * @throws 
     * Create Date: 2016-8-26 下午2:15:39
     */
//    public double selectNoLendAmtByLoanNo(String loanNo){
//        CrowdfundingSupportModel model = new CrowdfundingSupportModel();
//        model.setLoanNo(loanNo);
//        return this.getsumAmt("selectNoLendAmtByLoanNo", model);
//    }
    
    public Map<String,Object> selectNoLendAmtByLoanNo(String loanNo){
        return this.selectOneMapByField("selectNoLendAmtByLoanNo", loanNo);
    }
    
    public Map<String,Object> getSupportDataByLoanNo(String loanNo){
        return this.selectOneMapByField("selectSupportDataByLoanNo", loanNo);
    }
    
    public Map<String,Object> getSupportAmtAndBuyAmt(String userId){
        return this.selectOneMapByField("selectTotalAmt", userId);
    }
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
    public double getTotalP2pFee(String loanNo){
        CrowdfundingSupportModel model = new CrowdfundingSupportModel();
        model.setLoanNo(loanNo);
        return this.getsumAmt("selectTotalP2pFee", model);
    }
    
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
    public long getNoSendSupportListCount(String loanNo){
        return this.getCount("selectNoSendDataByLoanNo", loanNo);
    }
    
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
    public long getPayedParts(String loanNo){
        return this.getCount("selectPayedParts",loanNo);
    }
    public long selectPayingParts(String loanNo){
        return this.getCount("selectPayingParts", loanNo);
    }
    
    public List<Map<String,Object>> getOrderSupportList(CrowdfundingSupportModel model){
        return this.selectMapByFields("selectOrderSupportList", model);
    }
    public long getOrderSupportCount(CrowdfundingSupportModel model){
        return this.getCount("selecOrderSupportCount", model);
    }
    
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
    public List<Map<String,Object>> getLeadSupportList(CrowdfundingSupportModel model){
        return this.selectMapByFields("selectLeadSupportList", model);
    }
    
    
    /**
     * 
     * Description:查询支持数
     *  
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-13 下午7:25:21
     */
    public long getSupportUserCounts(String loanNo){
        CrowdfundingSupportModel support = new CrowdfundingSupportModel();
        support.setLoanNo(loanNo);
        support.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
        long supportCnt = this.getCount(support);
        return supportCnt;
    }
	public List<Map<String, Object>> getLeaderSupportList(
			CrowdfundingSupportModel model) {
		return this.selectMapByFields("selectLeaderSupportList", model);
	}
	public long getLeaderSupportCount(CrowdfundingSupportModel model) {
		return this.getCount("selectLeaderSupportCount", model);
	}
	
	   /**
     * Description: 查询可挂牌列表
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-10 下午4:26:20
     */
    public List<Map<String, Object>> getCrowdfundingInvestTransferList(
            CrowdfundingSupportModel model) {
        // TODO Auto-generated method stub
        return this.selectMapByFields("selectCrowdfundingInvestTransferList",model);
    }
    /**
     * Description: 查询可挂牌列表统计
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-10 下午4:26:25
     */
    public long getCrowdfundingInvestTransferCount(CrowdfundingSupportModel model) {
        // TODO Auto-generated method stub
        return this.getCount("selectCrowdfundingInvestTransferCount", model);
    }
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
            CrowdfundingSupportModel model) {
        // TODO Auto-generated method stub
        return this.selectMapByFields("selectSupportPartsDetailList",model);
    }
    /**
     * Description: 统计每个项目支持份数列表
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-14 上午11:08:33
     */
    public long getSupportPartsDetailCount(CrowdfundingSupportModel model) {
        // TODO Auto-generated method stub
        return this.getCount("selectSupportPartsDetailCount", model);
    }
    
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
    public long getTotalBuyNum(String loanNo){
        return this.getCount("selectBuyNum",loanNo);
    }
    /**
     * Description: 查询该项目一共投资分数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-1-25 下午4:22:29
     */
    public long selectSumBuyNum(CrowdfundingSupportModel model) {
        // TODO Auto-generated method stub
        return this.getCount("selectSumBuyNum", model);
    }
    
    /**
     * Description: 获取可抽奖的支持列表
     *
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-10 下午5:04:44
     */
    public List<CrowdfundingSupportModel> getPaySucessUser(String loanNo,String backNo) {
        if(StringUtil.isEmpty(loanNo)){
            throw new ApplicationException("项目编号不能为空");
        }
        if(StringUtil.isEmpty(backNo)){
            throw new ApplicationException("回报编号不能为空");
        }
        Map<String,String> param=new HashMap<String,String>();
        param.put("loanNo", loanNo);
        param.put("backNo", backNo);
        return this.selectByField("selectPaySucessUser", param);
    }
    
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
            CrowdfundingSupportModel model) {
        return this.selectMapByFields("selectCanTransferList", model);
    }
    
    /**
     * Description: 统计可转让产品
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-11 下午4:37:45
     */
    public long getCanTransferCount(CrowdfundingSupportModel model) {
        return this.getCount("selectCanTransferCount", model);
    }
    
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
            CrowdfundingSupportModel model) {
        return this.selectOneMapByField("selectCanTransferDetail", model);
    }
    /**
     * Description: 通过订单号更新
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-27 上午11:34:25
     */
    public void updateByOrderNo(CrowdfundingSupportModel model) {
        this.update("updateByOrderNo",model);
    }
    /**
     * Description: 通过意向金尾款订单号查询
     *
     * @param 
     * @return CrowdfundingSupportModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-27 下午12:42:48
     */
    public CrowdfundingSupportModel getByIntentionNo(String intentionNo) {
        return this.selectOneByField("selectByIntentionNo", intentionNo);
    }
    
    /**
     * 
     * Description: 查询投资信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin
     */
    public Map<String, Object> getSupportInfo(String orderId) {
        return this.selectOneMapByField("selectSupportInfo", orderId);
    }
    /**
     * Description: 获取投资第二日待收益投资
     *
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-31 上午10:58:25
     */
    public List<CrowdfundingSupportModel> getDailyIncomeSupportList() {
        return this.selectByField("selectDailyIncomeSupportList", "");
    }
    /**
     * Description: 统计个人中心资产
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-9-1 上午11:17:09
     */
    public Map<String, Object> getCountInvestAll(String userId) {
        return this.selectOneMapByField("selectCountInvestAll", userId);
    }
    public List<Map<String,Object>> selectUserSupportByLoanNo(String loanNo){
        return this.selectMapByFields("selectUserSupportByLoanNo", loanNo);
    }
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
    public List<Map<String,Object>>selectDailyIncomeData(Map<String,Object>param){
        return this.selectMapByFields("selectDailyIncomeData", param);
    }
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
    public long selectIntentionEndIsSucess(String orderId){
        return this.getCount("selectIntentionEndIsSucess", orderId);
    }
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
    public long selectPayIntentionIsSucess(String orderId){
        return this.getCount("selectPayIntentionIsSucess", orderId);
    }
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
    public long selectPayIsSuccess(String orderId){
        return this.getCount("selectPayIsSuccess", orderId);
    }
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
    public List<CrowdfundingSupportModel> selectIntentionsByLoanNo(String loanNo){
        //return this.getCount("selectIsIntentionCount", loanNo);
        return this.selectByField("selectIntentionsByLoanNo", loanNo);
    }
    
    /**
     * 查询项目是否还有未完成尾款支付的投资记录
     * Description: 
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * Create Date: 2016-11-22 下午2:02:47
     */
    public List<CrowdfundingSupportModel> selectNoComplateListByLoanNo(String loanNo){
        return this.selectByField("selectNoComplateListByLoanNo",loanNo);
    }
    
    /**
     * 查询已过期但未支付尾款的支持记录
     * Description: 
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * Create Date: 2016-11-23 上午11:23:45
     */
    public List<CrowdfundingSupportModel>selectOverDueNoComplateList(CrowdfundingSupportModel model){
        return this.selectByField("selectOverDueNoComplateList",model);
    }
}
