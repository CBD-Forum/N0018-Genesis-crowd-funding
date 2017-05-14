package com.fbd.web.app.huiyuan.service;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.huiyuan.model.CreatePersonalMember;

public interface ICreatePersonalMemberService {
	  public int savePersonalMember(CreatePersonalMember member);
	    public long getCount(CreatePersonalMember member);
	    /**
		 * 开通个人会员
		 * @param member
		 * @return
		 */
		@Transactional(propagation = Propagation.REQUIRED)
		public Map<String,Object> PersonalMemberRequest( CreatePersonalMember member);
		
		 public CreatePersonalMember selectByUserId(String userId);
}
