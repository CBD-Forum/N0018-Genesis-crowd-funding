package com.fbd.web.app.test;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.fbd.core.app.redis.IRedisDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.helper.PKGenarator;


@Controller
@Scope("prototype")
@RequestMapping("/test")
public class TestAction extends BaseAction {
	
	
	private static final Logger logger = LoggerFactory.getLogger(TestAction.class);
	
    @Resource
    private IRedisDao redisDao;
    
//    @Resource
//    private IEhcacheDao ehcacheDao;
    
   /* @RequestMapping(value = "/set.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> set(String value){
    	
    	Map<String,Object> resultMap= new HashMap<String,Object>();
    	try{
    		
    		UserModel user = new UserModel();
        	user.setId(PKGenarator.getId());
        	user.setUserId(PKGenarator.getUserId());
        	user.setNickName("操蛋");
        	user.setCreateTime(new Date());
        	String userStr = JsonHelper.getStrFromObject(user);
    		String token = PKGenarator.getId();
    		this.redisDao.set(token, userStr,1500);
    		resultMap.put("token",token);
    		resultMap.put(SUCCESS,true);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put(SUCCESS,false);
    	}
    	return resultMap;
    }
    
    @RequestMapping(value = "/get.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> get(String key,HttpServletRequest request){
    	
    	Map<String,Object> resultMap= new HashMap<String,Object>();
    	try{
    		 
    		String result = this.redisDao.get(key.toString());
    		
    		System.out.println(this.redisDao.exists(key));
    		 
    		resultMap.put("userId",result);
    		resultMap.put(SUCCESS,true);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put(SUCCESS,false);
    	}
    	return resultMap;
    }
    
    
    @RequestMapping(value = "/redisCheck.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> redisCheck(HttpServletRequest request){
    	
    	Map<String,Object> resultMap= new HashMap<String,Object>();
    	try{
    		String result = this.redisDao.ping();
    	}catch(RedisConnectionFailureException e1){
    		System.out.println("===================RedisConnectionFailureException========================");
    		System.out.println("===============重启redis服务======================");
    	
    	
    		e1.printStackTrace();
    	}catch(JedisConnectionException e2){
    		e2.printStackTrace();
    		System.out.println("===================JedisConnectionException========================");
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println("===================Exception========================");
    		resultMap.put(SUCCESS,false);
    	}
    	return resultMap;
    }*/
    
  /*  
    @RequestMapping(value = "/testGetEhcache.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> testGetEhcache(String key){
    	
    	Map<String,Object> resultMap= new HashMap<String,Object>();
    	
    	try{
    		Element value = this.ehcacheDao.get(key);
    		System.out.println(value);
    		System.out.println(value.getValue());
    		System.out.println(JsonUtil.parseObject(value));
    		
    		System.out.println(this.ehcacheDao.getValue(key));
    		resultMap.put(SUCCESS,true);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put(SUCCESS,false);
    	}
    	return resultMap;
    }
    
    
    @RequestMapping(value = "/testSetEhcache.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> testSetEhcache(String key,String value){
    	
    	Map<String,Object> resultMap= new HashMap<String,Object>();
    	try{
    		this.ehcacheDao.set(key, value);
    		resultMap.put(SUCCESS,true);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put(SUCCESS,false);
    	}
    	return resultMap;
    }*/
    
    
    /*
    public static void main(String[] args) {
 
    	
    	
    	UserModel user = new UserModel();
    	user.setId(PKGenarator.getId());
    	user.setUserId(PKGenarator.getUserId());
    	user.setNickName("操蛋");
    	user.setCreateTime(new Date());
    	
    	System.out.println("createTime:"+user.getCreateTime());
    	String userStr = JsonHelper.getStrFromObject(user);
    
    	System.out.println("user:"+userStr);
    	
    	UserModel user2 = (UserModel) JsonHelper.getObjectFromJsonString(userStr, UserModel.class);
    	System.out.println("user2:"+user2);
    	
    	System.out.println("nickName:"+user2.getNickName());
    	
    	System.out.println("createTime:"+user2.getCreateTime());
	}
    
    
    
    
    @RequestMapping(value = "/dataBaseBak.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> dataBaseBak(HttpServletRequest request){
    	
    	Map<String,Object> resultMap= new HashMap<String,Object>();
    	logger.info("=================>数据库备份开始==========");
    	try{
    		 Runtime rt = Runtime.getRuntime();
    	     Process child = rt.exec("mysqldump -uroot -pSB85vJZcOimXd5Nb -R -c --set-charset=utf8 letvdb");
    	     InputStream in = child.getInputStream();
    	     InputStreamReader xx = new InputStreamReader(in, "utf8");
    	     String inStr;
    	     StringBuffer sb = new StringBuffer("");
    	     String outStr;
    	     BufferedReader br = new BufferedReader(xx);
    	     while ((inStr = br.readLine()) != null) {
    	           sb.append(inStr + "\r\n");
    	     }
    	     outStr = sb.toString();
    	     Random random = new Random();
    	     String fileName = String.valueOf(random.nextInt(1000));
    	     FileOutputStream fout = new FileOutputStream("/letv/dataBak/letvdb_"+fileName+".sql");
    	     OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
    	     writer.write(outStr);
    	     writer.flush();
    	     in.close();
    	     xx.close();
    	     br.close();
    	     writer.close();
    	     fout.close();
    	     resultMap.put(SUCCESS,true);
    	     logger.info("=================>数据库备份完成============================");
    	}catch(Exception e){
    		System.out.println("===================Exception========================");
    		resultMap.put(SUCCESS,false);
    	}
    	return resultMap;
    }
    
    
    @RequestMapping(value = "/testRequestHead.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> testRequestHead(HttpServletRequest request){
    	
    	Map<String,Object> resultMap= new HashMap<String,Object>();
    	try{
    	 
    		String tocke = request.getHeader("token");
    		String mobileTerminalFlag = request.getHeader("mobileTerminalFlag");
    		
    		resultMap.put(SUCCESS,true);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put(SUCCESS,false);
    	}
    	return resultMap;
    }*/
 

}
