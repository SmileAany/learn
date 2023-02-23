package com.fs.learn.swagger.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: smile
 * @title: swagger controller
 * @projectName: swagger
 * @description: swagger controller
 * @date: 2023/2/22 10:04 AM
 */
@Api(tags = "测试")
@RestController
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "smile";
    }
}