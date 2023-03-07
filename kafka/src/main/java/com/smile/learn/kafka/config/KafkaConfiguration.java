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
    /**
     * 雇员主题
     **/
    public static final String TOPIC_EMPLOYEE = "livechat_employee";

    /**
    * 部门主题
    **/
    public static final String TOPIC_DEPARTMENT = "livechat_department";

    @Bean
    public NewTopic employeeTopic() {
        return TopicBuilder.name(TOPIC_EMPLOYEE).build();
    }

    @Bean
    public NewTopic departmentTopic() {
        return TopicBuilder.name(TOPIC_EMPLOYEE).build();
    }
}