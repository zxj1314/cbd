package com.test.cbd.framework.controller;

import com.test.cbd.framework.context.ContextHelper;
import com.test.cbd.framework.response.HttpResponse;
import com.test.cbd.framework.service.BaseBusService;
import com.test.cbd.framework.vo.BaseVO;
import com.test.cbd.framework.vo.PageResult;
import com.test.cbd.framework.vo.QueryDTO;
import com.test.cbd.framework.vo.QueryParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <br>
 * <b>功能：</b>增、删、改和查的基础controller类<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
@Slf4j
public abstract class BaseController<V extends BaseVO>{

	protected abstract BaseBusService<V> getBaseService();

	@ApiOperation(value = "增加对象", notes = "增加对象", httpMethod = "POST")
	@RequestMapping(value = "",method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse add(@RequestBody V vo){
		return new HttpResponse(this.getBaseService().insert(ContextHelper.getUserToken(), vo));
	}

	@ApiOperation(value = "删除对象", notes = "增加对象", httpMethod = "DELETE")
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public HttpResponse remove(@PathVariable String id){
		this.getBaseService().deleteById(ContextHelper.getUserToken(), id);
		return new HttpResponse();
	}

	@ApiOperation(value = "修改对象", notes = "修改对象", httpMethod = "PUT")
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	@ResponseBody
	public HttpResponse update(@PathVariable String id, @RequestBody V vo){
		this.getBaseService().update(ContextHelper.getUserToken(), vo);
		return new HttpResponse();
	}

	@ApiOperation(value = "获取对象", notes = "获取对象", httpMethod = "GET")
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse get(@PathVariable String id){
		HttpResponse response = new HttpResponse();
		response.setData(this.getBaseService().findById(id));
		return response;
	}

	@ApiOperation(value = "条件查询返回分页", notes = "条件查询返回分页", httpMethod = "GET")
	@RequestMapping(value = "/page",method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse<PageResult<V>> page(@ModelAttribute QueryDTO queryDTO, @ModelAttribute V vo){
		return new HttpResponse(this.getBaseService().findPage(convert2QueryParam(queryDTO, vo)));
	}

	protected QueryParam convert2QueryParam(QueryDTO queryDTO, V vo){
		QueryParam queryParam = new QueryParam();
		if(queryDTO != null){
			queryParam.setPageBean(queryDTO.getPageBean());
			queryParam.setOrderBean(queryDTO.getOrderBean());
			if(vo != null){
				queryParam.setParamBean(vo);
			}
			//queryParam.setParamBean(queryDTO.getParamBean());
			queryParam.setUserToken(ContextHelper.getUserToken());
		}
		return queryParam;
	}

	protected HttpResponse resultAllErrors(BindingResult result) {

		List<ObjectError> allErrors = result.getAllErrors();
		StringBuilder mess =new StringBuilder();
		for(ObjectError objectError :allErrors){
			String defaultMessage = objectError.getDefaultMessage();
			mess.append(defaultMessage);
		}
		return new HttpResponse(mess);
	}
}
