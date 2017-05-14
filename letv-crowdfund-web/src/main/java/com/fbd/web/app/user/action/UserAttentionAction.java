/* 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserAttentionAction.java 
 *
 * Created: [2015-6-23 上午10:25:58] by haolingfeng
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

package com.fbd.web.app.user.action;

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
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.user.model.UserAttentionModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingInvestService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
import com.fbd.web.app.user.service.IUserAttentionService;

/** 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 * 
 * Description:用户关注关系 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/userAttention")
public class UserAttentionAction extends BaseAction{
    private static final Logger logger = LoggerFactory.getLogger(UserAttentionAction.class);
    @Resource
    private IUserAttentionService userAttentionService;
    
    @Resource
    private ICrowdfundingService crowdfundingService;
    
    @Resource
    private ICrowdfundingInvestService crowdfundingInvestService;
    
    /**
     * 
     * Description: 查询我是否关注
     * 
     * @param
     * @return String
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午12:10:10
     */
    @RequestMapping(value = "/getIsAttention.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getIsAttention(UserAttentionModel model,HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>(); 
        String result = "0";
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            List<Map<String,Object>> userList = userAttentionService.getAttentionUser(model);
            if(userList.size()>0){
                result = "1";
            }
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "查询失败");
        }
        return resultMap;
    }
    
    

    /**
     * 
     * Description: 关注
     * 
     * @param
     * @return String
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午12:10:10
     */
    @RequestMapping(value = "/attentionUser.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> attentionUser(UserAttentionModel model,HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>(); 
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            userAttentionService.saveAttention(model);
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "关注失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询领投人的关注与我的粉丝
     * 
     * @param
     * @return String
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午12:10:10
     */
    @RequestMapping(value = "/getAttentionUser.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getAttentionUser(UserAttentionModel model,HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>(); 
        try{
            List<Map<String,Object>> userList = userAttentionService.getAttentionUser(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, userList);
        }catch(Exception e){
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "查询失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询领投人的认购项目
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午12:34:33
     */
    @RequestMapping(value = "/getLeaderSupportList.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getLeaderSupportList(HttpServletRequest request,CrowdfundingSupportModel model){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            model.setSort("supportTime");
            model.setOrder("desc");
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getLeaderSupportList(model);
            amap.put(MSG,pageList);
            amap.put(SUCCESS, true);
        }catch(Exception e){
             logger.error(e.getMessage());
            amap.put(SUCCESS, false);
        }
        return amap;
    }
    
    /**
     * 
     * Description:查询领投人的关注列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-10 上午10:13:40
     */
    @RequestMapping(value = "/getLeaderAttentionList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getLeaderAttentionList(HttpServletRequest request,CrowdfundingAttentionModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getAttentionList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
             logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 查询我的个人信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午12:34:23
     */
    @RequestMapping(value = "/getCrowdfundUserDetail.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getUserDetail(String userId){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            Map<String,Object> crowdfundUser = crowdfundingInvestService.getCrowdfundUserInfo(userId);
            amap.put(SUCCESS, true);
            amap.put(MSG,crowdfundUser);
        }catch(Exception e){
            logger.error(e.getMessage());
            amap.put(SUCCESS, false);
        }
        return amap;
    }
}
