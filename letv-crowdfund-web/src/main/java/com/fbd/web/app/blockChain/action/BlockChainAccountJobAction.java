package com.fbd.web.app.blockChain.action;

import java.util.Date;
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

import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.BlockChainCore.BlockAsynSource;
import com.fbd.core.app.blockChain.service.IBlockChainAccountService;
import com.fbd.core.app.blockChain.service.BlockChainCore.ResultStatus;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.DateUtil;
import com.fbd.web.app.blockChain.service.IBlockChainAsynTranscationService;
/**
 * 
 * @author Administrator
 * 开户相关定时异步
 */
@Controller
@Scope("prototype")
@RequestMapping("/blockChainAccountJob")
public class BlockChainAccountJobAction extends BaseAction{

	private static final long serialVersionUID = -8087817090879155799L;

	private static final Logger logger = LoggerFactory.getLogger(BlockChainAccountJobAction.class);
	
    @Resource
    private IBlockChainAccountService blockChainAccountService;
    @Resource
    private IBlockChainAsynTranscationService blockChainAsynTransactionService;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IUserDao userDao;
	/**
     * 项目激活查询异步
     * @param request
     * @param response
     */
    @RequestMapping(value = "/loanActivationAccountQueryNotify.html", method = RequestMethod.POST)
    public void loanActivationAccountQueryNotify(HttpServletRequest request,HttpServletResponse response) {
    	logger.info("=============项目激活异步开始调用(job)============");
    	Map<String, String> map=getBlockChainParamsStr();
        try {
        	logger.info(map.toString());
        	String id=map.get(BlockChainCore.REQUEST_ID);
            String status=map.get(BlockChainCore.STATUS);
            String message=map.get(BlockChainCore.MESSAGE);
        	
        	if(ResultStatus.TRANSACTION_SUCCESS.equals(status)){
        		BlockAsynTranModel model=blockAsynTranDao.getById(id);
        		if(model==null){
        			throw new ApplicationException("blockAsynTran表中id:"+id+"不存在。");
        		}
            	Double amount=Double.parseDouble(map.get(BlockChainCore.AMOUNT));
        		String address=map.get(BlockChainCore.ADDRESS);
        		CrowdfundingModel loan=crowdfundingDao.getByBlockChainAddress(address);
            	if(amount!=null&&amount>0){
            		model.setStatus(ResultStatus.SUCCESS);
            		model.setMessage("因开户余额大于0,手动处理(job)");
            		model.setSource(BlockAsynSource.JOB);
            		
            		model.setQueryStatus(status);
            		model.setQueryMessage(message);
                    this.blockAsynTranDao.updateBySelective(model);
                    Map<String,String> data=new HashMap<String,String>();
                    data.put(BlockChainCore.ADDRESS, address);
                    Map<String,Object> result=null;
                    if(CrowdfundCoreConstants.crowdFundingState.preheat_locking.getValue().equals(loan.getLoanState())){
                    	blockChainAccountService.updateLoanPreheatActivationAccount(data);
                    	result=blockChainAccountService.getTrustLoanAccountBlockChainResult(loan, "blockChainAccount/receiveLoanPreheatTrustAccountS2S.html",BlockAsynSource.JOB,model);
            		}else if(CrowdfundCoreConstants.crowdFundingState.funding_locking.getValue().equals(loan.getLoanState())){
            			blockChainAccountService.updateLoanActivationAccount(data);
            			result=blockChainAccountService.getTrustLoanAccountBlockChainResult(loan, "blockChainAccount/receiveLoanTrustAccountS2S.html",BlockAsynSource.JOB,model);
            		}else{
            			result=new HashMap<String,Object>();
            			result.put("blockId", id);
            			result.put("loanNo", loan.getLoanNo());
            			result.put("msg", loan.getLoanState()+"不在处理范围内");
            		}
                    logger.info("---------------执行重新发送信任("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+id+"同步返回结果:"+result.toString()+" ------");
            	}else{
            		//重新发送
            		String notifyURL=null;
            		if(CrowdfundCoreConstants.crowdFundingState.preheat_locking.getValue().equals(loan.getLoanState())){
            			notifyURL="blockChainAccount/receiveLoanPreheatActivationAccountS2S.html";
            		}else if(CrowdfundCoreConstants.crowdFundingState.funding_locking.getValue().equals(loan.getLoanState())){
            			notifyURL="blockChainAccount/receiveLoanActivationAccountS2S.html";
            			
            		}
            		Map<String,Object> result=blockChainAccountService.getActiveLoanAccountBlockChainResult(loan,notifyURL,BlockAsynSource.JOB,model);
            		logger.info("---------------执行重新发送激活("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+id+"同步返回结果:"+result.toString()+" ------");
            	}
        		
        	}
        }catch (Exception e) {
            logger.error("用户查询异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("用户查询异步接口回调调用结束，返回的结果为：:" + map);	
        }
    }
    
    
	/**
     * 用户激活查询异步
     * @param request
     * @param response
     */
    @RequestMapping(value = "/userActivationAccountQueryNotify.html", method = RequestMethod.POST)
    public void userActivationAccountQueryNotify(HttpServletRequest request,HttpServletResponse response) {
    	logger.info("=============用户激活异步开始调用(job)============");
    	Map<String, String> map=getBlockChainParamsStr();
        try {
        	logger.info(map.toString());
        	String id=map.get(BlockChainCore.REQUEST_ID);
            String status=map.get(BlockChainCore.STATUS);
            String message=map.get(BlockChainCore.MESSAGE);
        	
        	if(ResultStatus.TRANSACTION_SUCCESS.equals(status)){
        		BlockAsynTranModel model=blockAsynTranDao.getById(id);
        		if(model==null){
        			throw new ApplicationException("blockAsynTran表中id:"+id+"不存在。");
        		}
            	Double amount=Double.parseDouble(map.get(BlockChainCore.AMOUNT));
        		String address=map.get(BlockChainCore.ADDRESS);
        		UserModel userModel=userDao.findByBlockChainAddress(address);
            	if(amount!=null&&amount>0){
            		model.setStatus(ResultStatus.SUCCESS);
            		model.setMessage("因开户余额大于0,手动处理(job)");
            		model.setSource(BlockAsynSource.JOB);
            		
            		model.setQueryStatus(status);
            		model.setQueryMessage(message);
                    this.blockAsynTranDao.updateBySelective(model);
                    Map<String,String> data=new HashMap<String,String>();
                    data.put(BlockChainCore.ADDRESS, address);
                    Map<String,Object> result=null;
            		blockChainAccountService.updateUserActivationAccount(data);
            		result=blockChainAccountService.trustUserAccount(address,BlockAsynSource.JOB,model);
                    logger.info("---------------执行重新发送信任("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+id+"同步返回结果:"+result.toString()+" ------");
            	}else{
            		//重新发送
            		Map<String,Object> result=blockChainAccountService.activeUserAccount(userModel.getUserId(),BlockAsynSource.JOB,model);;
            		logger.info("---------------执行重新发送激活("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+id+"同步返回结果:"+result.toString()+" ------");
            	}
        		
        	}
        }catch (Exception e) {
            logger.error("用户查询异步调用时，输出信息出现异常:"+e.getMessage());
        }finally{
            logger.info("用户查询异步接口回调调用结束，返回的结果为：:" + map);	
        }
    }
}
