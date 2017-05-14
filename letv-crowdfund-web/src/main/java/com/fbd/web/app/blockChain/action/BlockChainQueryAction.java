package com.fbd.web.app.blockChain.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.base.BaseAction;
import com.fbd.web.app.blockChain.service.IBlockChainAsynTranscationService;

/**
 * 
 * @author Administrator
 * 事物查询异步
 */
@Controller
@RequestMapping("/blockChainQuery")
public class BlockChainQueryAction extends BaseAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(BlockChainQueryAction.class);
	
    @Resource
    private IBlockChainAsynTranscationService  blockChainAsynTranscationService;
    @Resource
    private IBlockChainQueryService blockChainQueryService;
	/**
     * 事物查询异步
     * @param request
     * @param response
     */
    @RequestMapping(value = "/transactionQueryNotify.html", method = RequestMethod.POST)
    public void receivetRansactionQueryS2S(HttpServletRequest request,HttpServletResponse response) {
    	logger.info("=============事物查询异步开始调用============");
    	Map<String, String> map=getBlockChainParamsStr();
        try {
        	blockChainQueryService.modifyTranscationQueryAsyn(map);
        }catch (Exception e) {
            logger.error("事物查询异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("事物查询接口异步回调调用结束，返回的结果为：:" + map);	
        }
    }
    /**
     * 查询事件是否成功
     * @param request
     * @param response
     */
    @RequestMapping(value = "/selectQueryIsSuccess.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> queryResult(String requestId) {
    	String status = blockChainQueryService.searchIsSucess(requestId);
    	Map<String,Object>map = new HashMap<String,Object>();
    	if(status!=null && status.equals(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS)){
      	    map.put(SUCCESS, true);
      	    map.put(MSG, "提现申请成功！");
    	}else if(status!=null && BlockChainCore.ResultStatus.FAIL.equals(status)){
    		 map.put(SUCCESS, true);
       	     map.put(MSG, "提现申请失败！");
    	}else{
    		map.put(SUCCESS, false);
    		map.put(MSG, "");
    	}
    	return map;
    }
    
}
