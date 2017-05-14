/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
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

package com.fbd.admin.app.withdraw.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.admin.app.withdraw.action.WithDrawAction;
import com.fbd.admin.app.withdraw.service.IWithDrawService;
import com.fbd.core.app.bank.dao.IUserBankDao;
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockChain.service.impl.BlockChainQueryServiceImpl;
import com.fbd.core.app.blockChain.util.BlockChainStringUtil;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.letvPay.utils.Core;
import com.fbd.core.app.letvPay.utils.RSAService;
import com.fbd.core.app.sxyPay.common.LetvPayUtils;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxDataParam;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.sxyPay.utils.SAXParser;
import com.fbd.core.app.sxyPay.utils.SxyPayMd5;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.withdraw.dao.IWithDrawDao;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.app.withdraw.service.IWithDrawQueryService;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
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
import com.fbd.core.util.spring.SpringUtil;
import com.fbd.admin.app.blockasyntran.service.IBlockAsynTranService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 取现
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("withDrawService")
public class WithDrawServiceImpl implements IWithDrawService{
    
    private static final Logger logger = LoggerFactory.getLogger(WithDrawAction.class);

    @Resource
    private IWithDrawDao withDrawDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private IUserBankDao userBankDao;
    @Resource
    private ISystemBillService systemBillService;
    @Resource
    private IWithDrawQueryService withDrawQueryService;
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    @Resource
    private IBlockAsynTranService blockAsynTranService;
    @Resource
	private IBlockAsynTranDao blockAsynTranDao;
    
    public WithDrawModel getByOrderId(String orderId){
        return this.withDrawDao.getByOrderId(orderId);
    }

    /**
     * Description: 查询用户提现列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午3:22:45
     */
    public SearchResult<Map<String, Object>> getWithDrawPage(Map<String, Object> param) {
        return this.withDrawDao.getWithDrawPage(param);
    }
    
    /**
     * 
     * Description:提现审核后更新提现记录 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-20 下午6:25:08
     */
    public void updateWithDrawAfterAudit(WithDrawModel model){
        this.withDrawDao.update(model);
        this.sendWithDrawMessage(model);
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
        platformParams.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT);  //转入到平台中间账户
        platformParams.put("amount",String.valueOf(Arith.round(model.getActualMoney())));
        platformParams.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"/withdraw/withDrawNotify.html");
		return platformParams;
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
     * 提现审核拒绝后的操作
     * @param model
     */
    @SuppressWarnings("unchecked")
	public void saveWithDrawAuditRefuse(WithDrawModel model,String requestId){
       	
    	try{
    		if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
    	       	 WithDrawModel withDrawModel = this.getByOrderId(model.getOrderId());
    	         withDrawModel.setState(FbdCoreConstants.withDrawState.refuse.getValue());
    	         withDrawDao.updateBySelective(withDrawModel);
    	         if(withDrawModel.getState().equals(FbdCoreConstants.withDrawState.refuse.getValue())){
    	             this.userBillService.addCancelWithDrawUserBill(withDrawModel);
    	        }
    	        //发送消息
    	        this.sendWithDrawMessage(withDrawModel);
    		}else{
        		String operationId = PKGenarator.getId();
                String type = BlockChainCore.Type.withdraw_refuse;  //提现审核拒绝
             	//添加一条事务通知数据
              	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
              	blockAsynTran.setId(operationId);
              	blockAsynTran.setParentId(model.getOrderId());  //支持编号
              	blockAsynTran.setCreateTime(new Date());
              	blockAsynTran.setUpdateTime(new Date());
              	blockAsynTran.setType(type);
              	blockAsynTran.setRequestID(requestId);
              	this.blockAsynTranDao.save(blockAsynTran);
              	
            	UserModel userModel = this.userDao.findByUserId(model.getUserId());
            	//发送提现审核拒绝转账请求(将用户的钱转到平台中间账户的钱转到用户账户(冻结资金解冻))
                Map<String,String> params = new HashMap<String,String>();
                params.put("serviceID", "transfer");
                params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
                params.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT); //转出账户地址
                params.put("sourceKey", FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_KEY);
                params.put("targetAddress",userModel.getBlockChainAddress());  //转入到平台中间账户
                double amount =Arith.round(model.getActualMoney());
                if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
        			amount = 0.01;
        		}
                params.put("amount",String.valueOf(amount));
                params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"withdraw/withDrawRefuseNotify.html");
                String transferNo = BlockChainStringUtil.getUniqueTransferNoEnc(model.getOrderId());
                params.put("requestID",requestId );
                params.put("transferNO",transferNo );
                logger.info("========>提现审核拒绝转账请求参数-params："+params);
                String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
                String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
                logger.info("========>提现审核拒绝转账相应参数-resultStr："+resultStr);
                Map<String,Object> platformResultMap = JsonHelper.getMapFromJson(resultStr);
                
                String platformState = platformResultMap.get("status").toString();
                if(BlockChainCore.ResultStatus.ServiceSuccess.equals(platformState)){ //转账成功
                   logger.info("==========提现拒绝转账提交成功===============");
               }else{
                    String message = platformResultMap.get("status")==null?"":platformResultMap.get("status").toString();
                     logger.info("提现拒绝转账【入账户转账到中间账户】失败-【"+message+"】！");
                    throw new ApplicationException(message);
                }
    		}
    	}catch(Exception e){
    		throw new ApplicationException(e.getMessage());
    	}
    }
    
    /**
     * 审核通过后的操作
     * @param model
     */
    public void saveWithDrawAuditPassed(WithDrawModel model){
    	try{
    		double amount = model.getAmt()+model.getFee();
    		String balance = withDrawQueryService.QueryBalanceSxy();
            if(balance!=null && amount<=Double.valueOf(balance)){
            	 try{
    				 //调用三方支付将钱转账到用户账户
    				 withDrawQueryService.sendRequestThird(model);
            	 }catch(Exception e){
                    throw new ApplicationException(e.getMessage());
            	 }
             }else{
            	 throw new ApplicationException("账户余额不足,账户余额是:"+balance);
             }
    		
    	}catch(Exception e){
    		throw new ApplicationException(e.getMessage());
    	}
    }
    
    
    /**
     * 申请提现申请
     * @param model
     */
    public void sendWithDrawRequest(WithDrawModel model){
    	try{
    		
    		String bankCardId = model.getBankCardId();
    		//查询银行卡信息
    		UserBankModel userBank = this.userBankDao.selectByPrimaryKey(bankCardId);
 
  
    	}catch(Exception e){
    		e.printStackTrace();
    		throw new ApplicationException(e.getMessage());
    	}
    }

    /**
     * 发送提现请求
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月29日 下午12:14:34
     */
    public void sendWithDrawSxyPay(WithDrawModel model) {
        try {
            Map<String,String> paramsMap = this.getWithDrawSxyParams(model);
            //提交Map
            String url = SxyPayConstants.Config.SXY_WITHDRAW;
            Client cc = Client.create();
            WebResource rr = cc.resource(url);
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            queryParams.add("v_mid", paramsMap.get("v_mid")); // 商户编号
            queryParams.add("v_data", paramsMap.get("v_data")); 
            queryParams.add("v_mac", paramsMap.get("v_mac"));
            queryParams.add("v_version", paramsMap.get("v_version"));
            String resultStr = rr.queryParams(queryParams).get(String.class);

            String status = SAXParser.SAXParseNodeValue(resultStr, "status");
            String statusdesc = SAXParser.SAXParseNodeValue(resultStr, "statusdesc");
            //发送成功
            if("0".equals(status)){
                model.setState(CrowdfundCoreConstants.withDrawState.sended.getValue());
                this.withDrawDao.update(model);
            } else{
                throw new ApplicationException(statusdesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Map<String, String> getWithDrawSxyParams(WithDrawModel model) {
        Map<String,String> map = new HashMap<String, String>();
        map.put(SyxParam.v_mid,SxyPayConstants.Config.SXY_SHBH);
        try {
            map.put(SyxParam.v_data,java.net.URLEncoder.encode(this.getRequestWithDrawData(model),"GBK"));
            map.put(SyxParam.v_mac,SxyPayMd5.createMd5(map.get(SyxParam.v_mid).toString()+java.net.URLEncoder.encode(map.get(SyxParam.v_data).toString(),"UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put(SyxParam.v_version,SxyPayConstants.VERSION);
        return map;
    }

    private String getRequestWithDrawData(WithDrawModel model) {
        
        Map<String,String> map = new HashMap<String, String>();
        map.put(SyxDataParam.FZXXZHS, "1");
        if(model.getAmt()<10){
            throw new ApplicationException("金额不能小于10元");
        }
        map.put(SyxDataParam.FZZJE, Arith.format(model.getAmt()));

        map.put(SyxDataParam.PCH, PKGenarator.getWithDrawOrderId());

        map.put(SyxDataParam.SFZH, model.getBankCardId());

        UserModel user = this.userDao.findByUserId(model.getUserId());
        
        map.put(SyxDataParam.SFZHM,user.getRealName());

        UserBankModel bankModel = this.userBankDao.getBnakByBankAccount(model.getBankCardId());
        map.put(SyxDataParam.SFKHH, bankModel.getBank());
        
        map.put(SyxDataParam.SFSF, bankModel.getBankProvinceName());
        if("北京".equals(bankModel.getBankProvinceName())||"上海".equals(bankModel.getBankProvinceName())||"天津".equals(bankModel.getBankProvinceName())||"重庆".equals(bankModel.getBankProvinceName()))     
           map.put(SyxDataParam.SFCS, bankModel.getBankProvinceName());
        else
            map.put(SyxDataParam.SFCS, bankModel.getBankCityName());
    
        map.put(SyxDataParam.FKJE, Arith.format(model.getAmt()));

        map.put(SyxDataParam.KHBS, model.getOrderId());

        map.put(SyxDataParam.LHH, bankModel.getBankNo());

        return LetvPayUtils.getWithDrawDataValue(map);
    }
    
    /**
     * 调用三方支付将钱转账到用户账户
     * @param model
     */
    public void sendRequestThird(WithDrawModel model) {
        if(model.getState().equals(FbdCoreConstants.withDrawState.refuse.getValue())){
            this.userBillService.addCancelWithDrawUserBill(model);
        }else{
            //如果审核成功   调用第三方  判断用户使用的哪个第三方
            if(LetvPayConstants.thirdType.SXY.equals(model.getThirdWtihDrawType())){
                this.sendWithDrawSxyPay(model);
            }else{
              //  this.sendWithDrawUpsPay(model);
            }
        }
    }

	@Override
	public long selectSuccessCount(String orderId) {
		// TODO Auto-generated method stub
		return withDrawDao.selectSuccessCount(orderId);
	}
    
    
    
}
