package com.weimob.mengdian.soa.kafka.annotation;

import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaCommitTypeEnum;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaFailStrategyEnum;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaManualCommitFailStrategyEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author chenwp
 * @Date 2017-06-20 21:49
 * @Company WeiMob
 * @Description
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface KafkaConsumed {

    /**
     * kafka broker 地址
     */
    String bootstrapServers() default "";

    /**
     * 消费主题
     */
    String topic() default "";

    /**
     * topic对应的消费组数
     * 当配置大于1时，每个分组都会获取一份完整的消息，
     * 每个分组通过对消息的时间戳取模消费指定的消息来避免重复消费
     */
    int groupNumber() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * 消费组名,当groupNo值大于1时，groupId会自动生成
     * 以groupId = defaultGroup ，groupNumber = 2为例
     * 会生成 defaultGroup_0 defaultGroup_1两个消费组
     */
    String groupId() default "";


    /**
     * 每个组拉取消息的线程数量,
     * 注意:用户需要知道自己消费的topic有几个分区，该值不能大于topic的partition数量
     * 程序启动时会进行检查，检查不通过直接抛异常
     */
    int pollerNumber() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * 每个poller消费的线程数量
     */
    int consumerNumber() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * 每个消费线程处理的步长
     */
    int consumerStep() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * 每个poller拉取的消息数量,
     * 注意:该值是kafka 0.10的新特性,0.9系列无法生效
     */
    int maxPollRecords() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * 当broker没有消息时poll等待的时间 单位ms
     */
    int readTimeMs() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * 重试次数，如果配置，当执行消费方法失败时，消费者会等待retryIntervalMs毫秒后
     * 进行重试。
     */
    int retryCount() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * 重试间隔 单位ms
     */
    int retryIntervalMs() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * 调用onFail方法失败后，程序重试的间隔时间 单位ms
     */
    int callOnFailExceptionRetryIntervalMs() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * 消费方法失败并且重试次数用完会执行用户自定义的onFail方法
     * 该设置的应用场景是onFail方法失败后如何处理
     * 失败策略
     * retry 无限重试 重试间隔为callOnFailExceptionRetryIntervalMs设置的值
     * discard 丢弃 offset继续提交
     */
    KafkaFailStrategyEnum failStrategy() default KafkaFailStrategyEnum.NOP;

    /**
     * zookeeper会话超时时间,取值范围在server.properties设置 默认范围6000-30000之间
     */
    int sessionTimeoutMs() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * zk心跳间隔
     */
    int heartbeatIntervalMs() default KafkaConstant.NUMBER_DEFAULT;

    /**
     * 请求的超时时间 必须大于sessionTimeoutMs
     */
    int requestTimeoutMs() default KafkaConstant.NUMBER_DEFAULT;


    String keyDeserializer() default "";

    String valueDeserializer() default "";

    /**
     * kafka 服务异常时拉取线程是否立即退出
     * 如果设置为true则拉取线程停止工作，如果pollerNo个线程都停止工作，该topic将没有消费者
     * 如果设置为false则之记录错误日志并继续拉取消息
     */
    boolean immediatelyExitWhenError() default false;

    /**
     * 是否自动提交offset
     */
    boolean autoCommit() default true;

    /**
     * 当手动提交发生异常时采取的策略
     */
    KafkaManualCommitFailStrategyEnum manualCommitFailStrategy() default KafkaManualCommitFailStrategyEnum.THROW_EXCEPTION;


    /**
     * 提交方式 默认同步提交
     */
    KafkaCommitTypeEnum commitType() default KafkaCommitTypeEnum.NOP;

}
