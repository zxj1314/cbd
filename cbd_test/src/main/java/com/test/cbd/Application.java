package com.test.cbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    final static String queueName = "hello";


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}