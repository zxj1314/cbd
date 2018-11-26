package com.test.cbd.controller.mq;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Jim on 2018/11/24.
 */
@Component
public class DLQueue {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            //            设置编码
            messageProperties.setContentEncoding("utf-8");
            //            设置过期时间10*1000毫秒
            messageProperties.setExpiration("5000");
            return message;
        };
        rabbitTemplate.convertAndSend("DL_EXCHANGE", "DL_KEY", content, messagePostProcessor);
    }
}
