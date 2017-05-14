/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserCenter.java 
 *
 * Created: [2015-1-19 下午2:58:05] by haolingfeng
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

package com.fbd.web.app.user.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSecurityLevelModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.user.service.IUserSecurityLevelService;
import com.fbd.web.app.user.service.IUserService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;

/** 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description: 安全中心
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/safeLevel")
public class UserSecurityLevelAction extends BaseAction{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5898870567419137435L;
    @Resource
    private IUserSecurityLevelService userSecurityLevelService;
    @Resource
    private IVerifyCodeService verifyCodeService;
    @Resource
    private IUserService userService;
    @Resource
    private IUserDao userDao;
    /**
     * 
     * Description: 查询安全信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-1-19 下午3:00:58
     */
    @RequestMapping(value = "/getSecurity.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getSecurityLevel(HttpServletRequest request){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            UserSecurityLevelModel result = this.userSecurityLevelService.getByModel(userId, 
                    FbdCoreConstants.userType.P);
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
     * Description: 认证手机
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-14 下午4:26:40
     */
    @RequestMapping(value = "/authMobile.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> authMobile(HttpServletRequest request,String verifyCode,String sendTarget){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            //验证手机验证码
            this.verifyCodeService.checkVerifyCode(verifyCode, 
                    FbdConstants.NODE_TYPE_MOBILE_VERIFY, userId,sendTarget,"mobile");
            this.userSecurityLevelService.updateAuthMobile(userId, sendTarget);
            amap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            amap.put(SUCCESS, false);
            amap.put(MSG, e.getMessage());
        }
        return amap;
    } 
    /**
     * 
     * Description: 验证原手机号
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 下午2:38:54
     */
    @RequestMapping(value = "/verifyOldMobile.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> verifyOldMobile(HttpServletRequest request,
            String verifyCode,String sendTarget){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            //验证手机验证码
            this.verifyCodeService.checkVerifyCode(verifyCode, 
                    FbdConstants.NODE_TYPE_MOBILE_VERIFY, userId,sendTarget,"mobile");
            this.userSecurityLevelService.updateUnbindMobile(userId, sendTarget);
            amap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            amap.put(SUCCESS, false);
            amap.put(MSG, e.getMessage());
        }
        return amap;
    } 
    
    /**
     * 
     * Description: 更新紧急人
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-14 下午4:26:40
     */
    @RequestMapping(value = "/updateEmergencyInfo.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> updateEmergencyInfo(HttpServletRequest request,UserModel user){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            user.setUserId(userId);
            this.userSecurityLevelService.updateEmergencyInfo(user);
            amap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            amap.put(SUCCESS, false);
            amap.put(MSG, e.getMessage());
        }
        return amap;
    }
    
    /**
     * 
     * Description: 更新地址
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-14 下午4:26:40
     */
    @RequestMapping(value = "/updateUserAddress.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> updateUserAddress(HttpServletRequest request,UserModel user){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            user.setUserId(userId);
            this.userSecurityLevelService.updateAddress(user);
            amap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            amap.put(SUCCESS, false);
            amap.put(MSG, e.getMessage());
        }
        return amap;
    }
    
    /**
     * 
     * Description: 手机验证码验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-2 下午2:59:44
     */
    @RequestMapping(value = "/checkVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> checkVerifyCode(HttpServletRequest request,
            String verifyCode){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            //验证手机验证码
            this.verifyCodeService.checkVerifyCode(verifyCode, 
                    FbdConstants.NODE_TYPE_MOBILE_VERIFY, userId);
            amap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            amap.put(SUCCESS, false);
            amap.put(MSG, e.getMessage());
        }
        return amap;
    }
    
    /**
     * 
     * Description: 认证邮箱
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-14 下午4:26:40
     */
    @RequestMapping(value = "/authEmail.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> authEmail(HttpServletRequest request,String verifyCode,String sendTarget){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            //验证邮箱验证码
            this.verifyCodeService.checkVerifyCode(verifyCode, 
                    FbdConstants.NODE_TYPE_EMAIL_AUTH, userId,sendTarget,"email");
            this.userSecurityLevelService.updateAuthEmail(userId, sendTarget);
            amap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            amap.put(SUCCESS, false);
            amap.put(MSG, e.getMessage());
        }
        return amap;
    } 
    
    
    /**
     * 
     * Description: 检测邮箱
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-14 下午4:26:40
     */
    @RequestMapping(value = "/checkEmail.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> checkEmail(HttpServletRequest request,String email){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            UserModel user = this.userDao.findByEmail(email);
            if(user!=null){
            	amap.put("isExist", true);
            }else{
            	amap.put("isExist", false);
            }
            amap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            amap.put(SUCCESS, false);
            amap.put(MSG, e.getMessage());
        }
        return amap;
    } 
    
}
