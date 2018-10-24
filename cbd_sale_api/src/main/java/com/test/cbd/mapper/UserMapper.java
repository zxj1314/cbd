package com.test.cbd.mapper;


import com.test.cbd.domain.UserDO;
import com.test.cbd.framework.dao.BaseDAO;
import com.test.cbd.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * DAO
 * @author mmh
 * @date 2018-06-11 16:19:21
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Mapper
public interface UserMapper extends BaseDAO<UserVO, UserDO> {

}
