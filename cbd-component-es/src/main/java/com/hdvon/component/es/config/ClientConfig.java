package com.hdvon.component.es.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 读取client配置信息
 * @author wanshaojian
 *
 */
@Configuration
@Getter
@Setter
public class ClientConfig {
	
    /** 
     * elk集群地址 
     */  
    @Value("${elasticsearch.ip}")
    private String esHostName;  
    /** 
     * 端口 
     */  
    @Value("${elasticsearch.port}")
    private Integer esPort;  
    /** 
     * 集群名称 
     */  
    @Value("${elasticsearch.cluster.name}")
    private String esClusterName;  
  
    /** 
     * 连接池 
     */  
    @Value("${elasticsearch.pool}")
    private Integer esPoolSize;  
  
    
    /** 
     * 是否服务启动时重新创建索引
     */  
    @Value("${elasticsearch.regenerateIndexEnabled}")
    private Boolean esRegenerateIndexFlag; 
    
    
    /** 
     * 是否服务启动时索引数据同步
     */  
    @Value("${elasticsearch.syncDataEnabled}")
    private Boolean esSyncDataEnabled; 
}
