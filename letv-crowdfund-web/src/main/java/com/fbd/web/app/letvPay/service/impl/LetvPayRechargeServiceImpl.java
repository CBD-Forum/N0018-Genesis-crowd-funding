package com.fbd.web.app.letvPay.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.letvPay.common.LetvPayConstants.TraderList;
import com.fbd.core.app.letvPay.utils.Core;
import com.fbd.core.app.letvPay.utils.JsonUtil;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.SysException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.HtmlElementUtil;
import com.fbd.core.util.LockUtil;
import com.fbd.core.util.MapUtil;
import com.fbd.web.app.letvPay.service.ILetvPayRechargeService;

@Service("letvPayRechargeService")
public class LetvPayRechargeServiceImpl implements ILetvPayRechargeService {

	@Resource
    private IRechargeDao rechargeDao;
	
	@Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
	
	private static final Logger logger = LoggerFactory
            .getLogger(LetvPayRechargeServiceImpl.class);
	
	/**
     * 
     * Description:充值
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String,Object>  sendRechargeRequest(RechargeModel recharge) {
		//插入充值单
        this.addRechargeOrder(recharge);
        String signKey = LetvPayConstants.Config.PRIVATE_KEY;
        String signType = LetvPayConstants.SIGNTYPE;
        String inputCharset = LetvPayConstants.INPUTCHARSET;
        String operationId = PKGenarator.getId();
        Map<String,String> sParaTemp = this.getRechargeParams(recharge);
        
        Map<String,Object> resultJsonMap = null;
        try {
            Map<String, String> reqMap = Core.buildRequestPara(sParaTemp, signType, signKey, inputCharset);
            
            String requestUrl = LetvPayConstants.Config.SHOUDAN;
	        trusteeshipOperationService.saveOperationModel(operationId, 
	                recharge.getUserId(), recharge.getOrderId(), 
	                LetvPayConstants.BusiType.recharge, 
	                requestUrl, 
	                LetvPayConstants.THIRD_ID, MapUtil.mapToString(reqMap));
	        resultJsonMap = JsonUtil.paseJson2Map(Core.sendRequest(reqMap,requestUrl));
	        
        } catch (Exception e) {
            e.printStackTrace();
        }
		return resultJsonMap;
        
	}

	private Map<String, String> getRechargeParams(RechargeModel recharge) {
		 Map<String, String> params = Core.publicParam(LetvPayConstants.Service.create_deposit);
	        params.put(Param.request_no, recharge.getOrderId());
	        params.put(Param.orig_trade_no,recharge.getOrderId());
	        params.put(Param.buyer_id, recharge.getUserId());
	        params.put(Param.buyer_id_type, LetvPayConstants.IdentityType.UID); 
	        params.put(Param.buyer_account_type, LetvPayConstants.MJHType.JBH); 
//	        params.put(Param.buyer_mobile, "18247768170"); 
	        params.put(Param.buyer_ip, recharge.getAddressIp()); 
	        params.put(Param.amount, Arith.format(recharge.getRechargeAmt())); 
	        params.put(Param.pay_method, "4:qpay^"+String.valueOf(recharge.getRechargeAmt()).length()+":"+recharge.getRechargeAmt()+"^"+recharge.getBankThirdNo().length()+":"+recharge.getBankThirdNo()); 
//	        params.put(Param.pay_method,"4:qpay^4:0.01^5:99786");//99617  99786
	        params.put(Param.notify_url,LetvPayConstants.Config.PRE_RESPONSE_WEB_URL+LetvPayConstants.RECHARGE_NOTIFY_URL);
	        params.put(Param.return_url,LetvPayConstants.Config.PRE_RESPONSE_WEB_URL+LetvPayConstants.RECHARGE_RETURN_URL);
	        params.put(Param.go_cashier, "Y"); 
	        params.put(Param.is_web_access, "Y"); 
	        
		return params;
	}
	
	private void addRechargeOrder(RechargeModel recharge) {
        recharge.setId(PKGenarator.getId());
        // 获得订单号
        recharge.setOrderId(PKGenarator.getOrderId());
        recharge.setState(FbdCoreConstants.rachargeState.recharging.getValue());
        recharge.setCreateTime(new Date());
        recharge.setThirdRechargeType(LetvPayConstants.thirdType.UPS);
        this.rechargeDao.save(recharge);
    }

	/**
     * 
     * Description:更新充值成功
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateRecharge(String orderId) {
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
            
         }catch (Exception e) {
             throw new SysException("更新充值单失败：" + e.getMessage());
         } finally {
                LockUtil.getInstance().remove(
                        FbdCoreConstants.LOCK_RECHARGE + orderId);
        }
	}
	
	/**
     * 
     * Description: 判断充值单是否被锁定
     * 
     * @param
     * @return boolean
     * @throws
     */
    public synchronized boolean isRechargeLock(String rechargeId) {
        boolean isLock = true;
        Object lockValue = LockUtil.getInstance().get(
                FbdCoreConstants.LOCK_RECHARGE + rechargeId);
        if (lockValue == null) {
            LockUtil.getInstance().put(
                    FbdCoreConstants.LOCK_RECHARGE + rechargeId, rechargeId);
            isLock = false;
        }
        logger.info("充值单isLock=====================" + isLock);
        return isLock;
    }

    /**
     * 
     * Description:支付推进
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> sendAdvanceInstantPay(String userId,
			String validationCode, String advanceVoucherNo) {
    	String signKey = LetvPayConstants.Config.PRIVATE_KEY;
        String signType = LetvPayConstants.SIGNTYPE;
        String inputCharset = LetvPayConstants.INPUTCHARSET;
        Map<String,String> sParaTemp = this.getAdvanceInstantPayParams(userId,validationCode,advanceVoucherNo);
        
        Map<String,Object> resultJsonMap = null;
        try {
            Map<String, String> reqMap = Core.buildRequestPara(sParaTemp, signType, signKey, inputCharset);
            
            String requestUrl = LetvPayConstants.Config.SHOUDAN;
	        resultJsonMap = JsonUtil.paseJson2Map(Core.sendRequest(reqMap,requestUrl));
	        
        } catch (Exception e) {
            e.printStackTrace();
        }
		return resultJsonMap;
	}

	private Map<String, String> getAdvanceInstantPayParams(String userId,
			String validationCode, String advanceVoucherNo) {
		Map<String, String> params = Core.publicParam(LetvPayConstants.Service.advance_instant_pay);
        params.put(Param.identity_no, userId);
        params.put(Param.identity_type,LetvPayConstants.IdentityType.UID);
        params.put(Param.advance_voucher_no, advanceVoucherNo);
        params.put(Param.validation_code, validationCode);
		return params;
	}

	/**
     * 
     * Description:即时到账
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> createImmediatePay(RechargeModel recharge) {
		//插入充值单
        this.addRechargeOrder(recharge);
        String signKey = LetvPayConstants.Config.PRIVATE_KEY;
        String signType = LetvPayConstants.SIGNTYPE;
        String inputCharset = LetvPayConstants.INPUTCHARSET;
        String operationId = PKGenarator.getId();
        Map<String,String> sParaTemp = this.getImmediatePayParams(recharge);
        
        Map<String,Object> resultJsonMap = null;
        try {
            Map<String, String> reqMap = Core.buildRequestPara(sParaTemp, signType, signKey, inputCharset);
            
            String requestUrl = LetvPayConstants.Config.SHOUDAN;
	        trusteeshipOperationService.saveOperationModel(operationId, 
	                recharge.getUserId(), recharge.getOrderId(), 
	                LetvPayConstants.BusiType.recharge, 
	                requestUrl, 
	                LetvPayConstants.THIRD_ID, MapUtil.mapToString(reqMap));
	        resultJsonMap = JsonUtil.paseJson2Map(Core.sendRequest(reqMap,requestUrl));
	        
        } catch (Exception e) {
            e.printStackTrace();
        }
		return resultJsonMap;
	}
	
	/**
     * 
     * Description:即时到账参数
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String, String> getImmediatePayParams(RechargeModel recharge) {
		Map<String, String> params = Core.publicParam(LetvPayConstants.Service.create_instant_trade);
        params.put(Param.request_no, recharge.getOrderId());
        params.put(Param.trade_list,this.getTradeList(recharge));
        params.put(Param.buyer_id, recharge.getUserId());
        params.put(Param.buyer_id_type, LetvPayConstants.IdentityType.UID); 
        params.put(Param.buyer_account_type, LetvPayConstants.MJHType.JBH); 
        params.put(Param.buyer_ip, recharge.getAddressIp()); 
        params.put(Param.pay_method, LetvPayConstants.rechargeType.qpay.length()+":"+LetvPayConstants.rechargeType.qpay+"^"+String.valueOf(recharge.getRechargeAmt()).length()+":"+recharge.getRechargeAmt()+"^"+recharge.getBankId().length()+":"+recharge.getBankId()); 
//        params.put(Param.notify_url,LetvPayConstants.Config.PRE_RESPONSE_WEB_URL+LetvPayConstants.RECHARGE_NOTIFY_URL);
//        params.put(Param.return_url,LetvPayConstants.Config.PRE_RESPONSE_WEB_URL+LetvPayConstants.RECHARGE_RETURN_URL);
        params.put(Param.go_cashier, "Y"); 
        params.put(Param.is_web_access, "Y"); 
        params.put(Param.is_anonymous, "Y"); 
        
	return params;
	}

	/**
     * 
     * Description:交易列表参数组装
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	private String getTradeList(RechargeModel recharge) {
		
		String[] chkParamNames = {TraderList.traderNo, TraderList.commodityName,
                TraderList.commodityPrice, TraderList.commodityNum, 
                TraderList.traderAmt, TraderList.royalty_parameters,
                TraderList.sellerId,TraderList.sellerType,TraderList.sellerMobile,
                TraderList.productDescription, TraderList.commodityShowUrl, TraderList.subOrderTime,
                TraderList.notifyUrl, TraderList.paymentExpires, TraderList.storeName,
                TraderList.sellerAccountType
		};
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(TraderList.traderNo, recharge.getOrderId());
		params.put(TraderList.commodityName, "充值");
		params.put(TraderList.commodityPrice, Arith.format(recharge.getRechargeAmt()));
		params.put(TraderList.commodityNum, "1");
		params.put(TraderList.traderAmt, Arith.format(recharge.getRechargeAmt()));
//		params.put(TraderList.royalty_parameters, value);
		params.put(TraderList.sellerId,LetvPayConstants.Config.PARENT_ID);
		params.put(TraderList.sellerType, LetvPayConstants.IdentityType.MEMBER_ID);
		params.put(TraderList.sellerMobile, "13311235122");
		params.put(TraderList.productDescription, "用户充值");
//		params.put(TraderList.commodityShowUrl, value);
		params.put(TraderList.subOrderTime,DateUtil.dateTime2Str(new Date(), "yyyymmddHHmmss"));
		params.put(TraderList.notifyUrl, LetvPayConstants.Config.PRE_RESPONSE_WEB_URL+LetvPayConstants.RECHARGE_NOTIFY_URL);
//		params.put(TraderList.paymentExpires, value);
//		params.put(TraderList.storeName, "");
		params.put(TraderList.sellerAccountType, LetvPayConstants.MJHType.JBH);
		
		return getTraderListString(chkParamNames, params);
	}
	
	public static String getTraderListString(String[] paramNames,
	            Map<String, String> params) {
		StringBuffer buffer = new StringBuffer();
        for (String str : paramNames) {
                Object value = params.get(str);
                if(value != null && !"".equals(value)){
                    buffer.append(StringUtils.trimToEmpty(value.toString()).length()+":"+StringUtils.trimToEmpty(value.toString())).append("~");
                }else{
                	buffer.append("0:").append("~");
                }
        }
        return buffer.toString().substring(0, buffer.toString().length()-1);     
	}
}
