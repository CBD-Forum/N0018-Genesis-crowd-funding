package com.fbd.web.app.message.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.message.model.PrivateLetterModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.message.service.IPrivateLetterService;

@Controller
@Scope("prototype")
@RequestMapping("/letter")
public class PrivateLetterAction extends BaseAction{

	 private static final long serialVersionUID = -6244496133888715272L;
	 
	 @Resource
	 private IPrivateLetterService privateLetterService;
	 
	 
	 	/**
	     * Description: 保存私信
	     *
	     * @param 
	     * @Author 武文斌
	     */
	 
	 
	 @RequestMapping(value = "/savePrivateLetter.html", method = RequestMethod.POST)
	    public @ResponseBody Map<String,Object> savePrivateLetter(HttpServletRequest request,PrivateLetterModel model){
	        Map<String,Object> resultMap = new HashMap<String,Object>();
	        try{
	            String userId = this.getUserId(request);
	            model.setSendUser(userId);
	            privateLetterService.savePrivateLetter(model);
	            resultMap.put(SUCCESS, true);
	       }catch(Exception e){
	           e.printStackTrace();
	           resultMap.put(SUCCESS, false);
	       }
	       return resultMap;
	    }
	 
	 /**
	     * Description: 分页查询私信
	     *
	     * @param 
	     * @return SearchResult<PrivateLetterModel>
	     * @throws 
	     * @Author wuwenbin
	     * Create Date: 2015-2-4 下午12:20:04
	     */
	    @RequestMapping(value = "/getPrivateLetterList.html", method = RequestMethod.POST)
	    public @ResponseBody Map<String,Object> getMessageList(HttpServletRequest request,PrivateLetterModel model){
	        Map<String,Object> resultMap = new HashMap<String,Object>();
	        try{
	            String userId = this.getUserId(request);
	            model.setReceiveUser(userId);
	            SearchResult<Map<String,Object>> result = privateLetterService.getPageList(model);
	            resultMap.put(SUCCESS, true);
	            resultMap.put(MSG, result);
	       }catch(Exception e){
	           e.printStackTrace();
	           resultMap.put(SUCCESS, false);
	       }
	       return resultMap;
	    }
	 
}
