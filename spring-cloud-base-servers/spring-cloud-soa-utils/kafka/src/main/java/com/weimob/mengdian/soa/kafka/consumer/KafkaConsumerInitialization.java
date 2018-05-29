package com.weimob.mengdian.soa.kafka.consumer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.weimob.mengdian.soa.kafka.annotation.KafkaConsumed;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaDefaultConsumerConfig;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaConsumerConfigEnum;
import com.weimob.mengdian.soa.kafka.model.exception.KafkaConfigSettingException;
import com.weimob.mengdian.soa.kafka.utils.KafkaConfigUtils;
import com.weimob.mengdian.soa.kafka.utils.StopWatch;
import com.weimob.mengdian.soa.utils.general.StreamUtils;
import com.weimob.mengdian.soa.utils.spring.BeanDefinitionUtils;
import com.weimob.mengdian.soa.utils.spring.PropertiesUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ApplicationContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @Author chenwp
 * @Date 2017-06-21 10:27
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class KafkaConsumerInitialization extends PropertiesUtils implements InitializingBean {
    @Autowired
    private ApplicationContext applicationContext;

    @Setter
    @Getter
    private Properties kafkaConsumerConfig;

    private Map<String, Consumer> consumerImplMap;

    private KafkaDefaultConsumerConfig kafkaDefaultConsumerConfig;

    @Override
    public void afterPropertiesSet() {
        System.out.println("# Kafka Consumer  Initializing ...");

        StopWatch kafkaConsumer = new StopWatch();
        // 读取配置模版
        try {
            String xmlBody = StreamUtils.inputStreamToString(
                    getClass().getResource(KafkaConstant.KAFKA_INIT_CONSUMER_XML_TEMPLATE_PATH).openStream()
            );
            xmlBody = getPropertyValue(xmlBody);
            BeanDefinitionUtils.loadXmlByInputStream(applicationContext, new ByteArrayInputStream(xmlBody.getBytes()));
            kafkaDefaultConsumerConfig = applicationContext.getBean(KafkaDefaultConsumerConfig.class);
        } catch (IOException e) {
            log.error("read kafka consumer xml template error", e);
        }


        consumerImplMap = applicationContext.getBeansOfType(Consumer.class);
        /*
         * 检查pollNo配置 不能大于topic的partition个数
         */
        checkConfig();
        /*
         * 将kafka消费者注册到容器
         */
        register();
        /*
         * 唤醒消费者 开始消费
         */
        wakeUpConsumeManager();
        System.out.println("# Kafka Consumer Initialize Cost Time :" + kafkaConsumer.elapsedTime());

        System.out.println("# Kafka Consumer Initialize Done.");
    }


    /**
     * 检查pollNo配置 不能大于topic的partition个数
     */
    private void checkConfig() {
        if (MapUtils.isEmpty(consumerImplMap))
            return;

        //构造kafka服务对应消费者信息 Map 因为构造Producer<String, String> kafkaProducer 非常耗时
        Map<Properties, List<Consumer>> kafkaServerMap = Maps.newHashMap();

        for (Map.Entry<String, Consumer> consumerEntry : consumerImplMap.entrySet()) {
            Consumer consumer = consumerEntry.getValue();
            KafkaConsumed annotation = consumer.getClass().getAnnotation(KafkaConsumed.class);
            if (annotation == null) {
                throw new KafkaConfigSettingException(consumer.getClass().getName() + "expect a @KafkaConsumed annotation");
            }
            Properties configProperties = new Properties();
            String keyDeserializer = (String) KafkaConfigUtils.getKafkaConsumerConfig(KafkaConsumerConfigEnum.KAFKA_KEY_DESERIALIZER, annotation, kafkaConsumerConfig, kafkaDefaultConsumerConfig);
            if (StringUtils.isBlank(keyDeserializer)) {
                throw new KafkaConfigSettingException(consumer.getClass().getName() + "expect a keyDeserializer setting");
            }

            String keySerializer = keyDeserializer.replace("Des", "S");
            String valueDeserializer = (String) KafkaConfigUtils.getKafkaConsumerConfig(KafkaConsumerConfigEnum.KAFKA_VALUE_DESERIALIZER, annotation, kafkaConsumerConfig, kafkaDefaultConsumerConfig);
            if (StringUtils.isBlank(valueDeserializer)) {
                throw new KafkaConfigSettingException(consumer.getClass().getName() + "expect a valueDeserializer setting");
            }

            String valueSerializer = valueDeserializer.replace("Des", "S");
            configProperties.put(KafkaConstant.BOOTSTRAP_SERVERS, KafkaConfigUtils.getKafkaConsumerConfig(KafkaConsumerConfigEnum.KAFKA_BOOTSTRAP_SERVERS, annotation, kafkaConsumerConfig, kafkaDefaultConsumerConfig));
            configProperties.put(KafkaConstant.KEY_SERIALIZER, keySerializer);
            configProperties.put(KafkaConstant.VALUE_SERIALIZER, valueSerializer);

            List<Consumer> kafkaConsumers = kafkaServerMap.get(configProperties);

            if (CollectionUtils.isEmpty(kafkaConsumers)) {
                kafkaServerMap.put(configProperties, Lists.newArrayList(consumer));
            } else {
                kafkaConsumers.add(consumer);
            }
        }

        for (Map.Entry<Properties, List<Consumer>> propertiesListEntry : kafkaServerMap.entrySet()) {
            Properties properties = propertiesListEntry.getKey();

            List<Consumer> kafkaConsumers = propertiesListEntry.getValue();
            //检查topic是否存在
            Producer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

            for (Consumer consumer : kafkaConsumers) {
                KafkaConsumed annotation = consumer.getClass().getAnnotation(KafkaConsumed.class);

                String topic = (String) KafkaConfigUtils.getKafkaConsumerConfig(KafkaConsumerConfigEnum.KAFKA_CONSUMER_TOPIC, annotation, kafkaConsumerConfig, kafkaDefaultConsumerConfig);
                List<PartitionInfo> partitionList = kafkaProducer.partitionsFor(topic);
                if (CollectionUtils.isEmpty(partitionList)) {
                    throw new KafkaConfigSettingException(consumer.getClass().getName() + "topic :" + topic + "not exists");
                }
                //检查拉取的线程数是否大于topic的分区数量
                int partitionSize = partitionList.size();
                if (annotation.pollerNumber() > partitionSize) {
                    throw new KafkaConfigSettingException(consumer.getClass().getName() + "pollNo config out of range,"
                            + "should not bigger than " + partitionSize);
                }

                //检查设置为手动提交 消费的线程数是否大于1
                boolean autoCommit = (boolean) KafkaConfigUtils.getKafkaConsumerConfig(KafkaConsumerConfigEnum.KAFKA_AUTO_COMMIT, annotation, kafkaConsumerConfig, kafkaDefaultConsumerConfig);
                int consumerNumber = (int) KafkaConfigUtils.getKafkaConsumerConfig(KafkaConsumerConfigEnum.KAFKA_CONSUMER_NUMBER, annotation, kafkaConsumerConfig, kafkaDefaultConsumerConfig);

                if (!autoCommit && consumerNumber > 1) {
                    throw new KafkaConfigSettingException(consumer.getClass().getName() + "multiple consumer threads not support " +
                            "commit offset customize,you can set your consumerNumber = 1 in @KafkaConsumed " +
                            "if you need commit offset yourself");
                }
            }
        }
    }


    /**
     * 注册消费者到容器
     */
    private void register() {
        if (MapUtils.isEmpty(consumerImplMap))
            return;

        for (Map.Entry<String, Consumer> consumerEntry : consumerImplMap.entrySet()) {
            Consumer consumer = consumerEntry.getValue();
            KafkaConsumed kafkaConsumed = consumer.getClass().getAnnotation(KafkaConsumed.class);
            KafkaConsumerManager kafkaConsumerManager = new KafkaConsumerManager(consumer, kafkaConsumerConfig, kafkaDefaultConsumerConfig);
            String topic = (String) KafkaConfigUtils.getKafkaConsumerConfig(KafkaConsumerConfigEnum.KAFKA_CONSUMER_TOPIC, kafkaConsumed, kafkaConsumerConfig, kafkaDefaultConsumerConfig);
            String groupId = (String) KafkaConfigUtils.getKafkaConsumerConfig(KafkaConsumerConfigEnum.KAFKA_GROUP_ID, kafkaConsumed, kafkaConsumerConfig, kafkaDefaultConsumerConfig);
            String beanName = "kafkaConsumerTopic_" + topic + "_" + groupId;
            if (applicationContext.containsBean(beanName)) {
                continue;
            }
            registerSingletonBean(beanName, kafkaConsumerManager);
            System.out.println("Kafka Consumer > 【" + consumer.getClass().getSimpleName() + "】 Registered ");
        }
    }

    private void registerSingletonBean(String beanName, Object bean) {
        if (bean == null) {
            return;
        }
        SingletonBeanRegistry registry = (SingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory();
        registry.registerSingleton(beanName, bean);
    }

    /**
     * 唤醒消费者 开始消费
     */
    private void wakeUpConsumeManager() {
        Map<String, KafkaConsumerManager> kafkaConsumerManagerMap = applicationContext.getBeansOfType(KafkaConsumerManager.class);
        for (Map.Entry<String, KafkaConsumerManager> kafkaConsumerManagerEntry : kafkaConsumerManagerMap.entrySet()) {
            kafkaConsumerManagerEntry.getValue().wakeupGroupWorker();
        }
    }

}


