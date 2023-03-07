package com.smile.learn.kafka.producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author: smile
 * @title:
 * @projectName:
 * @description: TODO
 * @date: 2023/2/28 6:05 PM
 */
@Component
public class MessageProducer {
    @KafkaListener(topics = "test",groupId = "livechatGroup")
    public void handleMessage(ConsumerRecord<Object, Object> record, Acknowledgment ack) {
        System.out.println("=======consumer01收到消息=======");
        System.out.println("topic：" + record.topic());
        System.out.println("partition：" + record.partition());
        System.out.println("msg：" + (String) record.value());

        ack.acknowledge();
    }
}