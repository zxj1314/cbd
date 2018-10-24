package com.test.cbd.framework.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>查询分页结果<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public class PageResult<T> implements Serializable {

	//-- 分页参数 --//
	protected int pageNum;
	protected int pageSize;

	//结果
	private long total;//总数据条数
	private int pages;//总页数
	private int size;//当前页数据条数
	private boolean hasPreviousPage;
	private boolean hasNextPage;

	private List<T> dataList = new ArrayList<T>();

	public PageResult(){

	}

	public PageResult(List<T> dataList) {
		this.dataList = dataList;
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

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
