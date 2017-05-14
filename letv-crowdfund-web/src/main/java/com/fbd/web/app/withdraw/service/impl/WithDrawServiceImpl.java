/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: WithDrawServiceImpl.java 
 *
 * Created: [2015-1-15 下午4:57:03] by haolingfeng
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
 * ProjectName: fbd-admin 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.withdraw.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.core.app.bank.dao.IUserBankDao;
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.letvPay.utils.Core;
import com.fbd.core.app.letvPay.utils.RSAService;
import com.fbd.core.app.sxyPay.common.LetvPayUtils;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxDataParam;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.sxyPay.utils.SxyPayMd5;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.withdraw.dao.IWithDrawDao;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.app.withdraw.service.IWithDrawQueryService;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.JsonUtil;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.MapUtil;
import com.fbd.core.util.ValidateUtils;
import com.fbd.web.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.web.app.withdraw.service.IWithDrawService;

/** 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description: 取现
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("withDrawService")
public class WithDrawServiceImpl implements IWithDrawService{
	
    private static final Logger logger = LoggerFactory.getLogger(WithDrawServiceImpl.class);

    @Resource
    private IWithDrawDao withDrawDao;
    @Resource
    private IUserBankDao userBankDao;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private IBusinessConfigDao businessConfigDao;
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    @Resource
    private IUserDao userDao;
    @Resource
	private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IBlockAsynTranService blockAsynTranService;
    @Resource
    private ISystemBillService systemBillService;
    @Resource
    private IWithDrawQueryService withDrawQueryService;
    public WithDrawModel getByOrderId(String orderId){
        return this.withDrawDao.getByOrderId(orderId);
    }
    
    /**
     * 
     * Description: 分页查询提现记录
     *
     * @param 
     * @return SearchResult<WithDrawModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-1-27 上午10:29:02
     */
    public SearchResult<WithDrawModel> getPageList(WithDrawModel model){
        if (model.getApplyStartTime() != null) {
            model.setApplyStartTime(DateUtil.getDayMin(model
                    .getApplyStartTime()));
        }
        if (model.getApplyEndTime() != null) {
            model.setApplyEndTime(DateUtil.getDayMax(model
                    .getApplyEndTime()));
        }
        SearchResult<WithDrawModel> result = new SearchResult<WithDrawModel>();
        result.setRows(this.withDrawDao.getList(model));
        result.setTotal(this.withDrawDao.getListCounts(model));
        return result;
    }
    
    /**
     * 
     * Description: 查询银行卡列表
     *
     * @param 
     * @return List<UserBankModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-9 上午11:36:57
     */
    public List<UserBankModel> getUserBankList(UserBankModel model){
        return this.userBankDao.getList(model);
    }
    
    
    /**
     * 
     * Description: 查询取现服务费
     *
     * @param 
     * @return double
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 上午10:22:51
     */
    public double getWithDrawFee(String feeType){
        double fee = 0.0;
        BusinessConfigModel feeConfig = businessConfigDao.getBusConfig(CrowdfundCoreConstants.WITHDRAW_FEE);
        if(feeConfig!=null){
            fee = Double.valueOf(feeConfig.getCode());
        }else{
            throw new ApplicationException("提现费用未设置");
        }
        return fee;
    }
    
    /**
     * 
     * Description:验证用户是否绑定银行卡 
     *
     * @param 
     * @return boolean
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-10 下午6:25:05
     */
    private boolean isExistBank(String userId){
        UserBankModel userBank = new UserBankModel();
        userBank.setUserId(userId);
        List<UserBankModel> userBanks = userBankDao.getList(userBank);
        if(userBanks.size() > 0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * Description: 取现前验证
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-3 下午6:10:23
     */
    public void checkWithDraw(WithDrawModel withDraw){
        UserModel user = userDao.findByUserId(withDraw.getUserId());
        if (withDraw.getAmt() == null) {
            throw new ApplicationException("对不起，取现金额不能为空");
        }
        if (!ValidateUtils.isNumber(withDraw.getAmt().toString())) {
            throw new ApplicationException("对不起，取现金额格式有误");
        }
        //验证是否有绑定的银行卡
        if(withDraw.getBankCardId()==null){
            throw new ApplicationException("您未选择银行卡");
        }
        // 验证账户余额
        UserBillModel bill = this.userBillService.getLatestBill(withDraw.getUserId());
        if(bill.getBalance() < withDraw.getAmt()+this.getWithDrawFee(withDraw.getFeeType())){
            throw new ApplicationException("对不起，您的账户余额不足");
        }
        
    }
    
    /**
     * 
     * Description: 保存提现
     *
     * @param 
     * @return WithDrawModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-15 下午12:30:56
     */
    public void saveWithDraw(WithDrawModel withDraw,String requestId){
        try{
        	UserModel userModel = this.userDao.findByUserId(withDraw.getUserId());
        	String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
        	String orderId = PKGenarator.getOrderId();
        	double amount=withDraw.getActualMoney();
        	//添加提现申请
        	withDraw.setId(PKGenarator.getId());
	        withDraw.setOrderId(orderId);
	        withDraw.setFee(this.getWithDrawFee(withDraw.getFeeType()));
	        withDraw.setActualMoney(withDraw.getAmt()+withDraw.getFee());
 		    //新建提现申请(区块链转账成功后将提现状态设置为提现等待审核)
	        withDraw.setState(CrowdfundCoreConstants.withDrawState.add.getValue());
	        withDraw.setApplyTime(new Date());
	        withDraw.setThirdWtihDrawType(LetvPayConstants.thirdType.SXY);
	        this.withDrawDao.save(withDraw);
	        
	        if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
	        	//将提现状态设置为审核中
	        	withDraw.setState(CrowdfundCoreConstants.withDrawState.auditing.getValue());
	        	this.withDrawDao.updateBySelective(withDraw);
	        	//添加用户提现冻结资金
	        	userBillService.addWithDrawFreezeUserBill(withDraw);
	    		//发送提现申请成功消息
	    		this.sendWithDrawApplyMessage(withDraw);
	        }else{
	 		    //添加区块链请求参数
	 		    BlockAsynTranModel blockAsynTran =  new BlockAsynTranModel();
	            blockAsynTran = new BlockAsynTranModel();
	            blockAsynTran.setParentId(orderId);
		       	blockAsynTran.setType(BlockChainCore.Type.withdraw_apply);
		       	blockAsynTran.setRequestID(requestId);
		       	blockAsynTran.setCreateTime(new Date());
		       	blockAsynTranService.save(blockAsynTran);
	        	//发送用户提现转账请求(将用户的钱转到平台中间账户【冻结】)
	            Map<String,String> params = new HashMap<String,String>();
	            params.put("serviceID", "transfer");
	            params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
	            params.put("transferNO",orderId);
	            params.put("sourceAddress",userModel.getBlockChainAddress()); //转出账户地址
	            params.put("sourceKey", userModel.getBlockChainSecretKey());
	            params.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT);  //转入到平台中间账户
	            if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
	            	amount = 0.01;
	    		}
	            params.put("amount",String.valueOf(Arith.round(amount)));
	            params.put("requestID", requestId);
	            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"/withdraw/withDrawNotify.html");
	            logger.info("===========>用户提现转账请求参数-params:"+params);
	            String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
	            logger.info("===========>用户提现转账相应参数-resultStr:"+resultStr);
	            @SuppressWarnings("unchecked")
	            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
	            String status = resultMap.get("status").toString();
	            if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){   
	                logger.info("=========>提现转账请求提交成功=====================");
	            }else{
	                String message = resultMap.get("message")==null?"":resultMap.get("message").toString();
	                logger.info("提现申请失败-【"+message+"】！");
	                throw new ApplicationException(message);
	            }
	        }
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
    /**
     * 拒绝成功异步回调
     * @param transactionID
     */
    public void withDrawRefuseSucess(String transactionID){
    	BlockAsynTranModel model = new BlockAsynTranModel();
    	model.setTranId(transactionID);
    	model = blockAsynTranService.selectByModel(model);
    	if(model!=null && model.getParentId()!=null){
	       	WithDrawModel withDrawModel = this.getByOrderId(model.getParentId());
	       	withDrawModel.setState(FbdCoreConstants.withDrawState.refuse.getValue());
	       	withDrawDao.updateBySelective(withDrawModel);
	        if(withDrawModel.getState().equals(FbdCoreConstants.withDrawState.refuse.getValue())){
	             this.userBillService.addCancelWithDrawUserBill(withDrawModel);
	        }
	        this.sendWithDrawMessage(withDrawModel);
    	}
    }
    
    /**
     * 拒绝成功异步回调
     * @param transactionID
     */
    public void withDrawFailAfter(String requestId){
    	BlockAsynTranModel model =  blockAsynTranService.selectByRequestId(requestId);
    	if(model!=null && model.getParentId()!=null){
	       	WithDrawModel withDrawModel = this.getByOrderId(model.getParentId());
	       	withDrawModel.setState(FbdCoreConstants.withDrawState.fail.getValue());
	       	withDrawDao.updateBySelective(withDrawModel);
	        //添加提现失败账单
	        this.userBillService.addWithDrawFailUserBill(withDrawModel);
	        //发送提现失败信息
	        this.sendWithDrawFailMessage(withDrawModel);
    	}
    }
    /**
     * 财务审核通过提现
     * @param transactionID
     */
    public void withDrawAuditSuccess(String transactionID){
    	 BlockAsynTranModel model = new BlockAsynTranModel();
    	 model.setTranId(transactionID);
    	 model = blockAsynTranService.selectByModel(model);
    	 if(model!=null && model.getParentId()!=null){
    		 WithDrawModel withDrawModel = this.getByOrderId(model.getParentId());
           	 withDrawSuccess(withDrawModel);
           	 withDrawQueryService.sendRequestThird(withDrawModel);
    	 }
    }
    /**
     * 
     * Description: 发送提现站内信
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendWithDrawMessage(WithDrawModel model){
        Map<String, String> params = new HashMap<String,String>();
        params.put("time",DateUtil.dateTime2Str(model.getApplyTime(), null));
        params.put("money",Arith.format(model.getAmt()));
        try{
            logger.info("发送提现手机短信开始");
            String nodeCode = "";
            if(model.getState().equals(FbdCoreConstants.withDrawState.passed.getValue())){
                nodeCode = FbdCoreConstants.NODE_TYPE_WITHDRAW_PASS_MOBILE;
            }else if(model.getState().equals(FbdCoreConstants.withDrawState.refuse.getValue())){
                nodeCode = FbdCoreConstants.NODE_TYPE_WITHDRAW_REFUSE_MOBILE;
            }
            if(!StringUtil.isEmpty(nodeCode)){
                SendMessageUtil.sendMobileMessage(nodeCode, model.getUserId(), params);
            }
            logger.info("发送提现手机短信结束");
        }catch(Exception e){
            logger.error("发送提现手机短信失败，"+e.getMessage());
        }
         try{
             logger.info("发送提现站内信开始");
             String nodeCode = "";
             if(model.getState().equals(FbdCoreConstants.withDrawState.passed.getValue())){
                 nodeCode = FbdCoreConstants.NODE_TYPE_WITHDRAW_AUDITPASS_MSG;
             }else if(model.getState().equals(FbdCoreConstants.withDrawState.refuse.getValue())){
                 nodeCode = FbdCoreConstants.NODE_TYPE_WITHDRAW_AUDITREFUSE_MSG;
             }
             if(!StringUtil.isEmpty(nodeCode)){
                 SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_RECHARGE_WITHDRAW, model.getUserId(), params);
             }
             logger.info("发送提现站内信结束");
         }catch(Exception e){
             logger.error("发送提现站内信失败，"+e.getMessage());
         }
    }
    
    
    /**
     * 
     * Description: 发送提现失败站内信
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendWithDrawFailMessage(WithDrawModel model){
        Map<String, String> params = new HashMap<String,String>();
        params.put("time",DateUtil.dateTime2Str(model.getApplyTime(), null));
        params.put("money",Arith.format(model.getAmt()));
        try{
            logger.info("发送提现失败手机短信开始");
            String nodeCode = "";
            nodeCode = FbdCoreConstants.NODE_TYPE_WITHDRAW_FAIL_MOBILE;
            if(!StringUtil.isEmpty(nodeCode)){
                SendMessageUtil.sendMobileMessage(nodeCode, model.getUserId(), params);
            }
            logger.info("发送提现手机短信结束");
        }catch(Exception e){
            logger.error("发送提现手机短信失败，"+e.getMessage());
        }
         try{
             logger.info("发送提现站内信开始");
             String nodeCode = FbdCoreConstants.NODE_TYPE_WITHDRAW_FAIL_MSG;
             if(!StringUtil.isEmpty(nodeCode)){
                 SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_RECHARGE_WITHDRAW, model.getUserId(), params);
             }
             logger.info("发送提现站内信结束");
         }catch(Exception e){
             logger.error("发送提现站内信失败，"+e.getMessage());
         }
    }
    /**
     * 提现申请成功
     * @param withDraw
     */
    public void withDrawApplySuccess(WithDrawModel withDraw){
    	try{
    		logger.info("================>withDrawApplySuccess  begin");
	        //等待处理
	        withDraw.setState(CrowdfundCoreConstants.withDrawState.auditing.getValue());
	        this.withDrawDao.update(withDraw);
	        //冻结金额
	        userBillService.addWithDrawFreezeUserBill(withDraw);
    		//发送提现申请成功消息
    		this.sendWithDrawApplyMessage(withDraw);
    		logger.info("================>withDrawApplySuccess  end");
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
    
    
    
    /**
     * 
     * Description: 发送提现申请成功信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendWithDrawApplyMessage(WithDrawModel withDraw){
    	
       Map<String, String> params = new HashMap<String,String>();
       params.put("time",DateUtil.dateTime2Str(new Date(), null));
       params.put("money",Arith.format(withDraw.getActualMoney()));
       String backCard = withDraw.getBankCardId();
       params.put("bankCard",backCard);
       try{
           logger.info("发送提现申请成功手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_WITHDRAW_APPLY_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode,withDraw.getUserId(), params);
           logger.info("发送提现申请成功手机短信结束");
       }catch(Exception e){
           logger.error("发送提现申请成功手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送提现申请成功站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_WITHDRAW_APPLY_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.NODE_TYPE_WITHDRAW_MSG, withDraw.getUserId(), params);
            logger.info("发送提现申请成功站内信结束");
        }catch(Exception e){
            logger.error("发送提现申请成功站内信失败，"+e.getMessage());
        }
    }
    
    
    
 

    /**
     * 
     * Description:查询交易中和交易失败的提现记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getUserWithDrawList(
			WithDrawModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(this.withDrawDao.getUserWithDrawList(model));
		result.setTotal(this.withDrawDao.getUserWithDrawCount(model));
		
		return result;
	}

	/**
     * 
     * Description:查询交易中和交易失败的提现记录详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getUserWithDrawDetail(WithDrawModel model) {
		return this.withDrawDao.getUserWithDrawDetail(model);
	}
	
	
	
	/**
	 * 提现转账通知操作
	 * @param params
	 */
	public void withDrawTransferAfter(Map<String,Object> params){
		String orderId = params.get("transferNO").toString();
		String transactionID =params.get("transactionID").toString();  //事务ID 
    	String status = params.get("status").toString();  //事务ID 
		WithDrawModel model = this.withDrawDao.getByOrderId(orderId);
		
		model.setWithdrawTransactionId(transactionID);
		model.setWithdrawBlockChainState(status);
		this.withDrawDao.update(model);
	}
	
	/**
	 * 提现审核转账通知操作
	 * @param params
	 */
	public void withDrawAuditTransferAfter(Map<String,Object> params){
		String orderId = params.get("transferNO").toString();
		String transactionID =params.get("transactionID").toString();  //事务ID 
    	String status = params.get("status").toString();  //事务ID 
		WithDrawModel model = this.withDrawDao.getByOrderId(orderId);
		model.setAuditTransactionId(transactionID);
		model.setAuditBlockChainState(status);
		this.withDrawDao.update(model);
	}
	public void withDrawSuccess(WithDrawModel model){
    	model.setState("passed");
    	model.setStateName("财务审核通过");
    	 //审核通过后状态为审核通过
   	   this.withDrawDao.updateBySelective(model);
    }
	
	 

	private Map<String, String> getWithDrawSxyParams(WithDrawModel model) {
		Map<String,String> map = new HashMap<String, String>();
		map.put(SyxParam.v_mid,SxyPayConstants.Config.SXY_SHBH);
		map.put(SyxParam.v_data,this.getRequestWithDrawData(model));
		try {
			map.put(SyxParam.v_mac,SxyPayMd5.createMd5(java.net.URLEncoder.encode(map.get(SyxParam.v_mid).toString()+map.get(SyxParam.v_data).toString(), "utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		map.put(SyxParam.v_version,SxyPayConstants.VERSION);
		return map;
	}
    private String getRequestWithDrawData(WithDrawModel model) {
		
		Map<String,String> map = new HashMap<String, String>();
		map.put(SyxDataParam.FZXXZHS, "1");
		map.put(SyxDataParam.FZZJE, Arith.format(model.getAmt()));
		map.put(SyxDataParam.PCH, PKGenarator.getWithDrawOrderId());
		map.put(SyxDataParam.SFZH, model.getBankCardId());
		UserModel user = this.userDao.findByUserId(model.getUserId());
		map.put(SyxDataParam.SFZHM,user.getRealName());
		
		UserBankModel bankModel = this.userBankDao.getBnakByBankAccount(model.getBankCardId());
		map.put(SyxDataParam.SFKHH, bankModel.getBank());
		map.put(SyxDataParam.SFSF, bankModel.getBankProvince());
		map.put(SyxDataParam.SFCS, bankModel.getBankCityName());
		map.put(SyxDataParam.FKJE, Arith.format(model.getAmt()));
		map.put(SyxDataParam.KHBS, model.getOrderId());
		map.put(SyxDataParam.LHH, bankModel.getBankNo());
		
		return LetvPayUtils.getWithDrawDataValue(map);
	}
    private void sendWithDrawUpsPay(WithDrawModel model) {
		try {
			String signKey = LetvPayConstants.Config.PRIVATE_KEY;
	        String signType = LetvPayConstants.SIGNTYPE;
	        String inputCharset = LetvPayConstants.INPUTCHARSET;
	        String operationId = PKGenarator.getId();
	        Map<String,String> sParaTemp = this.getWithDrawUpsParams(model);
	        
	        Map<String,Object> resultJsonMap = null;
	        try {
	            Map<String, String> reqMap = Core.buildRequestPara(sParaTemp, signType, signKey, inputCharset);
	            
	            String requestUrl = LetvPayConstants.Config.SHOUDAN;
		        trusteeshipOperationService.saveOperationModel(operationId, 
		        		model.getUserId(), model.getOrderId(), 
		                LetvPayConstants.BusiType.withdraw, 
		                requestUrl, 
		                LetvPayConstants.THIRD_ID, MapUtil.mapToString(reqMap));
		        resultJsonMap = JsonUtil.paseJson2Map(Core.sendRequest(reqMap,requestUrl));
		        
		        if(resultJsonMap.get(Param.is_success).equals("T")){
		        	model.setState(CrowdfundCoreConstants.withDrawState.sended.getValue());
	            	this.withDrawDao.update(model);
		        }else{
		        	throw new ApplicationException("提现失败！");
		        }		        
		        
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		} catch (Exception e) {
			throw new ApplicationException("提现失败");
		}
	}
	private Map<String, String> getWithDrawUpsParams(WithDrawModel model) {
		Map<String, String> params = Core.publicParam(LetvPayConstants.Service.payment_to_card);
        params.put(Param.outer_trade_no, model.getOrderId());
        params.put(Param.identity_no, model.getUserId());
        params.put(Param.identity_type,LetvPayConstants.IdentityType.UID);
        UserBankModel bankModel = this.userBankDao.getBnakByBankAccount(model.getBankCardId());
        params.put(Param.bank_id,bankModel.getThirndBankId());
        params.put(Param.bank_account_no,RSAService.encryptPub(bankModel.getBankAccount(),LetvPayConstants.INPUTCHARSET));
        UserModel userModel = this.userDao.findByUserId(model.getUserId());
        params.put(Param.account_name, RSAService.encryptPub(userModel.getRealName(),LetvPayConstants.INPUTCHARSET));
        
        params.put(Param.bank_code,bankModel.getBankNo());
        params.put(Param.bank_name,bankModel.getBank());
        params.put(Param.bank_branch,bankModel.getName());
        params.put(Param.card_type,LetvPayConstants.cardType.DEBIT);
        params.put(Param.card_attribute,LetvPayConstants.cardAttribute.C);
        
        params.put(Param.amount,Arith.format(model.getAmt()));
        params.put(Param.notify_url,LetvPayConstants.Config.PRE_RESPONSE_WEB_URL+LetvPayConstants.WITHDRAW_RETURN_ADMIN_URL);
        params.put(Param.cert_no,userModel.getCertificateNo());
        params.put(Param.mobile,userModel.getMobile());
        
        return params;
	}
    private Map<String, String> getTransferParams(WithDrawModel model) {
		Map<String,String> platformParams = new HashMap<String,String>();
		UserModel userModel = this.userDao.findByUserId(model.getUserId());
        platformParams.put("serviceID", "transfer");
        platformParams.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
        platformParams.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_IN_ACCOUNT); //转出账户地址
        platformParams.put("sourceKey", FbdCoreConstants.BLOCK_CHAIN_PLATFORM_IN_KEY);
        platformParams.put("targetAddress",userModel.getBlockChainAddress());  //转入到平台中间账户
        platformParams.put("amount",String.valueOf(Arith.round(model.getActualMoney())));
        platformParams.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"/withdraw/WithDrawSXYNotify.html");
		return platformParams;
	}

    /**
     * 
     * Description:查询交易中
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getTrasactionList(
			WithDrawModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(withDrawDao.selectTranactionList(model));
		result.setTotal(withDrawDao.selectTranactionListCount(model));
		return result;
	}

	@Override
	public Map<String, Object> selectTranactionById(WithDrawModel model) {
		// TODO Auto-generated method stub
		return withDrawDao.selectTranactionById(model);
	}
}
