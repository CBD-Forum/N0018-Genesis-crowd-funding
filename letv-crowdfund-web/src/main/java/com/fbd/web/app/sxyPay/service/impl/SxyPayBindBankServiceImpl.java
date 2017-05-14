package com.fbd.web.app.sxyPay.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.sxyPay.utils.AESfunction;
import com.fbd.core.app.sxyPay.utils.SxyPayMd5;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.sxyPay.service.ISxyPayBindBankService;

@Service("sxyPayBindBankService")
public class SxyPayBindBankServiceImpl implements ISxyPayBindBankService {
	
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
	public String createUserBank(UserBankModel model) {
		
		Map<String, String> map = this.getUserBankParam(model);
		String url = SxyPayConstants.Config.SXY_ACCOUNT+"?v_mid="+map.get(SyxParam.v_mid)+"&v_cardno="+map.get(SyxParam.v_cardno)+"&v_idtype="+
		map.get(SyxParam.v_idtype)+"&v_idnumber="+map.get(SyxParam.v_idnumber)+"&v_idname="+map.get(SyxParam.v_idname)+"&v_phone="+map.get(SyxParam.v_phone)+
		"&v_mac="+map.get(SyxParam.v_mac);
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
	private Map<String,String> getUserBankParam(UserBankModel model) {
		UserModel user = this.userDao.findByUserId(model.getUserId());
		Map<String,String> map = new HashMap<String, String>();
		
		try {
			map.put(SyxParam.v_mid, SxyPayConstants.Config.SXY_SHBH);
			map.put(SyxParam.v_cardno, AESfunction.Encrypt(model.getBankAccount(), SxyPayConstants.Config.SXY_AES));
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
			e.printStackTrace();
		}
		return map;
		
		
	}

	/**
     * 
     * Description:保存银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void saveUserBank(UserBankModel userBank) {
		userBank.setId(PKGenarator.getId());
        userBank.setCreateTime(new Date());
        userBank.setUpdateTime(new Date());
        userBank.setState(FbdCoreConstants.USER_BANK_STATE_BIND);
        userBank.setThirdBindType(LetvPayConstants.thirdType.SXY);
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
	public void unbundlingBank(UserBankModel model) {
		UserBankModel bankModle = this.userBankDao.selectByPrimaryKey(model.getId());
		bankModle.setState(FbdCoreConstants.USER_BANK_STATE_UNBIND);
		this.userBankDao.update(bankModle);
	}

	/**
     * 
     * Description:通过卡号查询银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public UserBankModel getBnakByBankAccount(String bankAccount) {
		return this.userBankDao.getBnakByBankAccount(bankAccount);
	}

	
	/**
     * 
     * Description:查询银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public UserBankModel getBnakByUserAndBankAccount(UserBankModel model){
		
		List<UserBankModel> list = this.userBankDao.getBnakByUserAndBankAccount(model.getUserId(), model.getBankAccount());
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
     * 
     * Description:更新银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateUserBank(UserBankModel model) {
		model.setState(FbdCoreConstants.USER_BANK_STATE_BIND);
		model.setUpdateTime(new Date());
		this.userBankDao.updateBySelective(model);
	}

}
