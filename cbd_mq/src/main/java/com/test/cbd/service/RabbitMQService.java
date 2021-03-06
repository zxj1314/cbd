package com.test.cbd.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Jim on 2018/10/23.
 */
@Service
public class RabbitMQService {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendText(String queueName,String msg) {
        this.rabbitTemplate.convertAndSend(queueName, msg);
    }

    public void sendTopicExchange(String type,String exchangeName,String message){
        this.rabbitTemplate.convertAndSend(type,exchangeName, message);
    }

    public void sendFanoutSender (String type,String exchangeName,String message){
        this.rabbitTemplate.convertAndSend(type,exchangeName, message);
    }
}
