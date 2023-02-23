package com.smile.learn.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: smile
 * @title: 测试类
 * @projectName: logback
 * @description: 测试类
 * @date: 2023/2/21 12:02 PM
 */
@Slf4j
@RestController
public class TestController {
    @GetMapping("/index")
    public String index() {
        log.info("这是一个info");
        log.error("这是一个error");
        log.debug("这是一个debug");

        return "smile";
    }
}