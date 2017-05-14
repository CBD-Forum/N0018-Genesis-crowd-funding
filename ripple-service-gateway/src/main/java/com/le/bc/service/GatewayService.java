package com.le.bc.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ripple.client.Client;
import com.ripple.client.enums.Command;
import com.ripple.client.requests.Request;
import com.ripple.client.responses.Response;

/**
 * Document Start 
 * 账户相关的实现类
 * Document End 
 * Author: songwenpeng@le.com
 * Time: 2016年8月20日 下午8:11:21
 */
@Component("gatewayService")
public class GatewayService {
	public Logger logger = LogManager.getLogger(GatewayService.class);
	
	
	/**
	 * Document Start 
	 * 创建网关
	 * 设置网关DefaultRipple
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月28日 下午8:08:54
	 * @param client
	 * @param gatewayAddress
	 */
	public void createGateway(Client client, String gatewayAddress,String key) {
		// TODO Auto-generated method stub
		Request request = client.newRequest(Command.submit);
//		{
//	    "method": "submit",
//	    "params": [
//	        {
//	            "secret": "sn3nxiW7v8KXzPzAqzyHXbSSKNuN9",
//	            "tx_json": {
//	                "Account": "rf1BiGeXwwQoi8Z2ueFYTEXSwuJYfV2Jpn",
//	                "Fee": "15000",
//	                "Flags": 0,
//	                "SetFlag": 8,
//	                "TransactionType": "AccountSet"
//	            }
//	        }
//	    ]
//	}
		request.json("secret", key);
		request.json("fee_mult_max", 1000);
		
		JSONObject txJson = new JSONObject();
		txJson.put("Account", gatewayAddress);
		txJson.put("Flags", 0);
		txJson.put("SetFlag", 8);
		txJson.put("TransactionType", "AccountSet");

		request.json("tx_json", txJson);

		logger.info(">>transfer>>请求开始,请求数据为" + request.json().toString());
		//调用成功 回调
		request.once(Request.OnSuccess.class, new Request.OnSuccess() {
			@Override
			public void called(Response response) {
				logger.info(">>transfer>>本次请求结束,响应数据为" + response.message.toString());
			}
		});
		request.request();
	}
}
