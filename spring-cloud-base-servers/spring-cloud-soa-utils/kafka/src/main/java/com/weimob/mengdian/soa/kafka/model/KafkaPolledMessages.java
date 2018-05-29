package com.weimob.mengdian.soa.kafka.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @Author chenwp
 * @Date 2017-06-22 20:32
 * @Company WeiMob
 * @Description 拉取的消息集合封装类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class KafkaPolledMessages<T> implements Serializable{
    private static final long serialVersionUID = 2596816870079605604L;
    private List<KafkaConsumerMessage<T>> message;
}
