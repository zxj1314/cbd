package com.hdvon.nmp.vcase.admin.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hdvon.client.service.IUserService;
import com.hdvon.client.vo.UserVo;
import com.hdvon.nmp.vcase.admin.vo.DepartmentVO;
import com.hdvon.nmp.vcase.admin.vo.DictTreeDTO;
import com.hdvon.nmp.vcase.common.Constants;
import com.hdvon.nmp.vcase.common.bean.BeanMapper;
import com.hdvon.nmp.vcase.framework.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hdvon.nmp.vcase.framework.service.BaseBusServiceImpl;
import com.hdvon.nmp.vcase.admin.vo.DepartmentVO;
import com.hdvon.nmp.vcase.admin.domain.DepartmentDO;
import com.hdvon.nmp.vcase.admin.mapper.DepartmentMapper;
import com.hdvon.nmp.vcase.admin.service.DepartmentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service
 * @author chenjiefeng
 * @date 2018-06-13 13:57:21
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Slf4j
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = DepartmentService.class)
@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentServiceImpl extends BaseBusServiceImpl<DepartmentVO, DepartmentDO, DepartmentMapper> implements DepartmentService{

//    private static OrderBean orderBean = new OrderBean();
//    static {
//        orderBean.setOrder("");//此表没有排序
//    }

    @Reference(check=false, version= Constants.DUBBO_VERSION)
    private IUserService userService;

    //定期从联网平台更新部门数据
    @Scheduled(cron = "0 0 1 * * 7 ")
    public void sycDataFormLwpt(){
        log.info("sycDataFormLwpt begin..................");
        List<com.hdvon.client.vo.DepartmentVo> lwptList = userService.getDepartmentTree();
        if(lwptList.size()>2000){
            log.info("sycDataFormLwpt break: list size over 2000");
            return;
        }
        //List lwptList = userService.getDepartmentTree();
        //List<DepartmentDO> list = BeanMapper.mapList(lwptList, DepartmentDO.class);
        Date now = new Date();
        List<DepartmentDO> list = new ArrayList();
        for(com.hdvon.client.vo.DepartmentVo departmentVo: lwptList){
            DepartmentDO departmentDO =BeanMapper.map(departmentVo, DepartmentDO.class);
            departmentDO.setValid(true);
            departmentDO.setCreateTime(now);
            departmentDO.setUpdateTime(now);
            list.add(departmentDO);
        }
        if(list.size() > 0){
            //清空数据
            dao.truncateTable();
            //批量插入数据
            dao.batchInsert(list);
        }
    }

    public List<TreeNodeVO> validTree(){
        List<TreeNodeVO> treeNodeList = new ArrayList<>();
        DepartmentVO paramVo = new DepartmentVO();
        paramVo.setPid("0");
        paramVo.setValid(true);
        dao.findBy(new QueryParam(paramVo)).forEach(departmentVO ->
                treeNodeList.add(configToNode(departmentVO))
        );
        return treeNodeList;
    }

    @Override
    public int countNum() {
        return this.dao.countNum();
    }

    private TreeNodeVO configToNode(DepartmentVO vo){
        TreeNodeVO treeNodeVO = TreeNodeVO.builder()
                .id(vo.getId())
                .pid(vo.getPid())
                .code(vo.getDepCode())
                .title(vo.getName())
                .build();

        List<TreeNodeVO> treeNodeList = new ArrayList<>();
        DepartmentVO paramVo = new DepartmentVO();
        paramVo.setPid(vo.getId());
        paramVo.setValid(true);
        dao.findBy(new QueryParam(paramVo)).forEach(departmentVO ->
                treeNodeList.add(configToNode(departmentVO))
        );
        if(treeNodeList.size() >0 ){
            treeNodeVO.setExpand(true);
            treeNodeVO.setChildren(treeNodeList);
        }else{
            treeNodeVO.setExpand(false);
        }
        return treeNodeVO;
    }


    public PageResult<TreeNode> findTreePage(QueryParam queryParam){
        PageResult<DepartmentVO> pageResultTemp = findPage(queryParam);
        List<TreeNode> treeNodeList = new ArrayList<>();
        pageResultTemp.getDataList().forEach(tempVO -> treeNodeList.add(configToNode1(tempVO)) );
        pageResultTemp.setDataList(null);
        PageResult<TreeNode> pageResult = new PageResult();
        BeanMapper.copy(pageResultTemp, pageResult);
        pageResult.setDataList(treeNodeList);
        return pageResult;
    }

    private TreeNode configToNode1(DepartmentVO vo) {
        return TreeNode.builder()
                .id(vo.getDepCode())
                .pid(vo.getParentDepCode())
                .code(vo.getDepCode())
                .title(vo.getName())
                .build();
    }

    public DictTreeDTO findAllByCode(String code){
        DictTreeDTO dto = new DictTreeDTO();
        List<TreeNode> treeNodes = new ArrayList();
        DepartmentVO vo = dao.findByCode(code);
        if(vo != null){
            treeNodes.add(configToNode1(vo));
            addParentNode(treeNodes, vo.getParentDepCode());

            PageBean pageBean = new PageBean();
            pageBean.setPageSize(30);
            pageBean.setPageNum(1);
            QueryParam queryParam = new QueryParam();
            queryParam.setPageBean(pageBean);
            DepartmentVO paramVO = new DepartmentVO();
            paramVO.setParentDepCode(code);
            queryParam.setParamBean(paramVO);
            PageResult<DepartmentVO> pageResultTemp = findPage(queryParam);
            pageResultTemp.getDataList().forEach(tempVO -> treeNodes.add(configToNode1(tempVO)) );
            if(pageResultTemp.getTotal()>30){
                dto.setHasMore(true);
            }
        }
        dto.setTreeNodes(treeNodes);
        return dto;
    }

    private void addParentNode(List<TreeNode> treeNodeVOs, String parentCode) {
        if(StringUtils.isNotBlank(parentCode) && !"0".equals(parentCode)){
            DepartmentVO vo = dao.findByCode(parentCode);
            if(vo != null){
                treeNodeVOs.add(configToNode1(vo));
                addParentNode(treeNodeVOs, vo.getParentDepCode());
            }
        }
    }


}