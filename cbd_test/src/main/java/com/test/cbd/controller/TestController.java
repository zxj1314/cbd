package com.test.cbd.controller;

import com.test.cbd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jim on 2018/10/27.
 */
@RestController
@RequestMapping("/cbd")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "test")
    public void test(){
        System.out.println("test==================");
        userService.login("1","1");
    }
}
