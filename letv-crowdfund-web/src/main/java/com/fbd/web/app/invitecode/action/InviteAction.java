/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: InviteAction.java 
 *
 * Created: [2015-3-6 上午10:40:36] by haolingfeng
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

package com.fbd.web.app.invitecode.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.invitecode.model.InviteCodeModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.invitecode.service.IInviteCodeService;

/** 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description: 邀请结果
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/invite")
public class InviteAction extends BaseAction{
    @Resource
    private IInviteCodeService inviteCodeService;
    
    /**
     * 
     * Description: 查询邀请结果
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-6 上午10:44:12
     */
    @RequestMapping(value = "/getInviteResult.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getInviteResult(HttpServletRequest request,InviteCodeModel model){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
          //  String inviteUser = this.getUserId(request);
            model.setInviteUser(model.getInviteUser());
            SearchResult<Map<String,Object>> result = this.inviteCodeService.
                    getInviteResult(model);
            amap.put("totalData",result.getTotal());
            amap.put(MSG, result);
            amap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            amap.put(SUCCESS, false);
        }
        return amap;
    } 
    
    
    /**
     * 
     * Description: 查询一级邀请结果
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-6 上午10:44:12
     */
    @RequestMapping(value = "/getDirectInviteResult.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getDirectInviteResult(HttpServletRequest request,InviteCodeModel model){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String inviteUser = this.getUserId(request);
            model.setInviteUser(inviteUser);
            SearchResult<Map<String,Object>> result = this.inviteCodeService.getDirectInviteResult(model);
            amap.put("totalData",this.inviteCodeService.getDirectRecommendData(inviteUser));
            amap.put(MSG, result);
            amap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            amap.put(SUCCESS, false);
        }
        return amap;
    } 
    
}
