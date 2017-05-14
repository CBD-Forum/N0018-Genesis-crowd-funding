package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:众筹项目 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface ICrowdfundingDao extends BaseDao<CrowdfundingModel>{
    
    public List<Map<String,Object>> getList(CrowdfundingModel model);
    
    public long getCount(CrowdfundingModel model);
    
    public CrowdfundingModel getByloanNo(String loanNo);
    
    public CrowdfundingModel getByBlockChainAddress(String blockChainAddress);
    
    public List<Map<String,Object>> getUserCapitalList(CrowdfundingModel model);
    
    public long getUserCapitalListCount(CrowdfundingModel model);
    
    /**
     * 
     * Description:根据项目编号删除项目 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-17 下午5:58:20
     */
    public void deleteByloanNo(String loanNo);
    
    public Map<String,Object> getLoanDetail(String loanNo);
    
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
     * Description: 查询已投资总额
     *
     * @param 
     * @return double
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-2-16 上午10:19:01
     */
    
    public double getSumSupportAmt();

    /**
     * Description: 统计项目总数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-2-16 上午10:26:24
     */
    
    public long getCountLoan();

    /**
     * Description: 查询可抽奖的产品项目
     *
     * @param 
     * @return CrowdfundingModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-10 下午6:14:12
     */
    
    public CrowdfundingModel getPrizeLoan(String loanNo);
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
    public List<Map<String, Object>> pushLoanFail(Map<String, Object> param);
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
     * 查询用户项目统计
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * Create Date: 2016-8-31 下午7:33:49
     */
    public Map<String, Object>  selectUserProjectInfo(String userId);
    
    public long updateByLoanNo(CrowdfundingModel model);
    /**
     * 
     * Description: 募集转账
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月17日 上午11:26:34
     */
    public List<Map<String,Object>>raiseTransfer(Map<String,Object>param);
    /**
     * 项目结算
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月17日 下午4:06:55
     */
    public List<Map<String,Object>>selectJsData(Map<String,Object>param);
    
    /**
     * 查询所有项目
     * Description: 
     *
     * @param 
     * @return List<CrowdfundingModel>
     * @throws 
     * Create Date: 2016-11-3 上午10:32:37
     */
    public List<CrowdfundingModel> selectByAll(CrowdfundingModel model);
    
    /**
     * 查询进行中的项目数量(预热和筹款中)
     * Description: 
     * @param 
     * @return long
     * @throws 
     * Create Date: 2016-12-14 下午5:20:48
     */
    public long getFundingLoanCount();

    /**
     * 查询成功的项目数量(已放款和已结束)
     * Description: 
     * @param 
     * @return long
     * @throws 
     * Create Date: 2016-12-14 下午5:20:48
     */
    public long getSuccessLoanCount();
    /**
     * 查询成功筹款的金额
     * Description: 
     *
     * @param 
     * @return Double
     * @throws 
     * Create Date: 2016-12-14 下午5:22:34
     */
    public Double getSuccessLoanAmt();
    
}