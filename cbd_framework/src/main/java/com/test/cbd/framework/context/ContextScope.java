package com.test.cbd.framework.context;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public class ContextScope {

	/**
	 * 上下文线程变量
	 */
	private final static ThreadLocal<ContextInfo> threadContext = new InheritableThreadLocal<ContextInfo>() {
		@Override
		protected ContextInfo initialValue() {
			return new ContextInfo();
		}
	};

	/**
	 * 获取参数值
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getParameter(String name) {
		return (T) threadContext.get().getParameter(name);
	}

	/**
	 * 设置参数
	 * @param name
	 * @param value
	 */
	public static void setParameter(String name, Object value) {
		threadContext.get().setParameter(name, value);
	}

	/**
	 * 移除参数
	 * @param name
	 */
	@SuppressWarnings("unchecked")
	public static <T> T remove(String name) {
		return (T) threadContext.get().removeParameter(name);
	}

	/**
	 * 释放上下文信息
	 */
	public static void releaseContext() {
		threadContext.remove();
	}

}
