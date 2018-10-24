package com.test.cbd.framework.service;

import java.util.Date;

/**
 * <br>
 * <b>功能：</b>统一的日志接口，需要实现<br>
 *     所有日志，必须统一使用此接口
 *     实现类中，可以调用DAO，也可以远程调用微服务
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */

public interface LogService {
	/**
	 * 记录登录日志
	 * @param userId
	 * @param userCode
	 * @param loginIp
	 * @param loginTime
	 * @param loginType
	 */
	void insertLoginLog(String userId, String userCode, String loginIp, Date loginTime, int loginType);
	
	
	/**
	 * 记录一般的业务日志
	 * @param userId
	 * @param moduleId
	 * @param operateText
	 * @param operateTime
	 */
	void insertBussLog(String userId, String moduleId, String operateText, Date operateTime);
}
