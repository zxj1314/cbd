package com.test.cbd.zookeeper.loadbalance.client;

public interface BalanceProvider<T> {

    public T getBalanceItem();

}