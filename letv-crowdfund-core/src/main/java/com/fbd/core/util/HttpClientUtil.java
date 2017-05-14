/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: HttpClientUtil.java 
 *
 * Created: [2016-8-25 上午10:15:51] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 * @version 1.0
 *
 */

public class HttpClientUtil {
    
    
    
    
    
    /**
    httpClient的get请求方式2
    * @return
    * @throws Exception
    */
   public static String doGet(String url, String charset)
       throws Exception {
     
      /** 使用 GetMethod 来访问一个 URL 对应的网页,实现步骤: 1:生成一个 HttpClinet 对象并设置相应的参数。
      * 2:生成一个 GetMethod 对象并设置响应的参数。 3:用 HttpClinet 生成的对象来执行 GetMethod 生成的Get
      * 方法。 4:处理响应状态码。 5:若响应正常，处理 HTTP 响应内容。 6:释放连接。**/
      
      //1 生成 HttpClinet 对象并设置参数 
     HttpClient httpClient = new HttpClient();
     // 设置 Http 连接超时为5秒
     httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
      //2 生成 GetMethod 对象并设置参数 
     GetMethod getMethod = new GetMethod(url);
     // 设置 get 请求超时为 5 秒
     getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
     // 设置请求重试处理，用的是默认的重试处理：请求三次
     getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,  new DefaultHttpMethodRetryHandler());
     String response = "";
     // 3 执行 HTTP GET 请求 
     try {
           int statusCode = httpClient.executeMethod(getMethod);
            //4 判断访问的状态码 
           if (statusCode != HttpStatus.SC_OK) {
             System.err.println("请求出错: "+ getMethod.getStatusLine());
           }
           //5 处理 HTTP 响应内容 
           // HTTP响应头部信息，这里简单打印
           Header[] headers = getMethod.getResponseHeaders();
           for (Header h : headers)
             System.out.println(h.getName() + "------------ " + h.getValue());
           // 读取 HTTP 响应内容，这里简单打印网页内容
           byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
           response = new String(responseBody, charset);
           System.out.println("----------response:" + response);
           // 读取为 InputStream，在网页内容数据量大时候推荐使用
           // InputStream response = getMethod.getResponseBodyAsStream();
     } catch (HttpException e) {
       // 发生致命的异常，可能是协议不对或者返回的内容有问题
         System.out.println("请检查输入的URL!");
         e.printStackTrace();
     } catch (IOException e) {
       // 发生网络异常
         System.out.println("发生网络异常!");
         e.printStackTrace();
     } finally {
        //6 .释放连接 
        getMethod.releaseConnection();
     }
     return response;
   } 

   /**
    * post请求
    * @param url
    * @param json
    * @return
    */
   public static JSONObject doPost(String url,JSONObject json){
         DefaultHttpClient client = new DefaultHttpClient();
         HttpPost post = new HttpPost(url);
         JSONObject response = null;
         try {
           StringEntity s = new StringEntity(json.toString());
           s.setContentEncoding("UTF-8");
           s.setContentType("application/json");//发送json数据需要设置contentType
           post.setEntity(s);
           HttpResponse res = client.execute(post);
           if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
             HttpEntity entity = res.getEntity();
             String result = EntityUtils.toString(res.getEntity());// 返回json格式：
             response = JSONObject.fromObject(result);
           }
         } catch (Exception e) {
           throw new RuntimeException(e);
         }
         return response;
   } 
    
   
   /**  
    * 执行一个HTTP POST请求，返回请求响应的HTML  
    *  
    * @param url         请求的URL地址  
    * @param params    请求的查询参数,可以为null  
    * @param charset 字符集  
    * @param pretty    是否美化  
    * @return 返回请求响应的HTML  
    */   
   public static String doPost(String url, Map<String, Object> params){ 
       
       return doPost(url, params, "utf-8", false);
   }
    
    /**  
     * 执行一个HTTP POST请求，返回请求响应的HTML  
     *  
     * @param url         请求的URL地址  
     * @param params    请求的查询参数,可以为null  
     * @param charset 字符集  
     * @param pretty    是否美化  
     * @return 返回请求响应的HTML  
     */   
    public static String doPost(String url, Map<String, Object> params, String charset, boolean pretty) {   
        StringBuffer response = new StringBuffer();   
        HttpClient client = new HttpClient();   
        HttpMethod method = new PostMethod(url);   
        //设置Http Post数据   
        if (params != null) {   
            HttpMethodParams p = new HttpMethodParams();   
            for (Map.Entry<String, Object> entry : params.entrySet()) {   
                p.setParameter(entry.getKey(), entry.getValue());   
            }  
            method.setParams(p);   
        }   
        try {   
            client.executeMethod(method);   
            if (method.getStatusCode() == HttpStatus.SC_OK) {   
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));   
                String line;   
                while ((line = reader.readLine()) != null) {   
                    if (pretty)   
                        response.append(line).append(System.getProperty("line.separator"));   
                    else   
                        response.append(line);   
                }   
                reader.close();   
            }   
        } catch (IOException e) {   
            System.out.println("执行HTTP Post请求" + url + "时，发生异常！"+e);
        } finally {   
            method.releaseConnection();   
        }   
        return response.toString();   
    }   

   
   public static void main(String arg[]) throws Exception {
       String url = "http://10.60.118.233:9000/letv-web/crowdfunding/getPageCrowdList.html";
       Map<String,Object>params = new HashMap<String,Object>();
       params.put("rows",6);
       params.put("page",1);
        
       String ret = doPost(url, params, "utf-8", false);
       System.out.println(ret);
     }

    
    
    
    
    
    
    

}
