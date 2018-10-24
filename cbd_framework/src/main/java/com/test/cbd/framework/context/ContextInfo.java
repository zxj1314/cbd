package com.test.cbd.framework.context;

import java.util.WeakHashMap;

/**
 * <br>
 * <b>功能：</b>上下文信息<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public class ContextInfo extends WeakHashMap<String, Object> {

	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	public Object getParameter(String name) {
		return this.get(name);
	}

	/**
	 * 设置参数
	 * @param name
	 * @param value
	 */
	public void setParameter(String name, Object value) {
		this.put(name, value);
	}

	/**
	 * 移除参数
	 * @param name
	 */
	public Object removeParameter(String name) {
		return this.remove(name);
	}
}