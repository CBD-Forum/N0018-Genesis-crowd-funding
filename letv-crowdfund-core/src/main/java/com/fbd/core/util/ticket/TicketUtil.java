/**
 * 说明：Demo仅提供加签、验签和简单的接口调用参考
 * 具体的开发请以平台情况和接口文档为准
 * 
 *汇付天下有限公司
 *
 * Copyright (c) 2006-2013 ChinaPnR,Inc.All Rights Reserved.
 */
package com.fbd.core.util.ticket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fbd.core.exception.ApplicationException;

public class TicketUtil {
	
	/**
	 * 接受单点登录的地址
	 */
	public static final String TOLOGIN_URL = "/user/tologin.html";
	/**
	 * 接受注册信息同步的地址
	 */
	public static final String TOREGISTER_URL = "/user/toregister.html";

    private static final Logger logger = LoggerFactory.getLogger(TicketUtil.class);
    
    /**
     * 单点登录
     * @param params
     * @param requestUrl
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> doPost(Map<String, String> params,String requestUrl) {
        Map<String, String> respMap = new HashMap<String, String>();
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(requestUrl);
        try {
            setPostMethod(postMethod,params);
            int statusCode = client.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                logger.debug("Method failed: " + postMethod.getStatusLine());
            }
            byte[] responseBody = postMethod.getResponseBody();
            logger.info(new String(responseBody, "UTF-8"));
            
            String result = new String(responseBody, "UTF-8");
            logger.info("单点返回结果："+result);
            
            JSONObject json = new JSONObject(result);
            respMap.put("msg", json.getString("msg"));
            respMap.put("id", json.getString("id"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("提交单点请求时出错");

        } finally {
             postMethod.releaseConnection();
        }
        return respMap;
    }
    
    /**
     * 注册同步信息
     * @param params
     * @param requestUrl
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> doPostReg(Map<String, String> params,String requestUrl) {
    	Map<String, String> respMap = new HashMap<String, String>();
    	HttpClient client = new HttpClient();
    	PostMethod postMethod = new PostMethod(requestUrl);
    	try {
    		setPostMethod(postMethod,params);
    		int statusCode = client.executeMethod(postMethod);
    		if (statusCode != HttpStatus.SC_OK) {
    			logger.debug("Method failed: " + postMethod.getStatusLine());
    		}
    		byte[] responseBody = postMethod.getResponseBody();
    		logger.info(new String(responseBody, "UTF-8"));
    		
    		String result = new String(responseBody, "UTF-8");
    		logger.info("注册同步返回结果："+result);
    		
    		JSONObject json = new JSONObject(result);
    		respMap.put("success", json.getString("success"));
    		respMap.put("msg", json.getString("msg"));
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new ApplicationException("提交注册同步请求时出错");
    		
    	} finally {
    		postMethod.releaseConnection();
    	}
    	return respMap;
    }
    
    public static void setPostMethod(PostMethod postMethod,Map<String,String> paramMap){
        Iterator<Entry<String, String>> iter = paramMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            postMethod.setParameter(entry.getKey(), entry.getValue());
        }
    }
}
