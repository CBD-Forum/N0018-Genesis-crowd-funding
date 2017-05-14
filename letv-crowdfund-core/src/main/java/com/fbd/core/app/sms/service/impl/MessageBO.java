package com.fbd.core.app.sms.service.impl;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.message.model.MessageTemplateModel;
import com.fbd.core.app.sms.service.SmsService;
import com.fbd.core.util.message.MailUtil;

@Service("messageBO")
public class MessageBO {
    @Resource
    private SmsService smsService;

    public void sendEmail(MessageTemplateModel msgTemp,
            Map<String, String> params, String email) {
        String msg = replaceParams(msgTemp, params);
        MailUtil.sendMail(email, msgTemp.getTplName(), msg);
    }

    public void sendSMS(MessageTemplateModel msgTemp,
            Map<String, String> params, String mobileNumber) {
        String msg = replaceParams(msgTemp, params);
        smsService.send(msg, mobileNumber);
    }

    // public void sendStationMsg(MessageTemplateModel msgTemp,
    // Map<String, String> params, User sender, User reveiver) {
    // String msg = replaceParams(msgTemp, params);
    // sendStationMsg(reveiver, sender, msgTemp.getTplName(), msg);
    // }

    /**
     * 替换模板里面的参数
     * 
     * @param msgTemp
     * @param params
     * @return 替换完成的内容
     */
    private String replaceParams(MessageTemplateModel msgTemp,
            Map<String, String> params) {
        String returnStr = msgTemp.getTplContent();
        for (String key : params.keySet()) {
            returnStr = returnStr.replace("#{" + key + "}", params.get(key));
        }
        return returnStr;
    }

    // public void sendStationMsg(User receiver, User sender, String title,
    // String msg) {
    // InBox inbox = new InBox();
    // inbox.setId(IdGenerator.randomUUID());
    // inbox.setRecevier(receiver);
    // inbox.setSender(sender);
    // inbox.setReceiveTime(new Date());
    // inbox.setContent(msg);
    // inbox.setStatus(MessageConstants.InBoxConstants.NOREAD);
    // inbox.setTitle(title);
    // ht.save(inbox);
    // }

}
