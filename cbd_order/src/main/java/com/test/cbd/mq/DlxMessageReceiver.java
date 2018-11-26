package com.test.cbd.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
/**
 * Created by Jim on 2018/11/24.
 */
@Component
public class DlxMessageReceiver {
    @RabbitHandler
    @RabbitListener(queues = "REDIRECT_QUEUE")//死信队列
    public void process(String msg, Channel channel, Message message)throws Exception {
        System.out.println("死信KEY_R: " +msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}




