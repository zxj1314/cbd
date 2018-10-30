package com.test.cbd.zookeeper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ftp配置信息实体类，用于测试zookeeper配置统一管理
 * Created by jimmy on 15/6/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FtpConfig implements Serializable {

    /**
     * 端口号
     */
    private int port;

    /**
     * ftp主机名或IP
     */
    private String host;

    /**
     * 连接用户名
     */
    private String user;

    /**
     * 连接密码
     */
    private String password;

    @Override
    public String toString() {
        return user + "/" + password + "@" + host + ":" + port;
    }
}