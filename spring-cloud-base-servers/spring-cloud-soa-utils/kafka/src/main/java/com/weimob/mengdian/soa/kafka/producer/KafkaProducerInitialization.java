package com.weimob.mengdian.soa.kafka.producer;

import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.producer.config.KafkaDefaultProducerConfig;
import com.weimob.mengdian.soa.kafka.utils.KafkaUtils;
import com.weimob.mengdian.soa.utils.general.ClassUtils;
import com.weimob.mengdian.soa.utils.general.StreamUtils;
import com.weimob.mengdian.soa.utils.spring.BeanDefinitionUtils;
import com.weimob.mengdian.soa.utils.spring.PropertiesUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author chenwp
 * @Date 2017-06-19 18:12
 * @Company WeiMob
 * @Description
 */
@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class KafkaProducerInitialization extends PropertiesUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

    @Getter
    @Setter
    private List<String> packageNames;

    @Getter
    @Setter
    private String server;

    private ApplicationContext applicationContext;

    @Getter
    @Setter
    private Properties kafkaProducerConfig;

    private KafkaProducerProxyFactory producerProxyFactory;

    private KafkaProducerScanner kafkaProducerScanner;

    private KafkaDefaultProducerConfig kafkaDefaultProducerConfig;

    private final Map<Class<?>, Object> proxy = new HashMap<>();

    private static volatile boolean isInited = false;

    private void init() {
        //已经初始化完毕
        if(isInited){
            return;
        }
        // 读取配置模版
        try {
            String xmlBody = StreamUtils.inputStreamToString(
                    getClass().getResource(KafkaConstant.KAFKA_INIT_PRODUCER_XML_TEMPLATE_PATH).openStream()
            );
            xmlBody = getPropertyValue(xmlBody);
            BeanDefinitionUtils.loadXmlByInputStream(applicationContext, new ByteArrayInputStream(xmlBody.getBytes()));
            kafkaDefaultProducerConfig = applicationContext.getBean(KafkaDefaultProducerConfig.class);
        } catch (IOException e) {
            log.error("read kafka consumer xml template error", e);
        }

        //生产者ip信息
        if (StringUtils.isEmpty(server)) {
            String senderIp = KafkaUtils.getIp();
            String senderPort = KafkaUtils.getTomcatPort();
            this.server = senderIp.concat(":").concat(senderPort);
        }

        producerProxyFactory = new KafkaProducerProxyFactory(server, kafkaProducerConfig, kafkaDefaultProducerConfig);
        kafkaProducerScanner = new KafkaProducerScanner(packageNames);
        autoProxy();

        isInited = true;
    }

    /**
     * 自动代理kafka生产者
     */
    private void autoProxy() {
        try {
            createProxy();
            register();
        } catch (Exception e) {
            log.error("auto proxy for kafka failed", e);
        }
    }

    /**
     * 为kafka生成者创建代理
     */
    private void createProxy() throws IOException, ClassNotFoundException {
        for (Class<?> producerClass : kafkaProducerScanner.scan()) {
            if (applicationContext.containsBean(ClassUtils.simpleName(producerClass.getName()))) {
                continue;
            }
            proxy.put(producerClass, producerProxyFactory.create(producerClass));
        }
    }

    /**
     * 将kafka生产者注册到容器
     */
    private void register() {
        for (Map.Entry<Class<?>, Object> entry : proxy.entrySet()) {
            registerSingletonBean(ClassUtils.simpleName(entry.getKey().getName()), entry.getValue());
        }
    }

    private void registerSingletonBean(String beanName, Object bean) {
        if (bean == null) {
            return;
        }
        SingletonBeanRegistry registry = (SingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory();
        registry.registerSingleton(beanName, bean);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        init();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
