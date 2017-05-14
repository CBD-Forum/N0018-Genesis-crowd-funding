/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RechargeAction.java 
 *
 * Created: [2014-12-24 下午6:00:17] by haolingfeng
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
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.recharge.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.recharge.service.IRechargeService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.SpringPropertiesHolder;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:充值列表
 * 
 * @author dongzhongwei
 * @version 1.0
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/recharge")
public class RechargeAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -4803963548470295353L;

    @Resource
    private IRechargeService rechargeService;
    
    
    
    /**
     * Description: 查询用户充值列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/getRechargelist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getRechargelist(RechargeModel recharge){
        SearchResult<Map<String, Object>> rechargePage = rechargeService.getRechargelist(recharge);
        return rechargePage;
    }
    
    
    /**
     * Description: 查询普通用户充值列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 上午11:48:00
     */
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getRechargePage(RechargeModel recharge){
        Map<String, String> map = this.getRequestParam();
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(map);
        param.putAll(this.getPageParam());
        SearchResult<Map<String, Object>> rechargePage = rechargeService.getUserRechargePage(param);
        return rechargePage;
    }
    
    /**
     * Description: 查询平台手续费账户充值
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-30 上午10:16:11
     */
    @RequestMapping(value = "/getMerlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getMerRechargePage(RechargeModel recharge){
        Map<String, String> map = this.getRequestParam();
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(map);
        param.putAll(this.getPageParam());
        SearchResult<Map<String, Object>> rechargePage = rechargeService.getFeeRechargePage(param);
        return rechargePage;
    }
    
    
    /**
     * Description: 审核充值 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/auditState.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> auditPass(RechargeModel recharge){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
    	 try {
    		 if(recharge.getOperatorOpinion()!=null && !"".equals(recharge.getOperatorOpinion())){
    		      recharge.setOperatorAuditTime(DateUtil.dateTime2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
    		      recharge.setOperator( MyRequestContextHolder.getCurrentUser().getEmployeeNo());
    		 }     
    		 else if(recharge.getFinancialOpinion()!=null &&!"".equals(recharge.getFinancialOpinion())){
	   		      recharge.setFinancialAuditTime(DateUtil.dateTime2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
			      recharge.setFinancialOpinion( MyRequestContextHolder.getCurrentUser().getEmployeeNo());
    		 } 
    		 this.rechargeService.updateAuditState(recharge);
             resultMap.put(SUCCESS, true);
             resultMap.put(MSG, "审核成功");

    	 }catch (Exception e) {
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG,"审核失败");
         }
         return resultMap;
    }
    
    
    @RequestMapping(value = "/selectRechargeState.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> selectRechargeState(String orderId){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
    	 try {
    		 RechargeModel recharge = this.rechargeService.selectByOrderId(orderId);
             resultMap.put(SUCCESS, true);
             resultMap.put(MSG,FbdCoreConstants.rachargeState.recharge_success.getValue().equals(recharge.getState())?true:false);
             
    	 }catch (Exception e) {
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG,"审核失败");
         }
         return resultMap;
    }
    

}
