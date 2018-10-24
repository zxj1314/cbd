package com.test.cbd.framework.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * snowflake-id 生成器
 */
@Component
public class IdGenerator {

    private static int snowflakeWorkId;

    @Value("${app.system.snowflakeWorkId:1}")//机器id，默认值为1
    public void setSnowflakeWorkId(int snowflakeWorkId){//应用启动读取配置
        IdGenerator.snowflakeWorkId = snowflakeWorkId;
    }

    private static IdWorker idWorker = new IdWorker(snowflakeWorkId);

    public static String nextId() {
        if (idWorker == null) {
            idWorker = new IdWorker(snowflakeWorkId);
        }
        return idWorker.nextId()+"";
    }

    public static void main(String[] args) {
        for(int i=0;i<281; i++){
            System.out.println(IdGenerator.nextId());
        }

    }
}