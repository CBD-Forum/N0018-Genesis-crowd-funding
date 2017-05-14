

package com.fbd.web.app.letvPay.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferExtendModel;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.exception.ApplicationException;
import com.fbd.web.app.blockChain.service.IBlockChainAsynTranscationService;
import com.fbd.web.app.letvPay.service.ILetvPayTransferService;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System..
 * 
 * Description:购买转让商品
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/letvPay/transfer")
public class LetvPayTransferAction extends BaseAction{
    
    /**
     * 
     */
    private static final long serialVersionUID = 583651072172660381L;

    private static final Logger logger = LoggerFactory
            .getLogger(LetvPayTransferAction.class);
    
    @Resource
    private ILetvPayTransferService letvPayTransferService;
    
    @Resource
    private IBlockChainAsynTranscationService  blockChainAsynTransactionService;
    
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    /**
     * 
     * Description: 购买转让商品前验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/checkBeforeBuyTransfer.html", method = RequestMethod.POST)
    public  @ResponseBody
    Map<String, Object> checkBeforeBuyTransfer(HttpServletRequest request,CrowdfundProductTransferModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            model.setBuyUser(userId);
            this.letvPayTransferService.checkBeforeBuyTransfer(model);
            resultMap.put(SUCCESS, true);
        }catch (ApplicationException e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "购买验证不通过");
        }
        return resultMap;
    }
    /**
     * 
     * @return 保存用户信息
     */
    @RequestMapping(value = "/saveUserInfo.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveUserInfo(CrowdfundTransferExtendModel model,HttpServletRequest request){
    	Map<String,Object> resultMap=new HashMap<String,Object>();
    	try{
    		String userId=this.getUserId(request);
    		model.setUserId(userId);
    		resultMap.put(MSG,letvPayTransferService.saveTransUserInfo(model));
    		resultMap.put(SUCCESS, true);
    	}catch(ApplicationException e){
            logger.error(e.getMessage());
            resultMap.put(MSG, e.getMessage());
            resultMap.put(SUCCESS, false);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(MSG, "保存失败!");
            resultMap.put(SUCCESS, false);
        }
    	return resultMap;
    }
    /**
     * 
     * @param id
     * @return 获取用户信息
     */
    @RequestMapping(value = "/getTransUserInfo.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getTransUserInfo(String id){
    	Map<String,Object> resultMap=new HashMap<String,Object>();
    	try{
    		resultMap.put(MSG,letvPayTransferService.getTransUserInfo(id));
    		resultMap.put(SUCCESS, true);
    	}catch(ApplicationException e){
            logger.error(e.getMessage());
            resultMap.put(MSG, e.getMessage());
            resultMap.put(SUCCESS, false);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(MSG, "查询失败!");
            resultMap.put(SUCCESS, false);
        }
    	return resultMap;
    }
    /**
     * 
     * Description: 购买转让商品
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/submitBuyTransfer.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> submitBuyTransfer(
                HttpServletRequest request,CrowdfundProductTransferModel model) {
        logger.info("=============网关支付接口开始调用============");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //发送请求
        try{
            String userId = this.getUserId(request);
            model.setBuyUser(userId);
            //购买前验证(最好都把所有可判断的验证都放这里)
            this.letvPayTransferService.checkBeforeBuyTransfer(model);
            //购买
            this.letvPayTransferService.createTransferOrder(model);
            resultMap.put(MSG, "恭喜,您已成功支付,请等待处理");
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("errorMsg", e.getMessage());
            resultMap.put(SUCCESS, false);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("errorMsg", "支付失败!");
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/receiveTransferS2S.html", method = RequestMethod.POST)
    public void receiveTransferS2S(HttpServletRequest request,HttpServletResponse response) {
        logger.info("=============转让接口异步回调开始调用============");
    	Map<String, String> map=getBlockChainParamsStr();
    	String result=map.toString();
        try {
        	//等待同步处理数据
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	blockChainAsynTransactionService.modifyTransferWebTransactionTimmer(map);
        }catch (Exception e) {
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + result);	
        }
    }
}
