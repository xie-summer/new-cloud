package com.weimob.mengdian.soa.kafka.model.exception;

/**
 * @Author chenwp
 * @Date 2017-06-24 12:50
 * @Company WeiMob
 * @Description
 */
public class KafkaCallbackException extends RuntimeException {
    private static final long serialVersionUID = 8387494424622637921L;

    public KafkaCallbackException(String message) {
        super(message);
    }
}
