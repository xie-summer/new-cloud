package com.cloud.util;

import java.sql.Timestamp;
import java.util.Date;
/**
 * @author summer
 */
public class ValueUtil  implements Util4Script{
	public static final ValueUtil instance = new ValueUtil();
	public Long lv(Object value){
		return longValue(value);
	}
	public Long longValue(Object value){
		if(value==null) {
            return null;
        }
		return Long.parseLong(value.toString());
	}
	public Integer iv(Object value){
		return intValue(value);
	}
	public Integer intValue(Object value){
		if(value==null) {
            return null;
        }
		return Integer.parseInt(value.toString());
	}
	
	public Double dv(Object value){
		return doubleValue(value);
	}
	public Double doubleValue(Object value){
		if(value==null) {
            return null;
        }
		return Double.parseDouble(value.toString());
	}
	
	public Timestamp tv(Object value){
		return timestampValue(value);
	}
	public Timestamp timestampValue(Object value){
		if(value==null) {
            return null;
        }
		if(value instanceof Timestamp){
			return (Timestamp) value;
		}else{
			return DateUtil.parseTimestamp(""+value);
		}
	}
	
	public Date dateValue(Object value){
		if(value==null) {
            return null;
        }
		if(value instanceof Date){
			return (Date) value;	
		}
		return DateUtil.parseDate(""+value); 
	}
	
	public String stringValue(Object value){
		if(value==null) {
            return null;
        }
		return value.toString();
	}

}
