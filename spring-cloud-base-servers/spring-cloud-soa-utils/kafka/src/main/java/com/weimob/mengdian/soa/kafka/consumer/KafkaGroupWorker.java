package com.weimob.mengdian.soa.kafka.consumer;

import com.google.common.collect.Lists;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaConsumerConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * kafka group处理类
 *
 * @Author chenwp
 * @Date 2017-06-21 10:34
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class KafkaGroupWorker implements Runnable {

    private KafkaConsumerConfig kafkaConsumerConfig;
    private Consumer consumerImpl;
    private final int groupIndex;
    private int pollerNumber;


    //结束工作倒数锁
    private final CountDownLatch stopWork;

    private List<KafkaPollWorker> pollerWorkers = Lists.newArrayList();


    public KafkaGroupWorker(KafkaConsumerConfig kafkaConsumerConfig, Consumer consumerImpl, int groupIndex) {
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        this.consumerImpl = consumerImpl;
        int pollerConfigNumber = kafkaConsumerConfig.getPollerNumber();
        this.pollerNumber = pollerConfigNumber > KafkaConstant.MAX_POLLER_NO ? KafkaConstant.MAX_POLLER_NO : pollerConfigNumber;
        this.groupIndex = groupIndex;
        this.stopWork = new CountDownLatch(pollerNumber);
    }


    @Override
    public void run() {
        //每个组拉取消息的线程

        for (int i = 0; i < pollerNumber; i++) {
            KafkaPollWorker kafkaPollWorker = new KafkaPollWorker(kafkaConsumerConfig, consumerImpl, this.groupIndex, this.stopWork);
            pollerWorkers.add(kafkaPollWorker);
        }

        for (KafkaPollWorker pollerWorker : pollerWorkers) {
            Thread thread = new Thread(pollerWorker);
            thread.start();
        }

        try {
            this.stopWork.await(Integer.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
