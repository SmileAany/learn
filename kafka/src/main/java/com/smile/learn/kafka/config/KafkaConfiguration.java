package com.smile.learn.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author: smile
 * @title: kafka配置
 * @projectName: kafka
 * @description: kafka配置
 * @date: 2023/2/27 6:49 PM
 */
@Configuration
public class KafkaConfiguration {
    public static final String TOPIC_MESSAGE = "message";

    @Bean
    public NewTopic customerServiceTopic() {
        return TopicBuilder.name(TOPIC_MESSAGE).build();
    }
}