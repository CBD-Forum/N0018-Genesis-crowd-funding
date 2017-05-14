package com.fbd.web.app.sxyPay.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.sxyPay.utils.AESfunction;
import com.fbd.core.app.sxyPay.utils.SxyPayMd5;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.web.app.sxyPay.service.ISxyPayWithDrawService;
public class SxyPayWithDrawServiceImpl implements ISxyPayWithDrawService {

	
	 /**
	  * 发送提现申请
	  */
	 public void sendWithDrawRequest(WithDrawModel model){
		 
		 try{
			 String userId = model.getUserId(); 
			 
			 
			 
			 
			 
			 
			 
			 
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	
	/**
     * Description:组装提现参数参数
     * @param 
     * @return void
     * @throws 
     */
	private Map<String,String> getUserWithDrawParam(UserModel user) {
		Map<String,String> map = new HashMap<String, String>();
		try {
			map.put(SyxParam.v_mid, SxyPayConstants.Config.SXY_SHBH);
			map.put(SyxParam.v_cardno, AESfunction.Encrypt(user.getBankNo(), SxyPayConstants.Config.SXY_AES));
			map.put(SyxParam.v_idtype, "01");
			map.put(SyxParam.v_idnumber, AESfunction.Encrypt(user.getCertificateNo(), SxyPayConstants.Config.SXY_AES));
			map.put(SyxParam.v_idname, AESfunction.Encrypt(java.net.URLEncoder.encode(user.getRealName(), "utf-8"), SxyPayConstants.Config.SXY_AES));
			map.put(SyxParam.v_phone, AESfunction.Encrypt(user.getMobile(), SxyPayConstants.Config.SXY_AES));
			
			
			StringBuffer sb = new StringBuffer();
			sb.append(map.get(SyxParam.v_mid));
			sb.append(map.get(SyxParam.v_idname));
			map.put(SyxParam.v_mac, SxyPayMd5.createMd5(sb.toString())); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	

}
