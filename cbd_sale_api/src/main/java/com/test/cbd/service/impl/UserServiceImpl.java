package com.test.cbd.service.impl;

import com.test.cbd.framework.service.impl.BaseBusServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.test.cbd.vo.UserVO;
import com.test.cbd.domain.UserDO;
import com.test.cbd.mapper.UserMapper;
import com.test.cbd.service.UserService;

/**
 * Service
 * @author zxj
 * @date 2018-10-24 23:39:38
 * 版权所有：@撸码识途 版权所有(C) 2018
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl extends BaseBusServiceImpl<UserVO, UserDO, UserMapper>
    implements UserService{

    @Override
    public UserVO login(String username, String password) {
        return null;
    }
}