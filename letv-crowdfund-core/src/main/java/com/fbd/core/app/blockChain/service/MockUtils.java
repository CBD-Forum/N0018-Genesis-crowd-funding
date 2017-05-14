/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: MockUtils.java 
 *
 * Created: [2016-8-25 下午9:03:18] by haolingfeng
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

package com.fbd.core.app.blockChain.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SslUtils;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public class MockUtils {
    
    /**
     * 开户
     * Description: 
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-25 下午9:17:39
     */
    public static String createAccount(String url,Map<String,String> params){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String resultStr = "";
        try{
            String productCode = params.get("productCode");
            if(!"1000000001".equals(productCode)){
                resultMap.put("state", "ProductIsExist");
                resultMap.put("message", "产品不存在");
                return JsonHelper.getStrFromObject(resultMap);
            }
            String serviceID = params.get("serviceID");
            if(!"createAccount".equals(serviceID)){
                resultMap.put("state", "ServiceException");
                resultMap.put("message", "服务异常");
                return JsonHelper.getStrFromObject(resultMap);
            }
            resultMap.put("address", PKGenarator.getId());
            resultMap.put("key", PKGenarator.getId());
            resultMap.put("productCode","1000000001");
            resultMap.put("state", "TransactionSuccess");
            resultMap.put("message", "事务成功");
            resultStr = JsonHelper.getStrFromObject(resultMap);
        }catch(Exception e){
            resultMap.put("productCode","1000000001");
            resultMap.put("state", "ServiceException");
            resultMap.put("message", "服务异常");
            resultStr = JsonHelper.getStrFromObject(resultMap);
        }
        return resultStr;
    }
    
    /**
     * 激活用户
     * Description: 
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-25 下午9:26:57
     */
    public static String activeAccount(String url,Map<String,String> params){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String resultStr = "";
        try{
            String productCode = params.get("productCode");
            if(!"1000000001".equals(productCode)){
                resultMap.put("state", "ProductIsExist");
                resultMap.put("message", "产品不存在");
                return JsonHelper.getStrFromObject(resultMap);
            }
            String serviceID = params.get("serviceID");
            if(!"activeAccount".equals(serviceID)){
                resultMap.put("state", "ServiceException");
                resultMap.put("message", "服务异常");
                return JsonHelper.getStrFromObject(resultMap);
            }
            resultMap.put("address", PKGenarator.getId());
            resultMap.put("productCode","1000000001");
            resultMap.put("state", "TransactionSuccess");
            resultMap.put("transactionID",PKGenarator.getId());
            resultMap.put("message", "事务成功");
            resultStr = JsonHelper.getStrFromObject(resultMap);
        }catch(Exception e){
            resultMap.put("productCode","1000000001");
            resultMap.put("state", "ServiceException");
            resultMap.put("message", "服务异常");
            resultStr = JsonHelper.getStrFromObject(resultMap);
        }
        return resultStr;
    }
    
    /**
     * 转账接口
     * Description: 
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-25 下午9:28:14
     */
    public static String transfer(String url,Map<String,String> params){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String resultStr = "";
        try{
            String productCode = params.get("productCode");
            if(!"1000000001".equals(productCode)){
                resultMap.put("state", "ProductIsExist");
                resultMap.put("message", "产品不存在");
                return JsonHelper.getStrFromObject(resultMap);
            }
            String serviceID = params.get("serviceID");
            if(!"transfer".equals(serviceID)){
                resultMap.put("state", "ServiceException");
                resultMap.put("message", "服务异常");
                return JsonHelper.getStrFromObject(resultMap);
            }
            if(params.get("amt")==null){
                resultMap.put("state", "ParameterError");
                resultMap.put("message", "参数错误[amt]");
                return JsonHelper.getStrFromObject(resultMap);
            }
            if(Double.parseDouble(params.get("amt").toString())<=0){
                resultMap.put("state", "ParameterError");
                resultMap.put("message", "参数错误[amt不能小于等于0]");
                return JsonHelper.getStrFromObject(resultMap);
            }
            Random random = new Random();
            int index = random.nextInt(10);
            boolean flag = randomResult(index);
            if(flag){
                resultMap.put("targetAddress", PKGenarator.getId());
                resultMap.put("productCode","1000000001");
                resultMap.put("state", "TransactionSuccess");
                resultMap.put("transactionID",PKGenarator.getId());
                resultMap.put("message", "事务成功");
                resultMap.put("transferNO", params.get("orderId"));
                resultStr = JsonHelper.getStrFromObject(resultMap);
            }else{
                resultMap.put("targetAddress", PKGenarator.getId());
                resultMap.put("productCode","1000000001");
                resultMap.put("state", "TransactionFail");
                resultMap.put("transactionID",PKGenarator.getId());
                resultMap.put("message", "事务失败");
                resultStr = JsonHelper.getStrFromObject(resultMap);
            }
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put("productCode","1000000001");
            resultMap.put("state", "ServiceException");
            resultMap.put("message", e.getMessage());
            resultStr = JsonHelper.getStrFromObject(resultMap);
        }
        return resultStr;
    }
    /**
     * 
     * Description: 执行区块链
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 下午1:41:54
     */
    public static String opBlockChain(Map<String,String> params){
        String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
        String result =  callHttpPOST(requestUrl, params);
       return result;
    }
    /**
     * 
     * Description: 执行区块链
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 下午1:41:54
     */
    public static String opBlockChain(String requestUrl,Map<String,String>params){
        String result =  callHttpPOST(requestUrl, params);
        return result;
    }
    public static  String transfer(Map<String,String>params){
        String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
        String result =  callHttpPOST(requestUrl, params);
        return result;
    }

  
    
    private static boolean randomResult(int index){
        
        List<Integer> result = new ArrayList<Integer>();
        result.add(0);
        result.add(0);
        result.add(0);
        result.add(0);
        result.add(0);
        result.add(0);
        result.add(0);
        result.add(0);
        result.add(0);
        result.add(0);
//        System.out.println("result:"+result);
        return result.get(index)==0?true:false;
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
            conn.setRequestProperty("Accept-Charset", "utf-8");
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
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
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
    
    
    public static void main(String[] args) {
        Map<String,String> params = new HashMap<String,String>();
        params.put("amt","10");
        String result = MockUtils.transfer("",params);
        System.out.println(result);
        
    }

}
