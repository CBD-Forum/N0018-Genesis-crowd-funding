package com.fbd.core.app.sms.service;

/**
 * 发短信 返回信息详见文档。
 * 
 * @author Administrator
 * 
 */
public abstract class SmsService {

    /**
     * 发送短信
     * 
     * @param content
     * @param mobileNumber
     * @throws SmsSendErrorException
     */
    public abstract void send(String content, String mobileNumber);

    public abstract void sendnew(String content, String mobileNumber);
}
