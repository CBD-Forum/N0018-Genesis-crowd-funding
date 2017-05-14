package com.le.bc.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.le.bc.commons.RequestAndResponseLogUtil;
import com.le.bc.commons.ReturnResultUtil;
import com.le.bc.commons.StringUtil;
import com.le.bc.component.ProductComponent;
import com.le.bc.constants.Constants;
import com.le.bc.entity.Product;
import com.ripple.client.Client;
import com.ripple.client.enums.Command;
import com.ripple.client.requests.Request;
import com.ripple.client.responses.Response;

/**
 * Document Start 
 * 交易服务
 * Document End 
 * Author: songwenpeng@le.com
 * Time: 2016年9月16日 下午12:11:46
 */
@Component("tradeService")
public class TradeService {
	public Logger logger = LogManager.getLogger(TradeService.class);

	/**
	 * Document Start 
	 * 转账服务
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 下午12:15:07
	 * @param client
	 * @param sourceAddress
	 * @param sourceKey
	 * @param targetAddress
	 * @param productCode
	 * @param notifyURL
	 */
	public void transfer(Client client, String sourceAddress, String sourceKey, String targetAddress, String value, String productCode, String transferNO, String notifyURL, String requestID) {
		Request request = client.newRequest(Command.submit);
		//		{
		//		  "id": 2,
		//		  "command": "submit",
		//		  "tx_json" : {
		//		      "TransactionType" : "Payment",
		//		      "Account" : "rf1BiGeXwwQoi8Z2ueFYTEXSwuJYfV2Jpn",
		//		      "Destination" : "ra5nK24KXen9AHvsdFTKHSANinZseWnPcX",
		//		      "Amount" : {
		//		         "currency" : "USD",
		//		         "value" : "1",
		//		         "issuer" : "rf1BiGeXwwQoi8Z2ueFYTEXSwuJYfV2Jpn"
		//		      }
		//		   },
		//		   "secret" : "s████████████████████████████",
		//		   "offline": false,
		//		   "fee_mult_max": 1000
		//		}
		request.json("secret", sourceKey);
		request.json("fee_mult_max", 1000);

		JSONObject txJson = new JSONObject();
		//构造PAYMENT
		JSONObject amount = new JSONObject();
		Product product = ProductComponent.getProductInfoByProductCode(productCode);
		if (product == null) {
			RequestAndResponseLogUtil.rippleRequestLog(logger, "产品码错误，产品码未找到对应产品", requestID);
			return;
		}
		amount.put("currency", product.getCurrency());
		amount.put("value", value);
		amount.put("issuer", product.getIssuer());

		txJson.put("TransactionType", "Payment");
		txJson.put("Account", sourceAddress);
		txJson.put("Destination", targetAddress);
		txJson.put("Amount", amount);

		request.json("tx_json", txJson);
		
		
//		Payment txn = (Payment) STObject.fromJSON(txJson.toJSONString());
//		String hashID = txn.sign(sourceKey).hash.toString();
//		System.out.println("HashID为========"+hashID);
		
		RequestAndResponseLogUtil.rippleRequestLog(logger, request.json().toString(), requestID);
		//调用成功 回调
		request.once(Request.OnSuccess.class, new Request.OnSuccess() {
			@Override
			public void called(Response response) {
				ReturnResultUtil.transferCallBack(response, requestID, notifyURL, transferNO);
			}
		});
		request.request();
	}

	/**
	 * Document Start 
	 * 从配置账户向指定账户转入XRP
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月29日 下午12:40:02
	 * @param client
	 * @param address
	 * @param admount
	 * @param productCode
	 * @param notifyURL
	 */
	public void transferXRP(Client client, String address, String amount, String productCode, String notifyURL, String requestID) {
		Request request = client.newRequest(Command.submit);
		//		{
		//		  "id": 2,
		//		  "command": "submit",
		//		  "tx_json" : {
		//		      "TransactionType" : "Payment",
		//		      "Account" : "rf1BiGeXwwQoi8Z2ueFYTEXSwuJYfV2Jpn",
		//		      "Destination" : "ra5nK24KXen9AHvsdFTKHSANinZseWnPcX",
		//		      "Amount" :"20000000"
		//		   },
		//		   "secret" : "s████████████████████████████",
		//		   "offline": false,
		//		   "fee_mult_max": 1000
		//		}

		request.json("secret", Constants.XRP_Key);
		request.json("fee_mult_max", 1000);

		JSONObject txJson = new JSONObject();
		txJson.put("TransactionType", "Payment");
		txJson.put("Account", Constants.XRP_ADDRESS);
		txJson.put("Destination", address);
		if (StringUtil.isEmpty(amount)) {
			txJson.put("Amount", "30000000");
		} else {
			txJson.put("Amount", amount);
		}
		request.json("tx_json", txJson);
		RequestAndResponseLogUtil.rippleRequestLog(logger, request.json().toString(), requestID);
		//调用成功 回调
		request.once(Request.OnSuccess.class, new Request.OnSuccess() {
			@Override
			public void called(Response response) {
				ReturnResultUtil.transactionXRPCallBack(response, requestID, notifyURL);
			}
		});
		request.request();
	}

}
