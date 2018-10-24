package com.test.cbd.controller;

import com.test.cbd.framework.redis.RedisDao;
import com.test.cbd.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private RabbitMQService rabbitMQService;

    @PostMapping("/test")
    public void hello() {
        String sendMsg = "hello1 " + new Date();
        System.out.println(sendMsg);
    }
}

