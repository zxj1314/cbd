package com.test.cbd.framework.bean;

import java.io.Serializable;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>（业务层）用户令牌信息<br>
 *     UserToken是纯javaBean，不与模块耦合。可以在后端服务（service\dao）直接传递
 *     UserToken只包括简单的数据对象，不负责处理是否有api权限，只负责处理用户数据权限
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2018-3-1 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2018<br>
 */
@SuppressWarnings("serial")
public class UserToken implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户代码
     */
    private String userCode;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 部门ID
     */
    private String deptId;

    //部门code
    private String deptCode;

    //部门名称
    private String deptName;

    /**
     * 是否管理员
     */
    private boolean isAdmin;

    /**
     * 令牌(一个用户同一时间用不同令牌访问系统后端)
     */
    private String token;
    /**
     * 登录设备类型
     */
    private DeviceInfo deviceInfo;

    /**
     * 是否开启权限过滤
     */
    private boolean filter;

    //将用户数据权限先放到一个表中
    //mysql中#{permissionSql}
    private String permissionSql;

    //将用户数据权限放到list
    //mysql中使用for each
    private List<Object> permissionList;

    public UserToken() {

    }

    public UserToken(String token) {
        this.token = token;
    }

    public UserToken(String userId, String userCode, String userName, boolean isAdmin) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
