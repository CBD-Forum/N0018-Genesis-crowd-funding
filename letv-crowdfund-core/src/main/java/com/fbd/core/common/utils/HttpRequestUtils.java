package com.fbd.core.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;

public class HttpRequestUtils {
  
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception 
     */
    public static String sendPost(String url, String param,String httpContentCharset) throws Exception {
        PrintWriter out = null;
        OutputStreamWriter out2 = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            try {
                if("https".equalsIgnoreCase(realUrl.getProtocol())){
                    SslUtils.ignoreSsl();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(20000);  //设置相应时间为20S
            conn.setRequestProperty("Accept-Charset",httpContentCharset);
            // 获取URLConnection对象对应的输出流
            //out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            //out.print(param);
            //flush输出流的缓冲
            //out.flush();
            out2 = new OutputStreamWriter(conn.getOutputStream(),httpContentCharset);   
            out2.write(param); //post的关键所在！  
            // remember to clean up  
            out2.flush();  
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),httpContentCharset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch(SocketTimeoutException e1){
        	throw new ApplicationException("请求超时！");
        }catch(ConnectException e2){
        	throw new ApplicationException("请求超时！");
        }catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
            throw new Exception();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(out2!=null){
                    out2.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			try {
				if("https".equalsIgnoreCase(realUrl.getProtocol())){
					SslUtils.ignoreSsl();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
    public static String test(String url,String params){
     
    	return null;
    }
    
    
    public static String callHttpPOST(String httpUrl, Map<String, String> params){
        
        String result = "";
        String requestStr = createLinkString(params);
        try {
            result =  sendPost(httpUrl, requestStr, "utf-8");
        }catch(SocketTimeoutException e1){
            throw new ApplicationException("请求超时！");
        }catch(ConnectException e2){
            throw new ApplicationException("请求超时！");
        }catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
            throw new ApplicationException("请求出现异常！");
        }
        return result;
    }
    
    public static String callHttpPOST(String httpUrl, Map<String, String> params, String encod) {
        if(StringUtil.isEmpty(encod)){
            encod = "utf-8";
        }
        String result = "";
        String requestStr = createLinkString(params);
        try {
            result =  sendPost(httpUrl, requestStr,encod);
        }catch(SocketTimeoutException e1){
            throw new ApplicationException("请求超时！");
        }catch(ConnectException e2){
            throw new ApplicationException("请求超时！");
        }catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
            throw new ApplicationException("请求出现异常！");
        }
        return result;
    }
    
    
   
    
    public static String createLinkString(Map<String, String> params) {// 静态的 返回值为String 类型名字为createLinkString 参数为Map类型的方法
        List<String> keys = new ArrayList<String>(params.keySet());//创建泛型为String类型的List集合用来保存参数Map集合的键。 
        Collections.sort(keys); //按键 升序排列
        String prestr = "";//定义String类型的变量
        for (int i = 0; i < keys.size(); i++) {//根据键遍历Map集合
           String key = keys.get(i);
           String value = params.get(key);
           if (i == keys.size() - 1) {//判断是否取到最后一个键
               prestr = prestr + key + "=" + value;
           } else {
               prestr = prestr + key +  "=" + value + "&";
           }
         }
         return prestr;//返回 prestr
     }
    
    
    
    
    /*** 
     * Compatible with GET and POST 
     *  
     * @param request 
     * @return : <code>byte[]</code> 
     * @throws IOException 
     */  
    public static byte[] getRequestQuery(HttpServletRequest request)  
            throws IOException {  
        String submitMehtod = request.getMethod();  
        String queryString = null;  
  
        if (submitMehtod.equals("GET")) {// GET  
            queryString = request.getQueryString();  
            String charEncoding = request.getCharacterEncoding();// charset  
            if (charEncoding == null) {  
                charEncoding = "UTF-8";  
            }  
            return queryString.getBytes(charEncoding);  
        } else {// POST  
            return getRequestPostBytes(request);  
        }  
    }  
  
/*** 
     * Get request query string, form method : post 
     *  
     * @param request 
     * @return byte[] 
     * @throws IOException 
     */  
    public static byte[] getRequestPostBytes(HttpServletRequest request)  
            throws IOException {  
        int contentLength = request.getContentLength();  
        if(contentLength<0){  
            return null;  
        }  
        byte buffer[] = new byte[contentLength];  
        for (int i = 0; i < contentLength;) {  
  
            int readlen = request.getInputStream().read(buffer, i,  
                    contentLength - i);  
            if (readlen == -1) {  
                break;  
            }  
            i += readlen;  
        }  
        return buffer;  
    }  
     /*** 
     * Get request query string, form method : post 
     *  
     * @param request 
     * @return 
     * @throws IOException 
     */  
    public static String getRequestPostStr(HttpServletRequest request)  
            throws IOException {  
        byte buffer[] = getRequestPostBytes(request);  
        String charEncoding = request.getCharacterEncoding();  
        if (charEncoding == null) {  
            charEncoding = "UTF-8";  
        }  
        return new String(buffer, charEncoding);  
    }  
    
    
    
    
    
    
    

    public static void main(String[] args) throws Exception {
        //发送 POST 请求
      
        
        Map<String,String> params = new HashMap<String,String>();
        
        params.put("productCode","1000000002");
        params.put("serviceID","transfer");
        params.put("transferNO",PKGenarator.getOrderId());
        
        String requestID = PKGenarator.getOrderId();
        System.out.println("requestID:"+requestID);
        params.put("requestID",requestID);
        params.put("amount","0.01");
        params.put("notifyURL","http://10.75.166.94:8080/letv-web/blockChainTest/notify.html");
        params.put("sourceKey","shL19fFkDBeTSNa4ccygigrWM1L9z");
        params.put("sourceAddress","rnwDpX4WBWnhYztJDu2599GqAxPpg5htjV");
        params.put("targetAddress","rUxC1rvD9ZHe5GQFaiL5iJ7oJXGTPUm17e");
        
        String aa = callHttpPOST("http://10.110.126.159:8088/service-gateway/transfer", params);
        System.out.println(aa); 
        
        
 
    }
    
}
