package com.fbd.admin.app.user.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.admin.app.user.service.IUserService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;

@Controller
@Scope("prototype")
@RequestMapping("/census")
public class CensusAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 167174126359697398L;
    @Resource
    public IUserService userService;

    /**
     * Description: 网站统计-重点客户统计
     * 
     * @param
     * @throws
     */
    @RequestMapping(value = "/getVipCustomerlist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getVipCustomerlist() {
    	Map<String, String> map = this.getRequestParam();
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(map);
        param.putAll(this.getPageParam());
        SearchResult<Map<String, Object>> userModels = userService.getVipCustomerPage(param);
        return userModels;
    }
    
    /**
     * Description: 用户充值统计
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-5-7 上午10:48:56
     */
    @RequestMapping(value = "/getRechargStatisticlist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getRechargStatisticlist() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(this.getRequestParam());
//        param.putAll(this.getPageParam());
        SearchResult<Map<String, Object>> userModels = userService.getRechargStatisticlist(param);
        return userModels;
    }
    
    /**
     * Description: 网站统计-潜在客户统计
     * 
     * @param
     * @throws
     */
    @RequestMapping(value = "/getPotentialCustomerlist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getPotentialCustomerlist() {
    	Map<String, String> map = this.getRequestParam();
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.putAll(map);
    	param.putAll(this.getPageParam());
    	SearchResult<Map<String, Object>> userModels = userService.getPotentialCustomerPage(param);
    	return userModels;
    }
    
    /**
     * Description: 网站统计-按投资年龄范围统计
     * 
     * @param
     * @throws
     */
    @RequestMapping(value = "/getInvestAgelist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getInvestAgelist() {
    	Map<String, String> map = this.getRequestParam();
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.putAll(map);
    	param.putAll(this.getPageParam());
    	SearchResult<Map<String, Object>> userModels = userService.getInvestAge(param);
    	return userModels;
    }

    
}
