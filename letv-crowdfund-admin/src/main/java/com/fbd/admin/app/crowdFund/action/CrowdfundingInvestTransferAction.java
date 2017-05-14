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

package com.fbd.admin.app.crowdFund.action;

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

import com.fbd.admin.app.crowdFund.service.ICrowdfundingInvestTransferService;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;

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
     * Description:跟新股权项目挂牌
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateCrowdFundTransfer.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateCrowdFundTransfer(HttpServletRequest request,CrowdfundTransferModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingInvestTransferService.updateCrowdFundTransfer(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"跟新挂牌信息失败");
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
    SearchResult<Map<String,Object>> getCrowdfundingTransferAuditList(HttpServletRequest request,CrowdfundTransferModel model) {
         SearchResult<Map<String,Object>> pageList = this.crowdfundingInvestTransferService.getCrowdfundingTransferAuditList(model);
        return pageList;
    }
    
    /**
     * 
     * Description:审核转让
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/auditTransfer.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> auditTransfer(CrowdfundTransferModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            this.crowdfundingInvestTransferService.updateTransfer(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "挂牌审核失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:查询挂牌详情
     *
     * @param 
     * @return CrowdfundTransferModel
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCrowdfundingTransferDetial.html", method = RequestMethod.POST)
    public @ResponseBody
    CrowdfundTransferModel getCrowdfundingTransferDetial(String transferNo) {
    	CrowdfundTransferModel model = this.crowdfundingInvestTransferService.getCrowdfundingTransferDetial(transferNo);
        return model;
    }
    
    /**
     * 
     * Description:查询每个项目支持份数列表
     *
     * @param 
     * @return SearchResult <Map<String,Object>>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getSupportPartsDetailList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getSupportPartsDetailList(HttpServletRequest request,CrowdfundingSupportModel model) {
         SearchResult<Map<String,Object>> pageList = this.crowdfundingInvestTransferService.getSupportPartsDetailList(model);
        return pageList;
    }
    
    
    /**
     * 查询可转让份数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/getCanTransferParts.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getCanTransferParts(String orderNo){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            long canTransferParts = this.crowdfundingInvestTransferService.getCanTransferParts(orderNo);
            resultMap.put(MSG, canTransferParts);
            resultMap.put(SUCCESS, true);
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "查询可转让份数失败");
        }
        return resultMap;
    }
    
}
