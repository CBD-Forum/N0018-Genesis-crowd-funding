package com.fbd.admin.app.crowdFund.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.admin.app.crowdFund.service.ICrowdfundingBonusService;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundAuditDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundBonusAuditDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundRefundAuditDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingCommentDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundBonusAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.reward.dao.ICrowdfundBonusDao;
import com.fbd.core.app.reward.dao.IRewardAssignDao;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.MapUtil;


@Service(value="crowdfundingBonusService")
public class CrowdfundingBonusServiceImpl implements ICrowdfundingBonusService {
	
	
	
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundingBonusServiceImpl.class);
	  
    @Resource
    private IUserDao userDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private ICrowdfundingProgressDao crowdfundingProgressDao;
    @Resource
    private ICrowdfundingCommentDao crowdfundingCommentDao;
    
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private IBusinessConfigDao businessConfigDao;
    @Resource
    private ICrowdfundAuditDao crowdfundAuditDao;
    @Resource
    private ISystemBillService systemBillService;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private IRewardAssignDao rewardAssignDao;
    @Resource
    private ICrowdfundBonusDao crowdfundBonusDao; 
    @Resource
    private ICrowdfundRefundAuditDao crowdfundRefundAuditDao ;
    @Resource
    private ICrowdfundBonusAuditDao crowdfundBonusAuditDao;
	@Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    

	/**
     * 
     * Description: 查询分红项目待审核列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getLoanBonusList(
			CrowdfundBonusModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(this.crowdfundBonusDao.getLoanBonusList(model));
		result.setTotal(this.crowdfundBonusDao.getLoanBonusCount(model));
		return result;
	}

	/**
     * 
     * Description:项目分红审核
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateLoanBonusState(CrowdfundBonusAuditModel model) {
		//查询分红信息
		CrowdfundBonusModel bonusmodel = this.crowdfundBonusDao.selectByPrimaryKey(model.getLoanBonusId());
		model.setId(PKGenarator.getId());
		model.setAuditTime(new Date());
		this.crowdfundBonusAuditDao.save(model);
		//查询项目信息
		CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(bonusmodel.getLoanNo());
		//平台审核分红成功
		if(FbdCoreConstants.bonusAuditState.passed.equals(model.getAuditState())){
			//审核通过  分红转账
			this.sendBonusTransfer(bonusmodel,loan);
			
			bonusmodel.setBonusAuditTime(new Date());
			bonusmodel.setBonusAuditUser(model.getAuditor());
			this.crowdfundBonusDao.updateBySelective(bonusmodel);
			
		}else{ //平台审核分红拒绝
	        if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
	        	bonusmodel.setBonusState("refuse");  //分红状态为拒绝
	        	bonusmodel.setBonusAuditState("refuse");  //审核状态为拒绝
	        	bonusmodel.setBonusAuditUser(model.getAuditor());
	        	bonusmodel.setBonusAuditTime(new Date());
	        	//审核拒绝 退换借款人分红金额
				this.userBillService.addAuditRefuseUnfreeze(bonusmodel,loan.getLoanUser());
				//添加分红审核拒绝站内信和短信
				this.sendLoanBonusRefuseMessage(loan, bonusmodel);
				
	        }else{
	        	String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
	        	//保存操作数据
				String operationId = PKGenarator.getId();
				//分红请求参数
				String requestID = PKGenarator.getOrderId();
				Map<String,String> params = this.getBonusRefuseParam(bonusmodel,requestID,loan,operationId);
				logger.info("分红审核不通过请求参数params:"+params);
		        trusteeshipOperationService.saveOperationModel(operationId,model.getId(),
		        		model.getId(),LetvPayConstants.BusiType.bonus_refuse,requestUrl,"blockChain",MapUtil.mapToString(params));
		       
            	//添加一条事务通知数据
            	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
            	blockAsynTran.setId(operationId);
            	blockAsynTran.setRequestID(requestID);
            	blockAsynTran.setParentId(bonusmodel.getId());  
            	blockAsynTran.setCreateTime(new Date());
            	blockAsynTran.setUpdateTime(new Date());
            	blockAsynTran.setType(BlockChainCore.Type.bonus_refuse);
            	blockAsynTran.setStatus(BlockChainCore.ResultStatus.add);
            	this.blockAsynTranDao.save(blockAsynTran);
		        
		        
		        String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
		        logger.info("==============分红拒绝响应参数-resultStr："+resultStr);
	        	@SuppressWarnings("unchecked")
				Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
	            String status = resultMap.get("status").toString();
	            String message = resultMap.get("message").toString();
	            if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){//转账提交成功
	            	BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestID);
	            	blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
	            	blockAsynTranModel.setUpdateTime(new Date());
	            	this.blockAsynTranDao.update(blockAsynTranModel);
		        	bonusmodel.setBonusAuditUser(model.getAuditor());
		        	bonusmodel.setBonusAuditTime(new Date());
	            }else{
	            	//一条分红失败不能影响整体状态
	            	logger.info("转账失败："+message);
	            }
	        }
	        this.crowdfundBonusDao.updateBySelective(bonusmodel);
		}
	}
	/**
     * 
     * Description:审核通过  发送分红 转账请求
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	private void sendBonusTransfer(CrowdfundBonusModel bonusmodel,CrowdfundingModel loan ) {
		List<RewardAssignModel> listModel = this.rewardAssignDao.getListModelByLoanBonusId(bonusmodel.getId());
		for(RewardAssignModel model : listModel){
			try {
				String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
				//分红请求参数
				String requestID = PKGenarator.getOrderId();
				Map<String,String> params = this.getBonusPassedParam(model,loan,requestID);
				logger.info("分红请求参数params:"+params);
				//保存操作数据
				String operationId = PKGenarator.getId();
		        trusteeshipOperationService.saveOperationModel(operationId,model.getGetUser(),
		        		model.getId(),LetvPayConstants.BusiType.investor_bonus,requestUrl,"blockChain",MapUtil.mapToString(params));
		        if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
		        	//设置分红状态为分红成功
		        	model.setBonusState(FbdCoreConstants.bonusState.bonus_success);
		        	model.setAssignTime(new Date());
		        	//用户记账
		        	userBillService.addLoanShareBonusUserBill(model.getAssignAmt(), model.getGetUser(), model.getInvestNo());
		            //发送消息   
		        	this.sendInvestBonusSuccessMessage(loan, model);
		        
		        
		        }else{
		        	//添加一条事务通知数据
		        	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
		        	blockAsynTran.setId(PKGenarator.getId());
		        	blockAsynTran.setRequestID(requestID);
		        	blockAsynTran.setParentId(model.getId());  
		        	blockAsynTran.setCreateTime(new Date());
		        	blockAsynTran.setUpdateTime(new Date());
		        	blockAsynTran.setType(BlockChainCore.Type.investor_bonus);
		        	blockAsynTran.setStatus(BlockChainCore.ResultStatus.add);
		        	this.blockAsynTranDao.save(blockAsynTran);
		        	
		            String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
		    		logger.info("区块链-用户分红请求相应数据："+resultStr);
		            @SuppressWarnings("unchecked")
		            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
		            String status = resultMap.get("status").toString();
		            String message = resultMap.get("message").toString();
		            if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){//转账事务提交成功
		            	BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestID);
		            	blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
		            	blockAsynTranModel.setUpdateTime(new Date());
		            	this.blockAsynTranDao.update(blockAsynTranModel);
		            }else{
		            	//一条分红失败不能影响整体状态
		            	logger.info("转账失败："+message);
		            }
		        }
		        //更新分红信息
		        this.rewardAssignDao.updateBySelective(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
			//设置分红状态为成功
			bonusmodel.setBonusAuditState(FbdCoreConstants.bonusAuditState.passed);
			bonusmodel.setBonusState(FbdCoreConstants.bonusState.bonus_success);
			bonusmodel.setBonusAuditTime(new Date());
			this.crowdfundBonusDao.updateBySelective(bonusmodel);
			//项目方分红解冻账单
		    this.userBillService.addAuditBonusPassBill(bonusmodel,loan.getLoanUser());
		}
	}
	
	
	/**
     * 
     * Description: 审核通过 分红转账  组装参数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String,String> getBonusPassedParam(RewardAssignModel model,CrowdfundingModel loan,String requestID){
		//查询用户信息
		UserModel user = this.userDao.findByUserId(model.getInvestor());
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("serviceID", "transfer");
		params.put("transferNO",model.getId()); //转账编号传分红编号
		params.put("requestID",requestID);
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		String amount = "0.01";
		if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
			amount = "0.01";
		}else{
			amount = String.valueOf(Arith.round(model.getAssignAmt()));
		}
		params.put("sourceAddress",loan.getBlockChainAddress()); //转出账户地址为项目中间账户
		params.put("sourceKey", loan.getBlockChainSecretKey());
		params.put("targetAddress",user.getBlockChainAddress());
		
		params.put("amount",amount);
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfundingBonus/auditBonusPassedNotify.html");
		return params;
	}
	
	
	/**
     * 
     * Description: 平台审核分红不通过组装参数(将项目账户钱转账到项目方账户)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String,String> getBonusRefuseParam(CrowdfundBonusModel bonusmodel,String requestID,CrowdfundingModel loan,String transferNO){
		//查询用户信息
		UserModel user = this.userDao.findByUserId(loan.getLoanUser());
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("serviceID", "transfer");
		params.put("transferNO",transferNO); //转账编号传分红编号
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		params.put("requestID",requestID);
		String amount = "0.01";
		if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
//			params.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_OUT_ACCOUNT); //转出账户地址为项目中间账户
//			params.put("sourceKey", FbdCoreConstants.BLOCK_CHAIN_PLATFORM_OUT_KEY);
//			params.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_IN_ACCOUNT);
			amount = "0.01";
		}else{
			amount = String.valueOf(Arith.round(bonusmodel.getBonusAmt()));
		}
		params.put("sourceAddress",loan.getBlockChainAddress()); //转出账户地址为项目中间账户
		params.put("sourceKey", loan.getBlockChainSecretKey());
		params.put("targetAddress",user.getBlockChainAddress());
		
		params.put("amount",amount);
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfundingBonus/auditBonusRefuseNotify.html");
		return params;
	}

	   /**
     * 
     * Description: 发送分红分红成功投资人信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendInvestBonusSuccessMessage(CrowdfundingModel model,RewardAssignModel assign){
       Map<String, String> params = new HashMap<String,String>();
       params.put("assignAmt",Arith.format(assign.getAssignAmt()));  //分红金额
       params.put("loanName",model.getLoanName());
       params.put("loanNo",model.getLoanNo());
       params.put("bonusTime",DateUtil.dateTime2Str(assign.getAssignTime(), null));  //分红时间
       params.put("bonusAuditTime",DateUtil.dateTime2Str(new Date(), null));  //分红审核时间
       
       try{
           logger.info("发送项目分红审核通过投资人手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_BONUS_PASSED_INVEST_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode,assign.getInvestor(), params);
           logger.info("发送项目分红审核通过投资人手机短信结束");
       }catch(Exception e){
           logger.error("发送项目分红审核通过投资人手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送项目分红审核通过投资人站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_BONUS_PASSED_INVEST_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, assign.getInvestor(), params);
            logger.info("发送项目分红审核通过投资人站内信结束");
        }catch(Exception e){
            logger.error("发送项目分红审核通过投资人站内信失败，"+e.getMessage());
        }
    }
    
    
    /**
     * 
     * Description: 发送分红审核拒绝消息（项目方）
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendLoanBonusRefuseMessage(CrowdfundingModel model,CrowdfundBonusModel bonus){
       Map<String, String> params = new HashMap<String,String>();
       params.put("money",Arith.format(bonus.getBonusAmt()));  //分红金额
       params.put("loanName",model.getLoanName());
       params.put("loanNo",model.getLoanNo());
       params.put("bonusTime",DateUtil.dateTime2Str(bonus.getBonusTime(), null));  //分红时间
       params.put("bonusAuditTime",DateUtil.dateTime2Str(bonus.getBonusAuditTime(), null));  //分红审核时间
       
       try{
           logger.info("发送项目分红审核拒绝手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_BONUS_REFUSE_LOAN_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getLoanUser(), params);
           logger.info("发送项目分红审核拒绝手机短信结束");
       }catch(Exception e){
           logger.error("发送项目分红审核拒绝手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送项目分红审核拒绝站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_BONUS_REFUSE_LOAN_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getLoanUser(), params);
            logger.info("发送项目分红审核拒绝站内信结束");
        }catch(Exception e){
            logger.error("发送项目分红审核拒绝站内信失败，"+e.getMessage());
        }
    }
	/**
     * 
     * Description:补发分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void sendRereissueBonus(String id) {
		/*try {
			RewardAssignModel model = this.rewardAssignDao.selectByPrimaryKey(id);
			Map<String,String> params = this.getBonusParam(model);
			String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL+"/transfer";
	        String userResultStr = MockUtils.transfer(requestUrl, params);
	        
	        Map<String,Object> userResultMap = JsonHelper.getMapFromJson(userResultStr);
	        if(userResultMap.get("status").equals("TransactionSuccess")){
	        	model.setAssignState(FbdCoreConstants.bonusState.bonus_success);
	        	model.setStatus(FbdCoreConstants.bonusAuditState.passed);
	        	this.rewardAssignDao.update(model);
	        	//增加账单
	        	//记账：投资人进
	            userBillService.addLoanShareBonusUserBill(model.getAssignAmt(), model.getGetUser(), model.getInvestNo());
	        	
	        }else{
	        	model.setAssignState(FbdCoreConstants.bonusState.bonus_fail);
	        	this.rewardAssignDao.update(model);
	        }
		} catch (Exception e) {
			// TODO: handle exception
		}*/
	}
	
	
	

	/**
     * 
     * Description: 奖励发放明细
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:51:35
     */
	public SearchResult<Map<String,Object>> getRewardPageList(RewardAssignModel model) {
		SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.rewardAssignDao.getList(model));
        result.setTotal(this.rewardAssignDao.getListCount(model));
        return result;
	}

	/**
     * 
     * Description: 查询分红项目    审核记录 列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getLoanAuditBonusPage(
			CrowdfundBonusAuditModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(this.crowdfundBonusAuditDao.getLoanAuditBonusList(model));
		result.setTotal(this.crowdfundBonusAuditDao.getLoanAuditBonusCount(model));
		return result;
	}
}
