package com.test.cbd.configure;

import com.test.cbd.framework.context.SessionHelper;
import com.test.cbd.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
//@EnableWebMvc
@ComponentScan("com.test.cbd")
@Primary
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private AppConfig config;
    @Autowired
    private SessionHelper sessionHelper;

    /**
     * setUseSuffixPatternMatch : 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真(即匹配(；
     * setUseTrailingSlashMatch : 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真(即匹配)；
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真(匹配)；
        configurer.setUseSuffixPatternMatch(false)
                .setUseTrailingSlashMatch(true);
    }

/*    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(config.isAuthEnable()) {
            registry.addInterceptor(
                    new AuthInterceptor(sessionHelper))
                    .addPathPatterns("/**")
                    .excludePathPatterns("/login","/alarmLogin","/Sync/**","/t/**","/redis/**","/rest/**","/swagger-resources/**","/error");
            super.addInterceptors(registry);
        }
    }*/
}

