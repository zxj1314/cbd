package com.test.cbd.master_slave_datasource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 主数据源配置
 */
@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "master.db")
public class MasterDataSourceConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
