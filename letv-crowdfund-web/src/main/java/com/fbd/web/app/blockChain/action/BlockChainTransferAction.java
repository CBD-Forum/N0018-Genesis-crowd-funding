package com.fbd.web.app.blockChain.action;

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

import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService;
import com.fbd.core.base.BaseAction;

/**
 * 
 * @author Administrator
 * 后台转让异步
 */
@Controller
@Scope("prototype")
@RequestMapping("/blockChainTransferAction")
public class BlockChainTransferAction extends BaseAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(BlockChainQueryAction.class);
	@Resource
	private IBlockChainCrowdfundingService blockChainCrowdfundingService;
    /**
     * 转让审核成功后异步处理(后台)
     * @param request
     * @param response
     */
    @RequestMapping(value = "/transferSuccessReceiveTransferS2S.html", method = RequestMethod.POST)
    public void transferSuccessReceiveTransferS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============转让审核成功异步回调开始调用============");
        Map<String, String> map=getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	blockChainCrowdfundingService.updateTimmerProductTransferAuditSuccess(map);
            //response.getWriter().write("ok");
        }catch (Exception e) {
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + map);	
        }
    }
    /**
     * 转让审核成功后手续费异步处理(后台)
     * @param request
     * @param response
     */
    @RequestMapping(value = "/transferSuccessReceiveTransferFeeS2S.html", method = RequestMethod.POST)
    public void transferSuccessReceiveTransferFeeS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============转让审核成功异步回调开始调用============");
        Map<String, String> map=getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	blockChainCrowdfundingService.updateTimmerProductTransferFeeAuditSuccess(map);
            //response.getWriter().write("ok");
        }catch (Exception e) {
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + map);	
        }
    }
    
    /**
     * 转让审核失败后异步处理(后台)
     * @param request
     * @param response
     */
    @RequestMapping(value = "/transferFailReceiveTransferS2S.html", method = RequestMethod.POST)
    public void transferFailReceiveTransferS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============转让审核失败异步回调开始调用============");
        Map<String, String> map=getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	blockChainCrowdfundingService.updateTimmerProductTransferAuditFail(map);
            //response.getWriter().write("ok");
        }catch (Exception e) {
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + map);	
        }
    }
}
