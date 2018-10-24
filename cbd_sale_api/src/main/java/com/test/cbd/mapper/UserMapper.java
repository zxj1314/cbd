package com.test.cbd.mapper;

import com.test.cbd.framework.dao.BaseDAO;
import com.test.cbd.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import com.test.cbd.domain.UserDO;

/**
 * DAO
 * @author zxj
 * @date 2018-10-24 23:39:38
 * 版权所有：@撸码识途 版权所有(C) 2018
 */
@Mapper
public interface UserMapper extends BaseDAO<UserVO, UserDO> {

}
