package com.fbd.core.util;

import java.util.List;

import org.dom4j.Document;

/**
 * xml文档解析处理器
 * @author dekunzhao
 * @param <T> 转换结果对象
 */
public interface XMLHandler<T> {
	/**
	 * Description:将XML转换成T对象
	 * @param document XML Document对象
	 * @return List<T>
	 */
	public List<T> handler(Document document);
	/**
	 * Description:将T对象转换成XML字符串
	 * @param document XML Document对象
	 * @param targetObj 转换成的目标对象
	 * @return String
	 */
	public String convert(Document document,T targetObj);
	
}
