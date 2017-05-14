package com.fbd.core.app.sxyPay.utils;
import java.io.IOException;
import java.io.StringReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
/**
 * 获取单个节点值（节点名必须唯一）
 * @author xw
 *
 */
 
 public class SAXParser {   
 
   class BaseHandler extends DefaultHandler {   
      private String nameList;
      private String nodename;
      private boolean title = false;   
      BaseHandler ( String nodename) {
    	  this.nodename = nodename;
      }
      public String getNameList() {   
         return nameList;   
      }   
      
      public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	@Override   
      public void startDocument() throws SAXException {   
         nameList = new String();   
      }   
      @Override   
      public void endDocument() throws SAXException {    
      }   
        
      @Override   
      public void startElement(String uri, String localName, String qName,   
         Attributes atts) throws SAXException {   
         if (qName.equals(nodename)) {   
            title = true;   
         }   
      }   
     
      @Override   
      public void endElement(String namespaceURI, String localName, String qName)   
         throws SAXException {   
         if (title) {   
            title = false;   
         }   
      }   
              
      @Override   
      public void characters(char[] ch, int start, int length) {   
         if (title) {   
            String bookTitle = new String(ch, start, length);   
            nameList=bookTitle;   
         }   
      }   
              
   }   
   public static String SAXParseNodeValue(String xmlStr, String nodeName) {
	   XMLReader parser;
	   String ret;
	try {
		parser = XMLReaderFactory.createXMLReader();
		BaseHandler handler = (new SAXParser()).new BaseHandler(nodeName);   
		parser.setContentHandler(handler);   
		//创建一个新的字符串
		StringReader read = new StringReader(xmlStr);
		//创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		InputSource source = new InputSource(read);
		parser.parse(source);   
		 ret = handler.getNameList();
		 return ret;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}   
   }
   public static void main(String[] args) throws SAXException, IOException {
	  String XML ="<?xml version='1.0' encoding='gb2312'?><ordermessage><order><a>发</a><b>3fd</b></order><order><a>发</a><b>3fd</b></order></ordermessage>";
	  String ret = SAXParseNodeValue(XML,"order");
	  System.out.println(ret);
	  if (ret!=null && "0".equals(ret)) {
//		  0→未提交；
//		  1支付完成；
//		  2未支付（支付结果未确定）；
//		  3支付被拒绝；
//		  4商户退款中；
//		  5退款已成功；
//		  6退款被拒绝；
//		  7持卡人拒付；
//		  8异常退款
		 String pstatus = SAXParseNodeValue(XML,"pstatus");
		 System.out.println(pstatus);
		 
	  }else {
		  System.out.println("数据对账请求或响应异常");
	  }
//      XMLReader parser = XMLReaderFactory.createXMLReader();   
//      BaseHandler handler = (new SAXParser()).new BaseHandler("");   
//      parser.setContentHandler(handler);   
//      //创建一个新的字符串
//      StringReader read = new StringReader("");
//      //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
//      InputSource source = new InputSource(read);
//      parser.parse(source);   
//      System.out.println(handler.getNameList());   
   }   
 }  