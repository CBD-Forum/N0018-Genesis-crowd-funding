package com.fbd.core.util.spring;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.dao.impl.SysConfigDaoImpl;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.common.cache.RedisManager;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 获取spring的上下文
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
public class InitSpringUtilListener implements ServletContextListener {
    private Log log = LogFactory.getLog(InitSpringUtilListener.class);

    /**
     * 构造
     */
    public InitSpringUtilListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 销毁上下文
     * 
     * @param evt
     *            servlet上下文事件对象
     */
    public void contextDestroyed(ServletContextEvent evt) {

    }

    /**
     * 初始化上下文
     * 
     * @param evt
     *            servlet上下文事件对象
     */
    public void contextInitialized(ServletContextEvent evt) {
        log.info("开始初始化SpringUtil...");
        ServletContext ctx = evt.getServletContext();
        SpringUtil.init(ctx);
        String webRootAbsPath = evt.getServletContext().getRealPath("/");
        log.info("web root abs path=" + webRootAbsPath);
        SpringUtil.setWebRootAbsPath(webRootAbsPath);
        log.info("SpringUtil初始化完成.");
        
        //加载系统设置
//        ISysConfigDao sysConfigDao = (SysConfigDaoImpl)SpringUtil.getBean("sysConfigDaoImpl");
//        List<SysConfigModel> list = sysConfigDao.getSysConfigList(null);
//        HashMap<String, Object> map = new LinkedHashMap<String, Object>();
//        for (int i = 0; i < list.size(); i++) {
//            map.put(list.get(i).getDisplayName(), list.get(i));
//        }
//        RedisManager.add("sysConfig", map);
        
        // SpringUtil.getBean("testService");
        
//        ISysConfigDao sysConfigDao = (SysConfigDaoImpl)SpringUtil.getBean("sysConfigDaoImpl");
//        String fileUrl = sysConfigDao.getByDisplayName("file_url").getCode();
//        String userWebUrl = sysConfigDao.getByDisplayName("user_web_url").getCode();
//        evt.getServletContext().setAttribute(fileUrl, fileUrl);
//        evt.getServletContext().setAttribute(userWebUrl, userWebUrl);
    }

}
