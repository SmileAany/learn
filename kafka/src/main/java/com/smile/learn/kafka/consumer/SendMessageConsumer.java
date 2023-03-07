package com.smile.learn.kafka.consumer;

import com.smile.learn.kafka.config.KafkaConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: smile
 * @title: 生产者
 * @projectName: kafka
 * @description: kafka 生产者
 * @date: 2023/2/28 6:03 PM
 */
@Service
public class SendMessageConsumer {
    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public boolean sendMessage(String topic, String message) {
        kafkaTemplate.send(topic,message);

        return true;
    }
}