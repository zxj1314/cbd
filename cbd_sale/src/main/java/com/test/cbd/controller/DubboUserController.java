package com.test.cbd.controller;

import com.test.cbd.framework.controller.BaseController;
import com.test.cbd.service.DubboUserService;
import com.test.cbd.service.UserService;
import com.test.cbd.vo.UserVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;

/**
 * Controller
 * @author zxj
 * @date 2018-10-24 23:39:38
 * 版权所有：@撸码识途 版权所有(C) 2018
 */
@Slf4j
@Api(value="",description="")
@RestController
@RequestMapping("cbd/dubbo/user")
public class DubboUserController extends BaseController<UserVO> {

    @Reference(url = "dubbo://127.0.0.1:20880",timeout = 60000)//目前不能通过 application.properties 定义
    private DubboUserService dubboUserService;

    @Override
    protected DubboUserService getBaseService() {
        return  dubboUserService;
    }

    @GetMapping(value = "test")
    public void test(){
        System.out.println("test");
        dubboUserService.login("dubbo","dubbo");
    }
}
