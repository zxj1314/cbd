package com.test.cbd.controller;

import com.test.cbd.framework.controller.BaseController;
import com.test.cbd.service.UserService;
import com.test.cbd.vo.UserVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller
 * @author zxj
 * @date 2018-10-24 23:39:38
 * 版权所有：@撸码识途 版权所有(C) 2018
 */
@Slf4j
@Api(value="",description="")
@RestController
@RequestMapping("cbd/user")
public class UserController extends BaseController<UserVO> {

    @Autowired
    private UserService userService;

    @Override
    protected UserService getBaseService() {
        return  userService;
    }
}