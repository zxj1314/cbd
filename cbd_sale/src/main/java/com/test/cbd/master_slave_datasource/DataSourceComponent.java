package com.test.cbd.master_slave_datasource;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置类
 */
@Configuration
public class DataSourceComponent {

    @Resource
    private MasterDataSourceConfig masterDataSourceConfig;

    @Resource
    private SlaveDataSourceConfig slaveDataSourceConfig;


     /*
     * 一开始以为springboot的自动配置还是会生效，直接加了@Resource DataSource dataSource；
     * 显示是不work的，会报create bean 错误
     */
     @Bean(name = "master")
    public DataSource masterDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(masterDataSourceConfig.getUrl());
        dataSource.setUsername(masterDataSourceConfig.getUsername());
        dataSource.setPassword(masterDataSourceConfig.getPassword());
        dataSource.setDriverClassName(masterDataSourceConfig.getDriverClassName());
        return dataSource;
    }

    /*
     * 一开始在这里加了@Bean的注解，当然secondDataSource()也加了
     * 会导致springboot识别的时候，发现有多个
     * 所以，其实都不要加@Bean，最终有效的的DataSource就只需要一个multiDataSource即可
     */
    @Bean(name = "slave")
    public DataSource slaveDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(slaveDataSourceConfig.getUrl());
        dataSource.setUsername(slaveDataSourceConfig.getUsername());
        dataSource.setPassword(slaveDataSourceConfig.getPassword());
        dataSource.setDriverClassName(slaveDataSourceConfig.getDriverClassName());
        return dataSource;
    }

    @Primary
    @Bean(name = "multiDataSource")
    public MultiRouteDataSource exampleRouteDataSource() {
        MultiRouteDataSource multiDataSource = new MultiRouteDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource());
        targetDataSources.put("slave", slaveDataSource());
        multiDataSource.setTargetDataSources(targetDataSources);
        multiDataSource.setDefaultTargetDataSource(masterDataSource());
        return multiDataSource;
    }

/*    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(exampleRouteDataSource());
        return manager;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(exampleRouteDataSource());
        return sessionFactoryBean;
    }*/

}
