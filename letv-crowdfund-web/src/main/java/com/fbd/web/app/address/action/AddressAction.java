/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: AddressAction.java 
 *
 * Created: [2015-3-31 上午11:36:04] by haolingfeng
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

package com.fbd.web.app.address.action;

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
import com.fbd.core.app.address.model.PostAddressModel;
import com.fbd.core.app.address.service.IPostAddressService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.exception.ApplicationException;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 地址维护
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/address")
public class AddressAction extends BaseAction{
    private static final Logger logger = LoggerFactory.getLogger(AddressAction.class);
    @Resource
    private IPostAddressService postAddressService;
    
    /**
     * 
     * Description:保存收件人地址 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-31 上午11:46:13
     */
    @RequestMapping(value = "/savePostAddress.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> savePostAddress(HttpServletRequest request,PostAddressModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            this.postAddressService.savePostAddress(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:查询收件人地址列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-31 上午11:56:26
     */
    @RequestMapping(value = "/getPostAddressList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getPostAddressList(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,this.postAddressService.getList(userId));
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 查询地址详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-31 下午12:02:22
     */
    @RequestMapping(value = "/getPostAddressById.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getPostAddressById(String id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put(MSG,this.postAddressService.selectPostAddressById(id));
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:修改收件人地址 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-31 上午11:46:13
     */
    @RequestMapping(value = "/updatePostAddress.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updatePostAddress(HttpServletRequest request,PostAddressModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            this.postAddressService.updatePostAddress(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:删除收件人地址 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-31 上午11:46:13
     */
    @RequestMapping(value = "/deletePostAddress.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> deletePostAddress(String id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.postAddressService.deletePostAddress(id);
            resultMap.put(SUCCESS, true);
        }catch (ApplicationException e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"删除失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询地址详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-31 下午12:02:22
     */
    @RequestMapping(value = "/getDefaultPostAddress.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getDefaultPostAddress(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	String userId = this.getUserId(request);
            resultMap.put(MSG,this.postAddressService.selectDefaultAddress(userId));
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
}
