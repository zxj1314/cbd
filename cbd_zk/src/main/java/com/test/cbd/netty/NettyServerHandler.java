package com.test.cbd.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NettyServerHandler extends ChannelHandlerAdapter {

    //有客户端连接时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("one client connect...");
    }

    //断开连接时触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("one client disconnect...");
    }

    //接收客户端发送的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端："+msg.toString());
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String result ="result";
        try{
            result = br.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        ctx.write(result);//给客户端回复
        ctx.flush();
    }
}
