package com.weimob.mengdian.soa.kafka.model.exception;

/**
 * @Author chenwp
 * @Date 2017-07-07 15:21
 * @Company WeiMob
 * @Description
 */
public class KafkaCommitException extends RuntimeException {
    private static final long serialVersionUID = -4924344580751280096L;

    public KafkaCommitException(String message) {
        super(message);
    }

    public KafkaCommitException(String message, Throwable cause) {
        super(message, cause);
    }
}
