package com.fbd.web.app.letvPay.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

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
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.web.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
import com.fbd.web.app.letvPay.service.ILetvPayInvestService;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System..
 * 
 * Description:投资
 *s
 * @author wuwenbin
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/letvPay/invest")
public class LetvPayInvestAction extends BaseAction{
    
    private static final long serialVersionUID = 583651072172660381L;

    private static final Logger logger = LoggerFactory.getLogger(LetvPayInvestAction.class);
    @Resource
    private ILetvPayInvestService letvPayInvestService;
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
	@Resource
	private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IUserDao userDao;
    @Resource 
    private IBlockAsynTranService blockAsynTranService;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private ICrowdfundingService crowdfundingService;
    @Resource
    private IBlockChainQueryService blockChainQuerySerivce;

	
	
    /**
     * 
     * Description: 领头支持前验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/checkBeforeLendSupport.html", method = RequestMethod.POST)
    public  @ResponseBody
    Map<String, Object> checkBeforeLendSupport(HttpServletRequest request,CrowdfundingSupportModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            model.setSupportUser(userId);
            letvPayInvestService.checkBeforeLendSupport(model,false);
            if(!FbdCoreConstants.BLOCK_CHAIN_IS_MOCK)
              letvPayInvestService.checkLoanAccountForBlockChain(model.getLoanNo());
            resultMap.put(SUCCESS, true);
        }catch (ApplicationException e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "支持验证不通过");
        }
        return resultMap;
    }
    
    

    /**
     * 
     * Description: 保存领头投资记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/saveLendSupport.html", method = RequestMethod.POST)
    public  @ResponseBody
    Map<String, Object> saveLendSupport(HttpServletRequest request,CrowdfundingSupportModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
        	String userId = this.getUserId(request);
            model.setSupportUser(userId);
            letvPayInvestService.checkBeforeLendSupport(model,false);
        	if(!FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
        	    //投资前检测项目是否区块链开户
        	    letvPayInvestService.checkLoanAccountForBlockChain(model.getLoanNo());
        	}
            model.setSupportUser(userId);
            model.setBuyType("leaderPay");
            String orderId = this.letvPayInvestService.saveUserSupport(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, orderId);
        }catch(ApplicationException e1){
        	 logger.error(e1.getMessage());
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG,e1.getMessage());
             return resultMap;
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "保存领头投资失败");
        }
        return resultMap;
    }
    
    
    /**
     * Description: 用户投资(领投)
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/submitLendInvest.html", method = RequestMethod.POST)
    public @ResponseBody
    	Map<String, Object> submitLendInvest(
                HttpServletRequest request,HttpServletResponse response ,CrowdfundingSupportModel model) {
        logger.info("=============全额投资接口调用开始============");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //发送请求
        try{
        	String userId = this.getUserId(request);
            model.setSupportUser(userId);
            //购买前验证
            this.letvPayInvestService.checkBeforeLendSupport(model,true);
            //购买
            model.setBuyType("leaderPay");
            this.letvPayInvestService.createFullInvest(model);
            resultMap.put(SUCCESS, true); 
        }catch(ApplicationException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }

    /**
     * 
     * Description: 跟投支持前验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/checkBeforeSupport.html", method = RequestMethod.POST)
    public  @ResponseBody
    Map<String, Object> checkBeforeSupport(HttpServletRequest request,CrowdfundingSupportModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            model.setSupportUser(userId);
            letvPayInvestService.checkBeforeSupport(model,false);
	        if(!FbdCoreConstants.BLOCK_CHAIN_IS_MOCK)
	             letvPayInvestService.checkLoanAccountForBlockChain(model.getLoanNo());
            resultMap.put(SUCCESS, true);
        }catch (ApplicationException e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "支持验证不通过");
        }
        return resultMap;
    }

    
    /**
     * 
     * Description: 保存跟投投资记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/saveFollowSupport.html", method = RequestMethod.POST)
    public  @ResponseBody
    Map<String, Object> saveFollowSupport(HttpServletRequest request,CrowdfundingSupportModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
        	String userId = this.getUserId(request);
            model.setSupportUser(userId);
            letvPayInvestService.checkBeforeSupport(model,false);
            model.setSupportUser(userId);
            model.setBuyType("investPay");
            System.out.println("LeadInvestor:"+model.getLeadInvestor());
            String orderId = this.letvPayInvestService.saveUserSupport(model);
            resultMap.put(MSG, orderId);
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "保存跟投投资失败");
        }
        return resultMap;
    }
    
    /**
     * 验证用户是否可以支付尾款
     * @param request
     * @param response
     * @param loanNo
     * @return
     */
    @RequestMapping(value = "/checkIsIntentionPay.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> checkIsIntentionPay(
                HttpServletRequest request,HttpServletResponse response ,String loanNo){
    	CrowdfundingModel model = crowdfundingService.getByloanNo(loanNo);
    	boolean flag = letvPayInvestService.checkIsIntentionPay(model);
    	Map<String,Object>map =  new HashMap<String,Object>();
    	map.put(SUCCESS, flag);
    	return map;
    }
    /**
     * 
     * Description: 用户投资(跟投)
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/submitFullInvest.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> submitFullInvest(
                HttpServletRequest request,HttpServletResponse response ,CrowdfundingSupportModel model) {
        logger.info("=============网关支付接口开始调用============");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //发送请求
        try{
        	String userId = this.getUserId(request);
            model.setSupportUser(userId);
            //购买前验证
            this.letvPayInvestService.checkBeforeSupport(model,true);
            //购买
            model.setBuyType("investPay");
            //验证项目是否在区块链开户
            if(!FbdCoreConstants.BLOCK_CHAIN_IS_MOCK) 
            	letvPayInvestService.checkLoanAccountForBlockChain(model.getLoanNo());
            this.letvPayInvestService.createFullInvest(model);
            
    	    resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }


	@RequestMapping(value = "/investPayNotify.html", method = RequestMethod.POST)
    public void investPayNotify(HttpServletRequest request,HttpServletResponse response) {
		CrowdfundingSupportModel supportModel =null;
        try{
			logger.info("=============项目投资支付接口异步回调开始调用============");
	        Map<String, String> map = this.getBlockChainParamsStr();
	        logger.info("=============项目投资支付接口通知参数："+map);
	        
	        String transactionID =  map.get("transactionID").toString();
	        String transferNO =  map.get("transferNO").toString();
	        String status = map.get("status")== null?"":map.get("status").toString();
	        String requestID = map.get("requestID");
	        
	        Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
	        BlockAsynTranModel blockAsynTran = blockAsynTranDao.findByRequestId(requestID);
	
	        if(blockAsynTran!=null){
	      		 supportModel = this.crowdfundingSupportDao.getByOrderId(blockAsynTran.getParentId());
	
	            if(BlockChainCore.ResultStatus.FAIL.equals(status)){
	            	 if("locking".equals(supportModel.getPayState())){
	            		 supportModel.setPayState(CrowdfundCoreConstants.crowdFundPayState.noPay);
	            	 }else if("locking".equals(supportModel.getPayState())){
	            		 supportModel.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.noPay); 
	            	 }
	            	 supportModel.setNewBuyNum(0);
	            	 crowdfundingSupportDao.updateBySelective(supportModel);
	             	return ;
	             }else{
	            	//查询支付数据
	                 String type="";
	          		 String buyType = supportModel.getBuyType();
	                 if("investPay".equals(buyType)){  //跟投
	                 	 //转账需要判断 是否为意向金支付
	               		if(null != supportModel.getIntentionAmt() && supportModel.getIntentionAmt()>0){
	               			type = BlockChainCore.Type.intention_pay;
	               		}else{
	               			type = BlockChainCore.Type.invest_pay;
	               		}
	                 }else if("leaderPay".equals(buyType)){  //领投
	                 	  type = BlockChainCore.Type.leader_pay;
	                 }
	                 blockAsynTran.setStatus(status);
	                 blockAsynTran.setTranId(transactionID);
	                 this.blockAsynTranDao.updateBySelective(blockAsynTran);
	         		 Timer timer = new Timer();
	             	 if(BlockChainCore.ResultStatus.SUCCESS.equals(map.get("status"))){
	             		timer.schedule( new MyInvestTask(timer,transactionID,status,type,supportModel,transferNO,requestID), 2000,2000);	
	             	 }
	             }
	        }
        }catch(Exception e){
    	    e.printStackTrace();
    	    if("locking".equals(supportModel.getPayState())){
    	    	supportModel.setPayState(CrowdfundCoreConstants.crowdFundPayState.noPay);
      	    }else if("locking".equals(supportModel.getPayState())){
      	    	supportModel.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.noPay); 
      	    }
       }finally{
    	   supportModel.setNewBuyNum(0);
      	   crowdfundingSupportDao.updateBySelective(supportModel);
       }
    }
	/**
	 * 定时任务
	 * @author 80bug
	 *
	 */
	class MyInvestTask  extends java.util.TimerTask{
         String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        String transNo="";
        String requestId="";
        CrowdfundingSupportModel supportModel = null;
        public MyInvestTask(Timer mytimer,String tranId,String status,String type,CrowdfundingSupportModel supportModel,String transNo,String requestId){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.supportModel = supportModel;
        	this.transNo = transNo;
        	this.requestId = requestId;
        }
		@Override
		public void run() {	
    			blockChainQuerySerivce.transactionQuery(transId,requestId);
    			try{
                    Thread.sleep(500);
      				BlockAsynTranModel blockAsynTran =blockAsynTranDao.findByRequestId(requestId);
      				status = blockAsynTran.getQueryStatus();
                    if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equalsIgnoreCase(status)){
        				letvPayInvestService.investPayAfter(supportModel.getOrderId(),requestId,timer);
                    	timer.cancel();
                    	System.gc();
                    }else if(BlockChainCore.ResultStatus.FAIL.equalsIgnoreCase(status)){
                    	 CrowdfundingSupportModel model = new CrowdfundingSupportModel();
                    	 model.setId(supportModel.getId());
                    	 model.setNewBuyNum(0);
                    	 crowdfundingSupportDao.updateBySelective(model);
                    	 timer.cancel();
                     	System.gc();
                    }
    			}catch(Exception e){
    				e.printStackTrace();
    				timer.cancel();
                	System.gc();
    			}
    		    }
		
		
		
	}
		
	
	/**
     * Description: 用户支付意向金尾款
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/createInvestByIntentionEndAmt.html", method = RequestMethod.POST)
    public @ResponseBody
    	Map<String, Object> createInvestByIntentionEndAmt(
                HttpServletRequest request,HttpServletResponse response ,CrowdfundingSupportModel model) {
        logger.info("=============网关支付接口开始调用============");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //发送请求
        try{
        	String userId = this.getUserId(request);
            model.setSupportUser(userId);
            this.letvPayInvestService.checkBeforeIntentionSupport(model);
            if(!FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
	            Map<String,Object> map = this.letvPayInvestService.createInvestByIntentionEndAmt(model);
		        
	            if(map.get("status").equals(BlockChainCore.ResultStatus.ServiceSuccess)){
	            	resultMap.put(SUCCESS, true);
	            	resultMap.put("requestId", map.get("requestID")) ;
	            }else{
	            	resultMap.put(SUCCESS, false);
	            	resultMap.put(MSG,"意向金支付失败，请联系管理人员") ;
	            }
            }else{
	            try {
	            	 Map<String,Object> map = new HashMap<String,Object>();
	            	 model =crowdfundingSupportDao.getByOrderId(model.getOrderId());
	    			 map.put("intentionNo", model.getIntentionNo());/*
	            	    trusteeshipOperationService.updateOperationModel(map.get("intentionNo").toString(), 
	    	                    LetvPayConstants.BusiType.invest, 
	    	                    null, SxyPayConstants.THIRD_ID, "status", map.get("status").toString());*/
    				this.letvPayInvestService.updateInvestIntentionEndAmt(map);
    				resultMap.put(SUCCESS, true);
    				resultMap.put(MSG, "意向金支付成功!");

	    		} catch (Exception e){
	    			e.printStackTrace();
	    			resultMap.put(MSG, e.getMessage());

	    		};
	    		}
           return resultMap;
        }catch(ApplicationException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "支付失败!");
        }
        return resultMap;
    }
    
    
    @SuppressWarnings("unused")
	private String receiveTransferIntentionCallback(Map<String, Object> map) {
    	String  handlerResult = FbdConstants.RESULT_TRUE;
		try {
			if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(map.get("status"))){
				trusteeshipOperationService.updateOperationModel(map.get("intentionNo").toString(), 
	                    LetvPayConstants.BusiType.invest, 
	                    null, SxyPayConstants.THIRD_ID, "status", map.get("status").toString());
				this.letvPayInvestService.updateInvestIntentionEndAmt(map);
			}	else{
				//更新投资尾款失败 从区块链中间账户转账给投资人
				this.letvPayInvestService.updateInvestIntentionFail(map);
				handlerResult = FbdConstants.RESULT_FALSE;
			}
		} catch (Exception e) {
			handlerResult = e.getMessage();
		}
		return handlerResult;
	}


	@RequestMapping(value = "/receiveTransferIntentionS2S.html", method = RequestMethod.POST)
    public void receiveTransferIntentionS2S(HttpServletRequest request,HttpServletResponse response) {
		  
		   logger.info("=============项目投资意向金尾款支付接口异步回调开始调用============");
	        Map<String, String> map = this.getBlockChainParamsStr();
	        logger.info("=============项目投资意向金尾款支付接口通知参数："+map);
	        
	        String transactionID =  map.get("transactionID").toString();
	        String transferNO =  map.get("transferNO").toString();
	        String status = map.get("status")== null?"":map.get("status").toString();
	        String requestID = map.get("requestID");
	        
	        //查询事务异步通知信息
	        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
		try{
		 
	        Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);

	        if(blockAsynTran!=null){
	        	  blockAsynTran.setStatus(status);
	                 blockAsynTran.setTranId(transactionID);
	                 this.blockAsynTranDao.updateBySelective(blockAsynTran);
	            if(BlockChainCore.ResultStatus.FAIL.equals(status)){
	            	CrowdfundingSupportModel model =crowdfundingSupportDao.getByOrderId(blockAsynTran.getParentId());
	            	model.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.noPay);
	        		this.crowdfundingSupportDao.updateByOrderNo(model);
	             	return ;
	             }else{
	               
	                 String type="";   
	         		 Timer timer = new Timer();
	             	 if(BlockChainCore.ResultStatus.SUCCESS.equals(map.get("status"))){
	             		timer.schedule( new MyIntentionTask(timer,transactionID,status,type,transferNO,requestID), 2000,2000);	

	             	}
	             }
	        }
		   }catch(Exception e){
			   e.printStackTrace();
			   CrowdfundingSupportModel model =crowdfundingSupportDao.getByOrderId(blockAsynTran.getParentId());
           	model.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.noPay);
       		this.crowdfundingSupportDao.updateByOrderNo(model);
		   }
    } 
	
	/**
	 * 意向金定时任务
	 * @author 80bug
	 *
	 */
	class MyIntentionTask  extends java.util.TimerTask{
         String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        String transNo="";
        String requestId="";
        public MyIntentionTask(Timer mytimer,String tranId,String status,String type,String transNo,String requestId){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.transNo = transNo;
        	this.requestId = requestId;
        }
		@Override
		public void run() {	
			
    			blockChainQuerySerivce.transactionQuery(transId,requestId);
    			try{
                    Thread.sleep(500);

      				BlockAsynTranModel blockAsynTran =blockAsynTranDao.findByRequestId(requestId);
      				status = blockAsynTran.getQueryStatus();

                    if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equalsIgnoreCase(status)){

                    	
        				letvPayInvestService.IntentionAsynSucess(requestId,blockAsynTran);

                    	timer.cancel();
                    	System.gc();
                    
                    }
    			}catch(Exception e){
    				e.printStackTrace();
    				timer.cancel();
                	System.gc();
    			}
    	 }		
	}
	
	@RequestMapping(value = "/checkBeforeIntentionPay.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> checkBeforeIntentionPay(HttpServletRequest request,String orderId){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        CrowdfundingSupportModel model = crowdfundingSupportDao.getByOrderId(orderId);
        boolean flag = letvPayInvestService.checkBeforeIntentionPay(model);
        resultMap.put(SUCCESS, flag);
        return resultMap;
	}    
        
}
