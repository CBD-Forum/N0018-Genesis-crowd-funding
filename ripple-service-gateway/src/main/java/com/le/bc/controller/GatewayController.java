package com.le.bc.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.le.bc.commons.GatewayConstants;
import com.le.bc.commons.RequestAndResponseLogUtil;
import com.le.bc.commons.ReturnResultUtil;
import com.le.bc.commons.StringUtil;
import com.le.bc.http.HttpPostUtil;
import com.le.bc.init.RippleWebSocketInit;
import com.le.bc.service.AccountService;
import com.le.bc.service.TradeService;
import com.le.bc.service.TransactionService;

/**
 * Document Start 
 * GatewayController
 * Document End 
 * uthor: songwenpeng@le.com
 * Time: 2016年8月14日 上午11:11:55
 */
@Controller
public class GatewayController {
	public Logger logger = LogManager.getLogger(GatewayController.class);
	@Resource(name = "accountService")
	AccountService accountService;
	@Resource(name = "transactionService")
	TransactionService transactionService;
	@Resource(name = "tradeService")
	TradeService tradeService;

	/**
	 * Document Start 
	 * 用于测试服务是否可用
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 上午12:42:26
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("test")
	@ResponseBody
	public String test(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		Map<String, String> params = HttpPostUtil.getParamaterMap(request);
		String requestID = params.get(GatewayConstants.REQUEST_ID);
		RequestAndResponseLogUtil.gatewayRequestLog(logger, HttpPostUtil.createLinkString(params), requestID);

		object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_SUCCESS_CODE);
		object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_SUCCESS_MESSAGE);

		RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
		return object.toJSONString();
	}

	/**
	 * Document Start 
	 * V1版本总入口
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年10月1日 下午3:51:22
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("V1")
	@ResponseBody
	public String v1(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		Map<String, String> params = HttpPostUtil.getParamaterMap(request);
		String requestID = params.get(GatewayConstants.REQUEST_ID);
		RequestAndResponseLogUtil.gatewayRequestLog(logger, HttpPostUtil.createLinkString(params), requestID);

		String serviceID = params.get(GatewayConstants.SERVICE_ID);
		//非空判断
		if (StringUtil.isEmpty(serviceID, requestID)) {
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_PARAMETER_ERROR_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_PARAMETER_ERROR_MESSAGE);
			RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
			return object.toJSONString();
		}

		switch (serviceID) {
		case GatewayConstants.CREATE_ACCOUNT:
			return this.createAccount(request, response);
		case GatewayConstants.ACTIVE_ACCOUNT:
			return this.activeAccount(request, response);
		case GatewayConstants.SET_TRUST:
			return this.setTrust(request, response);
		case GatewayConstants.TRANSFER:
			return this.transfer(request, response);
		case GatewayConstants.ACCOUNT_XRP_QUERY:
			return this.accountXRPQuery(request, response);
		case GatewayConstants.ACCOUNT_QUERY:
			return this.accountQuery(request, response);
		case GatewayConstants.TRANSACTION_QUERY:
			return this.transactionQuery(request, response);
		default:
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_PARAMETER_ERROR_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_PARAMETER_ERROR_MESSAGE);
			RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
			return object.toJSONString();
		}
	}

	/**
	 * Document Start 
	 * 开户
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年8月14日 上午11:13:10
	 * @param model
	 * @return
	 */
	@RequestMapping("createAccount")
	@ResponseBody
	public String createAccount(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		Map<String, String> params = HttpPostUtil.getParamaterMap(request);
		String requestID = params.get(GatewayConstants.REQUEST_ID);
		RequestAndResponseLogUtil.gatewayRequestLog(logger, HttpPostUtil.createLinkString(params), requestID);

		//获取参数
		String serviceID = params.get(GatewayConstants.SERVICE_ID);
		String productCode = params.get(GatewayConstants.PRODUCT_CODE);
		String notifyURL = params.get(GatewayConstants.NOTIFYURL);
		String uid = params.get(GatewayConstants.UID);

		//TODO 只判断是否为空 需要判断格式及长度
		if (StringUtil.isEmpty(serviceID, productCode, notifyURL, uid, requestID)) {
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_PARAMETER_ERROR_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_PARAMETER_ERROR_MESSAGE);
			RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
			return object.toJSONString();
		}

		this.accountService.walletPropose(RippleWebSocketInit.client.getClient(), productCode, uid, notifyURL, requestID);

		object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_SUCCESS_CODE);
		object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_SUCCESS_MESSAGE);
		RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
		return object.toJSONString();
	}

	/**
	 * Document Start 
	 * 激活账号
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年8月14日 上午11:23:39
	 * @param model
	 * @return
	 */
	@RequestMapping("activeAccount")
	@ResponseBody
	public String activeAccount(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		Map<String, String> params = HttpPostUtil.getParamaterMap(request);
		String requestID = params.get(GatewayConstants.REQUEST_ID);
		RequestAndResponseLogUtil.gatewayRequestLog(logger, HttpPostUtil.createLinkString(params), requestID);
		//获取参数
		String serviceID = params.get(GatewayConstants.SERVICE_ID);
		String address = params.get(GatewayConstants.ADDRESS);
		String amount = params.get(GatewayConstants.AMOUNT);
		String productCode = params.get(GatewayConstants.PRODUCT_CODE);
		String notifyURL = params.get(GatewayConstants.NOTIFYURL);

		//TODO 只判断是否为空 需要判断格式及长度
		if (StringUtil.isEmpty(serviceID, address, productCode, notifyURL, requestID)) {
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_PARAMETER_ERROR_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_PARAMETER_ERROR_MESSAGE);
			RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
			return object.toJSONString();
		}

		this.tradeService.transferXRP(RippleWebSocketInit.rippleClient.getClient(), address, amount, productCode, notifyURL, requestID);

		object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_SUCCESS_CODE);
		object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_SUCCESS_MESSAGE);
		RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
		return object.toJSONString();
	}

	/**
	 * Document Start 
	 * 设置信任网关
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月29日 下午5:12:31
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("setTrust")
	@ResponseBody
	public String setTrust(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		Map<String, String> params = HttpPostUtil.getParamaterMap(request);
		String requestID = params.get(GatewayConstants.REQUEST_ID);
		RequestAndResponseLogUtil.gatewayRequestLog(logger, HttpPostUtil.createLinkString(params), requestID);
		//获取参数
		String serviceID = params.get(GatewayConstants.SERVICE_ID);
		String sourceAddress = params.get(GatewayConstants.SOURCE_ADDRESS);
		String sourceKey = params.get(GatewayConstants.SOUCRCE_KEY);
		String productCode = params.get(GatewayConstants.PRODUCT_CODE);
		String notifyURL = params.get(GatewayConstants.NOTIFYURL);

		//TODO 只判断是否为空 需要判断格式及长度
		if (StringUtil.isEmpty(serviceID, sourceAddress, sourceKey, productCode, notifyURL, requestID)) {
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_PARAMETER_ERROR_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_PARAMETER_ERROR_MESSAGE);
			RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
			return object.toJSONString();
		}

		//业务处理
		this.accountService.trustSet(RippleWebSocketInit.rippleClient.getClient(), sourceAddress, sourceKey, productCode, notifyURL, requestID);

		object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_SUCCESS_CODE);
		object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_SUCCESS_MESSAGE);
		RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
		return object.toJSONString();
	}

	/**
	 * Document Start 
	 * 转账
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年8月14日 下午12:09:17
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("transfer")
	@ResponseBody
	public String transfer(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		Map<String, String> params = HttpPostUtil.getParamaterMap(request);
		String requestID = params.get(GatewayConstants.REQUEST_ID);
		RequestAndResponseLogUtil.gatewayRequestLog(logger, HttpPostUtil.createLinkString(params), requestID);
		//获取参数
		String serviceID = params.get(GatewayConstants.SERVICE_ID);
		String sourceAddress = params.get(GatewayConstants.SOURCE_ADDRESS);
		String sourceKey = params.get(GatewayConstants.SOUCRCE_KEY);
		String targetAddress = params.get(GatewayConstants.TARGET_ADDRESS);
		String amount = params.get(GatewayConstants.AMOUNT);
		String productCode = params.get(GatewayConstants.PRODUCT_CODE);
		String transferNO = params.get(GatewayConstants.TRANSFER_NO);
		String notifyURL = params.get(GatewayConstants.NOTIFYURL);

		//TODO 只判断是否为空 需要判断格式及长度
		if (StringUtil.isEmpty(serviceID, sourceAddress, targetAddress, amount, productCode, transferNO, notifyURL, requestID)) {
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_PARAMETER_ERROR_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_PARAMETER_ERROR_MESSAGE);
			RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
			return object.toJSONString();
		}

		//业务处理
		this.tradeService.transfer(RippleWebSocketInit.rippleClient.getClient(), sourceAddress, sourceKey, targetAddress, amount, productCode, transferNO, notifyURL, requestID);

		object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_SUCCESS_CODE);
		object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_SUCCESS_MESSAGE);
		RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
		return object.toJSONString();
	}

	/**
	 * Document Start 
	 * 产品账户信息查询
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 上午12:06:25
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("accountQuery")
	@ResponseBody
	public String accountQuery(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		Map<String, String> params = HttpPostUtil.getParamaterMap(request);
		String requestID = params.get(GatewayConstants.REQUEST_ID);
		RequestAndResponseLogUtil.gatewayRequestLog(logger, HttpPostUtil.createLinkString(params), requestID);
		//获取参数
		String serviceID = params.get(GatewayConstants.SERVICE_ID);
		String address = params.get(GatewayConstants.ADDRESS);
		String notifyURL = params.get(GatewayConstants.NOTIFYURL);
		String productCode = params.get(GatewayConstants.PRODUCT_CODE);

		//TODO 只判断是否为空 需要判断格式及长度
		if (StringUtil.isEmpty(serviceID, address, notifyURL, productCode, requestID)) {
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_PARAMETER_ERROR_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_PARAMETER_ERROR_MESSAGE);
			RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
			return object.toJSONString();
		}

		this.accountService.accountObject(RippleWebSocketInit.rippleClient.getClient(), address, notifyURL, productCode, requestID);

		object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_SUCCESS_CODE);
		object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_SUCCESS_MESSAGE);
		RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
		return object.toJSONString();
	}

	/**
	 * Document Start 
	 * XRP账户查询
	 * 开户账户查询
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 上午2:18:09
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("accountXRPQuery")
	@ResponseBody
	public String accountXRPQuery(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		Map<String, String> params = HttpPostUtil.getParamaterMap(request);
		String requestID = params.get(GatewayConstants.REQUEST_ID);
		RequestAndResponseLogUtil.gatewayRequestLog(logger, HttpPostUtil.createLinkString(params), requestID);
		//获取参数
		String serviceID = params.get(GatewayConstants.SERVICE_ID);
		String address = params.get(GatewayConstants.ADDRESS);
		String notifyURL = params.get(GatewayConstants.NOTIFYURL);
		//TODO 只判断是否为空 需要判断格式及长度
		if (StringUtil.isEmpty(serviceID, address, notifyURL, requestID)) {
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_PARAMETER_ERROR_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_PARAMETER_ERROR_MESSAGE);
			RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
			return object.toJSONString();
		}

		this.accountService.accountInfo(RippleWebSocketInit.rippleClient.getClient(), address, notifyURL, requestID);

		object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_SUCCESS_CODE);
		object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_SUCCESS_MESSAGE);
		RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
		return object.toJSONString();
	}

	/**
	 * Document Start 
	 * 事务查询
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 上午1:28:30
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("transactionQuery")
	@ResponseBody
	public String transactionQuery(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object = new JSONObject();
		Map<String, String> params = HttpPostUtil.getParamaterMap(request);
		String requestID = params.get(GatewayConstants.REQUEST_ID);
		RequestAndResponseLogUtil.gatewayRequestLog(logger, HttpPostUtil.createLinkString(params), requestID);
		//获取参数
		String serviceID = params.get(GatewayConstants.SERVICE_ID);
		String transactionID = params.get(GatewayConstants.TRANSACTION_ID);
		String notifyURL = params.get(GatewayConstants.NOTIFYURL);

		//TODO 只判断是否为空 需要判断格式及长度
		if (StringUtil.isEmpty(serviceID, transactionID, notifyURL, requestID)) {
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_PARAMETER_ERROR_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_PARAMETER_ERROR_MESSAGE);
			RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
			return object.toJSONString();
		}

		this.transactionService.tx(RippleWebSocketInit.rippleClient.getClient(), transactionID, notifyURL, requestID);

		object.put(GatewayConstants.STATUS, ReturnResultUtil.SERVICE_SUCCESS_CODE);
		object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SERVICE_SUCCESS_MESSAGE);
		RequestAndResponseLogUtil.gatewayResponseLog(logger, object.toJSONString(), requestID);
		return object.toJSONString();
	}

	/**
	 * Document Start 
	 * callback信息
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月6日 下午7:57:49
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("callback")
	@ResponseBody
	public String callback(HttpServletRequest request, HttpServletResponse response) {
		int len = request.getContentLength();
		byte[] buffer = new byte[len];
		ServletInputStream inputStream;
		try {
			inputStream = request.getInputStream();
			inputStream.read(buffer, 0, len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String content = new String(buffer);
		logger.info("CallBack内容为=====>" + content);
		System.out.println("CallBack内容为=====>" + content);
		return content;
	}

}
