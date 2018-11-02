package com.test.cbd.controller.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
//RabbitTemplate.ConfirmCallback
@Service
public class HelloSender implements RabbitTemplate.ReturnCallback {

    @Autowired
//    private AmqpTemplate rabbitTemplate;
    private RabbitTemplate rabbitTemplate;
    /**
     * 生产上用confirm模式的居多。一旦channel进入confirm模式，所有在该信道上面发布的消息都将会被指派一个唯一的ID(从1开始)，
     * 一旦消息被投递到所有匹配的队列之后，rabbitMQ就会发送一个Ack给生产者(包含消息的唯一ID)，这就使得生产者知道消息已经正确到达目的队列了.
     * 如果rabiitMQ没能处理该消息，则会发送一个Nack消息给你，你可以进行重试操作。
     * @param
     * @return
     * @Author zxj
     */
    public void send() {
        String context = "你好现在是 " + new Date() +"";
        System.out.println("HelloSender发送内容 : " + context);
        this.rabbitTemplate.setReturnCallback(this);
        //confirm会发送一个Ack给生产者(包含消息的唯一ID)，如果rabiitMQ没能处理该消息，则会发送一个Nack消息给你，你可以进行重试操作
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
            } else {
                System.out.println("HelloSender 消息发送成功 ");
            }
        });
        this.rabbitTemplate.convertAndSend("topic.messages", context);
        //System.out.println(1/0);
    }

/*
    public void sendObj() {
        MessageObj obj = new MessageObj();
        obj.setACK(false);
        obj.setId(123);
        obj.setName("zhangsan");
        obj.setValue("data");
        System.out.println("发送 : " + obj);
        this.rabbitTemplate.convertAndSend("helloObj", obj);
    }
*/

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("sender return success" + message.toString()+"==="+i+"==="+s1+"==="+s2);
    }

//    @Override
//    public void confirm(CorrelationData correlationData, boolean b, String s) {
//        System.out.println("sender success");
//    }

}
