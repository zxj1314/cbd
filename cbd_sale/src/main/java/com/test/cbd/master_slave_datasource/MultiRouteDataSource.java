package com.test.cbd.master_slave_datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/*
*    重写的函数决定了最后选择的DataSource
*/
public class MultiRouteDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        //通过绑定线程的数据源上下文实现多数据源的动态切换,有兴趣的可以去查阅资料或源码
        return DataSourceContext.getDataSource();
    }

}