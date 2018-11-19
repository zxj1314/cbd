package com.hdvon.nmp.vcase.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hdvon.client.service.IUserService;
import com.hdvon.nmp.vcase.admin.vo.DepartmentVO;
import com.hdvon.nmp.vcase.admin.vo.DictTreeDTO;
import com.hdvon.nmp.vcase.common.Constants;
import com.hdvon.nmp.vcase.framework.response.HttpResponse;
import com.hdvon.nmp.vcase.framework.response.ResponseCode;
import com.hdvon.nmp.vcase.framework.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.hdvon.nmp.vcase.framework.controller.BaseController;
import com.hdvon.nmp.vcase.admin.service.DepartmentService;
import com.hdvon.nmp.vcase.admin.vo.DepartmentVO;

import java.util.List;

/**
 * Controller
 * @author chenjiefeng
 * @date 2018-06-13 13:57:21
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Slf4j
@Api(value="部门表",description="部门表")
@RestController
@RequestMapping("admin/department")
public class DepartmentController extends BaseController<DepartmentVO> {

    @Reference
    private DepartmentService departmentService;
    @Reference(check=false, version= Constants.DUBBO_VERSION)
    private IUserService userService;

    @Override
    protected DepartmentService getBaseService() {
        return  departmentService;
    }

    @ApiOperation(value = "返回有效部门树", notes = "返回有效部门树", httpMethod = "GET")
    @RequestMapping(value = "/tree",method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<TreeNodeVO>> validTree(){
        return new HttpResponse(this.getBaseService().validTree());
    }

    @ApiOperation(value = "返回有效部门列表", notes = "返回有效部门列表", httpMethod = "GET")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<DepartmentVO>> validList(){
        QueryParam queryParam = new QueryParam();
        DepartmentVO  vo = new DepartmentVO();
        vo.setValid(true);
        queryParam.setParamBean(vo);
        return new HttpResponse(this.getBaseService().findBy(queryParam));
    }


    @ApiOperation(value = "返回部门用户列表", notes = "返回部门用户列表", httpMethod = "GET")
    @RequestMapping(value = "{depId}/user",method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getUserListByDepId(@PathVariable String depId){
        try {
            return new HttpResponse(userService.getUserListByDepId(depId));
        } catch (Exception e) {
            return new HttpResponse(ResponseCode.FAILURE);
        }
    }

    @ApiOperation(value = "根据父编码查询下级", notes = "根据父编码查询下级", httpMethod = "GET")
    @RequestMapping(value = "/treeNode",method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<PageResult<TreeNode>> treeNode(String parentCode){
        if(StringUtils.isNotBlank(parentCode)) {
            DepartmentVO DepartmentVO = new DepartmentVO();
            DepartmentVO.setParentDepCode(parentCode);

            PageBean pageBean = new PageBean();
            pageBean.setPageSize(30);
            pageBean.setPageNum(1);
            QueryParam queryParam = new QueryParam();
            queryParam.setPageBean(pageBean);
            queryParam.setParamBean(DepartmentVO);
            return new HttpResponse(getBaseService().findTreePage(queryParam));
        }

        return new HttpResponse(ResponseCode.ERROR_CODE, "父编码不能为空");
    }

    @ApiOperation(value = "根据关键字查询", notes = "根据关键字查询", httpMethod = "GET")
    @RequestMapping(value = "/findByKeyword",method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<PageResult<TreeNode>> findByKeyword(String keyword){
        if(StringUtils.isNotBlank(keyword)){
            DepartmentVO DepartmentVO = new DepartmentVO();
            if(keyword.matches("[a-zA-Z]+")){//英文
                DepartmentVO.setDescriptionLike(keyword);
            }else if(keyword.matches("[0-9]+")){//数字
                DepartmentVO.setCodeLike(keyword);
            }else{
                DepartmentVO.setTextLike(keyword);//中文
            }

            PageBean pageBean = new PageBean();
            pageBean.setPageSize(30);
            pageBean.setPageNum(1);
            QueryParam queryParam = new QueryParam();
            queryParam.setPageBean(pageBean);
            queryParam.setParamBean(DepartmentVO);
            return new HttpResponse(getBaseService().findTreePage(queryParam));
        }

        return new HttpResponse(ResponseCode.ERROR_CODE, "关键字不能为空");
    }

    @ApiOperation(value = "根据编码查询完整树", notes = "根据编码查询完整树", httpMethod = "GET")
    @RequestMapping(value = "/findAllByCode",method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<DictTreeDTO> findAllByCode(String code){
        if(StringUtils.isNotBlank(code)){
            return new HttpResponse(getBaseService().findAllByCode(code));
        }

        return new HttpResponse(ResponseCode.ERROR_CODE, "关键字不能为空");
    }


}