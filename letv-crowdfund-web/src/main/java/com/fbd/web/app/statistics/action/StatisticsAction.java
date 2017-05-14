package com.fbd.web.app.statistics.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.web.app.statistics.service.IStatisticsService;


@Controller
@Scope("prototype")
@RequestMapping("/statistics")
public class StatisticsAction extends BaseAction {
	
	 private static final Logger logger = LoggerFactory.getLogger(StatisticsAction.class);
	 
	 private static final long serialVersionUID = 9089297921676327771L;
	    
	 @Resource
	 private IStatisticsService statisticsService;
	
    /**
     * 
     * Description:网站统计
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午12:13:10
     */
    @RequestMapping(value = "/getWebStatistics.html", method = RequestMethod.POST)  
    public @ResponseBody Map<String, Object>  updateSafetyEyeFlag (HttpServletRequest request,UserModel user){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
        	resultMap = this.statisticsService.getWebStatistics();
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           resultMap.put(SUCCESS, false);
        }
        return resultMap;
    } 

}
