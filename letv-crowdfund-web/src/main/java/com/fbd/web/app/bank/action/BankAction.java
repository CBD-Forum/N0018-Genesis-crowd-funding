/* 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BankAction.java 
 *
 * Created: [2015-6-28 下午4:21:31] by haolingfeng
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

package com.fbd.web.app.bank.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.web.app.bank.service.IBankService;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;

/** 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/bank")
public class BankAction extends BaseAction{
    @Resource
    private IBankService bankService;
    
    @Resource
    private IVerifyCodeService verifyCodeService;
    
    /**
     * 
     * Description: 绑定银行卡
     * 
     * @param
     * @return SearchResult<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:30:32
     */
    @RequestMapping(value = "/bindUserBank.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> bindUserBank(HttpServletRequest request,UserBankModel userBank) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            
            //验证手机验证码
            String verifyCode = request.getParameter("verifyCode");
            this.verifyCodeService.checkVerifyCode(verifyCode, 
                    FbdConstants.NODE_TYPE_MOBILE_VERIFY, userId);
            userBank.setUserId(userId);
            bankService.saveUserBank(userBank);
            resultMap.put(SUCCESS,true);
        }catch(ApplicationException e){
        	resultMap.put(MSG, e.getMessage());
        	resultMap.put(SUCCESS, false);
        }catch(Exception e){
        	resultMap.put(MSG, "操作异常");
            resultMap.put(SUCCESS,false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 查询银行卡
     * 
     * @param
     * @return SearchResult<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:30:32
     */
    @RequestMapping(value = "/getUserBank.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserBank(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            List<UserBankModel> userBankList = bankService.getUserBank(userId);
            resultMap.put(SUCCESS,true);
            resultMap.put(MSG,userBankList);
        }catch(Exception e){
            resultMap.put(SUCCESS,false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 绑定银行卡
     * 
     * @param
     * @return SearchResult<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:30:32
     */
    @RequestMapping(value = "/delUserBank.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> delUserBank(HttpServletRequest request,UserBankModel userBank) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            if(StringUtil.isEmpty(userBank.getId())){
                throw new ApplicationException("银行卡参数ID不能为空");
            }
            String userId = this.getUserId(request);
            userBank.setUserId(userId);
            bankService.delUserBank(userBank);
            resultMap.put(SUCCESS,true);
        }catch(ApplicationException e){
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,e.getMessage());
        }catch(Exception e){
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,"删除失败");
        }
        return resultMap;
    }
    
    
}
