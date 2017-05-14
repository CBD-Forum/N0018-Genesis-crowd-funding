/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserAuthAction.java 
 *
 * Created: [2015-5-29 下午6:46:57] by haolingfeng
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
 * ProjectName: crowdfund-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.user.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.crowdFund.service.ICrowdfundInvestService;
import com.fbd.admin.app.user.service.IUserService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
 * 
 * Description: 投资人认证
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/userAuth")
public class UserAuthAction extends BaseAction{
    @Resource
    private ICrowdfundInvestService crowdfundInvestService;
    @Resource
    public IUserService userService;
    
    /**
     * 
     * Description: 查询投资人申请认证列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午6:45:22
     */
    @RequestMapping(value = "/getAuthApplyList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getAuthApplyList(HttpServletRequest request,CrowdfundUserStuffModel model){
           model.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.auditing);
           model.setAuthType("investor");  //投资人认证
           return this.crowdfundInvestService.getApplyAuthList(model);
    }
    
    /**
     * 
     * Description: 查询领投人申请领投列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午6:45:22
     */
    @RequestMapping(value = "/getLeadApplyList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getLeadApplyList(HttpServletRequest request,CrowdfundUserStuffModel model){
    	  model.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.auditing);
          model.setAuthType("leadInvestor");  //领投人认证
          return this.crowdfundInvestService.getApplyAuthList(model);
    }
    
    /**
     * 
     * Description: 查询领头机构申请列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午6:45:22
     */
    @RequestMapping(value = "/getOrgLeadApplyList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getOrgLeadApplyList(HttpServletRequest request,CrowdfundUserStuffModel model){
//          model.setOrgLendAuthState(CrowdfundCoreConstants.crowdFundAuditState.auditing);
    	
    	  model.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.auditing);
          model.setAuthType("orgLeadInvestor");  //领投机构认证
          return this.crowdfundInvestService.getApplyAuthList(model);
    }
    
    /**
     * 
     * Description: 查询跟投机构申请列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午6:45:22
     */
    @RequestMapping(value = "/getOrgInvestApplyList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getOrgInvestApplyList(HttpServletRequest request,CrowdfundUserStuffModel model){
//          model.setOrgInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.auditing);
	  	  model.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.auditing);
	      model.setAuthType("orgInvestor");  //跟投机构
          return this.crowdfundInvestService.getApplyAuthList(model);
    }
    
    /**
     * 
     * Description:投资人认证审核 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-31 下午7:39:26
     */
    @RequestMapping(value = "/auditInvestorAuth.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> auditInvestorAuth(CrowdfundUserStuffModel model){
        String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            model.setInvestAuthor(operator);
            this.crowdfundInvestService.updateInvestorAuthState(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "审核投资人认证失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:领投人认证审核 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-31 下午7:39:26
     */
    @RequestMapping(value = "/auditLeadorAuth.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> auditLeadorAuth(CrowdfundUserStuffModel model){
        String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            model.setLeadAuthor(operator);
            this.crowdfundInvestService.updateLeadAuthState(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "审核领投人认证失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:机构领投人认证审核 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin	
     */
    @RequestMapping(value = "/auditOrgLeadorAuth.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> auditOrgLeadorAuth(CrowdfundUserStuffModel model){
    	String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
        	model.setLeadAuthor(operator);
            this.crowdfundInvestService.auditOrgLeadorAuth(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "审核领投机构认证失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:机构跟投投人认证审核 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin	
     */
    @RequestMapping(value = "/auditOrgInvestorAuth.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> auditOrgInvestorAuth(CrowdfundUserStuffModel model){
    	String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
        	model.setLeadAuthor(operator);
            this.crowdfundInvestService.auditOrgInvestorAuth(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "审核跟投机构认证失败");
        }
        return resultMap;
    }
    
    
    
    /**
     * 
     * Description: 查询认证信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/getUserAuthInfoByUserId.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdfundUser(HttpServletRequest request,CrowdfundUserStuffModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put(MSG,this.userService.getCrowdfundUserAuth(model));
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"获取数据失败");
        }
        return resultMap;
    } 
    
}
