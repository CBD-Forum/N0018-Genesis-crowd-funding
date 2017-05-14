/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: Test.java 
 *
 * Created: [2015-4-14 上午11:15:33] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import sun.misc.BASE64Decoder;
import cryptix.jce.provider.MD5;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public class Test {
    public void sendnew(String content, String mobileNumber) {
        //logger.info("发送的短信内容：" + content);
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(
                "http://sdk2.entinfo.cn:8061/mdsmssend.ashx");
        post.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
        NameValuePair[] data = {
                new NameValuePair("sn", "SDK-NVF-010-00014"),
                new NameValuePair("pwd", new MD5().toMD5(
                        "SDK-NVF-010-00014c-8600-[").toUpperCase()),
                new NameValuePair("mobile", mobileNumber),
                new NameValuePair("content", content),
                new NameValuePair("ext", ""), new NameValuePair("stime", ""),
                new NameValuePair("rrid", ""), new NameValuePair("msgfmt", "") };
        post.setRequestBody(data);
        try {
            client.executeMethod(post);
            String result = new String(post.getResponseBodyAsString().getBytes(
                    "utf-8"));
            //logger.info("短信发送后返回的结果：" + result);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    public void sendMsg(String content, String mobileNumber) {
        //logger.info("发送的短信内容：" + content);
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://ms.go.le.com/service/message");
        post.addRequestHeader("Content-Type","application/json;charset=utf-8");// 在头文件中设置转码
        String password = MD5Utis.MD5("5ap2ih87", 16).toUpperCase();
        String body = "{\"usr\":\"lskg-04-ls-01\",\"pwd\":\""+password+"\",\"ext\":\"11\",\"to\":\"13161161152\",\"msg\":\"你好\",\"linkid\":\"\"}";
        post.setRequestBody(body);
        try {
            client.executeMethod(post);
            String result = new String(post.getResponseBodyAsString().getBytes(
                    "utf-8"));
            System.out.println("短信发送后返回的结果：" + result);
            Map<String,Object> map = JsonHelper.getMapFromJson(result);
            
            System.out.println("短信发送后返回的结果-map：" + map);
            
            
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        
        String image = "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";
        String filename = "base2016.jpg";
        FileOutputStream fos = null;  
        try{  
            String toDir = "E:"+File.separator;   //存储路径  
            byte[] buffer = new BASE64Decoder().decodeBuffer(image);   //对android传过来的图片字符串进行解码   
            File destDir = new File(toDir);    
            if(!destDir.exists()) destDir.mkdir();  
            fos = new FileOutputStream(new File(destDir,filename));   //保存图片  
            fos.write(buffer);  
            fos.flush();  
            fos.close();  
        }catch (Exception e){  
            e.printStackTrace();  
        }  
 
    }
}
