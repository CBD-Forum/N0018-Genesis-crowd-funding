package com.le.bc.business.service.test;

import com.le.bc.init.RippleWebSocketInit;
import com.le.bc.service.AccountService;

public class AccountInfoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RippleWebSocketInit websocket = new RippleWebSocketInit();
		websocket.webSocketInit();
		AccountService service = new AccountService();
		String address = "rLbppnUciWQ2AfLkcdTfBwb1yh97Kc9cfx";
		String requestID = "12";
		service.accountInfo(RippleWebSocketInit.client.getClient(), address,"www.baidu.com",requestID);
	}

}
