package com.fbd.web.app.crowdfunding.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.core.web.MyRequestContextHolder;
import com.fbd.web.app.crowdfunding.service.CrowdfundProgressService;
@Controller
@Scope("prototype")
@RequestMapping("/crowdfundProgress")
public class CrowdfundProgressAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private CrowdfundProgressService crowdfundProgressService;
	
	 
    /**
     * 
     * Description: 查询项目进展
     * @param 
     * @return Map<String,Object>
     * Create Date: 2015-3-28 下午3:43:10
     */
    @RequestMapping(value = "/getProgessList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getProgessList(CrowdfundingProgressModel model) {
           return this.crowdfundProgressService.getProgessPageList(model);
    }
    
    
	   /**
  * 
  * Description: 项目进度保存
  *
  * @param 
  * @return Map<String,Object>
  * @throws 
  * @Author haolingfeng
  * Create Date: 2015-6-2 下午7:44:32
  */
	 @RequestMapping(value = "/save.html", method = RequestMethod.POST)
	 public @ResponseBody  Map<String, Object> saveModel(CrowdfundingProgressModel model,String loanType,HttpServletRequest request){
	     Map<String, Object> resultMap = new HashMap<String, Object>();
	     try { 
	    	 
	    	String userId = this.getUserId(request);
	         
	     	crowdfundProgressService.save(model,userId);
	     	resultMap.put(SUCCESS, true);
	     } catch (ApplicationException e) {
	         e.printStackTrace();
	         resultMap.put(SUCCESS, false);
	         resultMap.put(MSG, e.getMessage());
	     }catch (Exception e) {
	         e.printStackTrace();
	         resultMap.put(SUCCESS, false);
	         resultMap.put(MSG, "保存失败");
	     }
	     return resultMap;
	 }	
     
    
    
}
