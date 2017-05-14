package com.fbd.core.app.letvPay.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.helper.PKGenarator;

public class Core {
    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    /**
     * 转码
     * @param sArray
     * @return
     */
      public static Map<String, String> encode(Map<String, String> sArray) {

          Map<String, String> result = new HashMap<String, String>();

          if (sArray == null || sArray.size() <= 0) {
              return result;
          }
          String charset = sArray.get("_input_charset");
          for (String key : sArray.keySet()) {
              String value = sArray.get(key);
              if (value != null && !value.equals("") ) {
              	try {
                      value = URLEncoder.encode(value, charset);
                  } catch (UnsupportedEncodingException e) {
                      e.printStackTrace();
                  }
              }

              result.put(key, value);
          }

          return result;
      }
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode 是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }



    /**
     * 生成要请求给钱包的参数数组
     *
     * @param sParaTemp         请求前的参数数组
     * @return                  要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,String signType, String key,String inputCharset) throws Exception {
    	// 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        if(StringUtils.isBlank(signType))return sPara;
        // 生成签名结果
        String mysign = "";
        if ("MD5".equalsIgnoreCase(signType)) {
            mysign = buildRequestByMD5(sPara, LetvPayConstants.Config.MD5KEY, inputCharset);
        } else if("RSA".equalsIgnoreCase(signType)){
            mysign = buildRequestByRSA(sPara, key, inputCharset);
        }
        // 签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", signType);

        return encode(sPara);
        //return sPara;
    }
    /**
     * 生成MD5签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara, String key,
                                           String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }

    /**
     * 生成RSA签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(Map<String, String> sPara, String privateKey,
                                           String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = RSA.sign(prestr, privateKey, inputCharset);
        return mysign;
    }
    
    
    public static String buildTradeListParameter(String[] params){
    	String tradeInfo = "";
    	for(int idx = 0; idx<params.length; idx++){
    		String para = params[idx];
			if (idx == 5 && StringUtils.isNotBlank(para)) {
				// 分润集处理
				para = parseRoyalty(para);
			}
			para = para.length() + ":" + para;
			tradeInfo += para;
			tradeInfo += "~";
    	}
    	tradeInfo = tradeInfo.substring(0, tradeInfo.length()-1);
		return tradeInfo;
    	
    }
    
    /**
     * 担保交易网关接口交易数据生成
     * @param params
     * @return
     */
    public static String buildETradeListParameter(String[] params){
    	String tradeInfo = "";
    	for(int idx = 0; idx<params.length; idx++){
    		String para = params[idx];
			para = para.length() + ":" + para;
			tradeInfo += para;
			tradeInfo += "~";
    	}
    	tradeInfo = tradeInfo.substring(0, tradeInfo.length()-1);
		return tradeInfo;
    	
    }
    
    private static String parseRoyalty(String origRoy) {
		String result = "";
		String[] strs=origRoy.split("|"); //分割成不同的分润记录
		for (int i=0; i<strs.length ;i++ ) {
			String[] seg = strs[i].split("^"); //分割成UID，账户类型，金额
			for (int j=0; j<seg.length; j++ ) {
				seg[j] = seg[j].length() + ':' + seg[j];
				result +=seg[j];
				if (j < seg.length -1) {
					result +="^";
				}
			}
			if (i < strs.length -1) {
				result +="|";
			}
		}
		return result;
	}
    
    
    public static Map<String,String> publicParam(String service){
        Map<String, String> params = new HashMap<String, String>();
        params.put(Param.service, service);
        params.put(Param.version,LetvPayConstants.VERSION);
        params.put(Param.partner_id,LetvPayConstants.Config.PARENT_ID);
        params.put(Param._input_charset,LetvPayConstants.INPUTCHARSET); 
        params.put(Param.sign_type,LetvPayConstants.SIGNTYPE);
        return params;
    }

    public static NameValuePair[] getValuePair(Map<String,String>reqMap){
     // 创建名/值组列表  
        NameValuePair[] nvps = new NameValuePair[reqMap.size()];  
        int i = 0;
        for(Entry<String, String> e : reqMap.entrySet()){
            nvps[i] = new NameValuePair(e.getKey(), e.getValue());
            i++;
        }
        return nvps;
    }
    
    
    public static String sendRequest(Map<String,String> reqMap,String url){
        String result = "";
        try {
            HttpClient httpclient = new HttpClient();
            PostMethod httppost = new PostMethod(url);
            // 创建名/值组列表  
            NameValuePair[] nvps = getValuePair(reqMap);
            httppost.setRequestBody(nvps);
            int statusCode = httpclient.executeMethod(httppost);
            result = httppost.getResponseBodyAsString();
            System.out.println(result);
            System.out.println(statusCode);
        } catch (Exception e) {
            
        }
        return result;
    }
    
    public static String sendRequestBindBank(Map<String,String> reqMap,String url){
        String result = "";
        try {
            HttpClient httpclient = new HttpClient();
            PostMethod httppost = new PostMethod(url);
            // 创建名/值组列表  
            NameValuePair[] nvps = new NameValuePair[reqMap.size()];  
            int i = 0;
            for(Entry<String, String> e : reqMap.entrySet()){
                nvps[i] = new NameValuePair(e.getKey(), e.getValue());
                i++;
            }
            httppost.setRequestBody(nvps);
            int statusCode = httpclient.executeMethod(httppost);
            result = httppost.getResponseBodyAsString();
            System.out.println(result);
            System.out.println(statusCode);
        } catch (Exception e) {
            
        }
        return result;
    }
    
    
}
