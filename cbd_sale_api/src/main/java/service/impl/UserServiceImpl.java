package service.impl;

import com.test.cbd.domain.UserDO;
import com.test.cbd.framework.service.impl.BaseBusServiceImpl;
import com.test.cbd.mapper.UserMapper;
import com.test.cbd.vo.UserVO;
import service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service
 * @author mmh
 * @date 2018-06-11 15:58:43
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseBusServiceImpl<UserVO, UserDO, UserMapper> implements UserService{

}