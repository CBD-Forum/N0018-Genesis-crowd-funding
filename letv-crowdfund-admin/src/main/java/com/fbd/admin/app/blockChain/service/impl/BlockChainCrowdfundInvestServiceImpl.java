package com.fbd.admin.app.blockChain.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.admin.app.blockChain.service.BlockChainCrowdfundInvestService;
import com.fbd.admin.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.admin.app.crowdFund.service.ICrowdfundingService;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;


@Service(value="blockChainCrowdfundInvestService")
public class BlockChainCrowdfundInvestServiceImpl implements BlockChainCrowdfundInvestService {

	
    private static final Logger logger = LoggerFactory.getLogger(BlockChainCrowdfundInvestServiceImpl.class);
    
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
    
    
    public void sendLendTrans2(String loanNo){
    	
    	
    	
    	if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){ 
    		
    		
    		
    		
    		
    		
    		
    	}else{
    		//进行区块链转账请求
    		
    		
    		
    	}
    } 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	@Override
	public void sendLendTrans(String loanNo) {
		//查询项目信息
		 CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
		 //查询未放款的总金额  (包括运费)
		 double noLendAmt = 0.0; //this.crowdfundingSupportDao.selectNoLendAmtByLoanNo(loanNo);
		 
		 //查询项目手续费比例
//		 Double chargeScale = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.SERVICE_FEE_SCALE).getCode()==null?"0.1":businessConfigDao.getBusConfig(CrowdfundCoreConstants.SERVICE_FEE_SCALE).getCode());
		 Double chargeScale = crowd.getChargeFee();
		 //查询项目手续费
		 double chargeFee = Arith.mul(noLendAmt, chargeScale);
		 //项目方应收款项
		 double projectFee = Arith.round(noLendAmt - chargeFee);
		 //放款转账操作
		 //1、将项目金额转账到项目方账户
		 //查询借款用户
		 UserModel loanUser = this.userDao.findByUserId(crowd.getLoanUser());
		 if(!"2".equals(loanUser.getBlockChainState())){
			 throw new ApplicationException("项目方区块链账户未开通！");
		 }
         String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
         if(Boolean.parseBoolean(FbdCoreConstants.isClientEnvironment)){
        	 chargeFee = 0.01;
 		}
         if(!"payed".equals(crowd.getPlatformTransferState())){
             //给平台收款账户转账平台手续费
    		 Map<String,String> platformParams = new HashMap<String,String>();
    		 platformParams.put("serviceID", "transfer");
    		 platformParams.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
    		 platformParams.put("sourceAddress",loanUser.getBlockChainAddress()); //转出账户地址
    		 platformParams.put("sourceKey", loanUser.getBlockChainSecretKey());
    		 platformParams.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_INCOME_ACCOUNT);
    		 platformParams.put("amount",String.valueOf(Arith.round(chargeFee)));
    		 platformParams.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"/crowdfundingInvest/receiveTransferS2S1.html");
    		 platformParams.put("transferNO",crowd.getLoanNo());
    		 platformParams.put("id",PKGenarator.getId());

             String platformResultStr = MockUtils.transfer(platformParams);
             @SuppressWarnings("unchecked")
    		 Map<String,Object> platformResultMap = JsonHelper.getMapFromJson(platformResultStr);
             String platformState = platformResultMap.get("status").toString();
             BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
           	 blockAsynTran.setParentId(loanNo);
        	// blockAsynTran.setTranId(crowd.getLoanNo());
//        	 blockAsynTran.setType(BlockChainCore.Type.SYSTEMLEND);
        	 blockAsynTranService.save(blockAsynTran);
             if("Success".equals(platformState)){ //转账成功
         
             }else{
            	 blockAsynTran.setStatus(platformResultMap.get("status").toString());
            	 blockAsynTran.setMessage(platformResultMap.get("message").toString());
            	 blockAsynTranService.update(blockAsynTran);
    	       	 String message = platformResultMap.get("status")==null?"":platformResultMap.get("message").toString();
    	            logger.info("放款【转账划款平台手续费】失败-【"+message+"】！");
    	       	 throw new ApplicationException("放款失败-【"+message+"】！");
             }
         }
         if(!"payed".equals(crowd.getLoanTransferState())){
        	 
        	 
             //给借款人转账
     		 Map<String,String>params = new HashMap<String,String>();
     		 params.put("serviceID", "transfer");
     		 params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
     		 params.put("sourceAddress", loanUser.getBlockChainAddress()); //转出账户地址
             params.put("sourceKey", loanUser.getBlockChainSecretKey());
             params.put("targetAddress", crowd.getBlockChainAddress());
             params.put("id",PKGenarator.getId());

             if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
            	 projectFee = 0.01;
     		}
             params.put("transferNO", crowd.getId());

             params.put("amount",String.valueOf(Arith.round(projectFee)));
             params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfundingInvest/receiveTransferS2S.html");
             String resultStr = MockUtils.transfer(params);
             @SuppressWarnings("unchecked")
     		 Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
             String state = resultMap.get("status").toString();
             BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
             blockAsynTran.setParentId(loanNo);
        	// blockAsynTran.setTranId((String)resultMap.get("transactionID"));
//        	 blockAsynTran.setType(BlockChainCore.Type.ORGANISERLEND);
        	 blockAsynTranService.save(blockAsynTran);
             if(BlockChainCore.ResultStatus.SUCCESS.equals(state)){ //转账成功
   
            
              }else{
            	  blockAsynTran.setStatus(resultMap.get("status").toString());
             	 blockAsynTran.setMessage(resultMap.get("message").toString());
             	 blockAsynTranService.update(blockAsynTran);
     	       	
     	       	 String message = resultMap.get("message")==null?"":resultMap.get("message").toString();
     	         logger.info("放款失败【转账划款项目款】-【"+message+"】！");
     	       	 throw new ApplicationException("放款失败-【"+message+"】！");
              }
         }

	}
	
    
	@Override
	public void sendLendTransForPlatform(String loanNo) {
		//查询项目信息
		 CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
		 //查询未放款的总金额
//		 double noLendAmt = this.crowdfundingSupportDao.selectNoLendAmtByLoanNo(loanNo);
		 //查询项目手续费
		 double chargeFee = crowd.getChargeFee();
		 //项目方应收款项
//		 double projectFee = Arith.round(noLendAmt - chargeFee);
		 //放款转账操作
		 //1、将项目金额转账到项目方账户
		 //查询借款用户
		 UserModel loanUser = this.userDao.findByUserId(crowd.getLoanUser());
		 if(!"2".equals(loanUser.getBlockChainState())){
			 throw new ApplicationException("项目方区块链账户未开通！");
		 }
         
	}
}
