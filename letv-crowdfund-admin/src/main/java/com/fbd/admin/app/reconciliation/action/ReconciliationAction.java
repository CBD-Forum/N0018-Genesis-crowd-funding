package com.fbd.admin.app.reconciliation.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.reconciliation.model.ReconciliationRechargeModel;
import com.fbd.core.app.reconciliation.service.IReconciliationService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;


@Controller
@Scope("prototype")
@RequestMapping("/reconciliation")
public class ReconciliationAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1204121119745537853L;
    @Resource
    private IReconciliationService reconciliationService;
    
    /**
     * Description: 分页查询充值对账的结果
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-13 上午11:27:14
     */
    @RequestMapping(value = "/rechargeList.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getRechargePage(){
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(this.getRequestParam());
        param.putAll(this.getPageParam());
        return reconciliationService.getRechargePage(param);
    }
    
    
    /**
     * Description: 分页查询还款对账的结果
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-13 下午12:44:19
     */
    @RequestMapping(value = "/repayList.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getRepayPage(){
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(this.getRequestParam());
        param.putAll(this.getPageParam());
        return reconciliationService.getRepayPage(param);
    }
    
    /**
     * Description: 分页查询自动对账的调度记录
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-13 下午5:24:23
     */
    @RequestMapping(value = "/recordList.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getRecordPage(){
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(this.getRequestParam());
        param.putAll(this.getPageParam());
        return reconciliationService.getRecordPage(param);
    }
    
     
    
}
