/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RewardAssignDaoImpl.java 
 *
 * Created: [2015-3-12 上午10:31:40] by haolingfeng
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

package com.fbd.core.app.reward.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.reward.dao.IRewardAssignDao;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.utils.StringUtil;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 奖励发放明细
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("rewardAssignDao")
public class RewardAssignDaoImpl extends BaseDaoImpl<RewardAssignModel>
implements IRewardAssignDao{
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
    public List<Map<String,Object>> getList(RewardAssignModel model){
        return this.selectMapByFields("selectList", model);
    }
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
    public long getListCount(RewardAssignModel model){
        return this.getCount("selectListCount", model);
    }
    
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
    public List<Map<String,Object>> getLoanRewardDetail(RewardAssignModel model){
        return this.selectMapByFields("getLoanRewardDetail", model);
    }
    
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
    public List<Map<String,Object>> getLoanRewardList(RewardAssignModel model){
        return this.selectMapByFields("getLoanRewardList", model);
    }
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
    public long getLoanRewardListCount(RewardAssignModel model){
        return this.getCount("getLoanRewardListCount", model);
    }
    
    /**
     * 根据金额更新奖励发放状态
     * @param id
     * @param status
     * @return
     */
    public int updateRewardAssingStatus(String id, String status){
    	if (StringUtil.isEmpty(id) || StringUtil.isEmpty(status)){
    		return -1;
    	}
    	RewardAssignModel r = new RewardAssignModel();
    	r.setId(id);
//    	r.setStatus(status);
    	return this.updateBySelective(r);
    }
    /**
     * Description: 查询当前分红次数
     *
     * @param 
     * @return RewardAssignModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-1-25 下午4:42:13
     */
//    public RewardAssignModel selectLastNum(String loanNo) {
//        // TODO Auto-generated method stub
//        return this.selectOneByField("selectLastNum", loanNo);
//    }
    public long selectLastNum(String loanNo){
        return this.getCount("selectLastNum", loanNo);
    }
    /**
     * Description: 通过项目分红id查询分红列表
     *
     * @param 
     * @return List<RewardAssignModel>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-27 下午4:07:45
     */
    public List<RewardAssignModel> getListModelByLoanBonusId(String loanBonusId) {
        return this.selectByField("selectListModelByLoanBonusId", loanBonusId);
    }
    
    //根据分红编号删除数据
    public int deleteByLoanBonusId(String loanBonusId){
        return this.deleteByField("deleteByLoanBonusId", loanBonusId);
    }
}
