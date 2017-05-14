package com.le.bc.service;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.le.bc.commons.RequestAndResponseLogUtil;
import com.le.bc.commons.ReturnResultUtil;
import com.le.bc.component.ProductComponent;
import com.le.bc.entity.Product;
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
@Component("accountService")
public class AccountService {
	public Logger logger = LogManager.getLogger(AccountService.class);
	@Resource(name = "tradeService")
	TradeService tradeService;

	/**
	 * Document Start 
	 * 生成一个Ripple Address 此地址不会改变RCL
	 * 仅仅生成一个地址
	 * 是一个Admin-command 需要运行自己的Local Rippled
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月4日 下午3:16:31
	 * @see com.le.bc.service.facade.IAccountServcie#createAccount(com.ripple.client.Client)
	 */
	public void walletPropose(Client client, String productCode, String uid, String notifyURL, String requestID) {
		// TODO Auto-generated method stub
		//生成一个新的Ripple钱包 
		Request request = client.newRequest(Command.wallet_propose);
		RequestAndResponseLogUtil.rippleRequestLog(logger, request.json().toString(), requestID);
		//调用成功 回调
		request.once(Request.OnSuccess.class, new Request.OnSuccess() {
			@Override
			public void called(Response response) {
				ReturnResultUtil.walletProposeCallback(response, requestID, uid, notifyURL);
			}
		});
		request.request();

	}

	/**
	 * Document Start 
	 * 账户信息查询
	 * XRP账户余额查询
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 上午2:12:43
	 * @param client
	 * @param accountAddress
	 * @param notifyURL
	 */
	public void accountInfo(Client client, String accountAddress, String notifyURL, String requestID) {
		// TODO Auto-generated method stub
		Request request = client.newRequest(Command.account_info);
		request.json("account", accountAddress);
		RequestAndResponseLogUtil.rippleRequestLog(logger, request.json().toString(), requestID);
		//调用成功 回调
		request.once(Request.OnSuccess.class, new Request.OnSuccess() {
			@Override
			public void called(Response response) {
				ReturnResultUtil.accountInfoCallback(response, requestID, notifyURL);
			}
		});
		request.request();
	}

	/**
	 * Document Start 
	 * 使用account_object命令
	 * 产品账户余额查询
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 上午2:06:02
	 * @param client
	 * @param accountAddress
	 * @param notifyURL
	 * @param productCode
	 */
	public void accountObject(Client client, String accountAddress, String notifyURL, String productCode, String requestID) {
		// TODO Auto-generated method stub
		Request request = client.newRequest(Command.account_objects);
		request.json("account", accountAddress);
		RequestAndResponseLogUtil.rippleRequestLog(logger, request.json().toString(), requestID);
		//调用成功 回调
		request.once(Request.OnSuccess.class, new Request.OnSuccess() {
			@Override
			public void called(Response response) {
				ReturnResultUtil.accountObjectCallback(response, requestID, productCode, notifyURL);
			}
		});
		request.request();
	}

	/**
	 * Document Start 
	 * 设置信任线
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月19日 下午10:05:52
	 * @param client
	 * @param accountAddress
	 * @param notifyURL
	 * @param productCode
	 */
	public void trustSet(Client client, String address, String key, String productCode, String notifyURL, String requestID) {
		Request request = client.newRequest(Command.submit);
		//		{
		//		  "id": 2,
		//		  "command": "submit",
		//		  "tx_json" : {
		//		       "TransactionType": "TrustSet",
		//					    "Account": "ra5nK24KXen9AHvsdFTKHSANinZseWnPcX",
		//					    "Fee": "12",
		//					    "Flags": 262144,
		//					    "LastLedgerSequence": 8007750,
		//					    "LimitAmount": {
		//					      "currency": "USD",
		//					      "issuer": "rsP3mgGb2tcYUrxiLFiHJiQXhsziegtwBc",
		//					      "value": "100"
		//					    },
		//					    "Sequence": 12
		//		   },
		//		   "secret" : "s████████████████████████████",
		//		   "offline": false,
		//		   "fee_mult_max": 1000
		//		}
		request.json("secret", key);
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
		amount.put("value", "1000000000");
		amount.put("issuer", product.getIssuer());

		txJson.put("TransactionType", "TrustSet");
		txJson.put("Account", address);
		txJson.put("LimitAmount", amount);
		//txJson.put("Flags", 262144);

		request.json("tx_json", txJson);
		

		RequestAndResponseLogUtil.rippleRequestLog(logger, request.json().toString(), requestID);
		//调用成功 回调
		request.once(Request.OnSuccess.class, new Request.OnSuccess() {
			@Override
			public void called(Response response) {
				ReturnResultUtil.trustSetCallback(response, requestID, notifyURL);
			}
		});
		request.request();
	}

}
