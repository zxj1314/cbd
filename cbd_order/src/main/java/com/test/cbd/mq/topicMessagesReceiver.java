package com.test.cbd.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.Date;

@Component
//topic.messages绑定的是"topic.#",应该两个两个生产者的消息都会收到
public class topicMessagesReceiver {

    @RabbitHandler
    @RabbitListener(queues = "topic.messages")//方法级注解
    public void process(String msg, Channel channel, Message message){
        System.out.println("topicMessageReceiver1收到  : " + msg +"收到时间"+new Date());
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则如果你在配置文件中设置了ack确认的话，消息服务器以为这条消息没处理掉 后续还会在发
            //System.out.println(1/0);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println(1/0);

        } catch (Exception e) {
            e.printStackTrace();
/*            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }*/
            //丢弃这条消息
/*            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);//如果没有这句，当捕获到异常，mq会重新发送消息，直至达到重试次数。
            } catch (IOException e1) {
                e1.printStackTrace();
            }*/
            System.out.println("receiver fail");
        }

    }

}