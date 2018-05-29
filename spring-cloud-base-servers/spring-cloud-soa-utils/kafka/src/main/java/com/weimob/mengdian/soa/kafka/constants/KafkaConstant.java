package com.weimob.mengdian.soa.kafka.constants;

/**
 * @Author chenwp
 * @Date 2017-07-03 10:07
 * @Company WeiMob
 * @Description
 */
public class KafkaConstant {
    private static final String TOPIC_PARTITION_OFFSET = "%s_%d_%d";

    public static final int NUMBER_DEFAULT = -1;

    /**
     * kafka拉取时间间隔
     */
    public static final int KAFKA_DEFAULT_INTERVAL_MS = 200;

    /*******************kafka消费者常量开始*******************/
    public static final String KAFKA_INIT_CONSUMER_XML_TEMPLATE_PATH = "/META-INF/template/kafka-consumer-init.xml.tpl";
    //最大的分组个数
    public static final Integer MAX_GROUP_NO = 10;
    //每个分组下最大拉取的线程数量
    public static final Integer MAX_POLLER_NO = 10;
    //kafka消息拉取者 用于cat打点
    public static final String KAFKA_CONSUMER_POLLER = "KafkaConsumerPoller";
    //kafka消费 用于cat打点
    public static final String KAFKA_CONSUMER = "KafkaConsumer";
    //kafka消费失败时执行 用于cat打点
    public static final String KAFKA_CALL_ON_FAIL = "KafkaCallOnFail";
    //kafka服务地址
    public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    //kafka组名
    public static final String GROUP_ID = "group.id";
    //是否由kafka自动提交offset 本工强制设为false
    public static final String ENABLE_AUTO_COMMIT = "enable.auto.commit";
    //每次polling最大的记录数,需要kafka服务版本0.10.0.0及以上才支持
    public static final String MAX_POLL_RECORDS = "max.poll.records";
    //zookeeper会话超时时间
    public static final String SESSION_TIMEOUT_MS = "session.timeout.ms";
    //zk心跳通知间隔 超过该间隔不提交会触发group rebalance
    public static final String HEARTBEAT_INTERVAL_MS = "heartbeat.interval.ms";
    //请求超时时间 必须大于session.timeout.ms
    public static final String REQUEST_TIMEOUT_MS = "request.timeout.ms";
    //key反序列化方式
    public static final String KEY_DESERIALIZER = "key.deserializer";
    //key反序列化方式
    public static final String VALUE_DESERIALIZER = "value.deserializer";
    //消费全部成功
    public static final String SUCCESS = "0";
    //全部消费失败
    public static final String FAIL = "1";
    //部分消费失败
    public static final String SOME_FAIL = "2";
    /*******************kafka消费者常量结束*******************/

    /*******************kafka生产者常量开始*******************/
    //producer需要server接收到数据之后发出的确认接收的信号
    public static final String ACKS = "acks";
    //发送失败的重试次数
    public static final String RETRIES = "retries";
    //批量处理消息字节数
    public static final String BATCH_SIZE = "batch.size";
    //从请求到发送的时间间隔 该间隔内的消息将会批量发送
    public static final String LINGER_MS = "linger.ms";
    //producer可以用来缓存数据的内存大小。如果数据产生速度大于向broker发送的速度，producer会阻塞或者抛出异常
    public static final String BUFFER_MEMORY = "buffer.memory";
    //key序列化方式
    public static final String KEY_SERIALIZER = "key.serializer";
    //key序列化方式
    public static final String VALUE_SERIALIZER = "value.serializer";
    //kafka发送消息 用于cat打点
    public static final String KAFKA_PRODUCER_SEND = "KafkaProducerSend";

    public static final String KAFKA_INIT_PRODUCER_XML_TEMPLATE_PATH = "/META-INF/template/kafka-producer-init.xml.tpl";

    /*******************kafka生产者常量结束*******************/
    public static String buildTopicPartitionOffsetKey(String topic, Integer partition, Long offset) {
        return String.format(TOPIC_PARTITION_OFFSET, topic, partition, offset);
    }
}
