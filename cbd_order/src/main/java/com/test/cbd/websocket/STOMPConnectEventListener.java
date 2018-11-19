package com.test.cbd.websocket;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


/**
 * STOMP监听类，用于缓存websocket_session
 */
@Slf4j
@Component
public class STOMPConnectEventListener { //implements ApplicationListener<SessionConnectEvent> {

    //连接成功
    @EventListener
    public void onConnectEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        if (sha.getUser() == null) {
            return;
        }
        String myPrincipal = sha.getUser().getName();
        log.info("onConnectEvent ====" + myPrincipal);
        //删除activemap
        WebSocketManager.removeNoActive(myPrincipal);
    }

    //连接断开
    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        if (sha.getUser() == null) {
            return;
        }
        String myPrincipal = sha.getUser().getName();
        log.info("onDisconnectEvent  ====" + myPrincipal);

    }
}

