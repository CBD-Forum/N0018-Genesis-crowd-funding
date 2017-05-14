package com.fbd.web.app.huiyuan.service;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.huiyuan.model.CreateEnterpriseMember;

public interface ICreateEnterpriseMemberService {
	/**
	 * 开企业户接口
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(CreateEnterpriseMember member);

	/**
	 * 判断login_name,uid是否被占用
	 * 
	 * @param member
	 * @return
	 */
	public long selectIsExists(CreateEnterpriseMember member);

	public long selectUpdateIsExists(CreateEnterpriseMember member);

	public long modify(CreateEnterpriseMember member);

	public CreateEnterpriseMember selectByUserId(String userId);

	/**
	 * 开通企业用户
	 * 
	 * @param member
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> EnterpriseMemberRequest(
			CreateEnterpriseMember member);
}
