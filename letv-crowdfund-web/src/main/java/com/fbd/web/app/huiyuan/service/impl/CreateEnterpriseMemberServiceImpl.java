package com.fbd.web.app.huiyuan.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.huiyuan.dao.ICreateEnterpriseMemberDao;
import com.fbd.core.app.huiyuan.model.CreateEnterpriseMember;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Enterprisemember;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.letvPay.utils.Core;
import com.fbd.core.app.letvPay.utils.JsonUtil;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.huiyuan.service.ICreateEnterpriseMemberService;

@Service("createEnterpriseMemberService")
public class CreateEnterpriseMemberServiceImpl implements ICreateEnterpriseMemberService{
	@Resource
	private ICreateEnterpriseMemberDao createEnterpriseMemberDao;
	/**
	 * 开企业户接口
	 */
	@Transactional(propagation = Propagation.REQUIRED)
    public void insert( CreateEnterpriseMember member){
		createEnterpriseMemberDao.insertEnterpriseMember(member);
	}
	/**
	 * 判断login_name,uid是否被占用
	 * @param member
	 * @return
	 */
	public long selectIsExists(CreateEnterpriseMember member){
		return createEnterpriseMemberDao.selectIsExists(member);
	}
	@Override
	public long selectUpdateIsExists(CreateEnterpriseMember member) {
		// TODO Auto-generated method stub
		return createEnterpriseMemberDao.selectUpdateIsExists(member);
	}
	@Override
	public long modify(CreateEnterpriseMember member) {
		// TODO Auto-generated method stub
		return createEnterpriseMemberDao.modify(member);
	}
	@Override
	public CreateEnterpriseMember selectByUserId(String userId) {
		// TODO Auto-generated method stub
		return createEnterpriseMemberDao.selectByUserId(userId);
	}
	/**
	 * 开通企业用户
	 * @param member
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String,Object> EnterpriseMemberRequest(CreateEnterpriseMember member) {
		 Map<String,Object> resultJsonMap = null;
		 long count =0;
		//判断用户是否存在
		 if(member.getId()!=null && !"".equals(member.getId())){
			 count = createEnterpriseMemberDao.selectUpdateIsExists(member);
		 }else{
			 count =createEnterpriseMemberDao.selectIsExists(member);
		 }
		if(count>0){
			resultJsonMap = new HashMap<String, Object>();
			resultJsonMap.put(Param.is_success, "T");
			return resultJsonMap;
		}		
		
		Map<String,String> sParaTemp = this.getParam(member);
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
	private Map<String,String> getParam(CreateEnterpriseMember member) {
        Map<String, String> params = Core.publicParam(LetvPayConstants.Service.create_enterprise_member);
        params.put(Enterprisemember.login_name, member.getLoginName());
        params.put(Enterprisemember.uid,member.getUid());
        params.put(Enterprisemember.enterprise_name, member.getEnterpriseName());
        params.put(Enterprisemember.member_name,member.getMemberName()); 
        params.put(Enterprisemember.legal_person,member.getLegalPerson());
        params.put(Enterprisemember.legal_person_phone,member.getLegalPersonPhone());
        params.put(Enterprisemember.website,member.getWebsite());
        params.put(Enterprisemember.address,member.getAddress());
        
        params.put(Enterprisemember.license_no, member.getLicenseNo());
        params.put(Enterprisemember.license_address,member.getLicenseAddress());
        params.put(Enterprisemember.license_expire_date, member.getLicenseExpireDate());
        params.put(Enterprisemember.business_scope,member.getBusinessScope()); 
        params.put(Enterprisemember.telephone,member.getTelephone());
        params.put(Enterprisemember.organization_no,member.getOrganizationNo());
        params.put(Enterprisemember.summary,member.getSummary());
        params.put(Enterprisemember.is_active,member.getIsActive());
        
        return params;
	}


}
