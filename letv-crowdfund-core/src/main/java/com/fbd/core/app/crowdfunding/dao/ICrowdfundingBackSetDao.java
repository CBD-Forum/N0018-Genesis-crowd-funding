package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹回报设置
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface ICrowdfundingBackSetDao extends BaseDao<CrowdfundingBackSetModel>{
    
    /**
     * 
     * Description: 查询回报设置
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-28 上午11:02:03
     */
    public List<Map<String,Object>> getList(CrowdfundingBackSetModel model);
    
    public CrowdfundingBackSetModel getByLoanNoAndSetNo(String loanNo,String backNo);
    /**
     * 
     * Description: 删除回报设置
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-17 下午6:01:04
     */
    public void deleteByloanNo(String loanNo);
    
    public CrowdfundingBackSetModel getByLoanNo(String loanNo);
    /**
     * 
     * Description: 根据项目编号查询出有奖的回报
     *
     * @param 
     * @return CrowdfundingBackSetModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-27 下午5:17:14
     */
    public CrowdfundingBackSetModel queryIsPrizeDrawFlag(String loanNo);
}