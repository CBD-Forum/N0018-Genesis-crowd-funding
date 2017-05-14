package com.fbd.web.app.huiyuan.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.huiyuan.dao.ICreatePersonalMemberDao;
import com.fbd.core.app.huiyuan.model.CreateEnterpriseMember;
import com.fbd.core.app.huiyuan.model.CreatePersonalMember;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.letvPay.common.LetvPayConstants.PersonalMember;
import com.fbd.core.app.letvPay.utils.Core;
import com.fbd.core.app.letvPay.utils.JsonUtil;
import com.fbd.web.app.huiyuan.service.ICreatePersonalMemberService;
@Service
public class CreatePersonalMemberServiceImpl implements ICreatePersonalMemberService {
   @Resource
   private ICreatePersonalMemberDao createPersonalMemberDao;
	@Override
	public int savePersonalMember(CreatePersonalMember member) {
		// TODO Auto-generated method stub
		return createPersonalMemberDao.savePersonalMember(member);
	}

	@Override
	public long getCount(CreatePersonalMember member) {
		// TODO Auto-generated method stub
		return createPersonalMemberDao.getCount(member);
	}
	/**
	 * 开通个人会员
	 * @param member
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String,Object> PersonalMemberRequest( CreatePersonalMember member) {
		 Map<String,Object> resultJsonMap = null;
		 long count =0;
		//判断用户是否存在
		count =createPersonalMemberDao.getCount(member);
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
	private Map<String,String> getParam( CreatePersonalMember member) {
        Map<String, String> params = Core.publicParam(LetvPayConstants.Service.create_personal_member);
        params.put(PersonalMember.mobile, member.getMobile());
        params.put(PersonalMember.uid,member.getUid());
        params.put(PersonalMember.email, member.getEmail());
        params.put(PersonalMember.id_card_no,member.getIdCardNo()); 
        params.put(PersonalMember.real_name,member.getRealName());
        params.put(PersonalMember.is_active,member.getIsActive());
        params.put(PersonalMember.member_name,member.getMemberName());
        params.put(PersonalMember.career,member.getCareer());
        
       
        
        return params;
	}

	@Override
	public CreatePersonalMember selectByUserId(String userId) {
		// TODO Auto-generated method stub
		return createPersonalMemberDao.selectByUserId(userId);
	}
}
