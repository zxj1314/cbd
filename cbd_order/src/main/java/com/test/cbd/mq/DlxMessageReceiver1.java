package com.test.cbd.mq;

import org.springframework.stereotype.Component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Jim on 2018/11/24.
 */
@Component
public class DlxMessageReceiver1 {
    @RabbitHandler
    @RabbitListener(queues = "DL_QUEUE")//方法级注解
    public void process(String msg, Channel channel, Message message){
        System.out.println("死信DL_KEY  : " + msg );
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则如果你在配置文件中设置了ack确认的话，消息服务器以为这条消息没处理掉 后续还会在发
            //System.out.println(1/0);
            //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println(1/0);

        } catch (Exception e) {
            e.printStackTrace();
            /*try {
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
            //System.out.println("receiver fail");
        }

    }

}
