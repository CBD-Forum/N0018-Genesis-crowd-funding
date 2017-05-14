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

import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.sxyPay.utils.SxyPayMd5;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
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

@Service("copysxyPayRechargeService")
public class CopyOfSxyPayRechargeServiceImpl implements ISxyPayRechargeService {
	private static final Logger logger = LoggerFactory
            .getLogger(CopyOfSxyPayRechargeServiceImpl.class);
	@Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
	
	@Resource
    private IRechargeDao rechargeDao;
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
	         
	         
		} catch (Exception e) {
            throw new SysException("更新充值单失败：" + e.getMessage());
        } finally {
               LockUtil.getInstance().remove(
                       FbdCoreConstants.LOCK_RECHARGE + orderId);
        }    
		 
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
