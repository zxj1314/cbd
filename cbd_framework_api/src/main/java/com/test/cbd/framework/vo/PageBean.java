package com.test.cbd.framework.vo;

import java.io.Serializable;


/**
 * <br>
 * <b>功能：</b>与具体ORM实现无关的分页参数及查询结果封装.<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public class PageBean implements Serializable {

	//-- 分页参数 --//
	protected int pageNum = 1;
	protected int pageSize = -1;

	//-- 构造函数 --//
	public PageBean() {
	}
	public PageBean(int pageNum, int pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


}
