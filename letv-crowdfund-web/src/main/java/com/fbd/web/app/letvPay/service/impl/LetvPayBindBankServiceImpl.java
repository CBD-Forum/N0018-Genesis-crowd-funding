package com.fbd.web.app.letvPay.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.bank.dao.IUserBankDao;
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.letvPay.utils.Core;
import com.fbd.core.app.letvPay.utils.JsonUtil;
import com.fbd.core.app.letvPay.utils.RSAService;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.letvPay.service.ILetvPayBindBankService;

@Service("letvPayBindBankService")
public class LetvPayBindBankServiceImpl implements ILetvPayBindBankService {

	
	@Resource
    private IUserBankDao userBankDao;
	
	@Resource
	private IUserDao userDao;
	
	/**
     * 
     * Description:绑定银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> createBindBnak(UserBankModel model) {
		
		Map<String,String> sParaTemp = this.getBindBnakParam(model);
		String signKey = LetvPayConstants.Config.PRIVATE_KEY;
        String signType = LetvPayConstants.SIGNTYPE;
        String inputCharset = LetvPayConstants.INPUTCHARSET;
        
        Map<String,Object> resultJsonMap = null;
        try {
            Map<String, String> reqMap = Core.buildRequestPara(sParaTemp, signType, signKey, inputCharset);
	        resultJsonMap = JsonUtil.paseJson2Map(Core.sendRequest(reqMap,LetvPayConstants.Config.HUIYUAN));
	        if(resultJsonMap.get(Param.is_success).equals("T")){
	        	System.out.print("===绑定银行卡成功===="+resultJsonMap);
	        	model.setThirndBankId(resultJsonMap.get(Param.bank_id).toString());
	        	this.saveUserBank(model);
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return resultJsonMap;
	}

	/**
     * 
     * Description:绑定银行卡参数
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     */
	private Map<String, String> getBindBnakParam(UserBankModel model) {
		
		UserModel userModel = this.userDao.findByUserId(model.getUserId());
		Map<String, String> params = Core.publicParam(LetvPayConstants.Service.create_bank_card);
        params.put(Param.identity_no, model.getUserId());
        params.put(Param.identity_type,LetvPayConstants.IdentityType.UID);
        params.put(Param.bank_code, model.getBankNo());
        params.put(Param.bank_name,model.getBank()); 
        params.put(Param.bank_branch,model.getName());
        params.put(Param.bank_account_no,RSAService.encryptPub(model.getBankAccount(),LetvPayConstants.INPUTCHARSET));
        params.put(Param.account_name, RSAService.encryptPub(userModel.getRealName(),LetvPayConstants.INPUTCHARSET));
        params.put(Param.cert_num,userModel.getCertificateNo());
        params.put(Param.mobile_num,userModel.getMobile());
        params.put(Param.pay_attribute,LetvPayConstants.payAttribute.qpay);
        params.put(Param.card_type,LetvPayConstants.cardType.DEBIT);
        params.put(Param.card_attribute,LetvPayConstants.cardAttribute.C);
        
        return params;
	}
	private  Map<String, String> getAddCardSParam(){
		Map<String, String> sParaTemp = Core.publicParam(LetvPayConstants.Service.create_bank_card);
        sParaTemp.put("identity_no", "brt5821940");
        sParaTemp.put("identity_type", "UID");
        sParaTemp.put("bank_code", "CMB");
        sParaTemp.put("bank_name", "招商银行");
        sParaTemp.put("bank_account_no", RSAService.encryptPub("6225880170703632","UTF-8"));
        sParaTemp.put("account_name", RSAService.encryptPub("李恒","UTF-8"));
        sParaTemp.put("cert_num", "110105198812166559");
        sParaTemp.put("mobile_num", "15210327525");
        sParaTemp.put("pay_attribute", "qpay");
        sParaTemp.put("card_type", "DEBIT");
        sParaTemp.put("card_attribute", "C");
        
        System.out.println("reqmsg: " + sParaTemp);
        return sParaTemp;
	}

	/**
     * 
     * Description:保存用户银行信息 
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     */
    private void saveUserBank(UserBankModel userBank){
        userBank.setId(PKGenarator.getId());
        userBank.setCreateTime(new Date());
        userBank.setState(FbdCoreConstants.USER_BANK_STATE_BIND);
        userBank.setThirdBindType(LetvPayConstants.thirdType.UPS);
        this.userBankDao.save(userBank);
    }

    /**
     * 
     * Description:解绑银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    @Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> unbundlingBank(UserBankModel model) {
		Map<String,String> sParaTemp = this.getUnbundlingBankParam(model);
		String signKey = LetvPayConstants.Config.PRIVATE_KEY;
        String signType = LetvPayConstants.SIGNTYPE;
        String inputCharset = LetvPayConstants.INPUTCHARSET;
        
        Map<String,Object> resultJsonMap = null;
        try {
            Map<String, String> reqMap = Core.buildRequestPara(sParaTemp, signType, signKey, inputCharset);
	        resultJsonMap = JsonUtil.paseJson2Map(Core.sendRequest(reqMap,LetvPayConstants.Config.HUIYUAN));
	        if(resultJsonMap.get(Param.is_success).equals("T")){
	        	System.out.print("===解绑银行卡成功===="+resultJsonMap);
	        	model.setState(FbdCoreConstants.USER_BANK_STATE_UNBIND);
	        	this.userBankDao.updateBySelective(model);
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return resultJsonMap;
	}

	private Map<String, String> getUnbundlingBankParam(UserBankModel model) {
		Map<String, String> params = Core.publicParam(LetvPayConstants.Service.unbundling_bank_card);
        params.put(Param.identity_no, model.getUserId());
        params.put(Param.identity_type,LetvPayConstants.IdentityType.UID);
        params.put(Param.bank_id, model.getThirndBankId());
        params.put(Param.pay_attribute, LetvPayConstants.payAttribute.qpay);
        return params;
	}

	/**
     * 
     * Description:查询银行卡列表
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getBankListByThirnd(UserBankModel model) {
		Map<String,String> sParaTemp = this.getBankListByThirndParam(model);
		String signKey = LetvPayConstants.Config.PRIVATE_KEY;
        String signType = LetvPayConstants.SIGNTYPE;
        String inputCharset = LetvPayConstants.INPUTCHARSET;
        
        Map<String,Object> resultJsonMap = null;
        try {
            Map<String, String> reqMap = Core.buildRequestPara(sParaTemp, signType, signKey, inputCharset);
	        resultJsonMap = JsonUtil.paseJson2Map(Core.sendRequest(reqMap,LetvPayConstants.Config.HUIYUAN));
	        if(resultJsonMap.get(Param.is_success).equals("T")){
	        	System.out.print("===查询银行卡成功===="+resultJsonMap);
//	        	model.setState(FbdCoreConstants.USER_BANK_STATE_UNBIND);
//	        	this.userBankDao.updateBySelective(model);
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return resultJsonMap;
	}
	
	private Map<String, String> getBankListByThirndParam(UserBankModel model) {
		Map<String, String> params = Core.publicParam(LetvPayConstants.Service.query_bank_card);
        params.put(Param.identity_no, model.getUserId());
        params.put(Param.identity_type,LetvPayConstants.IdentityType.UID);
        params.put(Param.card_type,LetvPayConstants.cardType.DEBIT);
        params.put(Param.card_attribute,LetvPayConstants.cardAttribute.C);
        params.put(Param.pay_attribute, LetvPayConstants.payAttribute.qpay);
        return params;
	}

	public UserBankModel getBnakByBankAccount(String bankAccount) {
		return this.userBankDao.getBnakByBankAccountAndState(bankAccount,FbdCoreConstants.USER_BANK_STATE_BIND);
	}

}
