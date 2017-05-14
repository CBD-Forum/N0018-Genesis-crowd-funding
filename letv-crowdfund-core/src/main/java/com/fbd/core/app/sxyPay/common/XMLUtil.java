package com.fbd.core.app.sxyPay.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
/**
 * 
 * @Title : 
 * @File Name : XMLUtil.java
 * @Description : 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
 * @Date : 2016-7-26 下午6:48:21 
 * @author : 赵志正
 * @version : 1.0
 * @Copyright:  0Bug
 * @Others :
 * @History 1.<br/>
 *          Date : <br/>
 *          Author : <br/>
 *          Modification: <br/>
 */
public class XMLUtil {
	/**
	 * 
	* 
	* @Function : doXMLParse
	* @Desc : 
	* @author : 赵志正
	* @param strxml
	* @return
	* @throws JDOMException
	* @throws IOException Map<String,String>
	* @date 2016-7-26 下午6:48:27
	 */
	public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String, String> m = new HashMap<String, String>();
		InputStream in = new ByteArrayInputStream(strxml.getBytes());
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtil.getChildrenText(children);
			}
			m.put(k, v);
		}
		// 关闭流
		in.close();
		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}

	/**
	 * 获取xml编码字符集
	 * 
	 * @param strxml
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static String getXMLEncoding(InputStream in) throws JDOMException, IOException {
//		InputStream in = new ByteArrayInputStream(strxml.getBytes());
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		return (String) doc.getProperty("encoding");
	}
}
