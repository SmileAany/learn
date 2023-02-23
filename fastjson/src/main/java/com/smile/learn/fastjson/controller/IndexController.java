package com.smile.learn.fastjson.controller;

import com.smile.learn.fastjson.vo.IndexVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: smile
 * @title:
 * @projectName:
 * @description: TODO
 * @date: 2023/2/23 4:03 PM
 */
@RestController
public class IndexController {
    @GetMapping("/index")
    public IndexVO index() {
        IndexVO indexVO = new IndexVO();

        return indexVO;
    }
}
