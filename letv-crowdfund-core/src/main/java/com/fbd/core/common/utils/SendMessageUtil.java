package com.fbd.core.common.utils;

import java.util.Map;
import com.fbd.core.app.message.dao.IMessageNodeDao;
import com.fbd.core.app.message.dao.IStationMessageDao;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.spring.SpringUtil;

/**
 * 消息发送工具类
 */
public class SendMessageUtil {
	
	private static IStationMessageDao stationMessageDao;
	private static IMessageNodeDao messageNodeDao;
	private static IUserDao userDao;
	
	static{
	    stationMessageDao = (IStationMessageDao)SpringUtil.getBean("stationMessageDao");
	    messageNodeDao = (IMessageNodeDao)SpringUtil.getBean("messageNodeDao");
	    userDao = (IUserDao)SpringUtil.getBean("userDao");
	}
	
	/**
	 * 
	 * Description: 发送站内信
	 *
	 * @param 
	 * @return void
	 * @throws 
	 * @Author haolingfeng
	 * Create Date: 2015-2-28 上午11:50:46
	 */
	public static void sendStationMessage(String nodeCode, String stationMsgType, String userId, Map<String,String> params){
	    boolean isOn = messageNodeDao.getNodeStatus(nodeCode);
        if(!isOn){
            return;
        }
         try{
             stationMessageDao.saveStationMessage(userId,stationMsgType,nodeCode, params);
         }catch(Exception e){
             e.printStackTrace();
             throw new ApplicationException("发送站内信失败");
         }
	}
	
	/**
     * 
     * Description: 发送手机短信(用户名)
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-10 下午7:01:29
     */
    public static void sendMobileMessage(String nodeCode,String userId,Map<String, String> params){
        boolean isOn = messageNodeDao.getNodeStatus(nodeCode);
        if(!isOn){
            return;
        }
         UserModel user = userDao.findByUserId(userId);
         try{
             MessageUtils.sendSMS(nodeCode, params, user.getMobile());
         }catch(Exception e){
             e.printStackTrace();
             throw new ApplicationException("发送手机短信失败");
         }
    }
    
    
    /**
     * 
     * Description: 发送手机短信(手机号)
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-10 下午7:01:29
     */
    public static void sendMessageToMobile(String nodeCode,String mobile,Map<String, String> params){
        boolean isOn = messageNodeDao.getNodeStatus(nodeCode);
        if(!isOn){
            return;
        }
         try{
             MessageUtils.sendSMS(nodeCode, params, mobile);
         }catch(Exception e){
             e.printStackTrace();
             throw new ApplicationException("发送手机短信失败");
         }
    }

    /**
     * Description: 群发站内信
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-2-2 上午11:44:26
     */
    
    public static void sendDirectStationMessage(String nodeCode, String stationMsgType, String userId,String content) {
        try{
            stationMessageDao.saveStationMessage(userId,stationMsgType,nodeCode,content);
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("发送站内信失败");
        }
    }
	
}