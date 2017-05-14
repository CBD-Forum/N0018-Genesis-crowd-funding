package com.fbd.admin.app.crowdFund.action;

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

import com.fbd.core.app.crowdfunding.model.CrowdfundingOperateDataModel;
import com.fbd.core.app.crowdfunding.service.ICrowdfundingOperateDataService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;

@Controller
@Scope("prototype")
@RequestMapping("/crowdfundingOperateData")
public class CrowdfundingOperateDataAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Resource
	private ICrowdfundingOperateDataService crowdfundingOperateDataService;

	/**
	 * 查询运营数据列表
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/selectPageList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<CrowdfundingOperateDataModel> selectPageList(HttpServletRequest request,CrowdfundingOperateDataModel model) {
         SearchResult<CrowdfundingOperateDataModel> pageList = new SearchResult<CrowdfundingOperateDataModel>();
         if(StringUtils.isEmpty(model.getLoanNo())){
        	 pageList.setTotal(0);
        	 return pageList;
         }
         pageList = this.crowdfundingOperateDataService.selectPageList(model);
        return pageList;
    }
    
    /**
     * 添加运营数据
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveOperateData.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveFounder(HttpServletRequest request,CrowdfundingOperateDataModel model) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		if(StringUtils.isEmpty(model.getLoanNo())){
    			result.put(SUCCESS,false);
    			result.put(MSG,"项目编号不能为空");
    			return result;
            }
    		this.crowdfundingOperateDataService.saveOperateData(model);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }
    
    
    @RequestMapping(value = "/deleteOperateData.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> deleteOperateData(HttpServletRequest request,String id) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		this.crowdfundingOperateDataService.deleteOperateData(id);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    		e.printStackTrace();
    	}
    	return result;
    }

}
