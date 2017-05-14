/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingAction.java 
 *
 * Created: [2015-3-27 下午7:03:44] by haolingfeng
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
 * ProjectName: rain-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.crowdfunding.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockChain.util.BlockChainStringUtil;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.InterviewRecordModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingInvestService;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 股权众筹投标
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/crowdfundingInvest")
public class CrowdfundingInvestAction extends BaseAction{
    
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundingInvestAction.class);
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private ICrowdfundingInvestService crowdfundingInvestService;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;

    @Resource
    private IBlockChainQueryService blockChainQuerySerivce;
    
    @Resource
    private IBlockAsynTranService blockAsynTranService;
    @Resource
    private IBlockChainCrowdfundingService blockChainCrowdfundingService;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    
    
    /**
     * 
     * Description: 保存众筹约谈记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午6:45:22
     */
    @RequestMapping(value = "/saveInterviewRecord.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveInterviewRecord(HttpServletRequest request,InterviewRecordModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setApplyUser(userId);
            this.crowdfundingInvestService.saveInterview(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"约谈失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询领投/跟投人
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-26 下午8:06:50
     */
    @RequestMapping(value = "/getInvestor.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getInvestor(CrowdfundUserStuffModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            model.setSort("defaultSort");
            model.setAuthInvestor("1");
            SearchResult<Map<String,Object>> pageList = this.crowdfundingInvestService.getInvestorList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询领投人
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-26 下午8:06:50
     */
    @RequestMapping(value = "/getLeader.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getLeader(String loanNo){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,this.crowdfundingInvestService.getLoanLeader(loanNo));
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
   
    
    /**
     * 
     * Description:查询项目的领投记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午4:31:12
     */
    @RequestMapping(value = "/getLeadSupportList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getLeadSupportList(CrowdfundingSupportModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            model.setSort("supportTime");
            model.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
            SearchResult<Map<String,Object>> pageList = this.crowdfundingInvestService.getLeadSpportList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 定时任务
     * @author 80bug
     *
     */
	class MyTask  extends java.util.TimerTask{
         String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        String transNo="";
        String requestID="";
        public MyTask(Timer mytimer,String tranId,String status,String type,String transNo,String requestID){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.transNo = transNo;
        	this.requestID = requestID;
        }
		@Override
		public void run() {	
			try{
		    if(BlockChainCore.ResultStatus.SUBMIT_PROCESS.equalsIgnoreCase(status)){
		    	BlockAsynTranModel model = new BlockAsynTranModel();
		    	model.setParentId(transNo);
		    	model.setType(type);
		    	model = blockAsynTranService.selectByModel(model);
  				model.setUpdateTime(new Date());
		    	model.setTranId(transId);
		    	blockAsynTranService.update(model);
               if(model!=null)
  				   status = model.getStatus();
               else 
            	   status ="";

                if(BlockChainCore.ResultStatus.SUCCESS.equalsIgnoreCase(status)){
                	Map<String, Object> map = new HashMap<String,Object>();
                	map.put("transferNO", transNo);
                	map.put("status", status);
                	map.put("message", "");
                	
                	if(BlockChainCore.Type.SYSTEMLEND.equals(type)){ 
                		new CrowdfundingInvestAction().receiveTransferCallback(map);
                	}else if(BlockChainCore.Type.ORGANISERLEND.equals(type)){
                		new CrowdfundingInvestAction().OrganiserTransferTransferCallback(map);
                	}else if(BlockChainCore.Type.FLOW.equals(type)){
                		new CrowdfundingInvestAction().FlowTransferCallback(map);
                	}
                	timer.cancel();
                	System.gc();
                
                }else{
                	 String result = blockChainQuerySerivce.transactionQueryCommon(transId, "");
                }
		    }
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
    

    /**
     * 平台的异步回调
     * @param request
     * @param response
     * @throws IOException 
     */
    @RequestMapping(value = "/receiveTransferS2S.html", method = RequestMethod.POST)
    public void receiveRechargeCallbackS2S(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	//String str = HttpRequestUtils.getRequestPostStr(request);
        logger.info("=============转让接口异步回调开始调用============");
    	Map<String ,Object>map = this.getBlockChainParams();
		Timer timer = new Timer();

		if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(map.get("status"))){
            this.receiveTransferCallback(map);
    	}else if(BlockChainCore.ResultStatus.SUBMIT_PROCESS.equals(map.get("status"))){
         
    		timer.schedule( new MyTask(timer,(String)map.get("transactionID"),BlockChainCore.ResultStatus.SUBMIT_PROCESS,BlockChainCore.Type.ORGANISERLEND,(String)map.get("transferNO"),(String)map.get("requestID")), 1000,1000);	
    	}       
    }

    /**()
     * 平台的异步回调
     * @param request
     * @param response
     * @throws IOException 
     */
    @RequestMapping(value = "/receiveTransferS2S1.html", method = RequestMethod.POST)
    public void receiveRechargeCallbackS2S1(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	//String str = HttpRequestUtils.getRequestPostStr(request);
        logger.info("=============转让接口异步回调开始调用============");
    	Map<String ,Object>map = this.getBlockChainParams();
		Timer timer = new Timer();

		if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(map.get("status"))){
            this.receiveTransferCallback(map);
    	}else if(BlockChainCore.ResultStatus.SUBMIT_PROCESS.equals(map.get("status"))){
         
    		timer.schedule( new MyTask(timer,(String)map.get("transactionID"),BlockChainCore.ResultStatus.SUBMIT_PROCESS,BlockChainCore.Type.ORGANISERLEND,(String)map.get("transferNO"),(String)map.get("requestID")), 1000,1000);	
    	}       
    }
    /**
     * 流标异步回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/flowTransferS2S.html")
    public void flowTransferCallbackS2S(HttpServletRequest request,HttpServletResponse response) {
        logger.info("=============项目流标区块链支付接口异步回调开始调用============");
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("==============项目流标区块链支付接口通知参数："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID = map.get("requestID");
        logger.info("============异步通知状态-status:"+status);
 	       try {
			Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = blockAsynTranDao.findByRequestId(requestID);
        if(blockAsynTran==null){
	        blockAsynTran = new BlockAsynTranModel();
	        blockAsynTran.setParentId(BlockChainStringUtil.getUniqueTransferNoDec(transferNO));
	       	blockAsynTran.setType(BlockChainCore.Type.FLOW);
	       	blockAsynTran.setRequestID(requestID);
	       	blockAsynTran.setCreateTime(new Date());
	        blockAsynTran.setParentId(transferNO.substring(0,16));
	       	blockAsynTran.setStatus(status);
	       	blockAsynTran.setTranId(transactionID);
	       	blockAsynTranService.save(blockAsynTran);
        }
        blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        blockAsynTran.setStatus(status);
        blockAsynTran.setTranId(transactionID);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        //查询支持信息
//        this.crowdfundingSupportDao.getByOrderId(transferNO);
        Timer timer = new Timer();
    	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
    		//将状态改为流标
    		logger.info("===============>事务成功，将状态改为流标===============");
    		crowdfundingInvestService.sendFlowSuccess(requestID,timer,false);
    	}else{
    		timer.schedule( new MyFlowTask(timer,transactionID,status,transferNO,requestID),1000,3000);	
    	}
    }
    
	class MyFlowTask  extends java.util.TimerTask{
        String transId="";
        Timer timer =null;
        String status ="";
        String transNo="";
        String requestID="";
        public MyFlowTask(Timer mytimer,String tranId,String status,String transNo,String requestID){
           	this.timer = mytimer;
           	this.transId = tranId;
           	this.status = status;
           	this.transNo = transNo;
           	this.requestID = requestID;
        }
   		@Override
   		public void run() {	
   			logger.info("===========>requestID-"+requestID+"-查询事务==========");
			blockChainQuerySerivce.transactionQuery(transId,requestID);
			try{
                Thread.sleep(500);
                logger.info("================处理流标数据======================");
                crowdfundingInvestService.sendFlowSuccess(requestID,timer,true);
			}catch(Exception e){
				e.printStackTrace();
				timer.cancel();
            	System.gc();
			}
   		}		
	}
    private String receiveTransferCallback(Map<String, Object> map) {
    	String  handlerResult = "false";
		try {
			String transferNO = (String)map.get("transferNO");
			BlockAsynTranModel blockAsynTranModel = new BlockAsynTranModel();
			blockAsynTranModel.setParentId(transferNO);
			blockAsynTranModel.setType(BlockChainCore.Type.SYSTEMLEND);
			BlockAsynTranModel blockAsynTran =blockAsynTranService.selectByModel(blockAsynTranModel);
			String transactionID = (String)map.get("transactionID");
			blockAsynTran.setTranId(transactionID);

			if(map.get("status").equals("TransactionSuccess")){
			    crowdfundingInvestService.dealWithSystemTransaction(blockAsynTran.getParentId());
				blockAsynTran.setStatus("Success");
				lendAfter(blockAsynTran.getParentId());
				handlerResult ="true";
			}else{
			
			}
			blockAsynTranService.update(blockAsynTran);

		} catch(ApplicationException e){
			throw new ApplicationException(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("放款失败！"+e.getMessage());
		}
		return handlerResult;
	}
    private String OrganiserTransferTransferCallback(Map<String, Object> map) {
		try {
			String transferNO = (String)map.get("transferNO");
			BlockAsynTranModel blockAsynTranModel = new BlockAsynTranModel();
			blockAsynTranModel.setParentId(transferNO);
			blockAsynTranModel.setType(BlockChainCore.Type.SYSTEMLEND);
			BlockAsynTranModel blockAsynTran =blockAsynTranService.selectByModel(blockAsynTranModel);
			String transactionID = (String)map.get("transactionID");
			blockAsynTran.setTranId(transactionID);
			if(map.get("status").equals("TransactionSuccess")){
				crowdfundingInvestService.dealWithOrganiserTransaction(blockAsynTran.getParentId());
				blockAsynTran.setStatus("success");
				blockAsynTranService.update(blockAsynTran);
				lendAfter(blockAsynTran.getParentId());
			}else{
				blockAsynTran.setStatus(map.get("status").toString());
				blockAsynTran.setMessage(map.get("message").toString());


	        	 String message=map.get("message")==null?"":map.get("message").toString();
		       	 throw new ApplicationException("投资失败-【"+message+"】！");
			}
			blockAsynTranService.update(blockAsynTran);

		} catch(ApplicationException e){
			throw new ApplicationException(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("投资失败！");
		}
		return null;
	}
    private void lendAfter(String loanNo){
		String operateType = AuditLogConstants.TYPE_SEND;
        
   	 //发送放款请求
       //blockChainCrowdfundInvestService.sendLendTrans(loanNo);
       //更新投资
       CrowdfundingModel loan = crowdfundingInvestService.getByloanNo(loanNo);
       if("payed".equals(loan.getPlatformTransferState())&&"payed".equals(loan.getLoanTransferState())){
       
       List<CrowdfundingSupportModel> supportList = crowdfundingInvestService.getNoLendSupportList(loanNo);
       for(CrowdfundingSupportModel support:supportList){
    	   crowdfundingInvestService.updateSupportAfterLend(support, loan);
       }
      /* String operator = MyRequestContextHolder.getCurrentUser()
               .getAdminId();*/
       //更新项目
       crowdfundingInvestService.updateAfterLoan(loan, "");
      //筹款结束 如果该项目为产品,分配抽奖编号
       if(loan.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.PRODUCT)){
    	   blockChainCrowdfundingService.saveUserPrize(loan.getLoanNo());
       }
       AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
               AuditLogConstants.RESULT_SUCCESS,"项目编号："+loanNo);
       }
   }
     
    private String FlowTransferCallback(Map<String, Object> map) {
		try {
			String transactionID = (String)map.get("transactionID");
            String state = map.get("state").toString();
			BlockAsynTranModel blockAsynTran =blockAsynTranService.selectByTranId(transactionID);

			if(map.get("state").equals("TransactionSuccess")){
				blockAsynTran.setStatus("success");
				blockAsynTranService.update(blockAsynTran);
				CrowdfundingModel loan = this.crowdfundingInvestService.getByloanNo(blockAsynTran.getParentId());
			}else{
				 blockAsynTran.setStatus(state);
				 blockAsynTran.setMessage(map.get("message").toString());
	        	 blockAsynTranService.update(blockAsynTran);
	        	 String message=map.get("message")==null?"":map.get("message").toString();
		       	 throw new ApplicationException("投资失败-【"+message+"】！");
			}
		} catch(ApplicationException e){
			throw new ApplicationException(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("投资失败！");
		}
		return null;
	}
    
    /**
     * 放款 发起人的异步回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/OrganiserTransferS2S.html", method = RequestMethod.POST)
    public void investorTransferS2S(HttpServletRequest request,HttpServletResponse response) {
        logger.info("=============转让接口异步回调开始调用============");
    	Map<String ,Object>map = this.getBlockChainParams();
		Timer timer = new Timer();
		if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(map.get("status"))){
            this.receiveTransferCallback(map);
    	}else if(BlockChainCore.ResultStatus.SUBMIT_PROCESS.equals(map.get("status"))){
    		 //防止并发            
            ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);  
            pool.schedule((new MyTask(timer,(String)map.get("transactionID"),BlockChainCore.ResultStatus.SUBMIT_PROCESS,BlockChainCore.Type.ORGANISERLEND,(String)map.get("transferNO"),(String)map.get("requestID"))), 20000, TimeUnit.MILLISECONDS);  
      	}
			
       
    }
    
    
    public static void main(String[] args) {
		String orderId = PKGenarator.getOrderId();
		System.out.println(orderId);
		String aa = BlockChainStringUtil.getUniqueTransferNoEnc(orderId);
		System.out.println(aa);
		System.out.println(aa.substring(0,16));
	}
}
