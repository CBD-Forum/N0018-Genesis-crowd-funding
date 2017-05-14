package com.fbd.admin.app.crowdFund.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.admin.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.admin.app.crowdFund.service.ICrowdfundingLendService;
import com.fbd.admin.app.crowdFund.service.ICrowdfundingService;
import com.fbd.admin.web.MyRequestContextHolder;
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
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
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
    
    
    
	@Override
	public void sendLoanLend(String loanNo) {
		//查询项目信息
		CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
		
        String operator = MyRequestContextHolder.getCurrentUser()
                .getAdminId();
		crowd.setCashUser(operator);
		if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){ 
			this.loanSendLendAlter(loanNo,crowd);
			crowd.setPlatformTransferState("payed");
			crowd.setLoanTransferState("payed");
			this.crowdfundingDao.updateBySelective(crowd);
		}else{
		    //项目方转账请求
			lendProjectTransfer(crowd);
			//查询平台服务费
			Double chargeScale = crowd.getChargeFee();
			if(chargeScale>0){
				//平台服务费转账请求
				lendPlatformTransfer(crowd);
			}else{
				crowd.setPlatformTransferState("payed");
				this.crowdfundingDao.update(crowd);
			}
		}
	}
	
	/**
	 * 放款项目方转账
	 * @param loanNo
	 */
	public void lendProjectTransfer(CrowdfundingModel crowd){
		
		Map<String,Object> supportAmtMap = this.crowdfundingSupportDao.selectNoLendAmtByLoanNo(crowd.getLoanNo());
		//支持总金额
		double supportSumAmt = (Double) supportAmtMap.get("supportAmt");
		//运费总金额
		double transAmt = (Double) supportAmtMap.get("transAmt");
		//平台手续费率
		Double chargeScale = crowd.getChargeFee();
		//计算平台手续费
	    double chargeFee = Arith.mul(supportSumAmt, chargeScale);
	    //项目方应结算款项
	    double projectFee =Arith.round(supportSumAmt - chargeFee + transAmt);
	    //进行转账操作
	    String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
    	//保存操作数据
		String operationId = PKGenarator.getId();
		UserModel loanUser = this.userDao.findByUserId(crowd.getLoanUser());
		String notifyUrl = FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfundingLend/lendProjectNotify.html";
		//放款项目方转账请求参数
		String requestID = PKGenarator.getOrderId();
		//获取组装参数
		Map<String,String> params = this.getsendLendParam(operationId,requestID,crowd, loanUser, projectFee, notifyUrl,false);
		logger.info("项目放款项目方转账请求参数params:"+params);
		
        trusteeshipOperationService.saveOperationModel(operationId,crowd.getLoanNo(),
        		crowd.getLoanNo(),LetvPayConstants.BusiType.lend_loan,requestUrl,"blockChain",MapUtil.mapToString(params));
        
        //添加一条事务通知数据
    	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
    	blockAsynTran.setId(operationId);
    	blockAsynTran.setRequestID(requestID);
    	blockAsynTran.setParentId(crowd.getLoanNo());  
    	blockAsynTran.setCreateTime(new Date());
    	blockAsynTran.setUpdateTime(new Date());
    	blockAsynTran.setType(BlockChainCore.Type.lend_loan);
    	blockAsynTran.setStatus(BlockChainCore.ResultStatus.add);
    	this.blockAsynTranDao.save(blockAsynTran);  
        
        String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
        logger.info("==============项目放款项目方转账响应参数-resultStr："+resultStr);
    	@SuppressWarnings("unchecked")
		Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
        String status = resultMap.get("status").toString();
        String message = resultMap.get("message").toString();
        if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){//转账事务提交成功
        	//添加一条事务通知数据
        	BlockAsynTranModel blockAsynTranModel =this.blockAsynTranDao.findByRequestId(requestID);
        	blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
        	blockAsynTranModel.setUpdateTime(new Date());
        	this.blockAsynTranDao.update(blockAsynTranModel);
        }else{
        	logger.info("项目放款项目方-转账失败："+message);
        }
	}
	
	
	/**
	 * 放款平台服务费转账
	 * @param loanNo
	 */
	public void lendPlatformTransfer(CrowdfundingModel crowd){
		
		Map<String,Object> supportAmtMap = this.crowdfundingSupportDao.selectNoLendAmtByLoanNo(crowd.getLoanNo());
		//支持总金额
		double supportSumAmt = (Double) supportAmtMap.get("supportAmt");
		//运费总金额
		double transAmt = (Double) supportAmtMap.get("transAmt");
		//平台手续费率
		Double chargeScale = crowd.getChargeFee();
		//计算平台手续费
	    double chargeFee = Arith.mul(supportSumAmt, chargeScale);
	    //项目方应结算款项
	    double projectFee =Arith.round(supportSumAmt - chargeFee + transAmt);
	    //进行转账操作
	    String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
    	//保存操作数据
		String operationId = PKGenarator.getId();
		UserModel loanUser = this.userDao.findByUserId(crowd.getLoanUser());
		
		String notifyUrl = FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfundingLend/lendPlatformNotify.html";
		//项目放款平台服务费转账请求参数
		String requestID =PKGenarator.getOrderId();
		Map<String,String> params = this.getsendLendParam(operationId,requestID, crowd, loanUser, chargeFee, notifyUrl,true);
		logger.info("项目放款平台服务费转账请求参数params:"+params);
        trusteeshipOperationService.saveOperationModel(operationId,crowd.getLoanNo(),
        		crowd.getLoanNo(),LetvPayConstants.BusiType.lend_platform,requestUrl,"blockChain",MapUtil.mapToString(params));
        
        //添加一条事务通知数据
    	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
    	blockAsynTran.setId(operationId);
    	blockAsynTran.setRequestID(requestID);
    	blockAsynTran.setParentId(crowd.getLoanNo());  
    	blockAsynTran.setCreateTime(new Date());
    	blockAsynTran.setUpdateTime(new Date());
    	blockAsynTran.setType(BlockChainCore.Type.lend_platform);
    	blockAsynTran.setStatus(BlockChainCore.ResultStatus.add);
    	this.blockAsynTranDao.save(blockAsynTran);  
        String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
        logger.info("==============项目放款平台服务费转账响应参数-resultStr："+resultStr);
    	@SuppressWarnings("unchecked")
		Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
        String status = resultMap.get("status").toString();
        String message = resultMap.get("message").toString();
        if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){//转账事务成功
        	//添加一条事务通知数据
        	BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestID);        	 
        	blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
        	blockAsynTranModel.setUpdateTime(new Date());
        	this.blockAsynTranDao.update(blockAsynTranModel);
        }else{
        	logger.info("转账失败："+message);
        }
	}
	/**
     * 
     * Description: 放款转账项目方资金(将项目中的钱转到项目方账中)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     */
	private Map<String,String> getsendLendParam(String transferNO,String requestID,CrowdfundingModel loan,
			  UserModel user,double transferAmt,String notifyUrl,boolean isPlatform){
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("serviceID", "transfer");
		params.put("transferNO",transferNO); //转账编号
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		params.put("requestID",requestID); //请求编号
		params.put("sourceAddress",loan.getBlockChainAddress()); //转出账户地址为项目中间账户
		
		params.put("sourceKey", loan.getBlockChainSecretKey());
		if(isPlatform){
			params.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_INCOME_ACCOUNT);
		}else{
			params.put("targetAddress",user.getBlockChainAddress());
		}
	    String amount = "0.01";
		if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
			amount = "0.01";
		}else{
			amount = String.valueOf(Arith.round(transferAmt));
		}
		params.put("amount",amount);
		params.put("notifyURL",notifyUrl);
		return params;
	}
	
	
	
	
	//项目放款成功后续操作
	public void loanSendLendAlter(String loanNo,CrowdfundingModel crowd){
		
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
       //变更项目
       crowd.setLoanState(CrowdfundCoreConstants.crowdFundingState.lended.getValue());
       crowd.setCashTime(new Date());
       crowdfundingDao.updateBySelective(crowd);
        
        
       this.crowdfundingService.addLoanAudit(crowd.getCashUser(),loanNo, 
                CrowdfundCoreConstants.loanAuditAction.cash.getValue(),
                null, CrowdfundCoreConstants.crowdFundingState.lended.getValue());
        
        //添加项目方进账账单
       double tradeAmt = Arith.round(supportSumAmt + transAmt);
       this.userBillService.addBorrowerBill(loanNo, crowd.getLoanName(), "", crowd.getLoanUser(), tradeAmt);
       //添加项目方服务费出账账单
       this.userBillService.addLoanBorrowFeeBill(loanNo,  crowd.getLoanName(),crowd.getLoanUser(), chargeFee);
        //添加平台服务费账单
       this.sysBillService.addBorrowerFeeSystemBill(chargeFee, loanNo, loanNo, crowd.getLoanName());
        //给发起人发送放款信息
       this.sendLoanCashMessage(crowd);
        
       System.out.println(crowd.getLoanType());
        //筹款结束 如果该项目为产品,分配抽奖编号
        if(crowd.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.PRODUCT)){
    	   blockChainCrowdfundingService.saveUserPrize(crowd.getLoanNo());
        }
        AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, AuditLogConstants.TYPE_SEND, 
               AuditLogConstants.RESULT_SUCCESS,"项目编号："+loanNo);
        
        
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
