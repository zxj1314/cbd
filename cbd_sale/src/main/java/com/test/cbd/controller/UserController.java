package com.test.cbd.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public void hello() {
        String sendMsg = "login " + new Date();
        System.out.println(sendMsg);
    }
}
