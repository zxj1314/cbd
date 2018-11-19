package com.hdvon.rcp.admin.mapper;

import com.hdvon.core.framework.query.QueryParam;
import com.hdvon.core.framework.vo.TreeNode;
import org.apache.ibatis.annotations.Mapper;
import com.hdvon.core.framework.dao.IBaseDAO;
import com.hdvon.rcp.admin.vo.SysPermissionsVO;
import com.hdvon.rcp.admin.domain.SysPermissionsDO;

import java.util.List;

/**
 * DAO
 * @author chenjiefeng
 * @date 2018-11-10 14:30:39
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Mapper
public interface SysPermissionsMapper extends IBaseDAO<SysPermissionsVO, SysPermissionsDO> {


    List<TreeNode> getTreeNode();
}
