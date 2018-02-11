package com.cloud.util;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author summer
 */
public class TJsonModule extends SimpleModule {
    private static final long serialVersionUID = 4093223126017721944L;
    public static TJsonModule TAOTAO_MODULE = new TJsonModule();

    private TJsonModule() {
        addDeserializer(Timestamp.class, TDateSerializers.TIMESTAMP_DESERIALIZER);
        addDeserializer(Date.class, TDateSerializers.DATE_DESERIALIZER);
        addDeserializer(java.sql.Date.class, TDateSerializers.SQL_DATE_DESERIALIZER);
        addSerializer(Timestamp.class, new TDateSerializer());
        addSerializer(Date.class, new TDateSerializer());
        addSerializer(java.sql.Date.class, new TDateSerializer());
    }

    public static class UpperCasePropertyNamingStrategy extends PropertyNamingStrategyBase {

        private static final long serialVersionUID = -6510034237130928673L;

        @Override
        /**
         * this method call back by PropertyNamingStrategy
         */
        public String translate(String propertyName) {
            if ("objectName".equals(propertyName)) {
                return propertyName;
            }
            String name = propertyName.replaceAll("^\\w", propertyName.toUpperCase().substring(0, 1));

            return name;
        }

    }
}
