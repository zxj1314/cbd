package com.test.cbd.service.impl;

import com.test.cbd.domain.UserDO;
import com.test.cbd.framework.service.impl.BaseBusServiceImpl;
import com.test.cbd.mapper.UserMapper;
import com.test.cbd.service.DubboUserService;
import com.test.cbd.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Service
 * @author zxj
 * @date 2018-10-24 23:39:38
 * 版权所有：@zxj 版权所有(C) 2018
 */
@Slf4j
@Component
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = DubboUserService.class,token = "123456")//全局设置不了token
public class DubboUserServiceImpl extends BaseBusServiceImpl<UserVO, UserDO, UserMapper>
    implements DubboUserService{

    @Override
    public UserVO login(String username, String password) {
        try {
            System.out.println("before+login==========");
            Thread.sleep(5000);
            System.out.println("after+login==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}