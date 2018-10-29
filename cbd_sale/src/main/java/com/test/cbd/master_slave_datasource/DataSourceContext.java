package com.test.cbd.master_slave_datasource;

/**
 * 数据源上下文
 */
public class DataSourceContext {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String value) {
        contextHolder.set(value);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
