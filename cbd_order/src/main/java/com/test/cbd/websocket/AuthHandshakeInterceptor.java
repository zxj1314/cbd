package com.test.cbd.websocket;

import com.test.cbd.framework.utils.ClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * websocket拦截器，
 * @author zhuxiaojin
 * @Date 2018-11-19
 */
@Slf4j
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    //@Autowired
    //private RedisHelper redisHelper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
            //servletRequest.getServletRequest().changeSessionId();
            //HttpSession session = servletRequest.getServletRequest().getSession();
            //System.out.println("=========sessionId:"+session.getId()+"======================");
            //获取token认证, 放到ws连接后面，get方式获取
            //或者 登陆时放到容器的HttpSession中，ws连接时从在容器的session中获取
            String token = httpServletRequest.getParameter("token");
            String wsId = httpServletRequest.getParameter("wsId");

            if (StringUtils.hasText(token) && StringUtils.hasText(wsId)) {
                //访问redis获取token是否有效
/*                UserVo userVo = redisHelper.getUserByToken(token);
                if(userVo==null){
                    return false;
                }*/
            	String reqIp = ClientUtil.getClientIp(httpServletRequest);
                Map<String, Object> sipMap = new HashMap<String, Object>();
                WsCacheBean wsCacheBean = WsCacheBean.builder().token(token).sipMap(sipMap).connectTime(System.currentTimeMillis()).userId(null).build();
                //if(WebSocketManager.put(wsId,wsCacheBean)){//写一个对象到内存(可能后面没有建立ws成功，通过心跳检测)
                    //no_active_map
                    WebSocketManager.putNoActive(wsId,wsCacheBean);
                    MyPrincipal myPrincipal = new MyPrincipal(wsId,reqIp);//使用wsId作为wssession的Id
                    attributes.put("myPrincipal", myPrincipal);
                    return true;
                //}
            }

        }
        log.error("请求无效，禁止登录websocket!");
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }


}
