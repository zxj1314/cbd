package com.test.cbd.framework.bean;

import java.io.Serializable;

/**
 * <br>
 * <b>功能：</b>session信息，保存在缓存<br>
 *     一个用户可以用多个设备登录，也可能多地登录，所以要用sessionInfo来区分
 *     不与模块耦合。只在web容器使用，
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2018-3-1 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2018<br>
 */
@SuppressWarnings("serial")
public class SessionInfo implements Serializable {

	/**
	 * 回话ID
	 */
	private String sessionId;

	private String userId;

	private long expireTime = 1800; //默认1800秒

	private long loginTime; //毫秒

	private long updateTime; //毫秒

	private boolean debug;//是否是debug状态

	public SessionInfo() {

	}

	public SessionInfo(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
}
