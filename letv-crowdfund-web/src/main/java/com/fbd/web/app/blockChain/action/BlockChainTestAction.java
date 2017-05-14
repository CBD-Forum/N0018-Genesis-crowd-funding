package com.fbd.web.app.blockChain.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.helper.PKGenarator;



@Controller
@Scope("prototype")
@RequestMapping("/blockChainTest")
public class BlockChainTestAction extends BaseAction {
	
	private static final Logger logger = LoggerFactory.getLogger(BlockChainQueryAction.class);
	


	/**
     * 测试转账请求  
     * @param request
     * @param response
     */
    @RequestMapping(value = "/testTransfer.html")
    public @ResponseBody Map<String,Object> testTransfer(HttpServletRequest request,HttpServletResponse response) {
    	logger.info("=============测试转账开始调用============");
        Map<String, Object> resultMap = new HashMap<String,Object>();
        try {
        	  Map<String,String> params = new HashMap<String,String>();
              params.put("productCode","1000000002");
              params.put("serviceID","transfer");
              params.put("transferNO",PKGenarator.getOrderId());
              String requestID = PKGenarator.getOrderId();
              System.out.println("requestID:"+requestID);
              params.put("requestID",requestID);
              params.put("amount","0.01");
              params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainTest/transferNotify.html");
              params.put("sourceKey",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_OUT_KEY);
//              params.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_OUT_ACCOUNT);
//              params.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_IN_ACCOUNT);
//              logger.info("========>测试转账请求参数："+params);
              String resultStr = HttpRequestUtils.callHttpPOST(FbdCoreConstants.BLOCK_CHAIN_URL, params);
              System.out.println(resultStr); 
        	  resultMap.put(SUCCESS, true);
        	  resultMap.put("resData", resultStr);
        }catch (Exception e) {
        	resultMap.put(SUCCESS, false);
        } 
        return resultMap;
    }
	
	
	/**
     * 测试转账异步通知  
     * @param request
     * @param response
     */
    @RequestMapping(value = "/transferNotify.html")
    public void transferNotify(HttpServletRequest request,HttpServletResponse response) {
    	logger.info("=============测试异步开始调用============");
        Map<String, String> map=getBlockChainParamsStr();
        try {
        	logger.info("======IP:"+request.getRemoteAddr());
        	
        	logger.info("======异步通知返回结果："+map);
        }catch (Exception e) {
            logger.error("测试异步通知，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("异步回调调用结束，返回的结果为：:" + map);	
        }  
    }
	/**
     * 测试异步通知  
     * @param request
     * @param response
     */
    @RequestMapping(value = "/transactionQueryNotify.html")
    public void transactionQueryNotify(HttpServletRequest request,HttpServletResponse response) {
    	logger.info("=============测试异步开始调用============");
        Map<String, String> map=getBlockChainParamsStr();
        try {
        	logger.info("======IP:"+request.getRemoteAddr());
        	
        	logger.info("======异步通知返回结果："+map);
        }catch (Exception e) {
            logger.error("测试异步通知，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("异步回调调用结束，返回的结果为：:" + map);	
        }  
    }
    
    
    
    

}
