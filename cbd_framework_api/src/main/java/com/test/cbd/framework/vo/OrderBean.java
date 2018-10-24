package com.test.cbd.framework.vo;

import java.io.Serializable;

/**
 * <br>
 * <b>功能：</b>排序bean<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public class OrderBean implements Serializable{
	//-- 公共变量 --//
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	/**
	 * 排序属性1，do中的属性
	 */
	protected String orderBy = null;
	//String[] orderBys = orderBy.split(",");多个排序字段时用','分隔.
	/**
	 * asc or desc
	 */
	protected String order = null;

	/**
	 * 排序字段2，限定最多两个排序字段
	 */
	protected String orderBy2 = null;

	protected String order2 = null;


	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy2() {
		return orderBy2;
	}

	public void setOrderBy2(String orderBy2) {
		this.orderBy2 = orderBy2;
	}

	public String getOrder2() {
		return order2;
	}

	public void setOrder2(String order2) {
		this.order2 = order2;
	}
}
