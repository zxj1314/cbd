package com.test.cbd.framework.controller;

import com.test.cbd.framework.exception.BizException;
import com.test.cbd.framework.exception.BizRuntimeException;
import com.test.cbd.framework.response.HttpResponse;
import com.test.cbd.framework.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * <br>
 * <b>功能：</b>处理类型转换以及异常<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
@Slf4j
@ControllerAdvice
public class BaseControllerAdvice {

	/**
	 * 处理数据类型的装换
	 * @param binder
	 * @param webRequest
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest webRequest) {
		/*
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Short.class, new CustomNumberEditor(Short.class, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
		binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
		binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, new DecimalFormat(NumberUtils.PATTERN_DEFAULT_MONEY), true));
		binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat(DateUtils.PATTERN_DEFAULT_DATE), true));
		binder.registerCustomEditor(java.sql.Timestamp.class, new CustomDateEditor(new SimpleDateFormat(DateUtils.PATTERN_DEFAULT_DATETIME), true));
		*/
		binder.registerCustomEditor(java.util.Date.class, new MyCustomDateEditor());
	}

	/**
	 * @ExceptionHandler定义全局异常处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BizRuntimeException.class)
	public @ResponseBody
	HttpResponse handleBizRuntimeException(BizRuntimeException ex) {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setResult(false);
		httpResponse.setCode(ResponseCode.FAILURE.getCode());
		httpResponse.setMessage(StringUtils.defaultIfBlank(ex.getMessage(), "系统业务异常。"));
		return httpResponse;
	}

	/**
	 * @ExceptionHandler定义全局异常处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BizException.class)
	public @ResponseBody
    HttpResponse handleBizException(BizException ex) {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setResult(false);
		httpResponse.setCode(ResponseCode.FAILURE.getCode());
		httpResponse.setMessage(StringUtils.defaultIfBlank(ex.getMessage(), "系统业务异常。"));
		return httpResponse;
	}

	/**
	 * @ExceptionHandler定义全局异常处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody
    HttpResponse handleOtherExceptions(Exception ex) {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setResult(false);
		httpResponse.setCode(ResponseCode.FAILURE.getCode());
		httpResponse.setMessage(ex.getMessage());  // 大杰说改这里
		log.error("system exception", ex);
		return httpResponse;
	}

	/**
	 * 此处使用@ModelAttribute注解将键值对添加到全局，所有注解了@RequestMapping的方法可获得此键值对
	 * @param model
	 */
	//@ModelAttribute
	public void addAttributes(Model model){
		//model.addAttribute("msg", "额外信息");
	}

}
