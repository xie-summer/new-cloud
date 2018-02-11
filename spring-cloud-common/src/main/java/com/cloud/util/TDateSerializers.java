package com.cloud.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.TimestampDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * @author summer
 */
public class TDateSerializers {
	public static final GewaDateDeserializer DATE_DESERIALIZER = new GewaDateDeserializer();
	public static final GewaTimestampDeserializer TIMESTAMP_DESERIALIZER = new GewaTimestampDeserializer();
	public static final GewaSqlDateDeserializer SQL_DATE_DESERIALIZER = new GewaSqlDateDeserializer();
	
	public static class GewaDateDeserializer extends DateDeserializer {
		private static final long serialVersionUID = -8049402816880830231L;

		@Override
		protected java.util.Date _parseDate(JsonParser jp, DeserializationContext ctxt) 
				throws IOException, JsonProcessingException {
			JsonToken t = jp.getCurrentToken();
			try {
				if (t == JsonToken.VALUE_NUMBER_INT) {
					return new java.util.Date(jp.getLongValue());
				}
				if (t == JsonToken.VALUE_STRING) {
					/*
					 * As per [JACKSON-203], take empty Strings to mean null
					 */
					String str = jp.getText().trim();
					if (str.length() == 0) {
						return null;
					}
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					return formatter.parse(str);
				}
				throw ctxt.mappingException(_valueClass);
			} catch (ParseException e) {
				throw ctxt.mappingException(_valueClass);
			} catch (IllegalArgumentException iae) {
				throw ctxt.weirdStringException(null, _valueClass,
						"not a valid representation (error: " + iae.getMessage()
								+ ")");
			}
		}
	}
	public static class GewaTimestampDeserializer extends TimestampDeserializer {
		private static final long serialVersionUID = -8049402816880830231L;

		@Override
		protected java.util.Date _parseDate(JsonParser jp, DeserializationContext ctxt) 
				throws IOException, JsonProcessingException {
			JsonToken t = jp.getCurrentToken();
			try {
				if (t == JsonToken.VALUE_NUMBER_INT) {
					return new java.util.Date(jp.getLongValue());
				}
				if (t == JsonToken.VALUE_STRING) {
					/*
					 * As per [JACKSON-203], take empty Strings to mean null
					 */
					String str = jp.getText().trim();
					if (str.length() == 0) {
						return null;
					}
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					return formatter.parse(str);
				}
				throw ctxt.mappingException(_valueClass);
			} catch (ParseException e) {
				throw ctxt.mappingException(_valueClass);
			} catch (IllegalArgumentException iae) {
				throw ctxt.weirdStringException(null, _valueClass,
						"not a valid representation (error: " + iae.getMessage()
								+ ")");
			}
		}
	}
	public static class GewaSqlDateDeserializer extends SqlDateDeserializer {
		private static final long serialVersionUID = -8049402816880830231L;

		@Override
		protected java.sql.Date _parseDate(JsonParser jp, DeserializationContext ctxt) 
				throws IOException, JsonProcessingException {
			JsonToken t = jp.getCurrentToken();
			try {
				if (t == JsonToken.VALUE_NUMBER_INT) {
					return new java.sql.Date(jp.getLongValue());
				}
				if (t == JsonToken.VALUE_STRING) {
					/*
					 * As per [JACKSON-203], take empty Strings to mean null
					 */
					String str = jp.getText().trim();
					if (str.length() == 0) {
						return null;
					}
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					return new java.sql.Date(formatter.parse(str).getTime());
				}
				throw ctxt.mappingException(_valueClass);
			} catch (ParseException e) {
				throw ctxt.mappingException(_valueClass);
			} catch (IllegalArgumentException iae) {
				throw ctxt.weirdStringException(null, _valueClass,
						"not a valid representation (error: " + iae.getMessage()
								+ ")");
			}
		}
	}
}
