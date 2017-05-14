/* 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
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

package com.fbd.web.app.user.action;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtils;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;

/** 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
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
	
    private static final Logger logger = LoggerFactory.getLogger(UserAuthAction.class);
    
	
	
    @Resource
    private ICrowdfundingService crowdfundingService;
    
    /**
     * 
     * Description: 投资人认证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午6:45:22
     */
    @RequestMapping(value = "/applyInvestorAuth.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> applyInvestorAuth(HttpServletRequest request,CrowdfundUserStuffModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            model.setAuthType("investor");  //投资人认证
            this.crowdfundingService.saveUserAuth(model);
            resultMap.put("id", model.getId());
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"申请认证");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 申请成为领投人
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午6:45:22
     */
    @RequestMapping(value = "/applyLeadInvestor.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> applyLeadInvestor(HttpServletRequest request,CrowdfundUserStuffModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            model.setAuthType("leadInvestor");  //领投人认证
            this.crowdfundingService.saveUserAuth(model);
            resultMap.put("id", model.getId());
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"申请认证");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 申请成为领投机构
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/applyOrgLeadInvestor.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> applyOrgLeadInvestor(HttpServletRequest request,CrowdfundUserStuffModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            model.setAuthType("orgLeadInvestor");  //领投机构认证
            this.crowdfundingService.saveUserAuth(model);
            resultMap.put("id", model.getId());
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"申请成为领投机构失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 申请成为跟投机构
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/applyOrgInvestInvestor.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> applyOrgInvestInvestor(HttpServletRequest request,CrowdfundUserStuffModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            model.setAuthType("orgInvestor");  //跟投机构认证
            this.crowdfundingService.saveUserAuth(model);
            resultMap.put("id", model.getId());
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"申请成为跟投机构");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 修改申请认证信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/updateUserAuth.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateUserAuth(HttpServletRequest request,CrowdfundUserStuffModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.updateCrowdfundUserStuff(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"修改申请认证信息");
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
    @RequestMapping(value = "/getCrowdfundUserByUserId.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdfundUser(HttpServletRequest request,CrowdfundUserStuffModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            resultMap.put(MSG,this.crowdfundingService.getCrowdfundUserAuth(model));
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
    
    //hch(废弃，不知道干嘛用的)
/*    @RequestMapping(value = "/getFollowInvestors.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getFollowInvestors(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            resultMap.put(MSG,this.crowdfundingService.getByUserId(userId));
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"申请认证");
        }
        return resultMap;
    } */
    
    /**
     * 
     * Description: 上传认证资料
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/uploadAuthFile.html", method = RequestMethod.POST)  
    public @ResponseBody
    Map<String, Object> uploadOrgLoanReceiveProve(@RequestParam("file") MultipartFile file, HttpServletRequest request) {  
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //保存  
        try {  
            String path = CrowdfundCoreConstants.CROWDFUNDING_AUTH_FILE + DateUtils.dateToString(new Date(), null)+"/";  
            String fileName = file.getOriginalFilename();
            String suffix=fileName.substring(fileName.lastIndexOf("."));
           
            List<String> allowSuffix = new ArrayList<String>();
            allowSuffix.add(".doc"); 
            allowSuffix.add(".docx");
            allowSuffix.add(".pdf");
            allowSuffix.add(".jpg"); 
            allowSuffix.add(".png");
            allowSuffix.add(".gif");
            allowSuffix.add(".jpeg");
            if (!allowSuffix.contains(suffix.toLowerCase())) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, "上传类型必须是doc,docx,pdf,jpg,png,gif,jpeg中的一种");
                return resultMap;
            }
            long size = file.getSize();
            logger.info("========>上传文件大小："+size+"========================");
            if(size>5242880){
            	  resultMap.put(SUCCESS,false);
                  resultMap.put(MSG, "上传图片大小不超过5M");
                  return resultMap;
            }
            String picId=PKGenarator.getId();
            String newfileName=picId+suffix;
            String fileUrl = path + newfileName;
            boolean flag = FTPManagerFactory.getFtpManager().uploadFile(path, newfileName, file.getInputStream());
            if(flag){//上传成功
                resultMap.put(MSG,fileUrl);
                resultMap.put(SUCCESS,true);
            }else{
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG,"上传失败");
            }
        } catch (Exception e) {  
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,"上传失败"+e.getMessage());
        }  
        return resultMap;   
    }  
    
}
