package com.test.cbd.framework.vo;


import com.test.cbd.framework.bean.UserToken;

import java.io.Serializable;


/**
 * <br>
 * <b>功能：</b>DAO查询参数<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
@SuppressWarnings("serial")
public class QueryParam implements Serializable {

	private PageBean pageBean;

	private OrderBean orderBean;

	private Object paramBean;//VO或者VO的子类，(DTO需要注意深度取值的问题)

	private UserToken userToken;//加入token，可以用来进行数据查询结果的权限过滤

	public QueryParam() {

	}

	public QueryParam(Object paramBean) {
		this.paramBean = paramBean;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	public Object getParamBean() {
		return paramBean;
	}

	public void setParamBean(Object paramBean) {
		this.paramBean = paramBean;
	}

	public UserToken getUserToken() {
		return userToken;
	}

	public void setUserToken(UserToken userToken) {
		this.userToken = userToken;
	}
}
