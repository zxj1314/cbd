package com.test.cbd.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.TimeUnit;

/**
 * 模拟子系统提供了几个示例方法，包括获取ZK的配置，文件上传，文件下载，业务方法执行
 * @author zxj
 * @Date 2018-10-30
 * 版权所有：zxj 版权所有(C) 2018
 */
public class ClientApp {

    FtpConfig ftpConfig;


    private FtpConfig getFtpConfig() {
        if (ftpConfig == null) {
            //首次获取时，连接zk取得配置，并监听配置变化
            ZkClient zk = ZKUtil.getZkClient();
            ftpConfig = (FtpConfig) zk.readData(ZKUtil.FTP_CONFIG_NODE_NAME);
            System.out.println("ftpConfig => " + ftpConfig);

            zk.subscribeDataChanges(ZKUtil.FTP_CONFIG_NODE_NAME, new IZkDataListener() {//监听zookeeper节点的数据变化

                @Override
                public void handleDataChange(String s, Object o) throws Exception {
                    System.out.println("ftpConfig is changed !");
                    System.out.println("node:" + s);
                    System.out.println("o:" + o.toString());
                    ftpConfig = (FtpConfig) o;//重新加载FtpConfig
                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("ftpConfig is deleted !");
                    System.out.println("node:" + s);
                    ftpConfig = null;
                }
            });
        }
        return ftpConfig;

    }

    /**
     * 模拟程序运行
     *
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {

        getFtpConfig();

        upload();

        download();
    }

    public void upload() throws InterruptedException {
        System.out.println("正在上传文件...");
        System.out.println(ftpConfig);
        TimeUnit.SECONDS.sleep(10);
        System.out.println("文件上传完成...");
    }


    public void download() throws InterruptedException {
        System.out.println("正在下载文件...");
        System.out.println(ftpConfig);
        TimeUnit.SECONDS.sleep(10);
        System.out.println("文件下载完成...");
    }


}