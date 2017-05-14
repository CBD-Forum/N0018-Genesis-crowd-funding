/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IRewardService.java 
 *
 * Created: [2015-3-3 下午3:20:46] by haolingfeng
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
 * ProjectName: fbd-admin 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.reward.service;

import java.util.Map;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 奖励发放
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IRewardAssignService {
    
    /**
     * 
     * Description: 分页查询优惠券基本信息
     *
     * @param 
     * @return SearchResult<RewardModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:41:33
     */
    public SearchResult<Map<String,Object>> getPageList(RewardAssignModel model);
    
    /**
     * 
     * Description: 奖励发放
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:45:19
     */
    public void saveInvestRewardAssign(String loanNo,double ratio);
    
    /**
     * 
     * Description: 推荐奖励发放
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:45:19
     */
    public void saveRecommendRewardAssign(String loanNo,double ratio,String recommandType);
    
    /**
     * 
     * Description: 查询项目奖励发放明细
     *
     * @param 
     * @return SearchResult<RewardModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:41:33
     */
    public SearchResult<Map<String,Object>> getLoanRewardDetail(RewardAssignModel model);
    /**
     * 
     * Description: 查询项目奖励发放列表
     *
     * @param 
     * @return SearchResult<RewardModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:41:33
     */
    public SearchResult<Map<String,Object>> getLoanRewardList(RewardAssignModel model);
    
    
}
