/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserCenter.java 
 *
 * Created: [2015-4-2 上午11:22:20] by haolingfeng
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
 * ProjectName: rain-web 
 * 
 * Description: 
 * 
 * ==========================================================*/
package com.fbd.web.app.platformStats;

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

import com.fbd.core.base.BaseAction;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
import com.fbd.web.app.userCenter.action.CrowdfundUserCenterAction;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹平台统计
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/stats")
public class StatsAction extends BaseAction{
	
	private static final Logger logger = LoggerFactory.getLogger(StatsAction.class);
	
	@Resource
    private ICrowdfundingService crowdfundingService;
	   
	/**
	 * 平台统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getStatsData.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getUserDetail(){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            Map<String,Object> statsData = crowdfundingService.getStatsData();
            amap.put(SUCCESS, true);
            amap.put(MSG,statsData);
        }catch(Exception e){
            logger.error(e.getMessage());
            amap.put(SUCCESS, false);
        }
        return amap;
    }
}
