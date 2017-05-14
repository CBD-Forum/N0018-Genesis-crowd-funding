package com.fbd.web.app.letvPay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.letvPay.utils.Core;
import com.fbd.core.app.letvPay.utils.JsonUtil;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.letvPay.service.ILetvPayAccountService;
import java.util.Map.Entry;

import javax.annotation.Resource;


@Service("letvPayAccountService")
public class LetvPayAccountServiceImpl implements ILetvPayAccountService {

	
	@Resource
	private IUserDao userDao;
	
	/**
     * 
     * Description: 实名认证
     *
     * @param 
     * @return void
     * @throws 
     * @Author WUWENBIN<br/>
     */
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String,Object> sendAuthAccountRequest(UserModel user) {
		 Map<String,Object> resultJsonMap = null;
		//判断如果 用户已经UPS成功实名认证则跳过
		 UserModel userModel = this.userDao.findByUserId(user.getUserId());
		if(FbdCoreConstants.IS.equals(userModel.getUPSAuth())){
			resultJsonMap = new HashMap<String, Object>();
			resultJsonMap.put(Param.is_success, "T");
			return resultJsonMap;
		}		
		
		Map<String,String> sParaTemp = this.getParam(user.getUserId());
		String signKey = LetvPayConstants.Config.PRIVATE_KEY;
        String signType = LetvPayConstants.SIGNTYPE;
        String inputCharset = LetvPayConstants.INPUTCHARSET;
        try {
            Map<String, String> reqMap = Core.buildRequestPara(sParaTemp, signType, signKey, inputCharset);
	        resultJsonMap = JsonUtil.paseJson2Map(Core.sendRequest(reqMap,LetvPayConstants.Config.HUIYUAN));
        } catch (Exception e) {
            e.printStackTrace();
        }
		return resultJsonMap;
		
	}

	/**
     * 
     * Description:组装实名认证数据
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String,String> getParam(String userId) {
        Map<String, String> params = Core.publicParam(LetvPayConstants.Service.bind_identity);
        UserModel user = this.userDao.findByUserId(userId);
        params.put(Param.identity_no, user.getUserId());
        params.put(Param.identity_type,LetvPayConstants.IdentityType.UID);
        params.put(Param.request_no, PKGenarator.getGroupNo());
        params.put(Param.auth_mode,LetvPayConstants.authMode.IDENTITY_CERT); 
        params.put(Param.account_name,user.getRealName());
        params.put(Param.identity_num,user.getCertificateNo());
        params.put(Param.mobile,user.getMobile());
        
        params.put(Param.check_card,LetvPayConstants.checkCard.userAuth);
        
//        params.put(Param.identity_no, "wwwwww");
//        params.put(Param.identity_type,LetvPayConstants.IdentityType.UID);
//        params.put(Param.request_no, PKGenarator.getGroupNo());
//        params.put(Param.auth_mode,LetvPayConstants.authMode.IDENTITY_CERT); 
//        params.put(Param.account_name,"武文斌");
//        params.put(Param.identity_num,"140181199309293039");
//        params.put(Param.mobile,"13311235192");
//        params.put(Param.check_card,LetvPayConstants.checkCard.userAuth);
        
        return params;
	}

	/**
     * 
     * Description:个人开户
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String,Object> createPersonalMember(UserModel user) {
		Map<String, Object> resultJsonMap = null;
		UserModel userModel = this.userDao.findByUserId(user.getUserId());
		//判断如果 用户已经UPS成功开户则跳过
		if(null != userModel.getThirdAccount() && !"".equals(userModel.getThirdAccount())){
			resultJsonMap = new HashMap<String, Object>();
			resultJsonMap.put(Param.is_success, "T");
			resultJsonMap.put(Param.is_thirnd, "T");
			return resultJsonMap;
		}
		//更新用户真实姓名和身份证号
		this.userDao.updateByUserId(user);
		
		Map<String, String> sParaTemp = this.getCrePMMerSParam(user.getUserId());
		String signKey = LetvPayConstants.Config.PRIVATE_KEY;
        String signType = LetvPayConstants.SIGNTYPE;
        String inputCharset = LetvPayConstants.INPUTCHARSET;
        
        try {
            Map<String, String> reqMap = Core.buildRequestPara(sParaTemp, signType, signKey, inputCharset);
            resultJsonMap = JsonUtil.paseJson2Map(Core.sendRequest(reqMap,LetvPayConstants.Config.HUIYUAN));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJsonMap;
		
	}
	private Map<String, String> getCrePMMerSParam(String userId){
		Map<String, String> params = Core.publicParam(LetvPayConstants.Service.create_personal_member);
		UserModel user = this.userDao.findByUserId(userId);
		params.put(Param.mobile, user.getMobile());
		params.put(Param.real_name, user.getRealName());
		params.put(Param.id_card_no, user.getCertificateNo());
        params.put(Param.uid, userId);
        return params;
	}

	/**
     * 
     * Description:修改用户第三方会员号
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateThirdAccount(UserModel user) {
		this.userDao.updateByUserId(user);
	}
}
