package com.le.bc.business.service.test;

import com.le.bc.init.RippleWebSocketInit;
import com.le.bc.service.AccountService;

public class TrustSetTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RippleWebSocketInit websocket = new RippleWebSocketInit();
		websocket.webSocketInit();
		AccountService service = new AccountService();
		String address = "rnwDpX4WBWnhYztJDu2599GqAxPpg5htjV";
		String key = "shL19fFkDBeTSNa4ccygigrWM1L9z";
		String productCode = "1000000002";
		String requestID = "12";
		service.trustSet(RippleWebSocketInit.client.getClient(), address,key,productCode,"www.baidu.com",requestID);
	}

}
