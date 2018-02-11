package com.cloud.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
/**
 * @author summer
 */
public class JsonViewer {
	public static final JsonViewer instance = new JsonViewer();
	private JsonViewer(){}
	public static String getHtml(Object obj){
		StringWriter writer = new StringWriter();
		try {
			outputObject(obj, writer);
		} catch (IOException e) {
		}
		return writer.toString();
	}
	public static void outputObject(Object obj, Writer writer) throws IOException{
		if(obj==null) {
			writer.write("null");
		}else	if(BeanUtil.isSimpleValueType(obj.getClass())){
			writer.write(obj.toString());
		}else if(obj instanceof Map){
			outputMap((Map) obj, writer);
		}else if(obj instanceof Collection){
			outputCollection((Collection) obj, writer);
		}else{
			writer.write(obj.toString());
		}
	}
	private static void outputMap(Map<String, ?> map, Writer sb) throws IOException{
		sb.write("<table class=\"table\">");
		for(String key: map.keySet()){
			sb.write("<tr><td>");
			sb.write(key);
			sb.write("</td><td>");
			outputObject(map.get(key), sb);
			sb.write("</td></tr>");
		}
		sb.write("</table>");
	}
	private static void outputCollection(Collection rowList, Writer sb) throws IOException{
		Set<String> columns = new LinkedHashSet<String>();
		for(Object row: rowList){
			if(row instanceof Map) {
				columns.addAll(((Map)row).keySet());
			}
		}
		if(columns.size() >0){//Map���
			sb.write("<table class=\"table\"><tr>");
			for(Object column: columns){
				sb.write("<td>" + column + "</td>");
			}
			sb.write("</tr>");
			for(Object row: rowList){
				sb.write("<tr>");
				for(Object column: columns){
					sb.write("<td>");
					outputObject(((Map)row).get(column), sb);
					sb.write("</td>");
				}
				sb.write("</tr>");
			}
			sb.write("</table>");
		}else{//������
			sb.write("<table class=\"table\">");
			for(Object row: rowList){
				sb.write("<tr>");
				sb.write("<td>");
				outputObject(row, sb);
				sb.write("</td>");
				sb.write("</tr>");
			}
			sb.write("</table>");
		}
	}
}
