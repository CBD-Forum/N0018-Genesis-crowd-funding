package com.fbd.core.app.sxyPay.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxDataParam;
import com.fbd.core.exception.ApplicationException;

public class LetvPayUtils {
	
	
	private static final Logger logger = LoggerFactory.getLogger(LetvPayUtils.class);
	
	public static String doWeixinPost(Map<String, String> params, String requestUrl) {
		HttpClient client = new HttpClient();
		String result = "";
		PostMethod postMethod = new PostMethod(requestUrl);
		System.out.println("--------------------------");
		System.out.println("params-----:" + params);
		try {
			setPostMethod(postMethod, params);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.debug("Method failed: " + postMethod.getStatusLine());
			}
			byte[] responseBody = postMethod.getResponseBody();
			logger.info(new String(responseBody, "UTF-8"));

			result = new String(responseBody, "UTF-8");
			System.out.println("返回：" + result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("提交请求时出错");

		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}

	public static void setPostMethod(PostMethod postMethod,
			Map<String, String> paramMap) {
		Iterator<Entry<String, String>> iter = paramMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			postMethod.setParameter(entry.getKey(), entry.getValue());
		}
	}
	
	
	/**
     * 产生签名
     * 
     * @param paramNames
     * @param params
     * @return
     */
    public static String getChkValue(String[] paramNames,
            Map<String, String> params) {
        StringBuffer content = new StringBuffer();
        content.append("<?xml version='1.0' encoding='UTF-8'?>");
        Arrays.sort(paramNames);
        for (String str : paramNames) {
                Object value = params.get(str);
                if(value != null){
                   content.append("<"+str.substring(str.substring(0,2).length(),str.length())+">"+value+"</"+str.substring(str.substring(0,2).length(),str.length())+">");
                 }
        }
        content.append("</xml>");
        return content.toString();
    }
	
	
    public static String getChkValue(Map<String, Object> params) {
        StringBuffer content = new StringBuffer();
        String[] paramNames = {"return_code","return_msg"};
        content.append("<xml>");
        Arrays.sort(paramNames);
        for (String str : paramNames) {
                Object value = params.get(str);
                if(value != null){
                   content.append("<"+str+">"+value+"</"+str+">");
                 }
        }
        content.append("</xml>");
        return content.toString();
    }
    
    public static String getWithDrawDataValue(Map<String, String> params) {
        StringBuffer content = new StringBuffer();
        content.append(params.get(SyxDataParam.FZXXZHS)).append("|").append(params.get(SyxDataParam.FZZJE)).append("|").append(params.get(SyxDataParam.PCH)).append("$");
        content.append(params.get(SyxDataParam.SFZH)).append("|");
        content.append(params.get(SyxDataParam.SFZHM)).append("|");
        content.append(params.get(SyxDataParam.SFKHH)).append("|");
        content.append(params.get(SyxDataParam.SFSF)).append("|");
        content.append(params.get(SyxDataParam.SFCS)).append("|");
        content.append(params.get(SyxDataParam.FKJE)).append("|");
        content.append(params.get(SyxDataParam.KHBS)).append("|");
        content.append(params.get(SyxDataParam.LHH));
        return content.toString();
    }
    
    /**
	 * 将xml转成map
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map xmltoMap(String xml) {
		//FIXME:只有能遍历xml第一层
		Map<String, String> xmlMap = new HashMap<String, String>();
		Document doc = null;
		try {
			xml = removeBom(xml);
			doc = DocumentHelper.parseText(xml);
			Element element = doc.getRootElement();
			Iterator iter = element.elementIterator();
			while (iter.hasNext()) {
				Element childElement = (Element) iter.next();
				xmlMap.put(childElement.getName(),
						childElement.getStringValue());
			}
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
		return xmlMap;
	}
	private static final byte[] UTF_BOM = new byte[]{(byte) 0xEF,(byte) 0xBB,(byte) 0xBF};
	
	/** 移除UTF-8的BOM */  
	public static String removeBom(String xml) {
		try {
			byte[] bs = xml.getBytes("UTF-8");
			if (bs[0] == UTF_BOM[0] && bs[1] == UTF_BOM[1] && bs[2] == UTF_BOM[2]) {
				xml = new String(bs, 3, bs.length - 3, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			//FIXME:throw exception
			e.printStackTrace();
		}
		return xml;
	}
	
	
	public static void main(String[] args) {
        String aa = "v_mid";
        aa = aa.substring(aa.substring(0,2).length(),aa.length());
        System.out.print(aa);
    }
}
