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
import com.fbd.core.app.blockChain.service.IBlockChainAccountService;
import com.fbd.core.base.BaseAction;
import com.fbd.web.app.blockChain.service.IBlockChainAsynTranscationService;


@Controller
@Scope("prototype")
@RequestMapping("/blockChainAccount")
public class BlockChainAccountAction extends BaseAction {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(BlockChainAccountAction.class);
	
    @Resource
    private IBlockChainAccountService blockChainAccountService;
    @Resource
    private IBlockChainAsynTranscationService blockChainAsynTransactionService;
    
	/**
     * 用户查询异步
     * @param request
     * @param response
     */
    @RequestMapping(value = "/userAccountQueryNotify.html", method = RequestMethod.POST)
    public void userAccountQueryNotify(HttpServletRequest request,HttpServletResponse response) {
    	logger.info("=============开户账户查询异步开始调用============");
    	Map<String, String> map=getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	logger.info(map.toString());
        	blockChainAsynTransactionService.modifyUserAccountQuery(map);
        }catch (Exception e) {
            logger.error("用户查询异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("用户查询异步接口回调调用结束，返回的结果为：:" + map);	
        }
    }
    
    /**
     * 用户开户
     * @param request
     * @param response
     */
    @RequestMapping(value = "/receiveUserOpenAccountS2S.html", method = RequestMethod.POST)
    public void receiveUserOpenAccountS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============用户开户接口异步回调开始调用============");
        Map<String,String> map =getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	logger.info("==========>用户开户接口异步回调参数："+map.toString());
        	blockChainAsynTransactionService.modifyUserOpenAccount(map);
        }catch (Exception e) {
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + map.toString());	
        }
    }
    /**
     * 用户激活
     * @param request
     * @param response
     */
    @RequestMapping(value = "/receiveUserActivationAccountS2S.html", method = RequestMethod.POST)
    public void receiveUserActivationAccountS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============用户账户激活异步回调开始调用============");
        Map<String,String> map =getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	System.out.println("------------"+map.toString());
        	blockChainAsynTransactionService.modifyUserActivationAccount(map);
        }catch (Exception e) {
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + map.toString());	
        }
    }
    /**
     * 用户信任
     * @param request
     * @param response
     */
    @RequestMapping(value = "/receiveUserTrustAccountS2S.html", method = RequestMethod.POST)
    public void receiveUserTrustAccountS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============用户账号添加信任异步回调开始调用============");
        Map<String,String> map =getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	logger.info("===============>用户账号添加信任异步回调参数："+map.toString());
        	blockChainAsynTransactionService.modifyUserTrustAccount(map);
        }catch (Exception e) {
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + map.toString());	
        }
    }
    /**
     * 项目开户
     * @param request
     * @param response
     */
    @RequestMapping(value = "/receiveLoanOpenAccountS2S.html", method = RequestMethod.POST)
    public void receiveLoanOpenAccountS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============项目开户接口异步回调开始调用============");
        Map<String,String> map =getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	logger.info(map.toString());
        	blockChainAsynTransactionService.modifyLoanOpenAccount(map);
        }catch (Exception e) {
            logger.error("项目开户接口异步回调调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("项目开户接口异步回调调用结束，返回的结果为：:" + map.toString());	
        }
    }
    /**
     * 项目开户(预热)
     * @param request
     * @param response
     */
    @RequestMapping(value = "/receiveLoanPreheatOpenAccountS2S.html", method = RequestMethod.POST)
    public void receiveLoanPreheatOpenAccountS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============项目(预热)开户接口异步回调开始调用============");
        Map<String,String> map =getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	logger.info(map.toString());
        	blockChainAsynTransactionService.modifyLoanPreheatOpenAccount(map);
        }catch (Exception e) {
            logger.error("项目(预热)开户接口异步回调调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("项目(预热)开户接口异步回调调用结束，返回的结果为：:" + map.toString());	
        }
    }
    /**
     * 项目激活
     * @param request
     * @param response
     */
    @RequestMapping(value = "/receiveLoanActivationAccountS2S.html", method = RequestMethod.POST)
    public void receiveLoanActivationAccountS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============项目开户激活异步回调开始调用============");
        Map<String,String> map =getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	logger.info(map.toString());
        	blockChainAsynTransactionService.modifyLoanActivationAccount(map);
        }catch (Exception e) {
        	e.printStackTrace();
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + map.toString());	
        }
    }
    /**
     * 项目激活(预热)
     * @param request
     * @param response
     */
    @RequestMapping(value = "/receiveLoanPreheatActivationAccountS2S.html", method = RequestMethod.POST)
    public void receiveLoanPreheatActivationAccountS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============项目(预热)激活异步回调开始调用============");
        Map<String,String> map =getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	logger.info(map.toString());
        	blockChainAsynTransactionService.modifyLoanPreheatActivationAccount(map);
        }catch (Exception e) {
        	e.printStackTrace();
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + map.toString());	
        }
    }
    
    /**
     * 项目信任
     * @param request
     * @param response
     */
    @RequestMapping(value = "/receiveLoanTrustAccountS2S.html", method = RequestMethod.POST)
    public void receiveLoanTrustAccountS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============项目账号添加信任异步回调开始调用============");
        Map<String,String> map =getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	logger.info(map.toString());
        	blockChainAsynTransactionService.modifyLoanTrustAccount(map);
        }catch (Exception e) {
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + map.toString());	
        }
    }
    /**
     * 项目信任(预热)
     * @param request
     * @param response
     */
    @RequestMapping(value = "/receiveLoanPreheatTrustAccountS2S.html", method = RequestMethod.POST)
    public void receiveLoanTrustPreheatAccountS2S(HttpServletRequest request,HttpServletResponse response){
        logger.info("=============项目账号(预热)添加信任异步回调开始调用============");
        Map<String,String> map =getBlockChainParamsStr();
        try {
        	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
        	logger.info(map.toString());
        	blockChainAsynTransactionService.modifyLoanPreheatTrustAccount(map);
        }catch (Exception e) {
            logger.error("转让异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("转让接口异步回调调用结束，返回的结果为：:" + map.toString());	
        }
    }
}
