package com.fbd.web.app.recharge.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRechargeAgreementService {
	
	
	public String signRechargeAgreement(String orderId,HttpServletRequest request,HttpServletResponse response);

}
