package com.fbd.web.app.crowdfunding.service;

import java.util.Map;

import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.common.model.SearchResult;

public interface CrowdfundProgressService {
	
	
	
	
    public SearchResult<CrowdfundingProgressModel> getProgessList(CrowdfundingProgressModel model);
	
	
	/**
	 * 分页查询
	 */
    public SearchResult<Map<String,Object>> getProgessPageList(CrowdfundingProgressModel model);
	
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
	 * 保存项目进展
	 * @param model
	 */
	public void save(CrowdfundingProgressModel model,String opeartor);
	 
}
