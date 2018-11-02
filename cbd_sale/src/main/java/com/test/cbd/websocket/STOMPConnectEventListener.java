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
 * @author zxj
 * @Date 2018-11-02
 * 版权所有：zxj 版权所有(C) 2018
 */
@Slf4j
@Component
public class STOMPConnectEventListener{ //implements ApplicationListener<SessionConnectEvent> {

    //连接成功
    @EventListener
    public void onConnectEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        if(sha.getUser() == null) {
        	return;
        }
        System.out.println("sha.getSessionId()============"+sha.getSessionId());
        String myPrincipal = sha.getUser().getName();
        log.info("onConnectEvent ===="+myPrincipal);
        //删除activemap
        WebSocketManager.removeNoActive(myPrincipal);
    }

    //连接断开
    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event)  {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        if(sha.getUser() == null) {
        	return;
        }
        String myPrincipal = sha.getUser().getName();
        log.info("onDisconnectEvent  ===="+myPrincipal);
        
        
        /**
         * 发送信令断开callId
         */
        //根据wsID 获取callId
      /*  WsCacheBean wsCacheBean = WebSocketManager.get(myPrincipal);
        Map<String,Object> sipMap = wsCacheBean.getSipMap();
        
        log.debug("========================sip:{}",JSON.toJSONString(sipMap));
        if(!sipMap.isEmpty()) {
        	for(Iterator<String> it=sipMap.keySet().iterator();it.hasNext();) {
            	String callID = it.next();
            	callTerminate(callID);
            }	
        }
        
        WebSocketManager.removePrincipal(myPrincipal);
        WebSocketManager.removeNoActive(myPrincipal);*/
    }

}

