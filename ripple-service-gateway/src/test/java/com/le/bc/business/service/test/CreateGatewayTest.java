package com.le.bc.business.service.test;

import com.le.bc.init.RippleWebSocketInit;
import com.le.bc.service.GatewayService;

public class CreateGatewayTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RippleWebSocketInit websocket = new RippleWebSocketInit();
		websocket.webSocketInit();
		GatewayService service = new GatewayService();
		String address = "rwb3irZfXT6cNDUUjsSNhoVSZhjoj22a7H";
		String key = "ssfEPoThKgXSnBrwP3Lnfrz6QELKL";
		service.createGateway(RippleWebSocketInit.rippleClient.getClient(), address,key);
	}

}
