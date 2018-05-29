package com.weimob.mengdian.soa.kafka.model;

import lombok.*;

import java.io.Serializable;

/**
 * 生产者向kafka发送的消息体
 * @Author chenwp
 * @Date 2017-06-20 13:30
 * @Company WeiMob
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class KafkaSendMessage<T> implements Serializable {
    private static final long serialVersionUID = -2404857779797497713L;
    /**
     * 生产者ip端口号 eg: 127.0.0.1:8080
     */
    private String server;
    private Long timeStamp;

    /*
     * 生产者向kafka发送的原始信息
     */
    private T data;
}
