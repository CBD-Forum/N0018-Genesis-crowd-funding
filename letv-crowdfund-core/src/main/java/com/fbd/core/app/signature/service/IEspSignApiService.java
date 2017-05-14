package com.fbd.core.app.signature.service;

import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fbd.core.app.user.model.UserSignatureOrgModel;
import com.fbd.core.app.user.model.UserSignaturePersonModel;

public interface IEspSignApiService {
	
	
	/**
	 * 发送短息验证码
	 * @param userId 当前用户ID
	 * @param mobile 手机号码
	 * @param usage  验证码用法(
	 * user/create:创建用户
	 * user/update：更新用户
	 * user/addOperator：增加用户授权
	 * user/removeOperator：删除用户授权
	 * authCert/create：申请授权证书
	 * contract/sign：签署单个合同
	 * contract/batchSign：签署批量合同
	 * )
	 * @param request
	 */
	public String sendVerifyCode(String userId,String mobile,String usage,HttpServletRequest request);
	
	
	
	/**
	 * 创建用户
	 * @param userId  当前登录用户ID
	 * @param type  用户类型 (0:个人用户  1：机构用户)
	 * @param verifyCode  验证码
	 * @param userPerson  
	 * @param userOrg
	 * @param request
	 */
	public void saveCreateSignUser(String userId,String type,String authCode,UserSignaturePersonModel userPerson,UserSignatureOrgModel userOrg,Certificate cert,HttpServletRequest request);
	
 
	/**
	 * 查询用户签章
	 * @param userId
	 */
	public List<Map<String,Object>> querySignList(String userId);
	
	/**
	 * 创建合同
	 * Description: 
	 *
	 * @param 
	 * @return void
	 * @throws 
	 * Create Date: 2016-8-17 上午11:44:09
	 */
	public String createContract(String LoanNo,String investNo,String userId,HttpServletRequest request,HttpServletResponse response);
	 
	/**
	 * 合同签署
	 * Description: 
	 * @param 
	 * @return void
	 * @throws 
	 * @Author haolingfeng
	 * Create Date: 2016-8-17 下午12:49:05
	 */
    public void signContract(String loanNo,String investNo,String userId,String contractId,String authCode,HttpServletRequest request);
	
    /**
     * 批量签署合同
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-17 下午6:52:25
     */
    public void batchSignContract(String loanNo,String userId,String authCode, HttpServletRequest request);
    
    /**
     * 获取合同
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-17 下午8:15:19
     */
    public String getContract(String investNo,HttpServletRequest request);
    
    /**
     * 查询用户的签章信息
     * Description: 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-1 上午11:02:26
     */
    public Map<String,Object> queryUserSignInfo(String userId);
}
