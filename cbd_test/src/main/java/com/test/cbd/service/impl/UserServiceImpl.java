package com.test.cbd.service.impl;

import com.test.cbd.mapper.UserMapper;
import com.test.cbd.service.UserService;
import com.test.cbd.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service
 * @author zxj
 * @date 2018-10-24 23:39:38
 * 版权所有：@zxj 版权所有(C) 2018
 */
@Service
public class UserServiceImpl
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO login(String username, String password) {
        UserVO userVO = userMapper.findById(username);
        System.out.println(userVO.getName()+"=====================");
        return null;
    }
}