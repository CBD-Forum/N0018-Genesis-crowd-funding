

package com.fbd.web.app.sxyPay.action;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.bank.util.BankUtil;
import com.fbd.core.app.blockChain.service.BlockChainCore.BlockAsynSource;
import com.fbd.core.app.blockChain.service.BlockChainCore.UserAuth;
import com.fbd.core.app.blockChain.service.IBlockChainAccountService;
import com.fbd.core.app.sxyPay.common.ResultHandler;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.web.app.sxyPay.service.ISxyPayAccountService;
import com.fbd.web.app.user.service.IUserService;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System..
 * 
 * Description:用户实名认证
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/sxyPay/account")
public class SxyPayAccountAction extends BaseAction{
    
    /**
     * 
     */
    private static final long serialVersionUID = 583651072172660381L;

    private static final Logger logger = LoggerFactory
            .getLogger(SxyPayAccountAction.class);
    
    @Resource
    private ISxyPayAccountService sxyPayAccountService;
    
    @Resource
    private IUserService userService;
    
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    
    @Resource
    private IBlockChainAccountService blockChainAccountService;
    
    /**
     * 
     * Description: 实名认证前验证用户信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/checkUserInfo.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> checkUserInfo(String verifyCode,UserModel user,HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            user.setUserId(userId);
            userService.checkUserInfo(user);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "验证失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:个人实名认证
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/createUserAccount.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> createUserAccount(UserModel user,HttpServletRequest request,HttpServletResponse response){
        logger.info("=============首信易支付实名认证接口开始调用============");
        Map<String, Object> resultData = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            
            if(StringUtils.isEmpty(user.getRealName())){
            	throw new ApplicationException("请输入真实姓名");
            }
            if(StringUtils.isEmpty(user.getCertificateNo())){
            	throw new ApplicationException("请输入身份证号");
            }
            if(StringUtils.isEmpty(user.getBankNo())){
            	throw new ApplicationException("请输入银行卡号");
            }else{
            	if(!BankUtil.checkBankCard(user.getBankNo())){
            		throw new ApplicationException("请输入正确的银行卡号");
            	}
            }
            //验证身份证号码是否存在
            userService.checkUserInfo(user);
            
            UserModel userModel = userService.findByUserId(userId);
            userModel.setBankNo(user.getBankNo());
            userModel.setCertificateNo(user.getCertificateNo());
            userModel.setRealName(user.getRealName());
            if(FbdCoreConstants.BLOCK_CHAIN_USER_IS_MOCK){
            	sxyPayAccountService.updateUserInfo(userModel);
            	//同步处理	
    			userModel.setBlockChainAddress("rKgHhdGPKWgNDikMu6cdRYPG8ApfvF7fE5");
    			userModel.setBlockChainSecretKey("saaSH4foPHGrgxVKanU9wtbkQxW4s");
    			
    			
    			userModel.setBlockChainState(UserAuth.END);
    			this.userService.updateByUserId(userModel);
    			request.getSession().setAttribute(FbdCoreConstants.SESSION_IS_AUTH,FbdCoreConstants.IS);
            	request.getSession().setAttribute(FbdCoreConstants.SESSION_SXY_AUTH,FbdCoreConstants.IS);
            }else{
            	Map<String, String> handerResultMap=null;
            	if(FbdCoreConstants.BLOCK_USER_IS_MOCK){
            		handerResultMap=new HashMap<String,String>();
            		handerResultMap.put("respCode", "00");
            		handerResultMap.put("respDesc", "成功");
            	}else{
            		logger.info("----------------->开始调用首信易支付银行卡签权---->实名认证");
                    String str = this.sxyPayAccountService.createUserAccount(userModel);
                    logger.info("----------------->开始调用首信易支付银行卡签权返回参数："+str);
                    handerResultMap= ResultHandler.convert2Str(str);
                    logger.info("----------------->开始调用首信易支付银行卡签权返回参数-handerResultMap："+handerResultMap);
            	}
                //用户开户
                String resultCode = handerResultMap.get("respCode");
                String respDesc = handerResultMap.get("respDesc");
                if("00".equals(resultCode)){//实名认证成功
                	sxyPayAccountService.updateUserInfo(userModel);
                	request.getSession().setAttribute(FbdCoreConstants.SESSION_IS_AUTH,FbdCoreConstants.IS);
                	request.getSession().setAttribute(FbdCoreConstants.SESSION_SXY_AUTH,FbdCoreConstants.IS);
                	try{
                		logger.info("----------------->首信易支付银行卡签权-实名认证通过,开始调用区块链进行开户");
                		this.blockChainAccountService.createUserAccount(userId,BlockAsynSource.NORMAL,null);
                	}catch(Exception e){
                		e.printStackTrace();
                		throw new ApplicationException("区块链开户异常:"+e.getMessage());
                	}
                	resultData.put(SUCCESS, true);
                }else{
            		logger.error("首信易异常文本:"+URLDecoder.decode(respDesc,"UTF-8"));
                    respDesc = "首信易:请检查信息是否填写正确";
                    resultData.put(SUCCESS, false);
                    throw new ApplicationException(respDesc);
                }
            }
        	resultData.put(SUCCESS, true);
        }catch(ApplicationException e){
        	logger.error(e.getMessage());
            resultData.put(MSG,e.getMessage());
            resultData.put(SUCCESS, false);
        }catch(Exception e){
           logger.error(e.getMessage());
            resultData.put(MSG,"实名认失败,请联系客服！");
            resultData.put(SUCCESS, false);
        }
        return resultData;
    }
    
   
}
