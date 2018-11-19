package com.test.cbd.websocket;
//
//import com.hdvon.sip.app.PortPoolManager;
//import com.hdvon.sip.entity.ErrorBean;
//import com.hdvon.sip.entity.InvtieEntity;
//import com.hdvon.sip.entity.RequestBean;
//import com.hdvon.sip.entity.ResponseBean;
//import com.hdvon.sip.entity.param.CloudParam;
//import com.hdvon.sip.service.SipService;
//import com.hdvon.sip.utils.ClientUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.annotation.SendToUser;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@Controller
//public class WsController {
//
//	@Autowired
//    SipService sipService;
//
//    @MessageMapping(SipConstants.WEBSOCKET_PATH_URL)//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
//    @SendToUser(SipConstants.WEBSOCKET_P2P_PATH_MSG)//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
//    //在springmvc 中可以直接获得principal,principal 中包含当前用户的信息(使用了wsId，作为principal)
//    public ResponseBean operate(SimpMessageHeaderAccessor headerAccessor, @Header String simpSessionId, MyPrincipal principal, String message) throws Exception {
//		System.out.println(headerAccessor.getSessionAttributes().get("HTTP.SESSION.ID")+"controller-session============");
//		System.out.println("simpSessionId========"+simpSessionId);
//		//获取客户端请求ip
//		String host = principal.getReqIp();
////		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>host:"+host);
//		//获取请求的ip地址
//        ResponseBean respData = sipService.sendMsg(message,host);
//
//        return null;
//
//    }
//	@PostMapping(value = "/cloudControl")
//	public ErrorBean cloudControl(CloudParam paramVo){
//		return sipService.cloudControl(paramVo);
//	}
//
//
//	@GetMapping(value = "/getInvtiePort")
//	public InvtieEntity getInvtiePort(HttpServletRequest request){
//		//获取客户端请求ip
//		String host = ClientUtil.getClientIp(request);
//		//生成接受流端口
//		int port = PortPoolManager.getInstance().getPool();
//
//		InvtieEntity entity = new InvtieEntity();
//		entity.setHost(host);
//		entity.setPort(port);
//		return entity;
//	}
//    @MessageMapping("/test")//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
//    @SendToUser(SipConstants.WEBSOCKET_P2P_PATH_MSG)//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
//    //在springmvc 中可以直接获得principal,principal 中包含当前用户的信息(使用了wsId，作为principal)
//    public ResponseBean test(MyPrincipal principal, RequestBean message) throws Exception {
//
//        ResponseBean<String> data = new ResponseBean();
//        data.setResult("testtest------principal="+principal.getName());
//
//        return data;
//    }
//}
//