package com.test.cbd.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jim on 2018/10/23.
 */
@Service
public class RabbitMQService1 {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendText(String queueName,String msg) {
        this.rabbitTemplate.convertAndSend(queueName, msg);
    }
}
