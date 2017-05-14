package com.fbd.web.app.letvPay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.withdraw.dao.IWithDrawDao;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.SysException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.LockUtil;
import com.fbd.core.util.ValidateUtils;
import com.fbd.web.app.letvPay.service.ILetvPayWithDrawService;

@Service("letvPayWithDrawService")
public class LetvPayWithDrawServiceImpl implements ILetvPayWithDrawService {

	private static final Logger logger = LoggerFactory.getLogger(LetvPayWithDrawServiceImpl.class);
	
	@Resource
	private IUserDao userDao ;
	
	@Resource
	private IUserBillService userBillService ;
	
	@Resource
	private IBusinessConfigDao businessConfigDao ;
	
	@Resource
	private IWithDrawDao withDrawDao ;
	
	@Resource
	private ISystemBillService systemBillService ;
	
	/**
     * 
     * Description:提交提现申请验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin<br/>
     */
	public void checkWithDraw(WithDrawModel withDraw) {
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
     * Description: 查询取现服务费
     *
     * @param 
     * @return double
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 上午10:22:51
     */
    private double getWithDrawFee(String feeType){
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
     * Description:提交提现申请验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin<br/>
     */
	public void saveWithDraw(WithDrawModel withDraw) {
		try{
        	
        	UserModel userModel = this.userDao.findByUserId(withDraw.getUserId());
        	
        	String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
        	//发送提现转账请求(将用户的钱转到平台中间账户)
            Map<String,String> platformParams = new HashMap<String,String>();
            platformParams.put("serviceID", "transfer");
            platformParams.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            platformParams.put("sourceAddress",userModel.getBlockChainAddress()); //转出账户地址
            platformParams.put("sourceKey", userModel.getBlockChainSecretKey());
            platformParams.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT);  //转入到平台中间账户
            platformParams.put("amt",String.valueOf(Arith.round(withDraw.getAmt()+withDraw.getFee())));
            platformParams.put("notifyURL","");
            String platformResultStr = MockUtils.transfer(requestUrl, platformParams);
            @SuppressWarnings("unchecked")
            Map<String,Object> platformResultMap = JsonHelper.getMapFromJson(platformResultStr);
            String platformState = platformResultMap.get("state").toString();
            if("TransactionSuccess".equals(platformState)){ //转账成功
                withDraw.setId(PKGenarator.getId());
                withDraw.setOrderId(PKGenarator.getOrderId());
                withDraw.setFee(this.getWithDrawFee(withDraw.getFeeType()));
                withDraw.setActualMoney(withDraw.getAmt()+withDraw.getFee());
                //等待处理
                withDraw.setState(CrowdfundCoreConstants.withDrawState.auditing.getValue());
                withDraw.setApplyTime(new Date());
                withDraw.setThirdWtihDrawType(LetvPayConstants.thirdType.UPS);
                this.withDrawDao.save(withDraw);
                //冻结金额
                userBillService.addWithDrawFreezeUserBill(withDraw);
            }else{
                String message = platformResultMap.get("state")==null?"":platformResultMap.get("state").toString();
                 logger.info("意向金退款【转账划款平台罚款】失败-【"+message+"】！");
                throw new ApplicationException(message);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("提现申请提交失败");
        }
	}

	/**
     * 
     * Description:提现成功更新提现记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin<br/>
     */
	public void updateUserWithDraw(String orderId) {
		if (this.isLock(orderId)) {
            logger.info("请求失败,orderId被锁！" + orderId);
            return;
        }
        WithDrawModel withDraw = this.withDrawDao.getByOrderId(orderId);
        try {
            if (withDraw == null) {
                throw new ApplicationException("订单号为" + orderId + "的充值单不存在");
            }
            String withDrawState = CrowdfundCoreConstants.withDrawState.success.getValue();
            if (withDraw.getState().equals(withDrawState)) {// 充值状态已经更新
                logger.info(orderId + "提现已经完成！");
                return;
            }
            //区块链转账
            Map<String,String> param = this.getTransferParams(withDraw);
            String requestUrl = "";
            String platformResultStr = MockUtils.transfer(requestUrl, param);
            Map<String,Object> platformResultMap = JsonHelper.getMapFromJson(platformResultStr);
            if("TransactionSuccess".equals(platformResultMap.get("state"))){
            	withDraw.setState(CrowdfundCoreConstants.withDrawState.success.getValue());
                this.withDrawDao.update(withDraw);
                //扣除提现用户账单
                this.userBillService.addWithDrawUserBill(withDraw, withDraw.getActualMoney());
                //平台收取手续费
                this.systemBillService.addBill(CrowdfundCoreConstants.systemTradeType.withDrawFee.getValue(), withDraw.getFee(),
                        FbdCoreConstants.tradeDirection.in.getValue(), "","用户提现收取手续费",withDraw.getOrderId());
            }
            
         }catch (Exception e) {
             throw new SysException("更新充值单失败：" + e.getMessage());
         } finally {
                LockUtil.getInstance().remove(
                        FbdCoreConstants.LOCK_RECHARGE + orderId);
        }
	}
	
	/**
     * Description: 区块链组装参数
     *
     * @param 
     * @return Map<String,String>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-30 上午11:16:08
     */
    private Map<String, String> getTransferParams(WithDrawModel model) {
        Map<String,String> platformParams = new HashMap<String,String>();
        UserModel userModel = this.userDao.findByUserId(model.getUserId());
        platformParams.put("serviceID", "transfer11");
        platformParams.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
        platformParams.put("sourceAddress",userModel.getBlockChainAddress()); //转出账户地址
        platformParams.put("sourceKey", userModel.getBlockChainSecretKey());
        platformParams.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT);  //转入到平台中间账户
        platformParams.put("amt",String.valueOf(Arith.round(model.getActualMoney())));
        platformParams.put("notifyURL","");
        return platformParams;
    }
	
	/**
     * 
     * Description: 判断充值单是否被锁定
     * 
     * @param
     * @return boolean
     * @throws
     */
    public synchronized boolean isLock(String rechargeId) {
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
}
