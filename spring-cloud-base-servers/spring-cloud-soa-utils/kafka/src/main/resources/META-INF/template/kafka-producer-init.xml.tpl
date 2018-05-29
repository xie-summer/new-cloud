<?xml version="1.0" encoding="UTF-8"?>
<!--
Dubbo配置
-->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">


    <bean class="com.weimob.mengdian.soa.kafka.producer.config.KafkaDefaultProducerConfig">
        <property name="acks" value="${kafka.acks}"/>
        <property name="retries" value="${kafka.retries}"/>
        <property name="batchSize" value="${kafka.batch.size}"/>
        <property name="bootstrapServers" value="${kafka.bootstrap.servers}"/>
        <property name="bufferMemory" value="${kafka.buffer.memory}"/>
        <property name="keySerializer" value="${kafka.key.serializer}"/>
        <property name="valueSerializer" value="${kafka.value.serializer}"/>
        <property name="lingerMs" value="${kafka.linger.ms}"/>
    </bean>
</beans>