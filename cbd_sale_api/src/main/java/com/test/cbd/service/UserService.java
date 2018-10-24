package com.test.cbd.service;

import com.test.cbd.framework.service.BaseBusService;
import com.test.cbd.vo.UserVO;

/**
 * service接口：
 * @author zxj
 * @date 2018-10-24 23:39:38
 * 版权所有：@撸码识途 版权所有(C) 2018
 */
public interface UserService extends BaseBusService<UserVO> {

    UserVO login(String username,String password);

}
