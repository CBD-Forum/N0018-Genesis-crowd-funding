package com.fbd.admin.app.crowdFund.service;

import java.util.Map;

import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.common.model.SearchResult;

public interface CrowdfundProgressService {
	
	
	
	
    public SearchResult<Map<String,Object>> getProgessList(CrowdfundingProgressModel model);
	
	
	/**
	 * 保存项目进展
	 * @param model
	 */
	public void save(CrowdfundingProgressModel model,String opeartor);
	
	
	/**
	 * 编辑项目进度
	 * @param model
	 */
	public void updateProgress(CrowdfundingProgressModel model);
	
	
	/**
	 * 查询列表
	 * @param model
	 * @return
	 */
	public Map<String,Object>selectList(CrowdfundingProgressModel model);
	
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	public CrowdfundingProgressModel selectDetailById(String id);
	
	
	
	
	
	/**
	 * 删除进展
	 * @param id
	 */
	public void deleteprogress(String id);

}
