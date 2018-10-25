package com.test.cbd.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>Swagger2配置<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2018-3-1 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */

@Configuration
@EnableSwagger2
public class Swagger2Configure {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.test.cbd.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("cbdapi文档")
                .description("restful 风格接口")
                //服务条款网址
                //.termsOfServiceUrl("http://www.hdvon.cn/")
                .version("1.0")
                //.contact(new Contact("广州弘度信息科技有限公司", "url", "email"))
                .build();
    }

    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList();
        tokenPar.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false);
        pars.add(tokenPar.build());
        return pars;
    }
}
