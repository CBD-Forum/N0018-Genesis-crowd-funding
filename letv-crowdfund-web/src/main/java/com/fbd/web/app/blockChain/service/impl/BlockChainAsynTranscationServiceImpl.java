/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BlockChainQueryServiceImpl.java 
 *
 * Created: [2016-9-19 下午8:00:43] by haolingfeng
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
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.blockChain.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.BlockChainCore.BlockAsynSource;
import com.fbd.core.app.blockChain.service.BlockChainCore.ResultStatus;
import com.fbd.core.app.blockChain.service.IBlockChainAccountService;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.spring.SpringUtil;
import com.fbd.web.app.blockChain.service.IBlockChainAsynTranscationService;
import com.fbd.web.app.letvPay.service.ILetvPayTransferService;
/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

@Service(value="blockChainAsynTranscationService")
public class BlockChainAsynTranscationServiceImpl implements IBlockChainAsynTranscationService {
    
    private static final Logger logger = LoggerFactory.getLogger(BlockChainAsynTranscationServiceImpl.class);
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IBlockChainAccountService blockChainAccountService;
    @Resource
    private IUserDao userDao;
    
    @Override
    public void modifyTranscationQueryAsyn(Map<String, String> result) {
        // TODO Auto-generated method stub
        String tranId=result.get(BlockChainCore.TRANSACTION_ID);
        String status=result.get(BlockChainCore.STATUS);
        String message=result.get(BlockChainCore.MESSAGE);
        
        //查询事务异步通知信息
        BlockAsynTranModel queryModel = new BlockAsynTranModel();
        queryModel.setTranId(tranId);
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.selectByModel(queryModel);
        if(blockAsynTran!=null){
            blockAsynTran.setQueryStatus(status);
            blockAsynTran.setQueryMessage(message);
            this.blockAsynTranDao.updateBySelective(blockAsynTran);
        }
    }
    
    
	@Override
	public void modifyTransferWebTransactionTimmer(Map<String, String> result) {
		// TODO Auto-generated method stub
        String transactionID =  result.get(BlockChainCore.TRANSACTION_ID).toString();
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message=result.get(BlockChainCore.MESSAGE);
        
        String requestId=result.get(BlockChainCore.REQUEST_ID);

        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setTranId(transactionID);
        blockAsynTran.setMessage(message);
        blockAsynTran.setUpdateTime(new Date());
        blockAsynTran.setQueryRequestID(PKGenarator.getId());
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
        if(ResultStatus.SUCCESS.equals(status)){
            Timer timer=new Timer();
            result.put(BlockChainCore.QUERY_REQUEST_ID, blockAsynTran.getQueryRequestID());
    		timer.schedule(new TransferWebTask(timer,result), 2000, 2000);	
        }
	}
    static class TransferWebTask extends java.util.TimerTask{
        private Map<String,String> value;
        private Timer timer;
        
        
        public TransferWebTask(Timer timer,Map<String,String> value){
            this.value=value;
            this.timer=timer;
        }
        @Override
        public void run() {
        	try{
                logger.info("-------------转让购买(事物处理)---------------------");
                String transactionID=value.get(BlockChainCore.TRANSACTION_ID);
                String requestId=value.get(BlockChainCore.QUERY_REQUEST_ID);
                
                IBlockChainQueryService service=(IBlockChainQueryService) SpringUtil.getBean("blockChainQueryService");
                service.transactionQuery(transactionID,requestId);
                IBlockAsynTranDao dao=(IBlockAsynTranDao) SpringUtil.getBean("blockAsynTranDao");
                BlockAsynTranModel model=dao.findByQueryRequestId(requestId);
                if(model!=null){
                   String queryStatus=model.getQueryStatus();
                   logger.info("-------------queryStatus:"+queryStatus);
                   if(ResultStatus.TRANSACTION_SUCCESS.equals(queryStatus)){
                	   ILetvPayTransferService opService=(ILetvPayTransferService)SpringUtil.getBean("letvPayTransferService");
                	   opService.updateTransferSuccess(value);
                	   logger.info("-------------success-----------------");
                	   timer.cancel();
                   }else if(ResultStatus.TRANSACTION_FAIL.equals(queryStatus)){
                	   timer.cancel();
                	   logger.info("-------------fail-----------------");
                   }
                }
            }catch(Exception e){
                e.printStackTrace();
                timer.cancel();
            }
            
        }
        
    }
	@Override
	public void modifyUserOpenAccount(Map<String, String> result) {
		// TODO Auto-generated method stub
        String uid =  result.get(BlockChainCore.UID).toString();
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message = result.get(BlockChainCore.MESSAGE);
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran=this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setUpdateTime(new Date());
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
        String resultStatus = result.get(BlockChainCore.STATUS).toString();
        logger.info("===============》用户开户异步通知返回状态-status:"+status);
        if(ResultStatus.SUCCESS.equals(resultStatus)){
        	logger.info("===========》更新用户区块链账户为开户成功激活中===========");
        	blockChainAccountService.updateUserAccount(result);
        	logger.info("============>发送激活区块链请求======================");
            blockChainAccountService.activeUserAccount(uid,BlockAsynSource.NORMAL,null);
        }
        
/*        Timer timer=new Timer();
		timer.schedule(new UserOpenAccountTask(timer,result), new Date(), 5000);*/
        
	}
	/**
	 * 
	 * @author Administrator
	 * 用户开户
	 */
    static class UserOpenAccountTask extends java.util.TimerTask{
        private Map<String,String> value;
        private Timer timer;
        
        
        public UserOpenAccountTask(Timer timer,Map<String,String> value){
            this.value=value;
            this.timer=timer;
        }
        
        @Override
        public void run() {
            try{
                System.out.println("----------------------------------------");
                String transactionID=value.get(BlockChainCore.TRANSACTION_ID);
                IBlockChainQueryService service=(IBlockChainQueryService) SpringUtil.getBean("blockChainQueryService");
                //订单编号
                String result=service.transactionQuery(transactionID);
                @SuppressWarnings("unchecked")
                Map<String,Object> resultMap = JsonHelper.getMapFromJson(result);
              
                String resultStatus = resultMap.get(BlockChainCore.STATUS).toString();
                if(ResultStatus.SUCCESS.equals(resultStatus)){
/*                	IBlockChainAccountService opService=(IBlockChainAccountService)SpringUtil.getBean("blockChainAccountService");
                	opService.updateUserAccount(value);
                	*/
                	timer.cancel();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        
    }


	@Override
	public void modifyUserActivationAccount(Map<String, String> result) {
		// TODO Auto-generated method stub
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message = result.get(BlockChainCore.MESSAGE);
        //查询事务异步通知信息
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        String transactionID = result.get(BlockChainCore.TRANSACTION_ID);
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran=this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setTranId(transactionID);
        blockAsynTran.setUpdateTime(new Date());
        String queryRequestId=PKGenarator.getId();
        blockAsynTran.setQueryRequestID(queryRequestId);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
        String resultStatus = result.get(BlockChainCore.STATUS).toString();
        logger.info("================>区块链激活账户异步返回状态-Status："+resultStatus);
        if(ResultStatus.SUCCESS.equals(resultStatus)){
        	logger.info("===========>开始更新用户【"+blockAsynTran.getParentId()+"】区块链状态为激活成功，等待信任中");
        	blockChainAccountService.updateUserActivationAccount(result);
        	result.put(BlockChainCore.QUERY_REQUEST_ID, blockAsynTran.getQueryRequestID());
            Timer timer=new Timer();
            timer.schedule(new UserAccountQueryTask(timer,result), 1000, 2000);
        }
	}
	/**
	 * 
	 * @author Administrator
	 * 开户账户查询定时器
	 */
    static class UserAccountQueryTask extends java.util.TimerTask{
        private Map<String,String> valueMap;
        private Timer timer;
        
        public UserAccountQueryTask(Timer timer,Map<String,String> resultMap){
            this.valueMap=resultMap;
            this.timer=timer;
        }
        
        @Override
        public void run() {
            try{
            	IBlockChainAccountService blockChainAccountService=(IBlockChainAccountService) SpringUtil.getBean("blockChainAccountService");
                String address = valueMap.get(BlockChainCore.ADDRESS);
                String requestId = valueMap.get(BlockChainCore.REQUEST_ID);
                String queryRequestId = valueMap.get(BlockChainCore.QUERY_REQUEST_ID);
                logger.info("===========>用户["+address+"],["+requestId+"]开始查询是否激活成功===================");
                blockChainAccountService.userAccountQuery(address, queryRequestId);
                
            	IBlockAsynTranDao dao=(IBlockAsynTranDao) SpringUtil.getBean("blockAsynTranDao");
                BlockAsynTranModel model=dao.findByRequestId(requestId);
                if(model!=null){
                   String queryStatus=model.getQueryStatus();
                   logger.info("------------queryStatus:"+queryStatus);
                   if(ResultStatus.TRANSACTION_SUCCESS.equals(queryStatus)){
                	   IBlockChainAccountService opService=(IBlockChainAccountService) SpringUtil.getBean("blockChainAccountService");
                	   opService.trustUserAccount(address,BlockAsynSource.NORMAL,null);
                	   logger.info("------------------Success-------------------------");
                       timer.cancel();      
                   }else if(ResultStatus.TRANSACTION_FAIL.equals(queryStatus)){
                	   logger.info("------------------Error-------------------------");
                	   timer.cancel();   
                   }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 统一处理异步开户部分
     * @param result
     */
    private void opUnifyLoanOpenAccount(Map<String, String> result){
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message = result.get(BlockChainCore.MESSAGE);
        
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setUpdateTime(new Date());
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
    }
    
	@Override
	public void modifyLoanPreheatOpenAccount(Map<String, String> result) {
		// TODO Auto-generated method stub
		opUnifyLoanOpenAccount(result);
		String uid =  result.get(BlockChainCore.UID).toString();
		String status =  result.get(BlockChainCore.STATUS).toString();
		if(ResultStatus.SUCCESS.equals(status)){
        	blockChainAccountService.updateLoanAccount(result);
            blockChainAccountService.activeLoanPreheatAccount(uid);
        }
	}
    
	@Override
	public void modifyLoanOpenAccount(Map<String, String> result) {
		// TODO Auto-generated method stub
		
		opUnifyLoanOpenAccount(result);
		String uid =  result.get(BlockChainCore.UID).toString();
		String status =  result.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.SUCCESS.equals(status)){
        	blockChainAccountService.updateLoanAccount(result);
            blockChainAccountService.activeLoanAccount(uid);
        }
	}
	/**
	 * 
	 * @author Administrator
	 * 项目账户查询定时器
	 */
    static class LoanAccountQueryTask extends java.util.TimerTask{
        private Map<String,String> valueMap;
        private Timer timer;
        
        public LoanAccountQueryTask(Timer timer,Map<String,String> resultMap){
            this.valueMap=resultMap;
            this.timer=timer;
        }
        
        @Override
        public void run() {
            try{
            	IBlockChainAccountService service=(IBlockChainAccountService) SpringUtil.getBean("blockChainAccountService");
                String address = valueMap.get(BlockChainCore.ADDRESS);
                String requestId = valueMap.get(BlockChainCore.REQUEST_ID);
                String queryRequestId = valueMap.get(BlockChainCore.QUERY_REQUEST_ID);
                
                service.userAccountQuery(address, queryRequestId);
            	IBlockAsynTranDao dao=(IBlockAsynTranDao) SpringUtil.getBean("blockAsynTranDao");
                BlockAsynTranModel model=dao.findByRequestId(requestId);
                if(model!=null){
                   String queryStatus=model.getQueryStatus();
                   logger.info("------------queryStatus:"+queryStatus);
                   if(ResultStatus.TRANSACTION_SUCCESS.equals(queryStatus)){
                	   IBlockChainAccountService opService=(IBlockChainAccountService) SpringUtil.getBean("blockChainAccountService");
                	   opService.trustLoanAccount(address);
                	   logger.info("------------------Success-------------------------");
                       timer.cancel();      
                   }else if(ResultStatus.TRANSACTION_FAIL.equals(queryStatus)){
                	   logger.info("------------------Error-------------------------");
                       //timer.cancel();    
                   }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
	/**
	 * 
	 * @author Administrator
	 * 项目预热账户查询定时器
	 */
    static class LoanPreheatAccountQueryTask extends java.util.TimerTask{
        private Map<String,String> valueMap;
        private Timer timer;
        
        public LoanPreheatAccountQueryTask(Timer timer,Map<String,String> resultMap){
            this.valueMap=resultMap;
            this.timer=timer;
        }
        
        @Override
        public void run() {
            try{
            	IBlockChainAccountService service=(IBlockChainAccountService) SpringUtil.getBean("blockChainAccountService");
                String address = valueMap.get(BlockChainCore.ADDRESS);
                String requestId = valueMap.get(BlockChainCore.REQUEST_ID);
                String queryRequestId = valueMap.get(BlockChainCore.QUERY_REQUEST_ID);
                
                service.userAccountQuery(address, queryRequestId);
            	IBlockAsynTranDao dao=(IBlockAsynTranDao) SpringUtil.getBean("blockAsynTranDao");
                BlockAsynTranModel model=dao.findByRequestId(requestId);
                if(model!=null){
                   String queryStatus=model.getQueryStatus();
                   logger.info("------------queryStatus:"+queryStatus);
                   if(ResultStatus.TRANSACTION_SUCCESS.equals(queryStatus)){
                	   IBlockChainAccountService opService=(IBlockChainAccountService) SpringUtil.getBean("blockChainAccountService");
                	   opService.trustLoanPreheatAccount(address);
                	   logger.info("------------------Success-------------------------");
                       timer.cancel();      
                   }else if(ResultStatus.TRANSACTION_FAIL.equals(queryStatus)){
                	   logger.info("------------------Error-------------------------");
                       //timer.cancel();    
                   }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 统一处理异步项目激活部分
     * @param result
     */
    private void opUnifyLoanActivationAccount(Map<String, String> result){
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message = result.get(BlockChainCore.MESSAGE);
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setUpdateTime(new Date());
        blockAsynTran.setQueryRequestID(PKGenarator.getId());
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
    	result.put(BlockChainCore.QUERY_REQUEST_ID, blockAsynTran.getQueryRequestID());
    }
    
	@Override
	public void modifyLoanPreheatActivationAccount(Map<String, String> result) {
		// TODO Auto-generated method stub
		opUnifyLoanActivationAccount(result);
		String resultStatus = result.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.SUCCESS.equals(resultStatus)){
        	blockChainAccountService.updateLoanPreheatActivationAccount(result);
            Timer timer=new Timer();
            timer.schedule(new LoanPreheatAccountQueryTask(timer,result), 2000, 2000);
        }
	}

	@Override
	public void modifyLoanActivationAccount(Map<String, String> result) {
/*		// TODO Auto-generated method stub
        String uid =  result.get(BlockChainCore.UID).toString();
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message = result.get(BlockChainCore.MESSAGE);
        //查询事务异步通知信息
        BlockAsynTranModel queryModel = new BlockAsynTranModel();
        queryModel.setParentId(uid);
        queryModel.setType(BlockChainCore.Type.ACTIVATION_ACCOUNT_LOAN);
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.selectByModel(queryModel);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setUpdateTime(new Date());
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        String resultStatus = result.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.SUCCESS.equals(resultStatus)){
        	blockChainAccountService.updateLoanActivationAccount(result);
        	result.put(BlockChainCore.QUERY_REQUEST_ID, blockAsynTran.getQueryRequestID());
            Timer timer=new Timer();
            timer.schedule(new LoanAccountQueryTask(timer,result), 2000, 2000);
        }*/
		opUnifyLoanActivationAccount(result);
		String resultStatus = result.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.SUCCESS.equals(resultStatus)){
        	blockChainAccountService.updateLoanActivationAccount(result);
            Timer timer=new Timer();
            timer.schedule(new LoanAccountQueryTask(timer,result), 2000, 2000);
        }
	}


	@Override
	public void modifyUserAccountQuery(Map<String, String> result) {
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        String status=result.get(BlockChainCore.STATUS);
        String message=result.get(BlockChainCore.MESSAGE);
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByQueryRequestId(requestId);
        if(blockAsynTran!=null){
        	Double amount=Double.parseDouble(result.get(BlockChainCore.AMOUNT));
        	if(amount!=null&&amount>0){
                blockAsynTran.setQueryStatus(status);
                blockAsynTran.setQueryMessage(message);
                this.blockAsynTranDao.updateBySelective(blockAsynTran);	
        	}else{
                blockAsynTran.setQueryStatus(ResultStatus.ServiceFail);
                blockAsynTran.setQueryMessage("区块链用户账户余额小于0");
                this.blockAsynTranDao.updateBySelective(blockAsynTran);	
        	}
        }
	}


	@Override
	public void modifyUserTrustAccount(Map<String, String> result) {
		// TODO Auto-generated method stub
		String address = result.get(BlockChainCore.ADDRESS);
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message = result.get(BlockChainCore.MESSAGE);
        //查询事务异步通知信息
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran=this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setUpdateTime(new Date());
        
        String queryRequestId=PKGenarator.getId();
        blockAsynTran.setQueryRequestID(queryRequestId);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
        String resultStatus = result.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.SUCCESS.equals(resultStatus)){
        	blockChainAccountService.updateUserTrustAccount(address);
        }
	}


	@Override
	public void modifyLoanPreheatTrustAccount(Map<String, String> result) {
		// TODO Auto-generated method stub
		String address = result.get(BlockChainCore.ADDRESS);
		opUnifyLoanTrustAccount(result);
		String resultStatus = result.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.SUCCESS.equals(resultStatus)){
        	blockChainAccountService.updateLoanPreheatTrustAccount(address);
        }
	}
    /**
     * 统一处理异步项目信任部分
     * @param result
     */
    private void opUnifyLoanTrustAccount(Map<String, String> result){
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message = result.get(BlockChainCore.MESSAGE);
        //查询事务异步通知信息
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran=this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setUpdateTime(new Date());
        
        String queryRequestId=PKGenarator.getId();
        blockAsynTran.setQueryRequestID(queryRequestId);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);;
    }

	@Override
	public void modifyLoanTrustAccount(Map<String, String> result) {
		// TODO Auto-generated method stub
/*		String address = result.get(BlockChainCore.ADDRESS);
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message = result.get(BlockChainCore.MESSAGE);
        //查询事务异步通知信息
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran=this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setUpdateTime(new Date());
        
        String queryRequestId=PKGenarator.getId();
        blockAsynTran.setQueryRequestID(queryRequestId);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
        String resultStatus = result.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.SUCCESS.equals(resultStatus)){
        	blockChainAccountService.updateLoanTrustAccount(address);
        }*/
		
		String address = result.get(BlockChainCore.ADDRESS);
		opUnifyLoanTrustAccount(result);
		String resultStatus = result.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.SUCCESS.equals(resultStatus)){
        	blockChainAccountService.updateLoanTrustAccount(address);
        }
	}
}
