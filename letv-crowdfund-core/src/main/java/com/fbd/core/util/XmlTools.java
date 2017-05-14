package com.fbd.core.util;

import java.util.Collections;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * xml解析器，转换成自定义对象
 * @author dekunzhao
 * @param <T> 转换结果对象
 */
public class XmlTools<T> {
	/**
	 * Description:解析XML，将XML转换成T
	 * @param xmlStr xml字符串
	 * @param xmlHandler 自定义xml处理器
	 * @return List<T>
	 */
	public List<T> parseXML(String xmlStr,XMLHandler<T> xmlHandler) {
		List<T> resultList = Collections.emptyList();
		try {
			Document doc = DocumentHelper.parseText(xmlStr);
			resultList = xmlHandler.handler(doc);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	/**
	 * Description:将T对象转换成XML字符串
	 * @param targetObj 目标对象
	 * @param xmlHandler 自定义xml处理器
	 * @return String 
	 */
	public String convertObj2XML(T targetObj,XMLHandler<T> xmlHandler){
		Document doc = DocumentHelper.createDocument();
		String xmlStr = xmlHandler.convert(doc, targetObj);
		return xmlStr;
	}
	
}
