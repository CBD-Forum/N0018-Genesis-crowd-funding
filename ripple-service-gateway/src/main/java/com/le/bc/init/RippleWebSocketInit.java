package com.le.bc.init;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.le.bc.constants.Constants;
import com.ripple.client.services.Service;

/**
 * Document Start 
 * Web启动时启动WebSocket初始化
 * 只启动一个Rippled的连接线程
 * 全局通用
 * Document End 
 * Author: songwenpeng@le.com
 * Time: 2016年8月14日 下午1:26:23
 */
public class RippleWebSocketInit {
	public Logger logger = LogManager.getLogger(RippleWebSocketInit.class);
	public static Service client;
	public static Service rippleClient;

	/**
	 * Document Start 
	 * 启动WebSocket连接
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年8月14日 下午1:27:49
	 */
	@PostConstruct
	public void webSocketInit(){
		RippleWebSocketInit.client = new Service(Constants.GLOBAL_RIPPLE_CHAIN_URL);
		RippleWebSocketInit.rippleClient = new Service(Constants.GLOBAL_RIPPLE_CHAIN_URL);
		logger.info("Loading Ripple WebSocket init ...");
	}
}
