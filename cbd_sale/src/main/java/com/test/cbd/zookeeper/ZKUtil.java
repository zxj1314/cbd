package com.test.cbd.zookeeper;

import org.I0Itec.zkclient.ZkClient;

/**
 * zookeeper工具类
 * @author zxj
 * @Date 2018-10-30
 * 版权所有：zxj 版权所有(C) 2018
 */
public class ZKUtil {

    public static final String FTP_CONFIG_NODE_NAME = "/config/ftp";

    public static ZkClient getZkClient() {
        return new ZkClient("localhost:2181,localhost:2182,localhost:2183");
    }


}