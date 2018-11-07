package com.test.cbd.service.impl;

import com.test.cbd.framework.service.impl.BaseBusServiceImpl;
import com.test.cbd.vo.SysPermission;
import com.test.cbd.vo.SysRole;
import com.test.cbd.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.cbd.vo.UserVO;
import com.test.cbd.domain.UserDO;
import com.test.cbd.mapper.UserMapper;
import com.test.cbd.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Service
 * @author zxj
 * @date 2018-10-24 23:39:38
 * 版权所有：@zxj 版权所有(C) 2018
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseBusServiceImpl<UserVO, UserDO, UserMapper>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO login(String username, String password) {
        UserVO userVO = userMapper.findById(username);
        return userVO;
    }

    public UserInfo findByUsername(String userName){
        SysRole admin = SysRole.builder().role("admin").build();
        List<SysPermission> list=new ArrayList<SysPermission>();
        SysPermission sysPermission=new SysPermission("read");
        SysPermission sysPermission1=new SysPermission("write");
        list.add(sysPermission);
        list.add(sysPermission1);
        admin.setPermissions(list);
        UserInfo root = UserInfo.builder().userName("root").password("b1ba853525d0f30afe59d2d005aad96c").credentialsSalt("123").state(0).build();
        List<SysRole> roleList=new ArrayList<SysRole>();
        roleList.add(admin);
        root.setRoleList(roleList);
        return root;
    }
}