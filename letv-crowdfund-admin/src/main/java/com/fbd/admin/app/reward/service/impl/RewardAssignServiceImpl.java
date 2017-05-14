package com.fbd.admin.app.reward.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.admin.app.reward.service.IRewardAssignService;
import com.fbd.core.app.reward.dao.IRewardAssignDao;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.util.DateUtil;

@Service("rewardAssignService")
public class RewardAssignServiceImpl implements IRewardAssignService {

	@Resource
    private IRewardAssignDao rewardAssignDao;
	
	
	public SearchResult<Map<String, Object>> getPageList(RewardAssignModel model) {
		SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        if(model.getAssignStartTime() != null){
            model.setAssignStartTime(DateUtil.getDayMin(model.getAssignStartTime()));
        }
        if(model.getAssignEndTime() != null){
            model.setAssignEndTime(DateUtil.getDayMax(model.getAssignEndTime()));
        }
        result.setRows(this.rewardAssignDao.getList(model));
        result.setTotal(this.rewardAssignDao.getListCount(model));
        return result;
	}

	
	/**
     * 
     * Description: 分页查询奖励发放明细(导出)
     *
     * @param 
     * @return SearchResult<RewardModel>
     * @throws 
     * @Author wuwenbin
     */
    public SearchResult<Map<String,Object>> getPageListImport(Map<String, Object> paramMap){
        return rewardAssignDao.getPage("selectListCount", "selectList", paramMap);
    }
	public void saveInvestRewardAssign(String loanNo, double ratio) {
		// TODO Auto-generated method stub

	}

	public void saveRecommendRewardAssign(String loanNo, double ratio,
			String recommandType) {
		// TODO Auto-generated method stub

	}

	public SearchResult<Map<String, Object>> getLoanRewardDetail(
			RewardAssignModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	public SearchResult<Map<String, Object>> getLoanRewardList(
			RewardAssignModel model) {
		// TODO Auto-generated method stub
		return null;
	}

}
