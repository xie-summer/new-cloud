<?xml version="1.0" encoding="UTF-8"?>
<!--
Dubbo配置
-->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">


    <bean class="com.weimob.mengdian.soa.kafka.consumer.config.KafkaDefaultConsumerConfig">
        <property name="bootstrapServers" value="${kafka.bootstrap.servers}"/>
        <property name="topic" value="${kafka.consumer.topic}"/>
        <property name="groupNumber" value="${kafka.group.number}"/>
        <property name="groupId" value="${kafka.group.id}"/>
        <property name="pollerNumber" value="${kafka.poller.number}"/>
        <property name="consumerNumber" value="${kafka.consumer.number}"/>
        <property name="consumerStep" value="${kafka.consumer.step}"/>
        <property name="maxPollRecords" value="${kafka.max.poll.records}"/>
        <property name="readTimeMs" value="${kafka.readTime.ms}"/>
        <property name="retryCount" value="${kafka.retry.count}"/>
        <property name="retryIntervalMs" value="${kafka.retryInterval.ms}"/>
        <property name="callOnFailExceptionRetryIntervalMs" value="${kafka.callOnFailException.retryInterval.ms}"/>
        <property name="failStrategy" value="${kafka.failStrategy}"/>
        <property name="sessionTimeoutMs" value="${kafka.session.timeout.ms}"/>
        <property name="heartbeatIntervalMs" value="${kafka.heartbeat.interval.ms}"/>
        <property name="requestTimeoutMs" value="${kafka.request.timeout.ms}"/>
        <property name="keyDeserializer" value="${kafka.key.deserializer}"/>
        <property name="valueDeserializer" value="${kafka.value.deserializer}"/>
        <property name="immediatelyExitWhenError" value="${kafka.immediatelyExit.whenError}"/>
        <property name="autoCommit" value="${kafka.auto.commit}"/>
        <property name="manualCommitFailStrategy" value="${kafka.manualCommit.failStrategy}"/>
        <property name="commitType" value="${kafka.commit.type}"/>
    </bean>
</beans>