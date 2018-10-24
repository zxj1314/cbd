package com.test.cbd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <br>
 * <b>功能：入口</b>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2018-3-1 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2018<br>
 */
@SpringBootApplication
@MapperScan("com.test.sale.mapper.GeneratorMapper")
public class GeneratorBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorBootstrap.class, args);
    }
}
