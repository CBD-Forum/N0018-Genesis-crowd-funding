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

import com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.crowdfunding.service.ICrowdfundProductTransferService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingInvestTransferService;
import com.fbd.web.app.user.service.IUserService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;

/** 
 * Copyright (C) 20152016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹项目 --产品转让
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/crowdfundProductTransfer")
public class CrowdfundProductTransferAction extends BaseAction{
    
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundProductTransferAction.class);

    @Resource
    private ICrowdfundProductTransferService crowdfundProductTransferService;
    
    @Resource
    private IVerifyCodeService verifyCodeService;
    
    @Resource
    private IUserService userService;
    @Resource
    private ICrowdfundProductTransferDao crowdfundProductTransferDao;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    
    /**
     * 
     * Description:查询可转让产品投资
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCanTransferList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCanTransferList(HttpServletRequest request,CrowdfundingSupportModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	String userId = this.getUserId(request);
            model.setSupportUser(userId);
            SearchResult<Map<String,Object>> pageList = this.crowdfundProductTransferService.getCanTransferList(model);
            int transferDay = this.crowdfundProductTransferService.getTransferDay();
            resultMap.put("transferDay",transferDay);
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
     * Description:查询可转让详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCanTransferDetail.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCanTransferDetail(HttpServletRequest request,CrowdfundingSupportModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String,Object> map = this.crowdfundProductTransferService.getCanTransferDetail(model);
            int transferDay = this.crowdfundProductTransferService.getTransferDay();
            map.put("transferDay",transferDay);
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
     * Description:手机短信登录发送手机验证码 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-17 上午10:23:11
     */
    @RequestMapping(value = "/sendMobileTransferVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendMobileLoginVerifyCode(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            UserModel user = this.userService.getUser(userId);
          //发送验证码
            this.verifyCodeService.sendVerifyCode(userId,user.getMobile(),
                    FbdCoreConstants.userType.P,FbdConstants.NODE_TYPE_TRANSFER_MOBILE);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "发送验证码失败");
        }
        return resultMap;
    }
    /**
     * 验证转让手续费,并返回对应的值
     * @param amt
     * @return
     */
    @RequestMapping(value = "/checkTransferAmt.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> checkTransferAmt(HttpServletRequest request,String supportNo,double transferAmt){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
    		this.getUserId(request);
    		if(StringUtil.isEmpty(supportNo)){
    			throw new ApplicationException("支持编号不能为空");
    		}
    		CrowdfundingSupportModel supportModle = this.crowdfundingSupportDao.getByOrderId(supportNo);
    		if(supportModle!=null){
    			resultMap.putAll(crowdfundProductTransferDao.checkTransferAmt(supportModle.getSupportAmt(),transferAmt));	
    		}else{
    			throw new ApplicationException("该支持编号无效");
    		}
    	}catch(ApplicationException e){
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
    	}catch(Exception e){
    		e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "验证异常");
    	}
    	return resultMap;
    }
    @RequestMapping(value = "/getTransferConfig.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getTransferConfig(HttpServletRequest request){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
    		this.getUserId(request);
    		resultMap.put(SUCCESS, true);
    		resultMap.putAll(crowdfundProductTransferDao.getTransferConfig());	
    	}catch(ApplicationException e){
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
    	}catch(Exception e){
    		e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "查询异常");
    	}
    	return resultMap;
    }
    /**
     * 
     * Description:保存产品项目转让
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/saveCrowdFundTransfer.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveCrowdFundTransfer(HttpServletRequest request,CrowdfundProductTransferModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            UserModel user = this.userService.getUser(userId);
            this.verifyCodeService.checkVerifyCode(request.getParameter("verifyCode").toString(), 
                    FbdConstants.NODE_TYPE_TRANSFER_MOBILE, userId, user.getMobile(), "mobile");
            
            model.setTransferUser(userId);
            this.crowdfundProductTransferService.saveCrowdFundTransfer(model);
            resultMap.put(SUCCESS, true);
        } catch(ApplicationException e){
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"保存转让信息失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:查询市场转让列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCrowdfundTransferDetailList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdfundTransferDetailList(HttpServletRequest request,CrowdfundProductTransferModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	
        	String sourceRemark = request.getParameter("sourceRemark");
        	if("userCenter".equals(sourceRemark)){  //手机端使用
        		String userId = this.getUserId(request);
        		model.setTransferUser(userId);
        	}
        	
            SearchResult<Map<String,Object>> pageList = this.crowdfundProductTransferService.getCrowdfundTransferDetailList(model);
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
     * Description:查询转让详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCrowdfundTransferDetail.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdfundTransferDetail(HttpServletRequest request,CrowdfundProductTransferModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String,Object> map = this.crowdfundProductTransferService.getCrowdfundTransferDetail(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
}
