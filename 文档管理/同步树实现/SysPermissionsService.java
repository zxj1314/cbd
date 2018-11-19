package com.hdvon.rcp.admin.service;

import com.hdvon.common.bean.BeanMapper;
import com.hdvon.core.framework.query.QueryParam;
import com.hdvon.core.framework.vo.PageBean;
import com.hdvon.core.framework.vo.PageResult;
import com.hdvon.core.framework.vo.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hdvon.core.framework.service.BaseBusService;
import com.hdvon.rcp.admin.vo.SysPermissionsVO;
import com.hdvon.rcp.admin.domain.SysPermissionsDO;
import com.hdvon.rcp.admin.mapper.SysPermissionsMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhuxiaojin
 * @Date 2018-11-10
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Service
@Transactional
public class SysPermissionsService extends BaseBusService<SysPermissionsVO, SysPermissionsDO, SysPermissionsMapper> {

    @Autowired
    private SysPermissionsMapper sysPermissionsMapper;


    /**
     * 返回有效权限树
     * @param
     * @return
     * @Author zxj
     */
    public List<TreeNode> getTree() {
       //所有的数据集合
        List<TreeNode> firstTree = dao.getTreeNode();
        // 返回的结果集
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        // 先找到所有的一级菜单
        for (int i = 0; i < firstTree.size(); i++) {
            // 一级菜单pid为0
            if (firstTree.get(i).getPid()==0) {
                treeNodeList.add(firstTree.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (TreeNode treeNode : treeNodeList) {
            treeNode.setChildren(getChild(treeNode.getId(), firstTree));
        }
        return  treeNodeList;
    }

    /**
     * 递归算法解析成树形结构
     * @param
     * @return
     * @Author zxj
     */
    public List<TreeNode> getChild(Integer id, List<TreeNode> firstTree) {
        // 子菜单
        List<TreeNode> childList = new ArrayList<>();
        for (TreeNode treeNode : firstTree) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (treeNode.getId()!=null) {
                if (treeNode.getPid().equals(id)) {
                    childList.add(treeNode);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (TreeNode treeNode : childList) {
            // 递归
            treeNode.setChildren(getChild(treeNode.getId(), firstTree));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }





}