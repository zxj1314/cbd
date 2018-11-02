package com.test.cbd.controller.mq;

import com.test.cbd.service.RabbitMQService;
import com.test.cbd.service.RabbitMQService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/rabbit")
public class RabbitTestController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @Autowired
    private RabbitMQService1 rabbitMQService1;


    @Autowired
    private HelloSender helloSender;

    @GetMapping("/hello")
    public void hello() {
        for(int i=0;i<=10;i++) {
            String sendMsg = "hello1 " + new Date();
            //多生产者-多消费者
            rabbitMQService.sendText("helloQueue", sendMsg);
            rabbitMQService1.sendText("helloQueue", sendMsg);
        }
    }

    @GetMapping("/hello1")
    public void hello1() {
        String sendMsg = "hello1 " + new Date();
        System.out.println(sendMsg);
        rabbitMQService.sendText("helloQueue",sendMsg);
    }

    //topic ExChange示例
    @GetMapping("/exchange")
    public void send() {
        String msg1 = "I am sender topic.mesaage msg======";
        System.out.println("sender1 : " + msg1);
        rabbitMQService.sendTopicExchange("exchange", "topic.message", msg1);

        String msg2 = "I am sender topic.mesaages msg########";
        System.out.println("sender2 : " + msg2);
        rabbitMQService.sendTopicExchange("exchange", "topic.messages", msg2);
    }

    //fanout ExChange示例，Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout转发器发送消息，绑定了这个转发器的所有队列都收到这个消息。
    @GetMapping("/FanoutTest")
    public void sendFanout() {
        String msgString="fanoutSender :hello i am hzb";
        System.out.println(msgString);
        rabbitMQService.sendFanoutSender("fanoutExchange", "cccc", msgString);//中间“cccc”参数随意不影响消费者接收。
    }

    //带callback的消息发送,回调处理,Confirm机制
    @GetMapping("/sendSend")
    public void sendSend() {
        helloSender.send();
    }

}

