/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RechargeServiceImpl.java 
 *
 * Created: [2014-12-22 下午2:32:18] by haolingfeng
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
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.recharge.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.admin.app.recharge.service.IRechargeService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.MapUtil;
import com.fbd.core.util.SpringPropertiesHolder;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author dongzhongwei
 * @version 1.0
 * 
 */
@Service(value="rechargeService")
public class RechargeServiceImpl implements IRechargeService {
	
    private static final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);
    
    @Resource
    private IRechargeDao rechargeDao;
    
    @Resource
    private IUserBillService userBillService;
    @Resource
    public IUserDao userDao;
	@Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
	@Resource
	private IBlockChainQueryService blockChainQueryService;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    /**
     * Description: 查询用户充值列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 上午11:48:00
     */
    public SearchResult<Map<String, Object>> getRechargePage(Map<String, Object> param) {
        return rechargeDao.getRechargePage(param);
    }

	public SearchResult<Map<String, Object>> getUserRechargePage(
			Map<String, Object> param) {
		param.put("notUserId", SpringPropertiesHolder.getProperty("sys_recharge_userId"));
		return rechargeDao.getRechargePage(param);
	}

	public SearchResult<Map<String, Object>> getFeeRechargePage(
			Map<String, Object> param) {
		param.put("userId", SpringPropertiesHolder.getProperty("sys_recharge_userId"));
		return rechargeDao.getRechargePage(param);
	}

	/**
     * Description: 查询用户充值列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getRechargelist(
			RechargeModel recharge) {
		SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
		try {
			result.setTotal(this.rechargeDao.getRechargeListCount(recharge));
	        result.setRows(this.rechargeDao.getRechargeList(recharge));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
	}

	/**
     * Description: 审核充值记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	public void updateAuditState(RechargeModel recharge) {
		try {
			RechargeModel model = this.rechargeDao.selectByOrderId(recharge.getOrderId());
			model.setOperator(recharge.getOperator());
			model.setOperatorAuditTime(recharge.getOperatorAuditTime());
			model.setOperatorOpinion(recharge.getOperatorOpinion());
			
			
			model.setFinancialAuditor(recharge.getFinancialAuditor());
			model.setFinancialAuditTime(recharge.getFinancialAuditTime());
			model.setFinancialOpinion(recharge.getFinancialOpinion());
			//财务审核
			if(recharge.getState()!=null &&"finaical_passed".equals(recharge.getState())){
				//查询用户信息
				UserModel user = this.userDao.findByUserId(model.getUserId());
				//调用区块链  转账
//				this.sendRechargeTransfer(model,user);
				//添加审核解冻账单
				this.userBillService.addUserRechargeUnFreezeBill(model);
				//更新充值状态为完成
				model.setState(FbdCoreConstants.rachargeState.recharge_success.getValue());
	        	this.rechargeDao.update(model);
				//发送审核到账站内信
				sendRechargePassedMessage(model);
				
			}else{
				model.setState(recharge.getState());
				this.rechargeDao.updateByOrderId(model);
				 //发送审核拒绝的站内信
				this.sendRechargeRefuseMessage(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e.getMessage());
		}
	}
	
	
	
	private void sendRechargePassedMessage(RechargeModel model){
		String state = model.getState();
		String userId = model.getUserId();
		String orderId = model.getOrderId();
	    Map<String, String> params = new HashMap<String,String>();
        params.put("time",DateUtil.dateTime2Str(new Date(), null));
        params.put("orderId",orderId);
        params.put("amt",String.valueOf(model.getRechargeAmt()));
		try{
	        logger.info("发送充值审核通过站内信开始");
	        String nodeCode = FbdCoreConstants.NODE_TYPE_RECHARGE_AUDIT_PASSED_MSG;
	        SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_SYS_MSG, userId, params);
	        logger.info("发送充值审核通过站内信结束");
	   }catch(Exception e){
	        logger.error("发送充值审核通过站内信失败，"+e.getMessage());
	   }
		
		
	   try{
           logger.info("发送充值审核通过手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_RECHARGE_AUDIT_PASSED_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode,userId, params);
           logger.info("发送充值审核通过手机短信结束");
       }catch(Exception e){
           logger.error("发送充值审核通过手机短信失败，"+e.getMessage());
        }
	}
	
	private void sendRechargeRefuseMessage(RechargeModel model){
		String state = model.getState();
		String userId = model.getUserId();
		String orderId = model.getOrderId();
	    Map<String, String> params = new HashMap<String,String>();
        params.put("time",DateUtil.dateTime2Str(model.getCreateTime(), null));
        params.put("orderId",orderId);
		if("recharge_yy_refuse".equals(state) || "recharge_cw_refuse".equals(state)){
	        try{
	           logger.info("发送充值审核拒绝手机短信开始");
	           String nodeCode = FbdCoreConstants.NODE_TYPE_RECHARGE_AUDIT_REFUSE_MOBILE;
	           SendMessageUtil.sendMobileMessage(nodeCode,userId, params);
	           logger.info("发送充值审核拒绝手机短信结束");
	        }catch(Exception e){
	           logger.error("发送充值审核拒绝手机短信失败，"+e.getMessage());
	        }
			try{
		        logger.info("发送充值审核拒绝站内信开始");
		        String nodeCode = FbdCoreConstants.NODE_TYPE_RECHARGE_AUDIT_REFUSE_MSG;
		        SendMessageUtil.sendStationMessage(nodeCode,FbdCoreConstants.STATION_MSG_TYPE_SYS_MSG, userId, params);
		        logger.info("发送充值审核拒绝站内信结束");
		    }catch(Exception e){
		        logger.error("发送充值审核拒绝站内信失败，"+e.getMessage());
		    }
		}
	}
 

	private void sendRechargeTransfer(RechargeModel model,UserModel user) {
		
		logger.info("区块链-充值转账开始");
		String requestID = PKGenarator.getOrderId();
		Map<String,String> params = this.getRechargeParams(model,user,requestID);
		logger.info("区块链-充值转账请求参数："+params);
		String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
		 //保存操作数据
		String operationId = PKGenarator.getId();
        trusteeshipOperationService.saveOperationModel(operationId, model.getOrderId(),
                model.getOrderId(),LetvPayConstants.BusiType.recharge,requestUrl,"blockChain",MapUtil.mapToString(params));
		if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
			model.setBlockChainState(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS);  //设置状态为区块链状态
			model.setState(FbdCoreConstants.rachargeState.recharge_success.getValue());
        	this.rechargeDao.update(model);
        	//添加用户账单
        	this.userBillService.addRechargeUserBill(model);
        	//发送站内信
        	this.sendRechargePassedMessage(model);
		}else{
			
			//验证用户是否已经开通区块链账户
			if(!"2".equals(user.getBlockChainState())){
				logger.info("============>区块链账户未开通=============");
				throw new ApplicationException("当前用户区块链账户未开通 ！");
			}
			//添加一条事务通知数据
        	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
        	blockAsynTran.setId(PKGenarator.getId());
        	blockAsynTran.setRequestID(requestID);
        	blockAsynTran.setParentId(model.getOrderId());  //支持编号
        	blockAsynTran.setCreateTime(new Date());
        	blockAsynTran.setUpdateTime(new Date());
        	blockAsynTran.setType(BlockChainCore.Type.recharge);
        	blockAsynTran.setStatus(BlockChainCore.ResultStatus.add);
        	this.blockAsynTranDao.save(blockAsynTran);
			
            String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
    		logger.info("区块链-审核充值请求相应数据："+resultStr);
            @SuppressWarnings("unchecked")
            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
            String message = resultMap.get("message")==null?"":resultMap.get("message").toString();
            String status = resultMap.get("status")==null?"":resultMap.get("status").toString();
            
            if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){//转账提交成功
            	BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestID);
            	blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
            	blockAsynTranModel.setUpdateTime(new Date());
            	this.blockAsynTranDao.update(blockAsynTranModel);
            }else if(BlockChainCore.ResultStatus.FAIL.equals(status)){  //提交失败
            	BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestID);
            	blockAsynTranModel.setStatus(status);
            	blockAsynTranModel.setUpdateTime(new Date());
            	this.blockAsynTranDao.update(blockAsynTranModel);
            	throw new ApplicationException(message);
            }
		}
	}

	private Map<String, String> getRechargeParams(RechargeModel model,UserModel user,String requestID) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("serviceID", "transfer");
		params.put("requestID",requestID);
		params.put("transferNO",model.getOrderId());
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		params.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_OUT_ACCOUNT); //转出账户地址为平台出账户
		params.put("sourceKey",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_OUT_KEY);
		
		String rechargeAmt = "0.01";
		if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
			rechargeAmt = "0.2";  //充值给用户转2令
		}else{
			rechargeAmt = String.valueOf(Arith.round(model.getRechargeAmt()));
		}
		params.put("targetAddress",user.getBlockChainAddress());  //转入账户为用户账号(测试使用用户)
		params.put("amount",rechargeAmt);
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"recharge/rechargeNotify.html");
		return params;
	}
	
	
	
    /**
     * 区块链转账成功后的后续操作
     * @param orderId
     */
    public void rechargeTransferAfter(String orderId,Map<String,String> resultMap){
//    	String transactionID =resultMap.get("transactionID");  //事务ID 
//    	RechargeModel model =  this.rechargeDao.selectByOrderId(orderId);
//    	model.setTransactionId(transactionID);
//    	
//    	String status = resultMap.get("status");
//    	if("".equals(status)){
//        	//给用户转众筹币成功后给用户增加充值账单
//    		this.userBillService.addBill(model.getUserId(), FbdCoreConstants.userTradeType.recharge.getValue(), model.getRechargeAmt(), 
//    			FbdCoreConstants.tradeDirection.in.getValue(), null, "用户增加充值账单", null, model.getOrderId());
//    		model.setState(FbdCoreConstants.rachargeState.recharge_completed_success.getValue());
//    		this.rechargeDao.update(model);
//    	}
    }
	public long updateByOrderId(RechargeModel model) {
		return rechargeDao.updateByOrderId(model);
	}
	
    /**
     * 充值处理任务
     */
    public void rechargeHandleTask(){
    	//查询充值列表
    	RechargeModel recharge = new RechargeModel();
    	recharge.setBlockChainState("Success");
    	List<RechargeModel> rechargeList = this.rechargeDao.selectListByModel(recharge);
    	for(RechargeModel model:rechargeList){
    		try{
    			String userId = model.getUserId();
    			String orderId = model.getOrderId();
    			String transactionId = model.getTransactionId();
    			if(!"".equals(transactionId) && transactionId!=null){
    				String resultStr = blockChainQueryService.transactionQuery(transactionId);
    		        @SuppressWarnings("unchecked")
					Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
    		        String status = resultMap.get("status").toString();
    		        String message = resultMap.get("message").toString();
    		        if("TransactionSuccess".equals(status)){//转账事务成功
    		        	//给用户转众筹币成功后给用户增加充值账单
    		    		this.userBillService.addBill(userId, FbdCoreConstants.userTradeType.recharge.getValue(), model.getRechargeAmt(), 
    		    			FbdCoreConstants.tradeDirection.in.getValue(), null, "用户增加充值账单", null, orderId);
    		    		model.setState(FbdCoreConstants.rachargeState.recharge_success.getValue());
    		        	model.setBlockChainState(status);  //设置状态为区块链状态
    		        	this.rechargeDao.update(model);
    		        }else{
    		        	logger.info("充值事务查询-message:"+message);
    		        }
    			}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    }
    
    /**
     * 根据orderId查询充值
     * @param orderId
     * @return
     */
    public RechargeModel selectByOrderId(String orderId){
    	return this.rechargeDao.selectByOrderId(orderId);
    }
}
