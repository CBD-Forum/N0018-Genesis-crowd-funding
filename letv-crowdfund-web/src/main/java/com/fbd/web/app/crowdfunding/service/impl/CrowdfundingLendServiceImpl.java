package com.fbd.web.app.crowdfunding.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.MapUtil;
import com.fbd.core.web.MyRequestContextHolder;
import com.fbd.web.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingLendService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;


@Service(value="crowdfundingLendService")
public class CrowdfundingLendServiceImpl implements ICrowdfundingLendService {

	
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundingLendServiceImpl.class);
    
    @Resource
    private IUserDao userDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private ISystemBillService sysBillService;
    @Resource
	private IBusinessConfigDao businessConfigDao;
    @Resource
    private ICrowdfundingService crowdfundingService;	
    @Resource
    private IBlockAsynTranService blockAsynTranService;
    @Resource
    private IBlockChainCrowdfundingService blockChainCrowdfundingService;
	@Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    
    
    
    /**
     * 放款区块链项目方转账成功后的后续操作
     * @param orderId
     */
    public void lendProjectTransferAfter(String requestID,Timer timer,boolean notifyFlag){
    	BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        String query_status = blockAsynTran.getQueryStatus();
        String loanNo = blockAsynTran.getParentId();
        Random random = new Random();
        try{
        	Thread.sleep(random.nextInt(1000));
        }catch(Exception e){
        	e.printStackTrace();
        }
        CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
        if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(query_status)){
        	crowd.setLoanTransferState("payed");
        	this.crowdfundingDao.updateByLoanNo(crowd);
        	if("payed".equals(crowd.getPlatformTransferState())){ //如果两笔转账都成功，则放款成功
        		this.loanSendLendAlter(loanNo, crowd,"project");
        		if(notifyFlag){
            		timer.cancel();
            	}
        	}
        }
    }

    
    
    
	
    /**
     * 放款区块链平台服务费转账成功后的后续操作
     * @param orderId
     */
    public void lendPlatformTransferAfter(String requestID,Timer timer,boolean notifyFlag){
    	BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        String query_status = blockAsynTran.getQueryStatus();
        String loanNo = blockAsynTran.getParentId();
        Random random = new Random();
        try{
        	Thread.sleep(random.nextInt(1000));
        }catch(Exception e){
        	e.printStackTrace();
        }
        CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
        if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(query_status)){
        	crowd.setPlatformTransferState("payed");
        	this.crowdfundingDao.updateByLoanNo(crowd);
        	if("payed".equals(crowd.getLoanTransferState())){ //如果两笔转账都成功，则放款成功
        		this.loanSendLendAlter(loanNo, crowd,"platform");
        		if(notifyFlag){
            		timer.cancel();
            	}
        	}
        }
    }
	
	//项目放款成功后续操作
	public void loanSendLendAlter(String loanNo,CrowdfundingModel crowd,String type){
		
		logger.info("===========当前放款处理标识:"+type);
		 
        Map<String,Object> supportAmtMap = this.crowdfundingSupportDao.selectNoLendAmtByLoanNo(crowd.getLoanNo());
		//支持总金额
		double supportSumAmt = (Double) supportAmtMap.get("supportAmt");
		//运费总金额
		double transAmt = (Double) supportAmtMap.get("transAmt");
		//平台手续费率
		Double chargeScale = crowd.getChargeFee();
		//计算平台手续费
	    double chargeFee = Arith.mul(supportSumAmt, chargeScale);
	    //项目方应结算款项(不扣除手续费的金额)
	    double projectFee =Arith.round(supportSumAmt + transAmt);
	    
	    //查询项目是否放款完成
	    CrowdfundingModel crowdModel = this.crowdfundingDao.getByloanNo(crowd.getLoanNo()); 
	    if(CrowdfundCoreConstants.crowdFundingState.lended.getValue().equals(crowdModel.getLoanState())){
	    	logger.info("项目"+crowd.getLoanNo()+"已经放款完成，无需重复处理");
	    	return ;
	    }
        //查询未放款的投资(逻辑修改了  为放款的订单状态为add)
        List<CrowdfundingSupportModel> supportList = this.crowdfundingSupportDao.getByLoanNo(loanNo);
       
        for(CrowdfundingSupportModel support:supportList){
    	   support.setState(CrowdfundCoreConstants.crowdFundOrderState.lended);
           crowdfundingSupportDao.update(support);
           //添加投资放款账单
           this.userBillService.addInvestorBill(support, crowd.getLoanName(), "项目【"+crowd.getLoanName()+"】,项目编号【"+crowd.getLoanNo()+"】", support.getSupportUser());
           //给支持者发送投标放款信息
           this.sendInvestCashMessage(support, crowd);
       }
       crowd.setLoanState(CrowdfundCoreConstants.crowdFundingState.lended.getValue());
       crowd.setCashTime(new Date());
       crowdfundingDao.update(crowd);
        
        
//       this.crowdfundingService.addLoanAudit(crowd.getCashUser(),loanNo, 
//                CrowdfundCoreConstants.loanAuditAction.cash.getValue(),
//                null, CrowdfundCoreConstants.crowdFundingState.lended.getValue());
        
        //添加项目方进账账单
       double tradeAmt = Arith.round(supportSumAmt + transAmt);
       this.userBillService.addBorrowerBill(loanNo, crowd.getLoanName(), "", crowd.getLoanUser(), tradeAmt);
       //添加项目方服务费出账账单
       this.userBillService.addLoanBorrowFeeBill(loanNo,  crowd.getLoanName(),crowd.getLoanUser(), chargeFee);
       //添加平台服务费账单
       this.sysBillService.addBorrowerFeeSystemBill(chargeFee, loanNo, loanNo, crowd.getLoanName());
        //给发起人发送放款信息
       this.sendLoanCashMessage(crowd);
        
        //筹款结束 如果该项目为产品,分配抽奖编号
       if(crowd.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.PRODUCT)){
    	   try{
    		   blockChainCrowdfundingService.saveUserPrize(crowd.getLoanNo());
    	   }catch(Exception e){
    		   logger.info("===============项目放款-产品项目分配抽奖编号出现异常========================");
    	   }
       }
       /*
        AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, AuditLogConstants.TYPE_SEND, 
               AuditLogConstants.RESULT_SUCCESS,"项目编号："+loanNo);*/
	}
	
	
    /**
     * 
     * Description: 发送众筹放款信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendInvestCashMessage(CrowdfundingSupportModel model,CrowdfundingModel loan){
       Map<String, String> params = new HashMap<String,String>();
       params.put("time",DateUtil.dateTime2Str(model.getSupportTime(), null));
       params.put("money",Arith.format(model.getSupportAmt()));
       params.put("loanName",loan.getLoanName());
       params.put("orderId",model.getOrderId());
       params.put("cashTime",DateUtil.dateTime2Str(new Date(), null));
       try{
           logger.info("发送投标放款手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_SUPPORT_CASH_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getSupportUser(), params);
           logger.info("发送投标放款手机短信结束");
       }catch(Exception e){
           logger.error("发送投标放款手机短信失败，"+e.getMessage());
       }
       try{
            logger.info("发送投标放款站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_SUPPORT_CASH_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getSupportUser(), params);
            logger.info("发送投标放款站内信结束");
        }catch(Exception e){
            logger.error("发送投标放款站内信失败，"+e.getMessage());
        }
    }
    /**
     * 
     * Description: 发送项目放款消息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendLoanCashMessage(CrowdfundingModel model){
       double chargeFee = Arith.round(model.getApproveAmt()*model.getChargeFee());
       Map<String, String> params = new HashMap<String,String>();
       params.put("money",Arith.format(model.getApproveAmt()));
       params.put("loanName",model.getLoanName());
       params.put("loanNo",model.getLoanNo());
       params.put("fee",String.valueOf(chargeFee));
       params.put("actualAmt",String.valueOf(Arith.format(model.getApproveAmt()-chargeFee)));
       params.put("time",DateUtil.dateTime2Str(model.getCashTime(), null));
       try{
           logger.info("发送项目放款手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_CASH_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getLoanUser(), params);
           logger.info("发送项目放款手机短信结束");
       }catch(Exception e){
           logger.error("发送项目放款手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送项目放款站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_CASH_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getLoanUser(), params);
            logger.info("发送项目放款站内信结束");
        }catch(Exception e){
            logger.error("发送项目放款站内信失败，"+e.getMessage());
        }
    }
}
