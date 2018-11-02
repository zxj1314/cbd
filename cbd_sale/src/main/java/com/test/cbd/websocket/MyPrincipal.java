package com.test.cbd.websocket;

import java.security.Principal;

/**
 * 用户信息
 * @author zxj
 * @Date 2018-11-02
 * 版权所有：zxj 版权所有(C) 2018
 */
public class MyPrincipal implements Principal {

    private String name;
    //请求的ip地址
    private String reqIp;


	public MyPrincipal(String name,String reqIp){
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
