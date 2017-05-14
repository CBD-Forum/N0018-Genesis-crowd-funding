package com.le.bc.commons;

import org.apache.logging.log4j.Logger;

/**
 * Document Start 
 * 请求响应工具类
 * Document End 
 * Author: songwenpeng@le.com
 * Time: 2016年10月8日 上午10:56:32
 */
public class RequestAndResponseLogUtil {
	/**
	 * Document Start 
	 * 接收系统传入网关报文
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年10月1日 下午2:57:50
	 * @param content
	 */
	public static void gatewayRequestLog(Logger logger, String content, String requestID) {
		logger.info("=====>接入系统传入网关请求报文，本次请求的请求ID为【" + requestID + "】,请求报文数据为" + content);
	}

	/**
	 * Document Start 
	 * 网关响应接入系统报文
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年10月1日 下午2:56:24
	 * @param content
	 */
	public static void gatewayResponseLog(Logger logger, String content, String requestID) {
		logger.info("<=====网关响应接入系统报文，本次响应的请求ID为【" + requestID + "】,响应报文数据为" + content);
	}
	
	
	/**
	 * Document Start 
	 * 请求Ripple日志记录
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年10月8日 上午11:44:17
	 * @param logger
	 * @param content
	 * @param requestID
	 */
	public static void rippleRequestLog(Logger logger, String content, String requestID) {
		logger.info("=====>请求Ripple网络，本次请求的请求ID为【" + requestID + "】,请求报文数据为" + content);
	}

	/**
	 * Document Start 
	 * 从Ripple获取异步响应的日志记录
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年10月1日 下午2:56:24
	 * @param content
	 */
	public static void rippleAsynResponseLog(Logger logger, String content, String requestID) {
		logger.info("<=====从区块链接收到的异步回告报文，本次响应的请求ID为【" + requestID + "】,响应报文数据为<=====" + content);
	}
}
