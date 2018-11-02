package com.test.cbd.websocket;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * websocket控制器
 * @author zxj
 * @Date 2018-11-02
 * 版权所有：zxj 版权所有(C) 2018
 */
@RestController
@Controller
public class WsController{
	

    @MessageMapping(SipConstants.WEBSOCKET_PATH_URL)//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    @SendToUser(SipConstants.WEBSOCKET_P2P_PATH_MSG)//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
    //在springmvc 中可以直接获得principal,principal 中包含当前用户的信息(使用了wsId，作为principal)
    public Object operate(SimpMessageHeaderAccessor headerAccessor, @Header String simpSessionId, MyPrincipal principal, String message) throws Exception {
		System.out.println(headerAccessor.getSessionAttributes().get("HTTP.SESSION.ID")+"controller-session============");
		System.out.println("simpSessionId========"+simpSessionId);
		//获取客户端请求ip
		String host = principal.getReqIp();
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>host:"+host);
		//获取请求的ip地址
        return null;

    }
}
