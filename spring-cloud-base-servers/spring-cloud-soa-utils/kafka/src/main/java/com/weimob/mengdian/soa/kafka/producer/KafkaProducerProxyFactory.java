package com.weimob.mengdian.soa.kafka.producer;

import com.weimob.mengdian.soa.kafka.annotation.KafkaTopic;
import com.weimob.mengdian.soa.kafka.model.KafkaSendResponse;
import com.weimob.mengdian.soa.kafka.model.exception.KafkaConfigSettingException;
import com.weimob.mengdian.soa.kafka.producer.config.KafkaDefaultProducerConfig;
import javassist.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @KafkaProduced动态代理工厂类
 * @Author chenwp
 * @Date 2017-06-20 11:25
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class KafkaProducerProxyFactory {

    private final String senderSever;
    private final Properties kafkaProducerConfig;
    private KafkaDefaultProducerConfig kafkaDefaultProducerConfig;


    public KafkaProducerProxyFactory(String senderSever, Properties kafkaProducerConfig, KafkaDefaultProducerConfig kafkaDefaultProducerConfig) {
        this.kafkaProducerConfig = kafkaProducerConfig;
        this.senderSever = senderSever;
        this.kafkaDefaultProducerConfig = kafkaDefaultProducerConfig;
    }

    /**
     * 创建代理对象
     */
    public Object create(Class<?> producerClass) {
        try {
            ClassPool pool = ClassPool.getDefault();
            //添加classPath
            ClassClassPath classClassPath = new ClassClassPath(producerClass);
            pool.appendClassPath(classClassPath);
            CtClass ctClass = pool.makeClass(producerClass.getName() + "interface$proxy" + System.currentTimeMillis());

            ctClass.addInterface(pool.get(producerClass.getName()));
            //新增 KafkaProducerProxy 字段
            ctClass.addField(CtField.make("public " + KafkaProducerProxy.class.getName() + " proxy;", ctClass));
            String response = KafkaSendResponse.class.getName();

            // 添加有参的构造体 参数为KafkaProducerProxy对象
            CtConstructor cons = new CtConstructor(new CtClass[]{pool.get("com.weimob.mengdian.soa.kafka.producer.KafkaProducerProxy")}, ctClass);
            cons.setBody("{$0.proxy = $1;}");
            ctClass.addConstructor(cons);

            //实现接口的所有方法
            for (Method method : producerClass.getDeclaredMethods()) {
                String methodParameterType = method.getParameterTypes()[0].getName();

                KafkaTopic kafkaTopic = method.getAnnotation(KafkaTopic.class);
                String methodName = method.getName();
                if (kafkaTopic == null) {
                    throw new KafkaConfigSettingException(methodName + " expect a @KafkaTopic annotation");
                }
                String topic = kafkaTopic.topic();
                //每个方法其实是调用KafkaProducerProxy的invoke方法
                String code = "public " + response + " " + methodName + "(" + methodParameterType + " req){" +
                        "             proxy.setTopic(\"" + topic + "\");" +
                        "             proxy.setMethodName(\"" + methodName + "\");" +
                        "             return proxy.invoke(req);" +
                        "     }";
                ctClass.addMethod(CtMethod.make(code, ctClass));
            }
            //编译
            Class<?> clazz = ctClass.toClass();
            KafkaProducerProxy kafkaProducerProxy = new KafkaProducerProxy(senderSever, kafkaProducerConfig, kafkaDefaultProducerConfig, producerClass);
            System.out.println("# Kafka Producer Create Proxy For 【"+producerClass.getSimpleName()+"】");

            //调用构造函数实例化
            return clazz.getConstructor(KafkaProducerProxy.class).newInstance(kafkaProducerProxy);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
