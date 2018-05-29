package com.weimob.mengdian.soa.kafka.test.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author chenwp
 * @Date 2017-06-24 18:27
 * @Company WeiMob
 * @Description
 */
@Data
public class KafkaConsumerResponse implements Serializable {
    private static final long serialVersionUID = 4613834679689363447L;

    private Long id;
    private String name;
    private Integer age;
}
