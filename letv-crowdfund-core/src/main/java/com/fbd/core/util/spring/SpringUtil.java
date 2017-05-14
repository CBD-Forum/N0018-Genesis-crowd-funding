package com.fbd.core.util.spring;

import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author
 * 
 */
public class SpringUtil {

    private static Log log = LogFactory.getLog(SpringUtil.class);

    private static ApplicationContext context;
    static String webRootAbsPath;

    /**
     * 构造
     */
    public SpringUtil() {

    }

    /*
     * public static final String [] configLocations = new String[]{
     * "applicationContext-service.xml", "applicationContext-resources.xml",
     * "applicationContext-hibernate.xml" };
     */
    /**
     * 得到上下文
     * 
     * @return 应用上下文
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 加载上下文
     * 
     * @param sc
     *            servlet上下文
     */
    public static void init(ServletContext sc) {
        log.info("加载上下文");
        context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(sc);
    }

    /**
     * 加载配置
     * 
     * @param configLocations
     *            配置文件集合
     */
    public static void init(String... configLocations) {
        log.info("加载配置");
        context = new ClassPathXmlApplicationContext(configLocations);
    }

    /**
     * 根据beanId得到bean
     * 
     * @param beanName
     *            beanId
     * @return bean实体
     */
    public static Object getBean(String beanName) {
        System.out.println(context);
        return context.getBean(beanName);
    }

    /**
     * 直接传入applicationContext
     * 
     * @param actx
     *            应用程序上下文对象
     */
    public static void init(ApplicationContext actx) {
        context = actx;
    }

    public static void setWebRootAbsPath(String webRootAbsPath) {
        SpringUtil.webRootAbsPath = webRootAbsPath;
    }

    public static String getWebRootAbsPath() {
        return webRootAbsPath;
    }
}