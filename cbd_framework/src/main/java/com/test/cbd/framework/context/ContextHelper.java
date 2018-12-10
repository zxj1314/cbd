package com.test.cbd.framework.context;


import com.test.cbd.framework.bean.DeviceInfo;
import com.test.cbd.framework.bean.SessionInfo;
import com.test.cbd.framework.bean.UserToken;

/**
 * <br>
 * <b>功能：</b>上下文帮助类(放到本地线程类中，方便线程后面继续使用)<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public class ContextHelper {

    public final static String CONTEXT_SYS_USER = "CONTEXT_SYS_USER";
	public final static String CONTEXT_SYS_USER_TOKEN = "CONTEXT_SYS_USER_TOKEN";
	public final static String CONTEXT_SYS_USER_DEVICE = "CONTEXT_SYS_USER_DEVICE";

	public final static String CONTEXT_SYS_SESSION = "CONTEXT_SYS_SESSION";


    public static void setUserToken(UserToken user) {
        ContextScope.setParameter(CONTEXT_SYS_USER, user);
    }

	/**
	 * 获取当前用户
	 * @return
	 */
    public static UserToken getUserToken() {
        return (UserToken) ContextScope.getParameter(CONTEXT_SYS_USER);
    }


	/**
	 * 获取用户token
	 * @return
	 */
	public static String getToken() {
		return ContextScope.getParameter(CONTEXT_SYS_USER_TOKEN);
	}

	/**
	 * 设置上下文用户token
	 * @param userToken
	 */
	public static void setToken(String userToken) {
		ContextScope.setParameter(CONTEXT_SYS_USER_TOKEN, userToken);
	}


	/**
	 * 获取使用的设备
	 * @return
	 */
	public static DeviceInfo getUserDevice() {
		return ContextScope.getParameter(CONTEXT_SYS_USER_DEVICE);
	}

	/**
	 * 设置用户使用的设备
	 * @param userDevice
	 */
	public static void setUserDevice(DeviceInfo userDevice) {
		ContextScope.setParameter(CONTEXT_SYS_USER_DEVICE, userDevice);
	}


	/**
	 * 释放上下文信息
	 */
	public static void releaseContext() {
		ContextScope.releaseContext();
	}

	public static void setSessionInfo(SessionInfo sessionInfo) {
		ContextScope.setParameter(CONTEXT_SYS_SESSION, sessionInfo);
	}

	/**
	 * 获取当前SessionInfo
	 * @return
	 */
	public static SessionInfo getSessionInfo() {
		return (SessionInfo) ContextScope.getParameter(CONTEXT_SYS_SESSION);
	}

}
