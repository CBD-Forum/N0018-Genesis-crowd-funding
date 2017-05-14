package com.fbd.web.app.crowdfunding.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.crowdfunding.dao.ICrowdfundingFounderBusinessDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingFounderEducationsDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingFounderWorksDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderBusinessModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderEducationsModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderWorksModel;
import com.fbd.core.app.crowdfunding.service.ICrowdFundFounderService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;

@Controller
@Scope("prototype")
@RequestMapping("/crowdfundingFounder")
public class CrowdfundingFounderAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private ICrowdFundFounderService crowdFundFounderService;
    @Resource
    private ICrowdfundingFounderBusinessDao crowdfundingFounderBusinessDao;
    @Resource
    private ICrowdfundingFounderEducationsDao crowdfundingFounderEducationsDao;
    @Resource
    private ICrowdfundingFounderWorksDao crowdfundingFounderWorksDao;
    
    
	/**
	 * 查询创始人信息
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/selectFounderById.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> selectFounderById(HttpServletRequest request,String id) {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
    		CrowdfundingFounderModel model = this.crowdFundFounderService.selectFounderInfoById(id);
    		
    		
    		
    		
    		resultMap.put(SUCCESS, true);
    		resultMap.put("info", model);
    	}catch(Exception e){
    		resultMap.put(SUCCESS, false);
    	} 
        return resultMap;
    }
        
    
    
	/**
	 * 查询创始人列表
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/selectFounderPageList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<CrowdfundingFounderModel> selectFounderPageList(HttpServletRequest request,CrowdfundingFounderModel model) {
    	SearchResult<CrowdfundingFounderModel> pageList = new SearchResult<CrowdfundingFounderModel>();
         if(StringUtils.isEmpty(model.getLoanNo())){
        	 pageList.setTotal(0);
        	 return pageList;
         }
         pageList = this.crowdFundFounderService.selectFounderPageList(model);
        return pageList;
    }
    
    /**
     * 添加创始人
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveFounder.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveFounder(HttpServletRequest request,CrowdfundingFounderModel model) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		if(StringUtils.isEmpty(model.getLoanNo())){
    			result.put(SUCCESS,false);
    			result.put(MSG,"项目编号不能为空");
    			return result;
            }
    		this.crowdFundFounderService.saveFounder(model);
    		result.put(SUCCESS,true);
    		result.put("id",model.getId());
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
    
    
    @RequestMapping(value = "/deleteFounder.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> deleteFounder(HttpServletRequest request,String id) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		this.crowdFundFounderService.deleteFounder(id);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
	
	
	
	
	/**
	 * 查询创始人工作经历
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/getFounderWorksList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<CrowdfundingFounderWorksModel> getFounderWorksList(HttpServletRequest request,CrowdfundingFounderWorksModel model) {
        SearchResult<CrowdfundingFounderWorksModel> pageList =
        		  new SearchResult<CrowdfundingFounderWorksModel>();
        pageList.setRows(this.crowdFundFounderService.selectFounderWorksList(model));		
        return pageList;
    }
    
    /**
     *保存工作经历
     */
    @RequestMapping(value = "/saveFounderWorks.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveFounderWorks(HttpServletRequest request,CrowdfundingFounderWorksModel model) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
//    		String userId = this.getUserId(request);
    		this.crowdFundFounderService.saveFounderWorks(model);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
    
    /**
     * 删除工作经历
     */
    @RequestMapping(value = "/deleteFounderWorks.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> deleteFounderWorks(HttpServletRequest request,String id) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
//    		String userId = this.getUserId(request);
    		this.crowdFundFounderService.deleteFounderWorks(id);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
		
	
	/**
	 * 根据ID查询工作经历
	 */
    @RequestMapping(value = "/selectFounderWorksById.html", method = RequestMethod.POST)
    public @ResponseBody CrowdfundingFounderWorksModel selectFounderWorksById(HttpServletRequest request,String id){
    	try{
//        	String userId = this.getUserId(request);
        	return this.crowdfundingFounderWorksDao.selectByPrimaryKey(id);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return null;
    }
    
    /**
     * 修改工作经历
     */
    @RequestMapping(value = "/updateFounderWorks.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> updateFounderWorks(HttpServletRequest request,CrowdfundingFounderWorksModel model) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
//    		String userId = this.getUserId(request);
    		this.crowdfundingFounderWorksDao.update(model);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }	
    
    //=========================================创业经历=================================
    
	/**
	 * 查询创始人创业经历
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/getFounderBusinessList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<CrowdfundingFounderBusinessModel> getFounderBusinessList(CrowdfundingFounderBusinessModel model) {
      
    	SearchResult<CrowdfundingFounderBusinessModel> pageList =
        		  new SearchResult<CrowdfundingFounderBusinessModel>();
        pageList.setRows(this.crowdFundFounderService.selectFounderBusinessList(model));		
        return pageList;
    }
    
    /**
     *保存创业经历
     */
    @RequestMapping(value = "/saveFounderBusiness.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveFounderBusiness(HttpServletRequest request,CrowdfundingFounderBusinessModel model) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		System.out.println("model.getFounderId():"+model.getFounderId());
    		this.crowdFundFounderService.saveFounderBusiness(model);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
    
    /**
     * 删除创业经历
     */
    @RequestMapping(value = "/deleteFounderBusiness.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> deleteFounderBusiness(HttpServletRequest request,String id) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		this.crowdFundFounderService.deleteFounderBusiness(id);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
		
	
	/**
	 * 根据ID查询创业经历
	 */
    @RequestMapping(value = "/selectFounderBusinessById.html", method = RequestMethod.POST)
    public @ResponseBody CrowdfundingFounderBusinessModel selectFounderBusinessById(String id){
    	return this.crowdfundingFounderBusinessDao.selectByPrimaryKey(id);
    }
    
    /**
     * 修改创业经历
     */
    @RequestMapping(value = "/updateFounderBusiness.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> updateFounderBusiness(HttpServletRequest request,CrowdfundingFounderBusinessModel model) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		this.crowdfundingFounderBusinessDao.update(model);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
    
    //====================================教育经历=================================
    
	/**
	 * 查询创始人教育经历
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/getFounderEducationsList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<CrowdfundingFounderEducationsModel> getFounderEducationList(CrowdfundingFounderEducationsModel model) {
        SearchResult<CrowdfundingFounderEducationsModel> pageList =
        		  new SearchResult<CrowdfundingFounderEducationsModel>();
        pageList.setRows(this.crowdFundFounderService.selectFounderEducationsList(model));		
        return pageList;
    }
    
    /**
     *保存教育经历
     */
    @RequestMapping(value = "/saveFounderEducations.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveFounderEducations(HttpServletRequest request,CrowdfundingFounderEducationsModel model) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		this.crowdFundFounderService.saveFounderEducations(model);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
    
    /**
     * 删除教育经历
     */
    @RequestMapping(value = "/deleteFounderEducations.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> deleteFounderEducations(HttpServletRequest request,String id) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		this.crowdFundFounderService.deleteFounderEducations(id);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
		
	
	/**
	 * 根据ID查询教育经历
	 */
    @RequestMapping(value = "/selectFounderEducationsById.html", method = RequestMethod.POST)
    public @ResponseBody CrowdfundingFounderEducationsModel selectFounderEducationsById(String id){
    	return this.crowdfundingFounderEducationsDao.selectByPrimaryKey(id);
    }
    
    /**
     * 修改教育经历
     */
    @RequestMapping(value = "/updateFounderEducations.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> updateFounderEducations(HttpServletRequest request,CrowdfundingFounderEducationsModel model) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		this.crowdfundingFounderEducationsDao.update(model);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }   
    
	/**
	 * 查询创始人信息
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/selectFounderInfo.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>selectFounderInfo(HttpServletRequest request,String id) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
//        	String userId = this.getUserId(request);
        	CrowdfundingFounderModel model = crowdFundFounderService.selectFounderInfoById(id);
        	resultMap.put(SUCCESS,true);
        	resultMap.put(MSG,model);
        }catch(Exception e){
        	resultMap.put(SUCCESS,false);
        	resultMap.put(MSG,"获取创始人信息失败");
        }
    	return resultMap;
    }
    
}
