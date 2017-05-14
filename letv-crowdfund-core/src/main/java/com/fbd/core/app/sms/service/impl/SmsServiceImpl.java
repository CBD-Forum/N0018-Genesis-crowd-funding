package com.fbd.core.app.sms.service.impl;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.sms.service.SmsService;
import com.fbd.core.exception.ApplicationException;
import cryptix.jce.provider.MD5;

/**
 * 发短信 返回信息详见文档。
 * 
 * @author Administrator
 * 
 */
@Service("smsService")
public class SmsServiceImpl extends SmsService {
    private static final Logger logger = LoggerFactory
            .getLogger(SmsServiceImpl.class);

    /**
     * 2 发送成功
     */
    public static final String SUCCESS = "2";

    public void send(String content, String mobileNumber) {
        logger.info("发送的短信内容：" + content);
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(
                "http://sdk2.entinfo.cn:8061/mdsmssend.ashx");
        post.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
        NameValuePair[] data = {
                new NameValuePair("sn", "SDK-JDH-010-00014"),
                new NameValuePair("pwd", new MD5().toMD5(
                        "SDK-JDH-010-000140-1F22-4").toUpperCase()),
                new NameValuePair("mobile", mobileNumber),
                new NameValuePair("content", content),
                new NameValuePair("ext", ""), new NameValuePair("stime", ""),
                new NameValuePair("rrid", ""), new NameValuePair("msgfmt", "") };
        post.setRequestBody(data);
        try {
            client.executeMethod(post);
            String result = new String(post.getResponseBodyAsString().getBytes(
                    "utf-8"));
            logger.info("短信发送后返回的结果：" + result);
            if (Long.valueOf(result) < 0) {
                throw new ApplicationException("消息发送失败:" + result);
            }
        } catch (HttpException e) {
            e.printStackTrace();
            throw new ApplicationException("消息发送失败:" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("消息发送失败:" + e.getMessage());
        }
    }

    public void sendnew(String content, String mobileNumber) {
        logger.info("发送的短信内容：" + content);
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(
                "http://sdk2.entinfo.cn:8061/mdsmssend.ashx");
        post.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
        NameValuePair[] data = {
                new NameValuePair("sn", "SDK-JDH-010-00014"),
                new NameValuePair("pwd", new MD5().toMD5(
                        "SDK-JDH-010-000140-1F22-4").toUpperCase()),
                new NameValuePair("mobile", mobileNumber),
                new NameValuePair("content", content),
                new NameValuePair("ext", ""), new NameValuePair("stime", ""),
                new NameValuePair("rrid", ""), new NameValuePair("msgfmt", "") };
        post.setRequestBody(data);
        try {
            client.executeMethod(post);
            String result = new String(post.getResponseBodyAsString().getBytes(
                    "utf-8"));
            logger.info("短信发送后返回的结果：" + result);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SmsServiceImpl().send("绑定手机号，认证码：074804。【钱图网】", "15313112907");
    }
}
