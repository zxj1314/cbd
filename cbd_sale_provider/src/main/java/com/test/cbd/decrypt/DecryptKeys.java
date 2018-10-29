package com.test.cbd.decrypt;

/**
 * @author toby
 */
public enum DecryptKeys {
    //数据库用户名密码
    DATASOURCE_USERNAME("spring.datasource.username"),

    DATASOURCE_PASSWORD("spring.datasource.password"),

    REDIS_PASSWORD("spring.redis.password");

    private String key;

    DecryptKeys(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.key;
    }
}
