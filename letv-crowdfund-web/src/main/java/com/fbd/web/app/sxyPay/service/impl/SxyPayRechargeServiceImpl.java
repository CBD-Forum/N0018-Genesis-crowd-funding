package com.fbd.web.app.sxyPay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.sxyPay.utils.SxyPayMd5;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.SysException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.HtmlElementUtil;
import com.fbd.core.util.LockUtil;
import com.fbd.core.util.MapUtil;
import com.fbd.web.app.sxyPay.service.ISxyPayRechargeService;

@Service("sxyPayRechargeService")
public class SxyPayRechargeServiceImpl implements ISxyPayRechargeService {
	private static final Logger logger = LoggerFactory
            .getLogger(SxyPayRechargeServiceImpl.class);

	@Resource
	private IUserDao userDao;
	@Resource
    private IRechargeDao rechargeDao;
    @Resource
    private IUserBillService userBillService;
	@Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
	@Resource
	private IBlockChainQueryService blockChainQueryService;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
	 /**
     * 
     * Description:充值
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	 @Transactional(propagation = Propagation.REQUIRED)
	public String createRechargeRequest(String userId, RechargeModel model) {
		//插入充值单
	       this.addRechargeOrder(model);
		    String operationId = PKGenarator.getId();
	        Map<String, String> params = null;
	        String requestUrl = null;
	        
	        String requestHtml = "";
	        params = this.getRechargeParams(model);
            requestUrl = SxyPayConstants.Config.SXY_PAY_REQ_URL;
            
            trusteeshipOperationService.saveOperationModel(operationId, 
            		model.getUserId(), model.getOrderId(), 
	                SxyPayConstants.BusiType.recharge, 
	                requestUrl, 
	                SxyPayConstants.THIRD_ID, MapUtil.mapToString(params));
            requestHtml = HtmlElementUtil.createAutoSubmitForm(params,
            		requestUrl,SxyPayConstants.INPUTCHARSET);    
	        return requestHtml;
	}
	 
	 
	public RechargeModel createRechangeOrderByMobile(String userId, RechargeModel model){
		
		this.addRechargeOrder(model);
		
		 String operationId = PKGenarator.getId();
	     Map<String, String> params = new HashMap<String,String>();
	     String requestUrl = SxyPayConstants.Config.SXY_PAY_REQ_URL;
         
         trusteeshipOperationService.saveOperationModel(operationId, 
         		model.getUserId(), model.getOrderId(), 
	                SxyPayConstants.BusiType.recharge, 
	                requestUrl, 
	                SxyPayConstants.THIRD_ID, MapUtil.mapToString(params));
		return model;
	}
	 
	 private Map<String, String> getRechargeParams(RechargeModel model) {
		 
		 Map<String, String> map = new HashMap<String, String>();
		 map.put(SyxParam.v_mid, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_oid, model.getOrderId());
		 map.put(SyxParam.v_rcvname, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_rcvaddr, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_rcvtel, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_rcvpost, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_amount, Arith.format(model.getRechargeAmt()));
		 map.put(SyxParam.v_ymd, DateUtil.date2Str(new Date(),"yyyyMMdd"));
		 map.put(SyxParam.v_orderstatus, "1");
		 map.put(SyxParam.v_ordername, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_moneytype, "0");
		 map.put(SyxParam.v_url, SxyPayConstants.Config.SXY_PRE_RESPONSE_RAIN_WEB_URL+SxyPayConstants.SXY_RECHARGE_RETURN_URL);
		 StringBuffer sb = new StringBuffer();
		 sb.append(map.get(SyxParam.v_moneytype));
		 sb.append(map.get(SyxParam.v_ymd));
		 sb.append(map.get(SyxParam.v_amount));
		 sb.append(map.get(SyxParam.v_rcvname));
		 sb.append(map.get(SyxParam.v_oid));
		 sb.append(map.get(SyxParam.v_mid));
		 sb.append(map.get(SyxParam.v_url));
		 map.put(SyxParam.v_md5info, SxyPayMd5.createMd5(sb.toString()));
		 
		 
		 return map;
	}
	 
	private void addRechargeOrder(RechargeModel recharge) {
        recharge.setId(PKGenarator.getId());
        // 获得订单号
        recharge.setOrderId(PKGenarator.getRechargeOrderId());
        recharge.setState(FbdCoreConstants.rachargeState.recharging.getValue());
        recharge.setCreateTime(new Date());
        recharge.setThirdRechargeType(LetvPayConstants.thirdType.SXY);
        this.rechargeDao.save(recharge);
	}

	/**
     * 
     * Description:更新充值订单为成功
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateRechargSucess(Map<String, String> resultMap) {
		 String orderId = resultMap.get(SyxParam.v_oid);
		 if (this.isRechargeLock(orderId)) {
			 logger.info("请求失败,orderId被锁！" + orderId);
	         return;   
	     }	       
		 RechargeModel recharge = this.rechargeDao.selectByOrderId(orderId);
		 try {
			 if (recharge == null) {
	                throw new ApplicationException("订单号为" + orderId + "的充值单不存在");
	         }
			 String rechargeState = FbdCoreConstants.rachargeState.recharge_success_auditing
	                    .getValue();
			 if (recharge.getState().equals(rechargeState)) {// 充值状态已经更新
	            logger.info(orderId + "充值已经完成！");
	            return;
	         }   
	         recharge.setState(rechargeState);
	         recharge.setCompleteTime(new Date());
	         double actualAmt = recharge.getRechargeAmt();
	         recharge.setActualAmt(actualAmt);
	         this.rechargeDao.update(recharge);
	         //发送信息
             this.sendRechargeMessage(recharge);
             
             //给用户转账添加众筹令
             logger.info("====================用户充值成功给用户转账众筹令=========================");
             try{
            	 if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
            		 //添加用户充值冻结账单
            		 this.userBillService.addUserRechargeFreezeBill(recharge);
            		 recharge.setBlockChainState("success");  //区块链转账完成
            		 recharge.setTransferComplateTime(new Date());
            		 this.rechargeDao.update(recharge);
            	 }else{
            		 this.rechargeTransfer(orderId);
            	 }
             }catch(Exception e){
            	 logger.info("======================用户充值成功转众筹令异常=================");
             }
		} catch (Exception e) {
            throw new SysException("更新充值单失败：" + e.getMessage());
        } finally {
               LockUtil.getInstance().remove(
                       FbdCoreConstants.LOCK_RECHARGE + orderId);
        }    
	}
	
	private void rechargeTransfer(String orderId){
		
		RechargeModel model = this.rechargeDao.selectByOrderId(orderId);
		if(model.getBlockChainState()!=null){
			logger.info("====充值【"+orderId+"】成功转账众筹令状态【"+model.getBlockChainState()+"】不能重复转众筹令=====================");
			return;
		}
		//查询用户信息
		UserModel user = this.userDao.findByUserId(model.getUserId());
		model.setBlockChainState("transfer_lock");  //装让锁定
		this.rechargeDao.updateByOrderId(model);
		
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
		}else{
			//验证用户是否已经开通区块链账户
			if(!"2".equals(user.getBlockChainState())){
				logger.info("============>充值成功转账到区块链验证失败，用户【"+user.getUserId()+"】区块链账户未开通=============");
				throw new ApplicationException("当前用户区块链账户未开通 ！");
			}else{
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
	            logger.info("===========首易信充值成功转账众筹令结果："+message);
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
//	            	throw new ApplicationException(message);
	            	logger.info("==============充值成功转账众筹令失败========================");
	            }
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
			rechargeAmt = "0.05";  //充值给用户转2令
		}else{
			rechargeAmt = String.valueOf(Arith.round(model.getRechargeAmt()));
		}
		params.put("targetAddress",user.getBlockChainAddress());  //转入账户为用户账号(测试使用用户)
		params.put("amount",rechargeAmt);
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"recharge/rechargeNotify.html");
		return params;
	}	
	
    /**
     * 
     * Description: 发送充值信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendRechargeMessage(RechargeModel model){
       Map<String, String> params = new HashMap<String,String>();
       params.put("time",DateUtil.dateTime2Str(model.getCompleteTime(), null));
       params.put("money",Arith.format(model.getRechargeAmt()));
       try{
           logger.info("发送手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_RECHARGE_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getUserId(), params);
           logger.info("发送手机短信结束");
       }catch(Exception e){
           logger.error("发送手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送充值站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_RECHARGE_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_RECHARGE_WITHDRAW, model.getUserId(), params);
            logger.info("发送充值站内信结束");
        }catch(Exception e){
            logger.error("发送充值站内信失败，"+e.getMessage());
        }
    }

	
	/**
     * 
     * Description: 判断支持单是否被锁定
     * 
     * @param
     * @return boolean
     * @throws
     * @Author wuwenbin
     */
    public synchronized boolean isRechargeLock(String orderId) {
        boolean isLock = true;
        Object lockValue = LockUtil.getInstance().get(
                CrowdfundCoreConstants.LOCK_INVEST + orderId);
        if (lockValue == null) {
            LockUtil.getInstance().put(
                    CrowdfundCoreConstants.LOCK_INVEST + orderId, orderId);
            isLock = false;
        }
        logger.info("众筹支持单isLock=====================" + isLock);
        return isLock;
    }

    /**
     * 
     * Description:快捷充值
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @Transactional(propagation = Propagation.REQUIRED)
	public String createQuickRechargeRequest(String userId, RechargeModel model) {
    	//插入充值单
	      this.addRechargeOrder(model);
		 String operationId = PKGenarator.getId();
	        Map<String, String> params = null;
	        String requestUrl = null;
	        
	        String requestHtml = "";
	        params = this.getRechargeQuickParams(model);
          requestUrl = SxyPayConstants.Config.SXY_QUICK_PAY_REQ_URL;
          
          trusteeshipOperationService.saveOperationModel(operationId, 
          		model.getUserId(), model.getOrderId(), 
	                SxyPayConstants.BusiType.recharge, 
	                requestUrl, 
	                SxyPayConstants.THIRD_ID, MapUtil.mapToString(params));
          requestHtml = HtmlElementUtil.createAutoSubmitForm(params,
          		requestUrl,SxyPayConstants.INPUTCHARSET);    
	            
	        
	        return requestHtml;
	}

    /**
     * 
     * Description:快捷充值参数组装
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String, String> getRechargeQuickParams(RechargeModel model) {
		Map<String, String> map = new HashMap<String, String>();
		 map.put(SyxParam.v_mid, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_oid, model.getOrderId());
		 map.put(SyxParam.v_rcvname, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_rcvaddr, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_rcvtel, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_rcvpost, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_amount, Arith.format(model.getRechargeAmt()));
		 map.put(SyxParam.v_ymd, DateUtil.date2Str(new Date(),"yyyyMMdd"));
		 map.put(SyxParam.v_orderstatus, "1");
		 map.put(SyxParam.v_ordername, SxyPayConstants.Config.SXY_SHBH);
		 map.put(SyxParam.v_moneytype, "0");
		 map.put(SyxParam.v_url, SxyPayConstants.Config.SXY_PRE_RESPONSE_RAIN_WEB_URL+SxyPayConstants.SXY_QUICK_RECHARGE_RETURN_URL);
		 StringBuffer sb = new StringBuffer();
		 sb.append(map.get(SyxParam.v_moneytype));
		 sb.append(map.get(SyxParam.v_ymd));
		 sb.append(map.get(SyxParam.v_amount));
		 sb.append(map.get(SyxParam.v_rcvname));
		 sb.append(map.get(SyxParam.v_oid));
		 sb.append(map.get(SyxParam.v_mid));
		 sb.append(map.get(SyxParam.v_url));
		 map.put(SyxParam.v_md5info, SxyPayMd5.createMd5(sb.toString()));
		 map.put(SyxParam.v_userref,model.getUserId());
		 map.put(SyxParam.v_pmode, "904");
		 
		 return map;
	}
}
