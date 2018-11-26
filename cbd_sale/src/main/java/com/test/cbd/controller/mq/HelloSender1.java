package com.test.cbd.controller.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

//测试消息者自动确认
@Service
public class HelloSender1 implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send() {
        String context = "你好现在是 " + new Date() +"";
        System.out.println("HelloSender发送内容 : " + context);
        this.rabbitTemplate.setConfirmCallback(this);//开启confirm机制
        MessageProperties properties=new MessageProperties();
        properties.setContentType(MessageProperties.DEFAULT_CONTENT_TYPE);
        properties.setDeliveryMode(MessageProperties.DEFAULT_DELIVERY_MODE);//持久化
        //properties.setExpiration("2018-12-15 23:23:23");//设置到期时间
        Message message=new Message("hello".getBytes(),properties);
        this.rabbitTemplate.sendAndReceive("exchange","topic.message",message);
        //exchange,queue 都正确,confirm被回调, ack=true
        //this.rabbitTemplate.convertAndSend("exchange","topic.message", context);

        //exchange 错误,queue 正确,confirm被回调, ack=false
        //this.rabbitTemplate.convertAndSend("fasss","topic.message", context);

        //exchange 正确,queue 错误 ,confirm被回调, ack=true; return被回调 replyText:NO_ROUTE
        //this.rabbitTemplate.convertAndSend("exchange","", context);

        //exchange 错误,queue 错误,confirm被回调, ack=false
        //this.rabbitTemplate.convertAndSend("fasss","fass", context);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("confirm--:correlationData:"+correlationData+",ack:"+ack+",cause:"+cause);
    }

}
