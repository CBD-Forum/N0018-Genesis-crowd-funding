package com.fbd.core.util;

import java.io.IOException;
import java.util.Properties;


/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author dongzhongwei
 * @version 1.0
 *
 */

public class SpringPropertiesHolder {
    
    private static Properties properties = null;

    protected SpringPropertiesHolder() {
        super();
    }

    /**
     * @param defaults
     * @throws IOException
     */
    protected SpringPropertiesHolder(
            SpringPropertyPlaceholderConfigurer propertyPlaceholderConfigurer)
            throws IOException {
        properties = new Properties (propertyPlaceholderConfigurer.getProperties());
    }
    
    /**
     * 
     * @author：Wuqingming           
     * @date：2012-4-10
     * @Description：获取属性值
     * @param key 键
     * @return
     */
    public static String getProperty (String key) {
        if (properties == null) {
            return null;
        }
        return properties.getProperty(key);
    }
    
    /**
     * 
     * @author：Wuqingming           
     * @date：2012-4-10
     * @Description：获取属性值
     * @param key 键
     * @param defaultValue 默认值，当未找到属性的时候返回该默认值
     * @return
     */
    public static String getProperty (String key, String defaultValue) {
        if (properties == null) {
            return defaultValue;
        }
        return properties.getProperty(key, defaultValue);
    }

    /**
     * 
     * @author：Wuqingming           
     * @date：2012-4-10
     * @Description：设置属性值
     * @param key 键
     * @param value 值
     */
    public static void setProperty (String key, String value) {
        if (properties == null) {
            properties = new Properties();
        }
        properties.setProperty(key, value);
    }
}