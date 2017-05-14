package com.fbd.web.app.withdraw.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IWithDrawAgreementService {
	
	
	public String signWithDrawAgreement(String orderId,HttpServletRequest request,HttpServletResponse response);

}
