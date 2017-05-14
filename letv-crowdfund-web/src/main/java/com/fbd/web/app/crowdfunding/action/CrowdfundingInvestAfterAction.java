package com.fbd.web.app.crowdfunding.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.crowdfunding.model.CrowdfundingInvestAfterModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingInvestAfterService;


@Controller
@Scope("prototype")
@RequestMapping("/crowdfundingInvestAfter")
public class CrowdfundingInvestAfterAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private ICrowdfundingInvestAfterService crowdfundingInvestAfterService;
	
	
	
	
	/**
	 * 查询列表
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/selectPageList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<CrowdfundingInvestAfterModel> selectPageList(HttpServletRequest request,CrowdfundingInvestAfterModel model) {
    	SearchResult<CrowdfundingInvestAfterModel> pageList = new SearchResult<CrowdfundingInvestAfterModel>();
    	try{
            pageList = this.crowdfundingInvestAfterService.selectPageList(model);
        }catch(Exception e){
        	e.printStackTrace();
        }
        return pageList;
    }
    
    /**
     * 添加 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveInvestAfter.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveFounder(HttpServletRequest request,CrowdfundingInvestAfterModel model) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		this.crowdfundingInvestAfterService.saveInvestAfter(model);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
    
    /**
     * 删除
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteInvestAfter.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> deleteInvestAfter(HttpServletRequest request,String id) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		this.crowdfundingInvestAfterService.deleteInvestAfter(id);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
    
    
    /**
     * 查询详情
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectById.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> selectById(HttpServletRequest request,String id) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		CrowdfundingInvestAfterModel model = this.crowdfundingInvestAfterService.selectById(id);
    		result.put(SUCCESS,true);
    		result.put(MSG,model);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
}
