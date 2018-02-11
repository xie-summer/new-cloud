package com.cloud.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.cloud.util.TJsonModule.UpperCasePropertyNamingStrategy;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author summer
 */
public class JsonUtils {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(JsonUtils.class);
	public static UpperCasePropertyNamingStrategy UPPER_CASE_PROPERTY_NAMING_STRATEGY = new UpperCasePropertyNamingStrategy();
	public static <T> T readJsonToObject(Class<T> clazz, String json) {
		if (StringUtils.isBlank(json)){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.registerModule(TJsonModule.TAOTAO_MODULE);
		
		try {
			T result = mapper.readValue(json, clazz);
			return result;
		} catch (Exception e) {
			DB_LOGGER.error(StringUtils.substring(json, 0, 500), e, 15);
		}
		return null;
	}

	public static <T> T readJsonToObject(TypeReference<T> type, String json){
		if (StringUtils.isBlank(json)){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(TJsonModule.TAOTAO_MODULE);
		try {
			T result = mapper.readValue(json, type);
			return result;
		} catch (Exception e) {
			DB_LOGGER.error("json:" + StringUtils.substring(json, 0, 500) + "\n" + LoggerUtils.getExceptionTrace(e, 15));
		}
		return null;
	}
	public static <T> List<T> readJsonToObjectList(Class<T> clazz, String json) {
		if (StringUtils.isBlank(json)){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(TJsonModule.TAOTAO_MODULE);

		try {
			CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
			List<T> result = mapper.readValue(json, type);
			return result;
		} catch (Exception e) {
			DB_LOGGER.error("json:" + StringUtils.substring(json, 0, 500) + "\n" + LoggerUtils.getExceptionTrace(e, 15));
		}
		return null;
	}

	public static Map readJsonToMap(String json) {
		if (StringUtils.isBlank(json)){
			return Maps.newHashMap();
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(TJsonModule.TAOTAO_MODULE);
		try {
			Map result = mapper.readValue(json, Map.class);
			if (result == null) {
                result = Maps.newHashMap();
            }
			return result;
		} catch (Exception e) {
			DB_LOGGER.error("json:" + StringUtils.substring(json, 0, 500) + "\n" + LoggerUtils.getExceptionTrace(e, 15));
			return Maps.newHashMap();
		}

	}

	/**
	 * @param clazz
	 * @param json
	 * @param pns
	 * @param <T>
	 * @return
	 */
	//-----------------------------------------------------------------
	public static <T> T readJsonToObject(Class<T> clazz, String json, PropertyNamingStrategy pns) {
		if (StringUtils.isBlank(json)){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.registerModule(TJsonModule.TAOTAO_MODULE);
		if(pns!=null){
			mapper.setPropertyNamingStrategy(pns);
		}
		try {
			T result = mapper.readValue(json, clazz);
			return result;
		} catch (Exception e) {
			DB_LOGGER.error(StringUtils.substring(json, 0, 500), e, 15);
		}
		return null;
	}
	public static <T> T readJsonToObject(TypeReference<T> type, String json, PropertyNamingStrategy pns){
		if (StringUtils.isBlank(json)){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(TJsonModule.TAOTAO_MODULE);
		if(pns!=null){
			mapper.setPropertyNamingStrategy(pns);
		}
		try {
			T result = mapper.readValue(json, type);
			return result;
		} catch (Exception e) {
			DB_LOGGER.error("json:" + StringUtils.substring(json, 0, 500) + "\n" + LoggerUtils.getExceptionTrace(e, 15));
		}
		return null;
	}
	public static <T> List<T> readJsonToObjectList(Class<T> clazz, String json, PropertyNamingStrategy pns) {
		if (StringUtils.isBlank(json)){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(TJsonModule.TAOTAO_MODULE);
		if(pns!=null){
			mapper.setPropertyNamingStrategy(pns);
		}

		try {
			CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
			List<T> result = mapper.readValue(json, type);
			return result;
		} catch (Exception e) {
			DB_LOGGER.error("json:" + StringUtils.substring(json, 0, 500) + "\n" + LoggerUtils.getExceptionTrace(e, 15));
		}
		return null;
	}

	public static Map readJsonToMap(String json, PropertyNamingStrategy pns) {
		if (StringUtils.isBlank(json)){
			return Maps.newHashMap();
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(TJsonModule.TAOTAO_MODULE);
		if(pns!=null){
			mapper.setPropertyNamingStrategy(pns);
		}
		try {
			Map result = mapper.readValue(json, Map.class);
			if (result == null) {
                result =Maps.newHashMap();
            }
			return result;
		} catch (Exception e) {
			DB_LOGGER.error("json:" + StringUtils.substring(json, 0, 500) + "\n" + LoggerUtils.getExceptionTrace(e, 15));
			return Maps.newHashMap();
		}
	}
	
	public static String writeObjectToJson(Object object) {
		return writeObjectToJson(object, false);
	}
	public static void writeObjectToStream(OutputStream os, Object object, boolean excludeNull) {
		writeObject(object, os, null, excludeNull);
	}
	public static void writeObjectToWriter(Writer writer, Object object, boolean excludeNull) {
		writeObject(object, null, writer, excludeNull);
	}
	public static String writeObjectToJson(Object object, boolean excludeNull) {
		return writeObject(object, null, null, excludeNull);
	}
	private static String writeObject(Object object, OutputStream os, Writer writer, boolean excludeNull) {
		if (object == null) {
            return null;
        }
		if(object instanceof Map){
			try{((Map) object).remove(null);}catch(Exception e){}
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
		//���Զ��ر���
		mapper.getFactory().disable(Feature.AUTO_CLOSE_TARGET);
		if(excludeNull) {
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}

		try {
			mapper.registerModule(TJsonModule.TAOTAO_MODULE);
			if(os!=null){
				mapper.writeValue(os, object);
			}else if(writer!=null){
				mapper.writeValue(writer, object);
			}else{
				String data = mapper.writeValueAsString(object);
				return data;
			}
		} catch (Exception e) {
			DB_LOGGER.error("object:" + object + "\n" + LoggerUtils.getExceptionTrace(e, 15));
		}
		return null;
	}
	
	public static String writeMapToJson(Map<String, String> dataMap){
		if(dataMap==null) {
            return null;
        }
		if(dataMap instanceof HashMap){
			try{dataMap.remove(null);}catch(Exception e){}
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
		try {
			String data = mapper.writeValueAsString(dataMap);
			return data;
		} catch (Exception e) {
			DB_LOGGER.error("", e);
		}
		return null;
	}

	public static String addJsonKeyValue(String json, String key, String value){
		Map info = readJsonToMap(json);
		info.put(key, value);
		return writeMapToJson(info);
	}
	public static String removeJsonKeyValue(String json, String key){
		Map info = readJsonToMap(json);
		info.remove(key);
		return writeMapToJson(info);
	}
	public static String getJsonValueByKey(String json, String key){
		Map<String, String> info = readJsonToMap(json);
		return info.get(key);
	}


}
