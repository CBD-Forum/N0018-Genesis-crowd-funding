package com.le.bc.business.service.test;

import com.le.bc.init.RippleWebSocketInit;
import com.le.bc.service.TradeService;

public class TransferTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RippleWebSocketInit websocket = new RippleWebSocketInit();
		websocket.webSocketInit();
		TradeService service = new TradeService();
		String address = "rraBDW7zYFUUcJoVyC6Dk9jZYQxf6V1Mkj";
		String key = "spiw83CFYhmGLmNsg7Abxg1XjnnpA";
		String productCode = "1000000001";
		String targetAddress = "rwH2aB4Mhy3YXiN5DtNjd7LW4uHsTGdnnd";
		String value = "11";
		String transferNO = "12345678";
		String notifyURL = "www.baidu.com";

		String requestID = "12";
		service.transfer(RippleWebSocketInit.client.getClient(), address, key, targetAddress, value, productCode, transferNO, notifyURL,requestID);
	}

}
