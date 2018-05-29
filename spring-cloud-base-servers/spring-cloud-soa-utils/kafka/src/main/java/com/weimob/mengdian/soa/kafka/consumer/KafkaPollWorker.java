package com.weimob.mengdian.soa.kafka.consumer;

import com.google.common.collect.Lists;
import com.weimob.mengdian.soa.cat.CatTransaction;
import com.weimob.mengdian.soa.kafka.KafkaContextHolder;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaConsumerConfig;
import com.weimob.mengdian.soa.kafka.consumer.service.AbstractConsumeService;
import com.weimob.mengdian.soa.kafka.consumer.service.MultiThreadAutoCommitService;
import com.weimob.mengdian.soa.kafka.consumer.service.SingleThreadAutoCommitService;
import com.weimob.mengdian.soa.kafka.consumer.service.SingleThreadManualCommitService;
import com.weimob.mengdian.soa.kafka.model.KafkaSendMessage;
import com.weimob.mengdian.soa.kafka.utils.KafkaUtils;
import com.weimob.mengdian.soa.utils.encrypt.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * 轮训从kafka 拉取消息线程
 *
 * @Author chenwp
 * @Date 2017-06-21 16:01
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class KafkaPollWorker implements Runnable {

    private KafkaConsumerConfig kafkaConsumerConfig;
    private KafkaConsumer<String, String> consumerClient;
    private Consumer consumerImpl;
    private AbstractConsumeService abstractConsumeService;


    /**
     * 所有poller线程都结束的倒数锁
     */
    private final CountDownLatch stopWork;

    /**
     * 当消息的timestamp % groupNumber = modelValue时获取该条消息
     */
    private final Integer modelValue;
    private final String topic;

    /*kafka 服务异常时是否立即退出*/
    private boolean immediatelyExitWhenError;

    /**
     * 睡一段时间再拉取
     */
    private Integer sleepTime = KafkaConstant.KAFKA_DEFAULT_INTERVAL_MS;


    public KafkaPollWorker(KafkaConsumerConfig kafkaConsumerConfig, Consumer consumerImpl, int modelValue, CountDownLatch stopWork) {
        this.consumerImpl = consumerImpl;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        this.modelValue = modelValue;
        this.topic = kafkaConsumerConfig.getTopic();
        this.stopWork = stopWork;
        this.immediatelyExitWhenError = kafkaConsumerConfig.getImmediatelyExitWhenError();

        this.consumerClient = createConsumerClient();
        this.consumerClient.subscribe(Arrays.asList(this.topic));

        createConsumeService();
    }

    private void createConsumeService() {
        boolean autoCommit = this.kafkaConsumerConfig.getAutoCommit();
        int consumerNumber = this.kafkaConsumerConfig.getConsumerNumber();

        if (autoCommit && consumerNumber == 1) {
            this.abstractConsumeService = new SingleThreadAutoCommitService();
        } else if (autoCommit && consumerNumber > 1) {
            this.abstractConsumeService = new MultiThreadAutoCommitService();
        } else {
            this.abstractConsumeService = new SingleThreadManualCommitService();
        }
    }

    private KafkaConsumer<String, String> createConsumerClient() {
        //分组个数
        int groupNumber = this.kafkaConsumerConfig.getGroupNumber();
        String groupId = this.kafkaConsumerConfig.getGroupId();
        Properties props = new Properties();
        props.put(KafkaConstant.BOOTSTRAP_SERVERS, this.kafkaConsumerConfig.getBootstrapServers());

        //多个分组
        if (groupNumber > 1) {
            props.put(KafkaConstant.GROUP_ID, groupId + "_" + this.modelValue);
        } else {
            props.put(KafkaConstant.GROUP_ID, groupId);
        }

        //自动提交设为false
        props.put(KafkaConstant.ENABLE_AUTO_COMMIT, "false");
        props.put(KafkaConstant.MAX_POLL_RECORDS, this.kafkaConsumerConfig.getMaxPollRecords());
        props.put(KafkaConstant.SESSION_TIMEOUT_MS, this.kafkaConsumerConfig.getSessionTimeoutMs());
        props.put(KafkaConstant.HEARTBEAT_INTERVAL_MS, this.kafkaConsumerConfig.getHeartbeatIntervalMs());
        props.put(KafkaConstant.REQUEST_TIMEOUT_MS, this.kafkaConsumerConfig.getRequestTimeoutMs());
        props.put(KafkaConstant.KEY_DESERIALIZER, this.kafkaConsumerConfig.getKeyDeserializer());
        props.put(KafkaConstant.VALUE_DESERIALIZER, this.kafkaConsumerConfig.getValueDeserializer());

        System.out.println("创建消费者属性:"+JsonUtils.writeValueAsString(this.kafkaConsumerConfig));
        return new KafkaConsumer<>(props);
    }


    @Override
    public void run() {
        while (true) {
            try {
                //记录拉取时间
                long pollingTime = System.currentTimeMillis();


                //拉取消息
                ConsumerRecords<String, String> records = polling();

                //挑选属于自己的消息
                List<ConsumerRecord<String, String>> ownConsumerRecords = pickMessages(records);

                //消费
                abstractConsumeService.consume(ownConsumerRecords, this.kafkaConsumerConfig, pollingTime, consumerClient, this.consumerImpl);
                Thread.sleep(this.sleepTime);
            } catch (Exception e) {
                // kafka异常立即退出
                log.error("kafka topic: + " + topic + " consume message error :", e);
                if (immediatelyExitWhenError) {
                    consumerClient.close();
                    break;
                }
            }
        }
        if (immediatelyExitWhenError) {
            stopWork.countDown();
        }
    }

    /**
     * 取消kafka消息
     *
     * @return
     */
    private ConsumerRecords<String, String> polling() {
        val catKafkaConsumerPoller = new CatTransaction(KafkaConstant.KAFKA_CONSUMER_POLLER, topic);
        try {
            int readTime = this.kafkaConsumerConfig.getReadTimeMs();
            ConsumerRecords<String, String> records = consumerClient.poll(readTime);
            catKafkaConsumerPoller.success();

            return records;
        } catch (Exception e) {
            catKafkaConsumerPoller.error(e);
            throw e;
        }
    }

    /**
     * 挑选属于自己的消息
     * 规则: 时间戳 % 分组数 = 组序列
     */
    private List<ConsumerRecord<String, String>> pickMessages(ConsumerRecords<String, String> records) throws Exception {

        List<ConsumerRecord<String, String>> ownConsumerRecords = Lists.newArrayList();
        int groupNumber = this.kafkaConsumerConfig.getGroupNumber();
        for (ConsumerRecord<String, String> record : records) {
            String value = record.value();
            KafkaSendMessage kafkaSendMessage = JsonUtils.readValue(value, KafkaSendMessage.class);


            //当消息的timestamp模groupNo等于指定值时，认为消息属于自己
            //外部消息不适用分组规则(上游已经将分组数设为1) 直接给
            if (KafkaUtils.isExtendMessage(kafkaSendMessage) || kafkaSendMessage.getTimeStamp() % groupNumber == modelValue) {
                ownConsumerRecords.add(record);
            }
        }
        return ownConsumerRecords;
    }


}
