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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.dao.impl.SysConfigDaoImpl;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.util.PropertiesUtil;
import com.fbd.core.util.spring.SpringUtil;

/**
 * 系统配置缓存
 * @author sunbo
 *
 */
@Component
public class SysConfigCache{
    
    private static HashMap<String,String> cacheMap;
    private ISysConfigDao sysConfigDao = (SysConfigDaoImpl) SpringUtil.getBean("sysConfigDaoImpl");
    private static SysConfigCache sysCache;
    private byte[] lock = new byte[0];
    
    /**
     * 存放图片位置
     */
    private static final String IMAGE_URL = "/image_sys";
    /**
     * 存放后台logo位置
     */
    private static final String IMAGE_LOGO_URL = "logo";
    /**
     * 存放后台登录页图片位置
     */
    private static final String IMAGE_LOGIN_URL = "login";
    
    /**
     * 第三方支付平台接口前缀，前后台页面获取url时，需要添加此前缀
     */
    private static final String INTERFACE_URL = "interface_";
    
    /**
     * ueditor富文本图片上传配置文件地址
     */
    private static final String UEDITOR_CONFIG = "/js/common/ueditor/jsp/config.json";
    
    /**
     * ueditor富文本图片上传配置文件初始地址
     */
    private static final String UEDITOR_CONFIG_INIT = "/js/common/ueditor/jsp/config_init.json";
    
    
    
    
    private SysConfigCache(){
    }
    
	public static SysConfigCache getInstance() {
		if (sysCache == null) {
			sysCache = new SysConfigCache();
			sysCache.loadAll();
		}
		return sysCache;
	}

    /**
     * 加载缓存
     */
    public  synchronized void loadAll() {
        System.out.println("===========得到缓存开始===================");
        cacheMap=new HashMap<String,String>();
        List<SysConfigModel> list = sysConfigDao.getSysConfigList(new SysConfigModel());
        if(list != null){
        	initCache(list);
        }
        System.out.println("===========得到缓存完毕===================");
    }

    /**
     * 重新加载缓存
     */
    public synchronized void reloadAll() {
        System.out.println("===========重新得到缓存开始===================");
        List<SysConfigModel> list = sysConfigDao.getSysConfigList(new SysConfigModel());
        if(list != null){
            cacheMap.clear();
            initCache(list);
        }
        System.out.println("===========重新得到缓存完成===================");
    }
    
    /**
     * 手动添加一个缓存
     * @param sysConfigModel
     */
    public synchronized void add(SysConfigModel s){
    	cacheMap.put(s.getDisplayName(),s.getCode());
    }
    
    /**
     * 手动更新一个缓存
     * @param sysConfigModel
     */
    public synchronized void update(SysConfigModel s) {
    	cacheMap.put(s.getDisplayName(),s.getCode());
    }
    
    /**
     * 根据code值获取显示名称
     * @param code
     * @return
     */
    public synchronized String findValue(String displayName){
    	Iterator<Map.Entry<String, String>> entries = cacheMap.entrySet().iterator();
    	while (entries.hasNext()) {
    	    Map.Entry<String, String> entry = entries.next();
    	    if(displayName.equals(entry.getKey())){
                return entry.getValue();
            }
    	}
        return "";
    }
    
    /**
     * 获取所有的缓存
     * @return
     */
    public synchronized HashMap<String,String> getallCache(){
        return cacheMap;
        
    }
    
    /**
     * 根据code的值移除
     * @param code
     */
    public void removeSysCache(String key) {
        synchronized (lock) {
            cacheMap.remove(key);
        }
    }
    
    /**
     * 加载所有缓存
     * @param list
     */
    private void initCache(List<SysConfigModel> list){
    	setCacheMap(list);//加载系统配置缓存
    	setLogoCache();//设置后台logo、后台登录页图片地址的缓存
    	setInterfaceCache();//加载第三方接口地址缓存
    	setUeditorConfig();//配置富文本图片上传地址
    	
    }
    
	/**
     * 设置后台logo、后台登录页图片地址的缓存
     */
    private void setLogoCache() {
    	String webRootAbsPath = SpringUtil.getWebRootAbsPath();
    	putImgCache(webRootAbsPath, IMAGE_LOGO_URL, "/style/images/logo.png");
    	putImgCache(webRootAbsPath, IMAGE_LOGIN_URL, "/style/images/login_bg.jpg");
	}
    private void putImgCache(String webRootAbsPath, String imgType, String defaultUrl){
    	File targetFiles = new File(webRootAbsPath + IMAGE_URL + "/" + imgType);
    	File[] files = targetFiles.listFiles();
    	String fileName = "";
    	if(null != files){
    		for (File file : files) {
	        	fileName = file.getName();
	        	break;
	        }
    	}
    	if(!"".equals(fileName)){
    		defaultUrl = IMAGE_URL + "/" + imgType + "/" + fileName;
    	}
        cacheMap.put("admin_"+imgType+"_url", defaultUrl);
        System.out.println("---------加载图片缓存，类型：" + imgType + "\t 图片地址：" + defaultUrl);
    }

	/**
     * 加载系统配置缓存
     * @param list
     */
    private void setCacheMap(List<SysConfigModel> list) {
        for (SysConfigModel s : list) {
            cacheMap.put(s.getDisplayName(),s.getCode());
        }
    }
    
    /**
     * 加载第三方接口地址缓存
     */
    private void setInterfaceCache() {
    	Properties properties = PropertiesUtil.getProperties("config/interface-url.properties");
    	
    	try {
            Enumeration en = properties.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String value = properties.getProperty (key);
                cacheMap.put(INTERFACE_URL + key, value);
                System.out.println("----------加载第三方接口缓存	key：" + key + "\t 接口地址：" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
    /**
     * 配置富文本图片上传地址
     */
    private void setUeditorConfig() {
		try {
			String webRootAbsPath = SpringUtil.getWebRootAbsPath();
			File file = new File(webRootAbsPath + UEDITOR_CONFIG_INIT);
			String result = txt2String(file);
			
			System.out.println("------初始化Ueditor图片富文本图片上传地址 before replace:" + result);
			result = result.replace("${imageUrlPrefix}", findValue("file_url"));
			
			System.out.println("------初始化Ueditor图片富文本图片上传地址 after replace:" + result);
			
			FileWriter writer = new FileWriter(webRootAbsPath + UEDITOR_CONFIG);
			writer.write(result);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 读取txt文件的内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static String txt2String(File file) {
		String result = "";
		try {
			InputStreamReader read = new InputStreamReader (new FileInputStream(file),"UTF-8");
			BufferedReader br = new BufferedReader(read);// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result = result + "\n" + s;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    
}