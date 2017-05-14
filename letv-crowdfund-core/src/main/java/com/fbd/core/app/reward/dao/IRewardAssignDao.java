package com.fbd.core.app.reward.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 奖励发放明细
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface IRewardAssignDao extends BaseDao<RewardAssignModel>{
    /**
     * 
     * Description: 查询奖励发放明细列表
     *
     * @param 
     * @return List<RewardAssignModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-12 下午2:21:13
     */
    public List<Map<String,Object>> getList(RewardAssignModel model);
    /**
     * 
     * Description: 查询奖励发放明细列表条数
     *
     * @param 
     * @return List<RewardAssignModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-12 下午2:21:13
     */
    public long getListCount(RewardAssignModel model);
    
    /**
     * 
     * Description: 查询项目奖励发放明细列表
     *
     * @param 
     * @return List<RewardAssignModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-12 下午2:21:13
     */
    public List<Map<String,Object>> getLoanRewardDetail(RewardAssignModel model);
    
    /**
    * 
    * Description: 查询项目奖励发放列表
    *
    * @param 
    * @return List<RewardAssignModel>
    * @throws 
    * @Author haolingfeng
    * Create Date: 2015-3-12 下午2:21:13
    */
   public List<Map<String,Object>> getLoanRewardList(RewardAssignModel model);
   
   /**
    * 
    * Description: 查询奖励发放明细列表条数
    *
    * @param 
    * @return List<RewardAssignModel>
    * @throws 
    * @Author haolingfeng
    * Create Date: 2015-3-12 下午2:21:13
    */
   public long getLoanRewardListCount(RewardAssignModel model);
   
   
   /**
    * 根据金额更新奖励发放状态
    * @param id
    * @param status
    * @return
    */
   public int updateRewardAssingStatus(String id, String status);
    /**
     * Description: 查询当前分红次数
     *
     * @param 
     * @return RewardAssignModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-1-25 下午4:42:13
     */

//   public RewardAssignModel selectLastNum(String loanNo);
   
   public long selectLastNum(String loanNo);
    /**
     * Description: 通过项目分红id查询分红列表
     *
     * @param 
     * @return List<RewardAssignModel>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-27 下午4:07:45
     */
    
    public List<RewardAssignModel> getListModelByLoanBonusId(String id);
    
    
    //根据分红编号删除数据
    public int deleteByLoanBonusId(String loanBonusId);
}