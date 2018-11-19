package com.test.cbd.websocket;


/**
 * websocket常量池
 * @author wanshaojian
 * @since 2018-10-15
 */
public class SipConstants {
    //webSocket相关配置
    //链接地址
    public static final String WEBSOCKET_PATH = "/sipWebSocket";
    //服务端接收消息代理地址前缀
    public static final String WEBSOCKET_PATH_PERFIX = "/sipApp";
    public static final String WEBSOCKET_PATH_URL = "/operate";

    //消息代理路径
    public static String WEBSOCKET_BROADCAST_PATH = "/topic";
    //服务端生产地址,客户端订阅此地址以接收服务端生产的消息
    public static final String WEBSOCKET_BROADCAST_PATH_MSG = "/topic/message";

    //p2p代理地址前缀
    public static final String WEBSOCKET_P2P_PATH_PERFIX = "/user";
    //p2p消息推送地址前缀
    public static final String WEBSOCKET_P2P_PATH = "/single";
    public static final String WEBSOCKET_P2P_PATH_MSG = "/single/message";


}
