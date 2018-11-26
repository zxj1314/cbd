package com.test.cbd.mq;

//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//
//public class topicMessageReceiver {
//
//    @RabbitHandler
//    @RabbitListener(queues = "topic.messages")//topic.message绑定的是“topic.message”,只会收到topic.message的消息
//    public void process(String msg, Channel channel, Message message)throws Exception {
//        System.out.println("topicMessageReceiver2: " +msg);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//    }
//
//}