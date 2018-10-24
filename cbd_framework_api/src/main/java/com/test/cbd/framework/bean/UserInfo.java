package com.test.cbd.framework.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>用户权限信息缓存，由用户登录时创建，超时回收。用了缓存用户是否有访问该api的权限<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2018-3-1 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2018<br>
 */
public class UserInfo implements Serializable {

    private String userId;

    //用户代码
    private String userCode;

    //用户名称
    private String userName;

    //部门ID
    private String deptId;

    //部门code
    private String deptCode;

    //部门名称
    private String deptName;

    //是否管理员
    private boolean isAdmin;

    private long expireTime;//失效时长，秒

    private long createTime;//创建时间，毫秒

    private long updateTime;//更新时间，毫秒

    private Map<String, String> rolesMap = null; //用户角色(如果有改动，需更新缓存)
    private Map<String, String> permsMap = null; //用户权限(如果有改动，需更新缓存)

    //json的序列化有缺陷。value不要放太复杂的类
    //不建议放太多东西进去
    //如果要放入其它对象，先用json转字符串
    private Map<String, String> attribute = new HashMap<String, String>();

    private boolean userMultiLogin;//允许用户多地登录

    private String curSessionId;//（如果需要）同一时间只能有一个pc浏览器登录

    public UserInfo(){

    }

    public String getUserId() {
        return userId;
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

    public void setUserId(String userId) {
        this.userId = userId;
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

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public Map<String, String> getRolesMap() {
        return rolesMap;
    }

    public void setRolesMap(Map<String, String> rolesMap) {
        this.rolesMap = rolesMap;
    }

    public Map<String, String> getPermsMap() {
        return permsMap;
    }

    public void setPermsMap(Map<String, String> permsMap) {
        this.permsMap = permsMap;
    }

    public Map<String, String> getAttribute() {
        return attribute;
    }

    public void setAttribute(Map<String, String> attribute) {
        this.attribute = attribute;
    }

    public String getAttributes(String key) {
        if(key==null || key.trim().equals("")){
            return null;
        }
        return attribute.get(key);
    }

    public void setAttribute(String key, String value) {
        if(key!=null && !key.trim().equals("")){
            attribute.put(key, value);
        }
    }

    public boolean isUserMultiLogin() {
        return userMultiLogin;
    }

    public void setUserMultiLogin(boolean userMultiLogin) {
        this.userMultiLogin = userMultiLogin;
    }

    public String getCurSessionId() {
        return curSessionId;
    }

    public void setCurSessionId(String curSessionId) {
        this.curSessionId = curSessionId;
    }
}
