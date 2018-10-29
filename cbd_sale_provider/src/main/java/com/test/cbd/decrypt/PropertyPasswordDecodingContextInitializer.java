package com.test.cbd.decrypt;

import com.test.cbd.common.decrypt.Base64PropertyPasswordDecoder;
import com.test.cbd.common.decrypt.PropertyPasswordDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 初始化时解密配置文件里加密的数据，具体解密那些字段，以DecryptKeys中定义的为准
 * @author zhuxiaojin
 * @Date 2018-10-29
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Slf4j
//@Component
public class PropertyPasswordDecodingContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private PropertyPasswordDecoder passwordDecoder = new Base64PropertyPasswordDecoder();

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            Map<String, Object> propertyOverrides = new LinkedHashMap();
            decodePasswords(propertySource, propertyOverrides);
            if (!propertyOverrides.isEmpty()) {
                PropertySource<?> decodedProperties = new MapPropertySource("decoded " + propertySource.getName(), propertyOverrides);
                environment.getPropertySources().addBefore(propertySource.getName(), decodedProperties);
            }
        }
    }

    private void decodePasswords(PropertySource<?> source, Map<String, Object> propertyOverrides) {
        if (source instanceof EnumerablePropertySource) {
            EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource<?>) source;
            for (String key : enumerablePropertySource.getPropertyNames()) {
                Object rawValue = getEncryptValueByKey(key, source);
                if ((rawValue instanceof String) && (StringUtils.hasText((String) rawValue))) {
                    String decodedValue = passwordDecoder.decodePassword((String) rawValue);
                    propertyOverrides.put(key, decodedValue);
                }
            }
        }
    }

    private Object getEncryptValueByKey(String especialKey, PropertySource<?> source) {
        for (DecryptKeys key : DecryptKeys.values()) {
            if (especialKey.equals(key.getValue())) {
                log.info("Encryption key: {}", key.getValue());
                return source.getProperty(especialKey);
            }
        }
        return "";
    }
}