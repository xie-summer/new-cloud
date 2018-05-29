package com.weimob.mengdian.soa.kafka.test.model;

import com.weimob.mengdian.soa.kafka.Compatible;
import lombok.Data;

/**
 * @Author chenwp
 * @Date 2017-06-24 13:49
 * @Company WeiMob
 * @Description
 */
@Data
public class KafkaProducerMessage implements Compatible {
    private Long id;
    private String name;
    private Integer age;
    private Long commitTime;
}
