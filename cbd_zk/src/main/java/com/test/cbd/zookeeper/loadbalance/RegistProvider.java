package com.test.cbd.zookeeper.loadbalance;

public interface RegistProvider {

    public void regist(Object context) throws Exception;

    public void unRegist(Object context) throws Exception;
}
