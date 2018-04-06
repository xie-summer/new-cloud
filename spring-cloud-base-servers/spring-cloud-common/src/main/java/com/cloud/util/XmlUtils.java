package com.cloud.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
/**
 * @author summer
 */
public class XmlUtils {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(XmlUtils.class);
	public static String formatXml(String str, String encoding) {
		try {
			StringWriter writer = new StringWriter();
			formatXml(str, encoding, writer);
			writer.close();
			return writer.toString();
		} catch (Exception e) {
			DB_LOGGER.warn(str + LoggerUtils.getExceptionTrace(e, 100));
			return str;
		}
	}
	/**
	 * 直接写入，不关闭writer
	 * @param str
	 * @param encoding
	 * @param writer
	 * @throws IOException
	 */
	public static void formatXml(String str, String encoding, Writer writer) throws IOException{
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding(encoding);
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			org.dom4j.Document document = DocumentHelper.parseText(str);
			xmlWriter.write(document);
		} catch (DocumentException e) {
			DB_LOGGER.warn(str + LoggerUtils.getExceptionTrace(e, 100));
		}

	}
	public static Document getDocument(String xml){
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new CharArrayReader(xml.toCharArray()));
		} catch (DocumentException e) {
			DB_LOGGER.error(LoggerUtils.getExceptionTrace(e, 100));
		}// 读取XML文件
		return document;
	}

	public static String filterInvalid(String xml){
		StringBuffer sb = new StringBuffer(xml);
		for(int i=0;i<sb.length();i++){
			int c = sb.charAt(i);
			if(c < 0x20 && c!= 0x09/*\t*/ && c!=0x0A/*\n*/ && c!= 0x0D/*\r*/){
				sb.delete(i, i+1);
			}
		}
		return sb.toString();
	}
	/**
	 * @see  syntax: http://www.w3schools.com/xpath/default.asp
	 * @param xml
	 * @param xpath
	 * @return
	 */
	public static String getNodeText(String xml, String xpath){
		return getNodeText(getDocument(xml), xpath);
	}
	/**
	 * @see  syntax: http://www.w3schools.com/xpath/default.asp
	 * @param document
	 * @param xpath
	 * @return
	 */
	public static String getNodeText(Document document, String xpath){
		if(document == null) {
            return null;
        }
		try{
			List<Node> nodeList = document.selectNodes(xpath);
			if(nodeList.isEmpty()) {
                return null;
            }
			return getText(nodeList.get(0));
		}catch(Exception e){
			DB_LOGGER.error(document.getText() + LoggerUtils.getExceptionTrace(e, 100));
			return null;
		}
	}
	public static List<String> getNodeTextList(String xml, String xpath, boolean ignoreEmpty){
		return getNodeTextList(getDocument(xml), xpath, ignoreEmpty);
	}
	public static List<String> getNodeTextList(Document document, String xpath, boolean ignoreEmpty){
		List<String> result = new ArrayList<String>();
		if(document == null) {
            return result;
        }
		try{
			List<Node> nodeList = document.selectNodes(xpath);
			for(Node node: nodeList){
				String s = getText(node);
				if(StringUtils.isNotBlank(s) || !ignoreEmpty) {
                    result.add(s);
                }
			}
		}catch(Exception e){
			DB_LOGGER.error(document.getText() + LoggerUtils.getExceptionTrace(e, 100));
		}
		return result;
	}
	private static String getText(Node node){
		if(node instanceof Attribute){
			return ((Attribute)node).getValue();
		}else{
			return node.getText();
		}
	}
	public static Map<String, Object> xml2Map(String infoXML) {
		Document document;
		Map<String, Object> map = Maps.newHashMap();
		try {
			document = DocumentHelper.parseText(infoXML);
			Element root = document.getRootElement();
			Iterator it = root.elements().iterator();
			while (it.hasNext()) {
				Element info = (Element) it.next();
				map.put(info.getName(), info.getText());
				Iterator itc = info.elements().iterator();
				while (itc.hasNext()) {
					Element infoc = (Element) itc.next();
					map.put(infoc.getName(), infoc.getText());
				}
			}
		} catch (DocumentException e1) {
			DB_LOGGER.warn(e1, 20);
		}
		return map;
	}
//	public static void main(String[] args){
//		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
//				"<bookstore>" +
//				"<book>" +
//				"<title lang=\"eng\">Harry Potter</title>" +
//				"<price> 29.99 </price>" +
//				"</book>" +
//				"</bookstore>";
//
//		xml = "<notify><trade_status>TRADE_FINISHED</trade_status><total_fee>0.90</total_fee><subject>123456</subject><out_trade_no>1118060201-7555</out_trade_no><notify_reg_time>2010-11-18 14:02:43.000</notify_reg_time><trade_no>2010111800209965</trade_no></notify>";
//		//DB_LOGGER.warn(getNodeText(document, "/bookstore/book[1]"));
//		//DB_LOGGER.warn(getNodeText(document, "/bookstore/book[1]/price"));
//		//DB_LOGGER.warn(getNodeText(document, "/bookstore/book[2]/title/@lang"));
//		//DB_LOGGER.warn(getNodeText(document, "/bookstore/book[2]/description"));
//		//DB_LOGGER.warn("xx"+getNodeText(document, "/bookstore/book[1]/price22"));
//		//printNode(document, "//*");//所有Element节点
//		//printNode(document, "//node()");//所有节点
//		//printNode(document, "//title | //price");//所有节点
//		//DB_LOGGER.warngetNodeTextList(document, "/bookstore/book/price", true));
//		Map<String, Object> map = xml2Map(xml);
//		System.out.println(map);
//	}

}
