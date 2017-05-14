package com.le.bc.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.le.bc.commons.RequestAndResponseLogUtil;
import com.le.bc.commons.ReturnResultUtil;
import com.ripple.client.Client;
import com.ripple.client.enums.Command;
import com.ripple.client.requests.Request;
import com.ripple.client.responses.Response;

/**
 * Document Start 
 * 事务相关的实现类
 * Document End 
 * Author: songwenpeng@le.com
 * Time: 2016年8月20日 下午8:11:21
 */
@Component("transactionService")
public class TransactionService {
	public Logger logger = LogManager.getLogger(TransactionService.class);

	/**
	 * Document Start 
	 * 查询事务信息
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 上午1:22:31
	 * @param client
	 * @param transactionHash
	 * @param notifyURL
	 */
	public void tx(Client client, String transactionHash, String notifyURL, String requestID) {
		// TODO Auto-generated method stub
		//		{
		//		    "method": "tx",
		//		    "params": [
		//		        {
		//		            "transaction": "E08D6E9754025BA2534A78707605E0601F03ACE063687A0CA1BDDACFCD1698C7",
		//		            "binary": false
		//		        }
		//		    ]
		//		}
		Request request = client.newRequest(Command.tx);
		request.json("transaction", transactionHash);
		request.json("binary", false);
		RequestAndResponseLogUtil.rippleRequestLog(logger, request.json().toString(), requestID);
		//调用成功 回调
		request.once(Request.OnSuccess.class, new Request.OnSuccess() {
			@Override
			public void called(Response response) {
				ReturnResultUtil.txCallback(response, requestID, transactionHash, notifyURL);
			}
		});
		request.request();
	}

}
