/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingAction.java 
 *
 * Created: [2015-3-27 下午7:03:44] by haolingfeng
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

package com.fbd.web.app.crowdfunding.action;

import java.util.HashMap;
import java.util.List;
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

import com.fbd.core.app.crowdfunding.model.CrowdfundTransferDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingInvestTransferService;

/** 
 * Copyright (C) 20152016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹项目 --挂牌
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/crowdfundingInvestTransfer")
public class CrowdfundingInvestTransferAction extends BaseAction{
    
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundingInvestTransferAction.class);

    @Resource
    private ICrowdfundingInvestTransferService crowdfundingInvestTransferService;
    /**
     * 
     * Description:查询股权项目挂牌列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCrowdfundingInvestTransferList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getcrowdfundingTransferList(HttpServletRequest request,CrowdfundingSupportModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	 String userId = this.getUserId(request);
             model.setSupportUser(userId);
            SearchResult<Map<String,Object>> pageList = this.crowdfundingInvestTransferService.getCrowdfundingInvestTransferList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:保存股权项目挂牌
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/saveCrowdFundTransfer.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveCrowdFundTransfer(HttpServletRequest request,CrowdfundTransferModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            this.crowdfundingInvestTransferService.saveCrowdFundTransfer(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"保存挂牌信息失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:查询股权项目挂牌列表(【审核中】【已审核】) 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCrowdfundingTransferAuditList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdfundingTransferAuditList(HttpServletRequest request,CrowdfundTransferModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	 String userId = this.getUserId(request);
             model.setUserId(userId);
            SearchResult<Map<String,Object>> pageList = this.crowdfundingInvestTransferService.getCrowdfundingTransferAuditList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:查询股权项目挂牌列表(【挂牌中】【挂牌完成】) 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCrowdfundingTransferInfoList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdfundingTransferInfoList(HttpServletRequest request,CrowdfundTransferModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	 String userId = this.getUserId(request);
             model.setUserId(userId);
            SearchResult<Map<String,Object>> pageList = this.crowdfundingInvestTransferService.getCrowdfundingTransferInfoList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:是否同意
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateIsAgree.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateIsAgree(HttpServletRequest request,CrowdfundTransferModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingInvestTransferService.updateIsAgree(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"修改挂牌信息失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:查询首页挂牌列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCrowdfundTransferDetailList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdfundTransferDetailList(HttpServletRequest request,CrowdfundTransferModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            SearchResult<Map<String,Object>> pageList = this.crowdfundingInvestTransferService.getCrowdfundTransferDetailList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:查询挂牌详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCrowdfundTransferDetail.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdfundTransferDetail(HttpServletRequest request,CrowdfundTransferModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String,Object> map = this.crowdfundingInvestTransferService.getCrowdfundTransferDetail(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:查询成功购买的用户
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCrowdfundTransferUserPayed.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdfundTransferUserPayed(HttpServletRequest request,CrowdfundTransferDetailModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	List<Map<String , Object>> listMap = this.crowdfundingInvestTransferService.getCrowdfundTransferUserPayed(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,listMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
}
