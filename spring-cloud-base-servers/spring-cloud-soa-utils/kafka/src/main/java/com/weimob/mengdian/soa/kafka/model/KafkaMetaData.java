package com.weimob.mengdian.soa.kafka.model;

import lombok.*;

import java.io.Serializable;

/**
 * @Author chenwp
 * @Date 2017-06-29 18:22
 * @Company WeiMob
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class KafkaMetaData implements Serializable {

    private static final long serialVersionUID = 3006944121573586703L;

    private Long offset;
    private String topic;
    private Integer partition;
}
