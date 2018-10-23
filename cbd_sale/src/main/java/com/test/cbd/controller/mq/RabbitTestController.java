package com.test.cbd.controller.mq;

import com.test.cbd.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/rabbit")
public class RabbitTestController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PostMapping("/hello")
    public void hello() {
        String sendMsg = "hello1 " + new Date();
        rabbitMQService.sendText("helloQueue",sendMsg);
    }
}

