package com.cloud.util;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * @author summer
 */
public class HtmlParser {
	public static List<String> getNodeAttrList(String html, String nodename, String attrName) {
		List<String> result = new ArrayList<String>();
		try {
			Document doc = Jsoup.parse(html);
			Elements els = doc.select(nodename);
			Iterator<Element> it = els.iterator();
			while(it.hasNext()){
				Element el = it.next();
				String s = el.attr(attrName);
				result.add(s);
			}
		} catch (Exception e) {//ignore
		}
		return result;
	}
	/**
	 * 获取Html中的text
	 * @param html
	 * @return
	 */
	public static String getHtmlText(String html){
		if(StringUtils.isBlank(html)) {
            return html;
        }
		String html2 = html.replaceAll("&nbsp;", " ");
		html2 = html2.replaceAll("<br/>", "\n");
		html2 = html2.replaceAll("<br />", "\n");
		if(html2.indexOf('>')<0 || html2.indexOf('<')<0) {
			return html2;//统计比：1:30
		}
		String result = parseHtmlInternal(html2);
		return result;
	}

	private static String parseHtmlInternal(String html) {
		String html2 = html.replaceAll("\n", "@nn@");//为了保留换行
		try{
			Document doc = Jsoup.parse(html2);
			doc.select("head").remove();
			doc.select("script").remove();
			doc.select("style").remove();
			return StringUtils.replace(doc.text(), "@nn@","\n");
		}catch(Exception e){
		}

		return html;
	}

}
