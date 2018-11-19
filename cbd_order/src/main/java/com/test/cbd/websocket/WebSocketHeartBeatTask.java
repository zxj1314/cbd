package com.test.cbd.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * websocket心跳
 * @author zhuxiaojin
 * @Date 2018-11-19
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Slf4j
@Component
public class WebSocketHeartBeatTask {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //可以试试ping - pong心跳
    @Scheduled(cron = "0/5 * * * * ? ")
    public void heartBeat() {
        log.debug("heartbeat");
        simpMessagingTemplate.convertAndSend(SipConstants.WEBSOCKET_BROADCAST_PATH_MSG, new Date().toString());
        //simpMessagingTemplate.convertAndSendToUser("abc", SipConstants.P2P_PUSH_PATH_MSG, "你妹的");
    }

    //定时扫描无用链接
    @Scheduled(cron = "0/10 * * * * ? ")
    public void clearNoActive() {
        WebSocketManager.clear();
    }

}
