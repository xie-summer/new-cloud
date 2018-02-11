package com.cloud.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author summer
 */
public class TDateSerializer extends JsonSerializer<Date> {
	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		SimpleDateFormat formatter = null;
		if (value instanceof Timestamp){
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		}
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
	}
}
