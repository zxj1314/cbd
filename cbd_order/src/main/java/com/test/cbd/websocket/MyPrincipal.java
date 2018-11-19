package com.test.cbd.websocket;

import java.security.Principal;

/**
 * 认证信息
 * @author zhuxiaojin
 * @Date 2018-11-19
 */
public class MyPrincipal implements Principal {

    private String name;
    //请求的ip地址
    private String reqIp;


	public MyPrincipal(String name, String reqIp){
        this.name = name;
        this.reqIp = reqIp;
    }
    @Override
    public String getName() {
        return name;
    }
    public String getReqIp() {
		return reqIp;
	}
	
}
