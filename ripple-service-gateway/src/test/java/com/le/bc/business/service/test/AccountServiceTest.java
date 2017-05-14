package com.le.bc.business.service.test;

import com.le.bc.init.RippleWebSocketInit;
import com.le.bc.service.AccountService;

public class AccountServiceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RippleWebSocketInit websocket = new RippleWebSocketInit();
		websocket.webSocketInit();
		AccountService service = new AccountService();
		String accountAddress = "rHa3trZg2MzGTkGA4PLmsTPo9azHUGbhUV";
		String requestID = "12";
		service.accountInfo(RippleWebSocketInit.client.getClient(), accountAddress,"http://www.baidu.com",requestID);
	}

}
