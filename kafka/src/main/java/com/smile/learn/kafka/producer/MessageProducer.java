package com.smile.learn.kafka.producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;

/**
 * @author: smile
 * @title:
 * @projectName:
 * @description: TODO
 * @date: 2023/2/28 6:05 PM
 */
public class MessageProducer {
    @KafkaListener(topicPartitions = {
            @TopicPartition(topic = "message", partitions = "0")
    })
    public void handleMessage(ConsumerRecord<Object, Object> record) {
        System.out.println("=======consumer01收到消息=======");
        System.out.println("topic：" + record.topic());
        System.out.println("partition：" + record.partition());
        System.out.println("msg：" + (String) record.value());
    }
}