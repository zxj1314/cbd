package com.test.cbd.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 *websocket拦截器
 * @author zxj
 * @Date 2018-11-02
 * 版权所有：zxj 版权所有(C) 2018
 */
@Slf4j
@Component
public class MyPrincipalHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        if(attributes != null && attributes.get("myPrincipal")!=null){
            return (Principal)attributes.get("myPrincipal");
        }

        log.error("未登录系统，禁止登录websocket!");
        return null;
    }

}
