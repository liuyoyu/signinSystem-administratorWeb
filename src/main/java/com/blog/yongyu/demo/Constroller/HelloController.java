package com.blog.yongyu.demo.Constroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试springboot
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String sayHello(){
        return "springboot test";
    }
}
