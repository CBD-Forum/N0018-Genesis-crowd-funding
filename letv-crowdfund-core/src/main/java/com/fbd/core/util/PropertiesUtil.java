/**
 * 
 */
package com.fbd.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaoPC
 *
 */
public class PropertiesUtil {
	
	private static Log log = LogFactory.getLog(PropertiesUtil.class);
	
	/**
	 * 构造
	 */
	public PropertiesUtil() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 得到配置对象
	 * @param name 配置名
	 * @return 配置对象
	 */
	public static Properties getProperties(String name) {
		Properties props = new Properties();
		loadProperties(props, name);
		return props;
	}

	/**
	 * 得到配置对象
	 * @param file 配置文件
	 * @return 配置对象
	 */
	public static Properties getProperties(File file) {
		Properties props = new Properties();
		loadProperties(props, file);
		return props;
	}

	/**
	 * 加载配置
	 * @param props 配置对象
	 * @param name 配置名
	 */
	public static void loadProperties(Properties props, String name) {
		if (props != null) {
			InputStream in = null;
			try {
				in = PropertiesUtil.class.getClassLoader().getResourceAsStream(
						name);
				if (in != null) {
					props.load(in);
				}
			} catch (IOException e) {
				log.error("load " + name + " error!");
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						log.error("close " + name + " error!");
					}
				}
			}
		}
	}

	/**
	 * 加载配置文件
	 * @param props 配置对象
	 * @param file 配置文件
	 */
	public static void loadProperties(Properties props, File file) {
		if (props != null) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				if (in != null) {
					props.load(in);
				}
			} catch (IOException e) {
				log.error("load " + file.getName() + " error!");
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						log.error("close " + file.getName() + " error!");
					}
				}
			}
		}
	}

}
