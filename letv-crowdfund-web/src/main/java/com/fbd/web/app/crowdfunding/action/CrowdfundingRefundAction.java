package com.fbd.web.app.crowdfunding.action;

import java.util.Date;
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
import com.fbd.core.app.blockChain.service.impl.BlockChainQueryServiceImpl;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.util.spring.SpringUtil;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
import com.fbd.web.app.crowdfunding.service.impl.CrowdfundingServiceImpl;
import com.fbd.web.app.user.service.IUserService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;


@Controller
@Scope("prototype")
@RequestMapping("/crowdfundingRefund")
public class CrowdfundingRefundAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(CrowdfundingRefundAction.class);

    @Resource
    private ICrowdfundingService crowdfundingService;
    @Resource
    private IUserService userService;
    @Resource
    private IBusinessConfigDao businessConfigDao;
    @Resource
    private IVerifyCodeService verifyCodeService;
	@Resource
	private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private IBlockChainQueryService blockChainQuerySerivce;
    
    //项目方退款异步通知
    @RequestMapping(value = "/loanAuditRefundNotify.html")
    public void loanAuditRefundNotify(HttpServletRequest request,HttpServletResponse res){
    	
        logger.info("=============退款项目方审核接口异步回调开始调用============");
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("=============退款项目方审核接口通知参数："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID =  map.get("requestID");
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        blockAsynTran.setStatus(status);
        blockAsynTran.setTranId(transactionID);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
      //更新退款数据
		CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(transferNO);
		
		Timer timer = new Timer();
    	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
    		supportModel.setRefundState(FbdCoreConstants.refundState.auditing);
    		supportModel.setRefuseLoanAuditTime(new Date());
           this.crowdfundingSupportDao.updateByOrderNo(supportModel);
    	}else if(BlockChainCore.ResultStatus.SUBMIT_PROCESS.equals(map.get("status"))){
         	timer.schedule(new loanAuditRefundTask(timer,transactionID,requestID,
         			 BlockChainCore.ResultStatus.SUBMIT_PROCESS,BlockChainCore.Type.REFUNDLOANAUDIT,supportModel),5000); 
    	}
    }
    /**
     * 退款项目方审核定时任务
     * @author 80bug
     *
     */
    class loanAuditRefundTask  extends java.util.TimerTask{
        String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        CrowdfundingSupportModel supportModel = null;
        String requestID = "";
        public loanAuditRefundTask(Timer mytimer,String tranId,String requestID,String status,String type,CrowdfundingSupportModel supportModel){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.supportModel = supportModel;
        	this.requestID = requestID;
        }
		@Override
		public void run() {	
			blockChainQuerySerivce.transactionQueryCommon(transId, "/letvPay/invest/receiveTransferS2S.html");
			//处理数据
			crowdfundingService.loanAuditRefundAfter(transId,requestID,timer, true);
		}
	}
    
    //查询项目方退款处理状态
    @RequestMapping(value = "/selectRefundStatus.html",method=RequestMethod.POST)
    public @ResponseBody Map<String,Object> selectRefundStatus(HttpServletRequest request,String orderId){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
    		CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(orderId);
    		resultMap.put(SUCCESS, true);
    		resultMap.put(MSG,FbdCoreConstants.refundState.auditing.equals(supportModel.getRefundState())?true:false);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put(SUCCESS, false);
    		resultMap.put(MSG,false);
    	}
    	return resultMap;
    } 
    
    
   /**===========================================================================
    **********************退款审核通过异步通知处理***************************************
   ===========================================================================**/ 
    
    /**
     * 退款审核通过异步通知处理(平台审核通过异步通知)
     * @param request
     * @param res
     */
    @RequestMapping(value = "/auditRefundPassedNotify.html")
    public void auditRefundPassedNotify(HttpServletRequest request,HttpServletResponse res){
    	
        logger.info("=============退款审核通过接口异步回调开始调用============");
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("=============退款审核通过接口通知参数："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID = map.get("requestID");
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        blockAsynTran.setStatus(status);
        blockAsynTran.setTranId(transactionID);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
      //更新退款数据
		CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(transferNO);
		Timer timer = new Timer();
    	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
          //处理数据
		  crowdfundingService.platformAuditRefundAfter(transactionID,requestID,"passed",null,false);
			
    	}else if(BlockChainCore.ResultStatus.SUBMIT_PROCESS.equals(map.get("status"))){
         	timer.schedule(new auditRefundPassesTask(timer,transactionID,requestID,
         			 BlockChainCore.ResultStatus.SUBMIT_PROCESS,BlockChainCore.Type.REFUNDPASSEDAUDIT,supportModel),5000); 
    	}
    }
    /**
     * 平台审核退款通过定时任务
     * @author 80bug
     */
    class auditRefundPassesTask  extends java.util.TimerTask{
        String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        CrowdfundingSupportModel supportModel = null;
        String requestID = "";
        public auditRefundPassesTask(Timer mytimer,String tranId,String requestID,String status,String type,CrowdfundingSupportModel supportModel){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.supportModel = supportModel;
        	this.requestID = requestID;
        }
		@Override
		public void run() {	
			blockChainQuerySerivce.transactionQueryCommon(transId, "/letvPay/invest/receiveTransferS2S.html");
			//处理数据
			crowdfundingService.platformAuditRefundAfter(transId,requestID,"passed",timer,true);
		}
	}
    
    
    
    
    
    
    
    
    /**===========================================================================
     **********************退款审核拒绝异步通知处理**********************************
    ===========================================================================**/ 
    
    /**
     * 退款审核拒绝异步通知处理
     * @param request
     * @param res
     */
    @RequestMapping(value = "/auditRefundRefuseNotify.html")
    public void auditRefundRefuseNotify(HttpServletRequest request,HttpServletResponse res){
    	
        logger.info("=============退款审核拒绝接口异步回调开始调用============");
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("=============退款审核拒绝接口通知参数："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID = map.get("requestID");
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        blockAsynTran.setStatus(status);
        blockAsynTran.setTranId(transactionID);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
        //更新退款数据
		CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(transferNO);
		
		Timer timer = new Timer();
    	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
           //处理数据
 		   crowdfundingService.platformAuditRefundAfter(transactionID,requestID,"refuse",null,false);
    	}else if(BlockChainCore.ResultStatus.SUBMIT_PROCESS.equals(map.get("status"))){
    		
         	timer.schedule(new auditRefundRefuseTask(timer,transactionID,requestID,
         			 BlockChainCore.ResultStatus.SUBMIT_PROCESS,BlockChainCore.Type.REFUNDREFUSEAUDIT,supportModel),5000); 
    	}
    }
    /**
     * 平台审核退款拒绝定时任务
     * @author 80bug
     */
    class auditRefundRefuseTask  extends java.util.TimerTask{
        String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        CrowdfundingSupportModel supportModel = null;
        String requestID = "";
        public auditRefundRefuseTask(Timer mytimer,String tranId,String requestID,String status,String type,CrowdfundingSupportModel supportModel){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.supportModel = supportModel;
        	this.requestID = requestID;
        }
		@Override
		public void run() {	
			blockChainQuerySerivce.transactionQuery(transId, requestID);
			//处理数据
			crowdfundingService.platformAuditRefundAfter(transId,requestID,"refuse",timer,true);
		}
	}
    
}
