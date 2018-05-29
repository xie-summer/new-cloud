package com.weimob.mengdian.soa.kafka.annotation;

import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;

import java.lang.annotation.*;

/**
 * @Author chenwp
 * @Date 2017-06-20 9:49
 * @Company WeiMob
 * @Description
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KafkaProduced {

    /**
     * kafka broker 地址
     */
    String bootstrapServers() default "";

    /**
     * producer需要server接收到数据之后发出的确认接收的信号
     * （1）acks=0： 设置为0表示producer不需要等待任何确认收到的信息。副本将立即加到socket  buffer并认为已经发送。
     * 没有任何保障可以保证此种情况下server已经成功接收数据，同时重试配置不会发生作用（因为客户端不知道是否失败）
     * 回馈的offset会总是设置为-1
     * <p>
     * （2）acks=1： 这意味着至少要等待leader已经成功将数据写入本地log，但是并没有等待所有follower是否成功写入。
     * 这种情况下，如果follower没有成功备份数据，而此时leader又挂掉，则消息会丢失。
     * <p>
     * （3）acks=all： 这意味着leader需要等待所有备份都成功写入日志，这种策略会保证只要有一个备份存活就不会丢失数据。
     * 这是最强的保证。
     */
    String acks() default "";

    /**
     * 设置大于0的值将使客户端在发送失败的情况下重新发送数据。
     * 允许重试将潜在的改变数据的顺序，如果这两个消息记录都是发送到同一个partition，则第一个消息失败第二个发送成功，
     * 则第二条消息会比第一条消息出现要早
     */
    int retries() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * producer将试图批处理消息记录，以减少请求次数。这将改善client与server之间的性能。这项配置控制默认的批量处理消息字节数。
     * 不会试图处理大于这个字节数的消息字节数。
     */
    int batchSize() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * producer组将会汇总任何在请求与发送之间到达的消息记录一个单独批量的请求。
     * 通常来说，这只有在记录产生速度大于发送速度的时候才能发生。
     * 单位 ms
     */
    int lingerMs() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * producer可以用来缓存数据的内存大小。如果数据产生速度大于向broker发送的速度，producer会阻塞或者抛出异常，
     * 以“block.on.buffer.full”来表明。
     */
    long bufferMemory() default KafkaConstant.NUMBER_DEFAULT;

    String keySerializer() default "";

    String valueSerializer() default "";
}
