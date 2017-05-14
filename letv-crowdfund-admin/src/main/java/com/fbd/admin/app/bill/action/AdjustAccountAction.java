/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserBillAction.java 
 *
 * Created: [2015-1-8 下午2:06:43] by haolingfeng
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

package com.fbd.admin.app.bill.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.bill.service.IAdjustAccountService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.bill.model.AdjustAccountModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:调账
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/adjustAccount")
public class AdjustAccountAction extends BaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = -947794267693157727L;
    @Resource
    private IAdjustAccountService adjustAccountService;

    /**
     * 
     * Description: 查询调账申请
     * 
     * @param
     * @return SearchResult<AdjustAccountModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午2:07:41
     */
    @RequestMapping(value = "/getPageList.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getUserBill() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(this.getRequestParam());
        param.putAll(this.getPageParam());
        SearchResult<Map<String, Object>> data =  this.adjustAccountService.getPageList(param);
        return data;
    }
    /**
     * 
     * Description: 提交调账申请
     * 
     * @param
     * @return SearchResult<AdjustAccountModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午2:07:41
     */
    @RequestMapping(value = "/submitAdjustApply.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> submitAdjustApply(AdjustAccountModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
            this.adjustAccountService.addAdjustAccountApply(operator, model);
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 审核通过调账申请
     * 
     * @param
     * @return SearchResult<AdjustAccountModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午2:07:41
     */
    @RequestMapping(value = "/passAdjustApply.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> passAdjustApply(AdjustAccountModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            model.setAuditor(operator);
            model.setAuditState(FbdCoreConstants.adjustAccountState.passed);
            this.adjustAccountService.saveAdjustAudit(model);
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 审核拒绝调账申请
     * 
     * @param
     * @return SearchResult<AdjustAccountModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午2:07:41
     */
    @RequestMapping(value = "/refuseAdjustApply.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> refuseAdjustApply(AdjustAccountModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            model.setAuditor(operator);
            model.setAuditState(FbdCoreConstants.adjustAccountState.refused);
            this.adjustAccountService.saveAdjustAudit(model);
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "审核操作失败！");
        }
        return resultMap;
    }

}
