package com.weimob.mengdian.soa.kafka.model.exception;

/**
 * @Author chenwp
 * @Date 2017-06-24 12:35
 * @Company WeiMob
 * @Description
 */
public class KafkaConfigSettingException extends RuntimeException {
    private static final long serialVersionUID = 2516635798953460137L;

    public KafkaConfigSettingException(String message) {
        super(message);
    }

}
