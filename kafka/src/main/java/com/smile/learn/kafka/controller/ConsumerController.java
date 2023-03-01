package com.smile.learn.kafka.controller;

import com.smile.learn.kafka.consumer.SendMessageConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: smile
 * @title:
 * @projectName:
 * @description: TODO
 * @date: 2023/2/27 2:35 PM
 */
@RestController
public class ConsumerController {
    @Autowired
    private SendMessageConsumer sendMessageConsumer;

    @GetMapping("/test")
    public String sendMessage() {
        sendMessageConsumer.sendMessage("message","admin");

        return "发送成功";
    }
}