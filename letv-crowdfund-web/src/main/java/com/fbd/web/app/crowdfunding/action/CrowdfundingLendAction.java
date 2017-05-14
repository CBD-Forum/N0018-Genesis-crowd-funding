package com.fbd.web.app.crowdfunding.action;

import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.impl.BlockChainQueryServiceImpl;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.util.HttpServletUtils;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingLendService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;


/**
 * 项目放款异步通知
 * @author Administrator
 */
@Controller
@Scope("prototype")
@RequestMapping("/crowdfundingLend")
public class CrowdfundingLendAction extends BaseAction {
	
	
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundingLendAction.class);

    /**
     * 
     */
    private static final long serialVersionUID = -4803963548470295353L;

	@Resource
	private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private BlockChainQueryServiceImpl blockChainQuerySerivce;

    @Resource
    private ICrowdfundingService crowdfundingService;
    @Resource
    private ICrowdfundingLendService crowdfundingLendService;
	
    /**
     * 项目放款区块链项目方转账异步通知处理
     * @param request
     * @param res
     */
    @RequestMapping(value = "/lendProjectNotify.html")
    public  void lendProjectNotify(HttpServletRequest request,HttpServletResponse res) throws Exception{
        logger.info("=============项目放款区块链项目方转账异步通知开始============");
        //等待同步处理数据
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("============项目放款区块链项目方转账异步通知参数："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID = map.get("requestID");
        
        if(StringUtils.isEmpty(requestID)){
        	HttpServletUtils.printString(res, "SUCCESS");
        	return ;
        }
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        if(blockAsynTran!=null){
        	
        	 String loanNo =  blockAsynTran.getParentId();
             blockAsynTran.setStatus(status);
             blockAsynTran.setTranId(transactionID);
             this.blockAsynTranDao.updateBySelective(blockAsynTran);
     		 Timer timer = new Timer();
     		 //事务成功
         	 if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
         		 CrowdfundingModel crowd = this.crowdfundingService.getByloanNo(loanNo);
         		 crowd.setLoanTransferState("payed");
            	 this.crowdfundingService.updateCrowdFund(crowd);
            	 if("payed".equals(crowd.getPlatformTransferState())){ //如果两笔转账都成功，则放款成功
            		 crowdfundingLendService.loanSendLendAlter(loanNo, crowd,"project");
            	 }
         	}else if(BlockChainCore.ResultStatus.SUCCESS.equals(status)){
              	timer.schedule(new lendProjectNotifyTask(timer,transactionID,requestID,
              			 BlockChainCore.ResultStatus.SUCCESS,BlockChainCore.Type.lend_loan),1000,3000); 
         	}
        }
    }
    /**
     * 项目放款区块链项目方转账异步通知定时任务
     * @author 80bug
     */
    class lendProjectNotifyTask  extends java.util.TimerTask{
        String transId="";
        String requestID = "";
        Timer timer =null;
        String status ="";
        String type ="";
        public lendProjectNotifyTask(Timer mytimer,String tranId,String requestID,String status,String type){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.requestID = requestID;
        	this.status = status;
        	this.type = type;
        }
		@Override
		public void run() {	
			blockChainQuerySerivce.transactionQuery(transId,requestID);
			//处理数据
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			crowdfundingLendService.lendProjectTransferAfter(requestID, timer, true);
 
		}
	}
    
	
	
	
	
	
	
    /**
     * 项目放款区块链平台方转账异步通知处理
     * @param request
     * @param res
     */
    @RequestMapping(value = "/lendPlatformNotify.html")
    public  void lendPlatformNotify(HttpServletRequest request,HttpServletResponse res) throws Exception{
        logger.info("=============项目放款区块链项目方转账异步通知开始============");
        //等待同步处理数据
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("============项目放款区块链项目方转账异步通知参数："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID = map.get("requestID");
        
        if(StringUtils.isEmpty(requestID)){
        	HttpServletUtils.printString(res, "SUCCESS");
        	return ;
        }
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        
        if(blockAsynTran!=null){
        	 String loanNo = blockAsynTran.getParentId();
        	 blockAsynTran.setStatus(status);
             blockAsynTran.setTranId(transactionID);
             this.blockAsynTranDao.updateBySelective(blockAsynTran);
     		 Timer timer = new Timer();
     		 //事务成功
         	 if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
         		 
         		 CrowdfundingModel crowd = this.crowdfundingService.getByloanNo(loanNo);
         		 crowd.setPlatformTransferState("payed");
            	 this.crowdfundingService.updateCrowdFund(crowd);
            	 if("payed".equals(crowd.getLoanTransferState())){ //如果两笔转账都成功，则放款成功
            		 crowdfundingLendService.loanSendLendAlter(loanNo, crowd,"platform");
            	 }
         	}else if(BlockChainCore.ResultStatus.SUCCESS.equals(status)){
              	timer.schedule(new lendPlatformNotifyTask(timer,transactionID,requestID,
              			 BlockChainCore.ResultStatus.SUCCESS,BlockChainCore.Type.lend_platform),1000,3000); 
         	}
        }
    }
    /**
     * 项目放款区块链平台方转账异步通知定时任务
     * @author 80bug
     */
    class lendPlatformNotifyTask  extends java.util.TimerTask{
        String transId="";
        String requestID = "";
        Timer timer =null;
        String status ="";
        String type = "";
        public lendPlatformNotifyTask(Timer mytimer,String tranId,String requestID,String status,String type){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.requestID = requestID;
        	this.status = status;
        	this.type = type;
        }
		@Override
		public void run() {	
			blockChainQuerySerivce.transactionQuery(transId,requestID);
			//处理数据
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//处理数据
			crowdfundingLendService.lendPlatformTransferAfter(requestID, timer, true);
		}
	}	
}
