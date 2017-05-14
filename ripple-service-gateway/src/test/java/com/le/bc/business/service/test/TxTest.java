package com.le.bc.business.service.test;

import com.le.bc.init.RippleWebSocketInit;
import com.le.bc.service.TransactionService;

public class TxTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RippleWebSocketInit websocket = new RippleWebSocketInit();
		websocket.webSocketInit();
		TransactionService service = new TransactionService();
		String hash = "896D51A69F552EC96B28AE32DE91AF9013899112F53A6F7412A0F7DD182B5F53";
		String requestID = "12";
		service.tx(RippleWebSocketInit.client.getClient(), hash,"www.baidu.com",requestID);
	}

}
