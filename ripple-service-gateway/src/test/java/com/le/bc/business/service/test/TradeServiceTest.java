package com.le.bc.business.service.test;


import com.le.bc.init.RippleWebSocketInit;
import com.le.bc.service.TradeService;

public class TradeServiceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RippleWebSocketInit websocket = new RippleWebSocketInit();
		websocket.webSocketInit();
		TradeService service = new TradeService();
		String address = "rajYoMV81KgAcpvy9YpnY9vNRiMVVwR87U";
		String value = "50000000";
		String productCode = "1000000002";

		String requestID = "12";
		service.transferXRP(RippleWebSocketInit.client.getClient(), address, value, productCode, "localhost",requestID);
	}
}
