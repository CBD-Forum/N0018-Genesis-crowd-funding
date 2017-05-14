package com.fbd.core.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.app.message.model.MessageTemplateModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.spring.SpringUtil;

public class MessageUtils {
    private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);

    private static SqlSessionTemplate sqlSession = (SqlSessionTemplate)SpringUtil.getBean("sqlSessionTemplate");
    
    private static final String mapperSpace = "com.fbd.core.app.config.model.SysConfigModelMapper.";
    
//    private static String sendSMS(String uri,String userName,String userPassword,String content, String mobileNumber){
//        HttpClient client = new HttpClient();
//        PostMethod post = new PostMethod(uri);
//        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");// 在头文件中设置转码
//        NameValuePair[] data = {
//                new NameValuePair("sn", userName),
//                new NameValuePair("pwd", new MD5().toMD5(userPassword).toUpperCase()),
//                new NameValuePair("mobile", mobileNumber),
//                new NameValuePair("content", content),
//                new NameValuePair("ext", ""), new NameValuePair("stime", ""),
//                new NameValuePair("rrid", ""), new NameValuePair("msgfmt", "") };
//        post.setRequestBody(data);
//        try {
//            client.executeMethod(post);
//            String result = new String(post.getResponseBodyAsString().getBytes("UTF-8"));
//            return result;
//        } catch (HttpException e) {
//            throw new ApplicationException("消息发送失败:" + e.getMessage());
//        } catch (IOException e) {
//            throw new ApplicationException("消息发送失败:" + e.getMessage());
//        }
//    }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    private static String sendSMS(String uri,String userName,String userPassword,String content, String mobileNumber){
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(uri);
        post.addRequestHeader("Content-Type","application/json;charset=utf-8");// 在头文件中设置转码
//        System.out.println("userName:"+userName);
//        System.out.println("userPassword:"+userPassword);
        String password = MD5Utis.MD5(userPassword,16).toUpperCase();
//        System.out.println("password:"+password);
        String body = "{\"usr\":\""+userName+"\",\"pwd\":\""+password+"\",\"ext\":\"11\",\"to\":\""+mobileNumber+"\",\"msg\":\""+content+"\",\"linkid\":\"\"}";
        post.setRequestBody(body);
        try {
            client.executeMethod(post);
            String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
            System.out.println("短信发送后返回的结果：" + result);
            Map<String,Object> map = JsonHelper.getMapFromJson(result);
            String statuscode = map.get("statuscode").toString();
            String errorinfo = map.get("errorinfo")==null?"":map.get("errorinfo").toString();
            String returnstatus = "";
            if("000000".equals(statuscode)){
                returnstatus = "Success";
            }else{
                throw new ApplicationException(errorinfo+"(状态吗:"+statuscode+")");
            }
            return returnstatus;
        } catch (HttpException e) {
            throw new ApplicationException("消息发送失败:" + e.getMessage());
        } catch (IOException e) {
            throw new ApplicationException("消息发送失败:" + e.getMessage());
        }catch(ApplicationException e) {
            throw new ApplicationException("消息发送失败:" + e.getMessage());
        }catch (Exception e) {
            throw new ApplicationException("消息发送失败:" + e.getMessage());
        }
    }
    
    /**
     * Description: 
     *
     * @param nodeCode:消息节点编号
     * @param params:消息节点参数Map列表
     * @param mobileNumber:短信号码
     * 
     * @return boolean
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-27 上午11:53:55
     */
    public static boolean sendSMS(String nodeCode,  Map<String, String> params, String mobileNumber) {
        MessageTemplateModel messageTpl = sqlSession.selectOne("com.fbd.core.app.message.model.MessageTemplateModelMapper.selectByTplCode", nodeCode);
        String content = messageTpl.getTplContent();
        for (String key : params.keySet()) {
            content = content.replace("#{" + key + "}", params.get(key));
        }
        logger.info(content);
        return sendSMS(content, mobileNumber);
    }
    
    /**
     * 
     * Description: 发送邮件
     *
     * @param 
     * @return boolean
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-2 下午3:20:35
     */
    public static boolean sendEmail(String nodeCode,  Map<String, String> params, String toAddress,String subject) {
        MessageTemplateModel messageTpl = sqlSession.selectOne("com.fbd.core.app.message.model.MessageTemplateModelMapper.selectByTplCode", nodeCode);
        String content = messageTpl.getTplContent();
        for (String key : params.keySet()) {
            content = content.replace("#{" + key + "}", params.get(key));
        }
        logger.info(content);
        return sendMail(toAddress,subject,content);
    }
    
    /**
     * 
     * Description: 获取发送内容
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-27 下午4:45:42
     */
    public static String getContent(String nodeCode, Map<String, String> params) {
        MessageTemplateModel messageTpl = sqlSession.selectOne("com.fbd.core.app.message.model.MessageTemplateModelMapper.selectByTplCode", nodeCode);
        String content = messageTpl.getTplContent();
        for (String key : params.keySet()) {
            content = content.replace("#{" + key + "}", params.get(key));
        }
        return content;
    }
    
    /**
     * Description: 发送短信
     *
     * @param 短信内容
     * @param 短信号码
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-3 下午2:37:19
     */
//    public static boolean sendSMS(String content, String mobileNumber) {
//        String uri1 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_1_server")).getCode();
//        String userName1 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_1_account")).getCode();
//        String userPassword1 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_1_password")).getCode();
//        String uri2 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_2_server")).getCode();
//        String userName2 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_2_account")).getCode();
//        String userPassword2 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_2_password")).getCode();
//  
//        String resultCode=sendSMS(uri1, userName1, userPassword1, content, mobileNumber);
//        
//        if (Long.valueOf(resultCode) < 0) {
//            resultCode=sendSMS(uri2, userName2, userPassword2, content, mobileNumber);
//            if (Long.valueOf(resultCode) < 0){
//                return false;
//            }else{
//                return true;
//            }
//        }else{
//            return true;
//        }
//    }
    
    public static boolean sendSMS(String content, String mobileNumber) {
      String uri1 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_1_server")).getCode();
      String userName1 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_1_account")).getCode();
      String userPassword1 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_1_password")).getCode();
      String uri2 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_2_server")).getCode();
      String userName2 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_2_account")).getCode();
      String userPassword2 = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "sms_2_password")).getCode();

      String resultCode= "Success";
      System.out.println("content:"+content);
      if(!"true".equals(FbdCoreConstants.isClientEnvironment)){
          resultCode = sendSMS(uri1, userName1, userPassword1, content, mobileNumber);
      }     
      if (!"Success".equals(resultCode)) {
          resultCode=sendSMS(uri2, userName2, userPassword2, content, mobileNumber);
          if (!"Success".equals(resultCode)){
              return false;
          }else{
              return true;
          }
      }else{
          return true;
      }
  }
    
    public static void main(String[] args) {
//        String uri1="http://sdk2.entinfo.cn:8061/mdsmssend.ashx";
//        String userName1="SDK-NVF-010-00010";
//        String userPassword1="SDK-NVF-010-00010Ecf21-80";
//        String content="尊敬的客户您好，欢迎使用东岳金融。【爱筹网】";
//        String mobileNumber="13552826035";
//        String resultCode=sendSMS(uri1, userName1, userPassword1, content, mobileNumber);
//        System.out.println(resultCode);
        
        String url = "http://ms.go.le.com/service/message";
        String userName  = "lskg-04-ls-01";
        String password = "5ap2ih87";
        String content = "尊敬的客户您好，欢迎使用乐视金融";
        String mobileNumber="13161161152";
        System.out.println("");
        
        
        
    } 
    
    /**
     * Description: 发送携带附件和图片的邮件
     *
     * @param toAddress  收件人邮箱，多个邮箱以“;”分隔
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return boolean
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-3 下午6:05:30
     */
    public static boolean sendMail(String toAddress,String subject, String content){
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
        
        String host = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "email_stmp_server")).getCode();
        String account = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "email_account")).getCode();
        String name = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "email_name")).getCode();
        String userPassword = ((SysConfigModel) sqlSession.selectOne(mapperSpace+"selectByDisplayName", "email_password")).getCode();
  
        try {
            // 设定mail server  
            senderImpl.setHost(host);  
      
            // 建立邮件消息  
            MimeMessage mailMessage = senderImpl.createMimeMessage();  
      
            MimeMessageHelper messageHelper = null;  
            messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");  
            // 设置发件人邮箱  
            messageHelper.setFrom(name+" <"+account+">");  
      
            // 设置收件人邮箱  
            String[] toEmailArray = toAddress.split(";");  
            List<String> toEmailList = new ArrayList<String>();  
            if (null == toEmailArray || toEmailArray.length <= 0) {  
                throw new RuntimeException("收件人邮箱不得为空！");  
            } else {  
                for (String s : toEmailArray) {
                    s = StringUtils.trimToEmpty(s);  
                    if (!s.equals("")) {  
                        toEmailList.add(s);  
                    }  
                }  
                if (null == toEmailList || toEmailList.size() <= 0) {  
                    throw new RuntimeException("收件人邮箱不得为空！");  
                } else {  
                    toEmailArray = new String[toEmailList.size()];  
                    for (int i = 0; i < toEmailList.size(); i++) {  
                        toEmailArray[i] = toEmailList.get(i);  
                    }  
                }  
            }  
            messageHelper.setTo(toEmailArray);  
            // 邮件主题  
            messageHelper.setSubject(subject);  
            // true 表示启动HTML格式的邮件  
            messageHelper.setText(content, true);  
      
            senderImpl.setUsername(account) ; // 根据自己的情况,设置username
            senderImpl.setPassword(userPassword) ; // 根据自己的情况, 设置password
            Properties prop = new Properties() ;
            prop.put("mail.smtp.auth", "true") ; // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
            prop.put("mail.smtp.timeout", "25000") ; 
            senderImpl.setJavaMailProperties(prop); 
      
            // 发送邮件  
            senderImpl.send(mailMessage);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    static class SimpleAuthenticator extends Authenticator {
        private String user;  
        private String pwd;  
     
        public SimpleAuthenticator(String user, String pwd) {  
            this.user = user;  
            this.pwd = pwd;  
        }  
     
        @Override 
        protected PasswordAuthentication getPasswordAuthentication() {  
            return new PasswordAuthentication(user, pwd);  
        }  
    }
    
    
    /**
     * Description: 发送携带附件和图片的邮件
     *
     * @param toAddress  收件人邮箱，多个邮箱以“;”分隔
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param attachments 邮件中的附件，为空时无附件。map中的key为附件ID，value为附件地址 
     * @param pictures 邮件中的图片，为空时无图片。map中的key为图片ID，value为图片地址 
     * @return boolean
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-3 下午6:05:30
     */
    public boolean sendMail(String toAddress, String subject, String content,
            Map<String, String> attachments,Map<String, String> pictures) {
        String host = ((SysConfigModel) sqlSession.selectOne(mapperSpace+ "selectByDisplayName", "email_host")).getCode();
        String userName = ((SysConfigModel) sqlSession.selectOne(mapperSpace+ "selectByDisplayName", "email_from_username")).getCode();
        String userPassword = ((SysConfigModel) sqlSession.selectOne(mapperSpace + "selectByDisplayName", "email_from_password")).getCode();

        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

        try {
         // 设定mail server
            senderImpl.setHost(host);

            // 建立邮件消息
            MimeMessage mailMessage = senderImpl.createMimeMessage();

            MimeMessageHelper messageHelper = null;
            messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            // 设置发件人邮箱
            messageHelper.setFrom(userName);

            // 设置收件人邮箱
            String[] toEmailArray = toAddress.split(";");
            List<String> toEmailList = new ArrayList<String>();
            if (null == toEmailArray || toEmailArray.length <= 0) {
                throw new RuntimeException("收件人邮箱不得为空！");
            } else {
                for (String s : toEmailArray) {
                    s = StringUtils.trimToEmpty(s);
                    if (!s.equals("")) {
                        toEmailList.add(s);
                    }
                }
                if (null == toEmailList || toEmailList.size() <= 0) {
                    throw new RuntimeException("收件人邮箱不得为空！");
                } else {
                    toEmailArray = new String[toEmailList.size()];
                    for (int i = 0; i < toEmailList.size(); i++) {
                        toEmailArray[i] = toEmailList.get(i);
                    }
                }
            }
            messageHelper.setTo(toEmailArray);

            // 邮件主题
            messageHelper.setSubject(subject);

            // true 表示启动HTML格式的邮件
            messageHelper.setText(content, true);

            // 添加图片
            if (null != pictures) {
                for (Iterator<Map.Entry<String, String>> it = pictures.entrySet()
                        .iterator(); it.hasNext();) {
                    Map.Entry<String, String> entry = it.next();
                    String cid = entry.getKey();
                    String filePath = entry.getValue();
                    if (null == cid || null == filePath) {
                        throw new RuntimeException("请确认每张图片的ID和图片地址是否齐全！");
                    }

                    File file = new File(filePath);
                    if (!file.exists()) {
                        throw new RuntimeException("图片" + filePath + "不存在！");
                    }

                    FileSystemResource img = new FileSystemResource(file);
                    messageHelper.addInline(cid, img);
                }
            }

            // 添加附件
            if (null != attachments) {
                for (Iterator<Map.Entry<String, String>> it = attachments
                        .entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, String> entry = it.next();
                    String cid = entry.getKey();
                    String filePath = entry.getValue();
                    if (null == cid || null == filePath) {
                        throw new RuntimeException("请确认每个附件的ID和地址是否齐全！");
                    }

                    File file = new File(filePath);
                    if (!file.exists()) {
                        throw new RuntimeException("附件" + filePath + "不存在！");
                    }

                    FileSystemResource fileResource = new FileSystemResource(file);
                    messageHelper.addAttachment(cid, fileResource);
                }
            }

            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
            prop.put("mail.smtp.timeout", "25000");
            // 添加验证
            SimpleAuthenticator auth = new SimpleAuthenticator(userName,userPassword);

            Session session = Session.getDefaultInstance(prop, auth);
            senderImpl.setSession(session);

            // 发送邮件
            senderImpl.send(mailMessage);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    
}
