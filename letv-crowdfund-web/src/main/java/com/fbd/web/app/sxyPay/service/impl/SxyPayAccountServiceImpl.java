package com.fbd.web.app.sxyPay.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.bank.dao.IUserBankDao;
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.sxyPay.utils.AESfunction;
import com.fbd.core.app.sxyPay.utils.SxyPayMd5;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.sxyPay.service.ISxyPayAccountService;

@Service("sxyPayAccountService")
public class SxyPayAccountServiceImpl implements ISxyPayAccountService {

	
	@Resource
	private IUserDao userDao;
	
	@Resource
    private IUserBankDao userBankDao;
	
	/**
     * 
     * Description:开户
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String createUserAccount(UserModel user) {
		
		Map<String, String> map = this.getUserAccountParam(user);
		String url = SxyPayConstants.Config.SXY_ACCOUNT+"?v_mid="+map.get(SyxParam.v_mid)+"&v_cardno="+map.get(SyxParam.v_cardno)+"&v_idtype="+
		map.get(SyxParam.v_idtype)+"&v_idnumber="+map.get(SyxParam.v_idnumber)+"&v_idname="+map.get(SyxParam.v_idname)+"&v_phone="+map.get(SyxParam.v_phone)+
		"&v_mac="+map.get(SyxParam.v_mac);
		
		System.out.println("首易信支付请求参数："+url);
		HttpClient httpClient = new HttpClient();  
        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式  
        PostMethod postMethod = new PostMethod(url);  
        int statusCode;
        String resultStr = "";
		try {
			statusCode = httpClient.executeMethod(postMethod);
			 System.out.println("statusCode:"+statusCode);
		      byte[] body  = postMethod.getResponseBody();
		        resultStr = new String(body);
//		        info = new String(body, "UTF-8");
//		        String ret = SAXParser.SAXParseNodeValue(resultStr, "status");
//		        String pstatus = SAXParser.SAXParseNodeValue(resultStr, "statusdesc");
//		        String v_idname = SAXParser.SAXParseNodeValue(resultStr, "verifystatus");
		        System.out.print(resultStr);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultStr;
	}

	/**
     * 
     * Description:组装实名认证参数
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String,String> getUserAccountParam(UserModel user) {
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
			sb.append(map.get(SyxParam.v_cardno));
			sb.append(map.get(SyxParam.v_idtype));
			sb.append(map.get(SyxParam.v_idnumber));
			sb.append(map.get(SyxParam.v_idname));
			sb.append(map.get(SyxParam.v_phone));
			map.put(SyxParam.v_mac, SxyPayMd5.createMd5(sb.toString())); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
		
		
	}

	/**
     * 
     * Description:修改用户认证信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateUserInfo(UserModel user) {
		user.setIsAuth(FbdCoreConstants.IS);
		user.setSXYAuth(FbdCoreConstants.IS);
		this.userDao.updateByUserId(user);
		
		
		System.out.println(user.getUserId());
		//增加银行卡号 为未绑定
		UserBankModel model = new UserBankModel();
		model.setId(PKGenarator.getId());
		String userId = user.getUserId();
		model.setUserId(userId);
		model.setBankAccount(user.getBankNo());
		model.setState(FbdCoreConstants.USER_BANK_STATE_UNBIND);
		model.setUpdateTime(new Date());
		model.setCreateTime(new Date());
        this.userBankDao.save(model);
		
	}

}
