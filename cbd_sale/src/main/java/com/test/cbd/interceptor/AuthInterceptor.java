package com.test.cbd.interceptor;

import com.google.gson.Gson;
import com.test.cbd.auth.FilterRuleEnum;
import com.test.cbd.auth.PermissionAuth;
import com.test.cbd.auth.PermissionAuthSupport;
import com.test.cbd.framework.bean.SessionInfo;
import com.test.cbd.framework.bean.UserInfo;
import com.test.cbd.framework.bean.UserToken;
import com.test.cbd.framework.context.ContextHelper;
import com.test.cbd.framework.context.SessionHelper;
import com.test.cbd.framework.controller.BaseController;
import com.test.cbd.framework.response.HttpResponse;
import com.test.cbd.framework.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 功能：用户登录和权限认证
 * 作者：chenjiefeng
 * 日期：2018/3/29
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private SessionHelper sessionHelper;

    public AuthInterceptor(SessionHelper sessionHelper) {
        this.sessionHelper = sessionHelper;
    }


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        PermissionAuth permissionAuth = null;
        boolean isBaseCrudMethod = false;//在Controller已注解的条件下判断是否是 BaseController类里面的方法。因为这个类里面的方法没法单独配置

        HandlerMethod handlerMethod = null;
        Method method = null;
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            handlerMethod = (HandlerMethod)handler;
            method = handlerMethod.getMethod();

            permissionAuth = method.getAnnotation(PermissionAuth.class); //获取方法的权限注解
            if(permissionAuth == null){//方法中没有配置。直接拿类的配置
                Class<?> thisClazz = handlerMethod.getBeanType();//获取具体子类Controller
                log.debug("------------------------------ HandlerInterceptor thisClazz="+thisClazz.getName());
                permissionAuth = thisClazz.getAnnotation(PermissionAuth.class); //获取具体子类Controller的权限注解

                if(permissionAuth != null){
                    Class<?> declaringClazz = method.getDeclaringClass();//B继承A: 如果方法在A中，则declaringClazz是A； 如果方法在B中，则declaringClazz是B
                    log.debug("------------------------------ HandlerInterceptor declaringClazz="+declaringClazz.getName());
                    if(declaringClazz.getName().equals(BaseController.class.getName())){
                        //判断是否是BaseCrudController类里面的方法
                        isBaseCrudMethod = true;
                    }
                }
            }
        }
        /* 注意permissionAuth有可能等于null */
        FilterRuleEnum filterRuleEnum = PermissionAuthSupport.getFilterRule(permissionAuth, isBaseCrudMethod);

        if(filterRuleEnum == (FilterRuleEnum.VIEW)){
            //view:不需session直接查询
            return true;
        }

        int responseCode = ResponseCode.TOKEN_INVALID.getCode();

        String token = httpServletRequest.getHeader("token");
        if (token == null || token.isEmpty()) {
            token = httpServletRequest.getParameter("token");
        }
        if (token == null || token.isEmpty()) {
            log.error("请重新登录");
            returnErrorStatus(httpServletResponse, responseCode, "请重新登录");
            return false;
        }

        SessionInfo sessionInfo = sessionHelper.getSession(token);
        if (sessionInfo == null) {
            log.error("会话超时，请重新登录");
            returnErrorStatus(httpServletResponse,  responseCode, "会话超时，请重新登录");
            return false;
        }

        if(filterRuleEnum == (FilterRuleEnum.ANON)){
            //ANON: 有session就通过
            ContextHelper.setSessionInfo(sessionInfo);
            return true;
        }

        /* FilterRuleEnum.USER */

        UserInfo userInfo = sessionHelper.getUserInfo(sessionInfo.getUserId());
        if (userInfo == null) {
            log.error("用户已登出，请重新登录");
            returnErrorStatus(httpServletResponse,  responseCode, "用户已登出，请重新登录");
            return false;
        }

        //验证用户权限begin-------------
        if(filterRuleEnum == (FilterRuleEnum.USER) && permissionAuth != null) {
            boolean authOk = auth(permissionAuth, userInfo);//权限认证是否成功
            if(isBaseCrudMethod || StringUtils.isNotBlank(permissionAuth.defPermPrefix())){//父类的方法
                String permKey = permissionAuth.defPermPrefix()+method.getName();
                if(userInfo.getPermsMap().containsKey(permKey)) {//含有权限
                    authOk = true;
                }
            }
            if (!authOk) {
                log.error("用户没有权限访问该接口");
                returnErrorStatus(httpServletResponse,  responseCode, "用户没有权限访问该接口");
                return false;
            }
        }
        //验证用户权限end-------------

        /*
        if(!userMultiLogin) {
            if (!sessionInfo.getSessionId().equals(userInfo.getCurSessionId())) {
                log.error("用户已被强制下线！");
                returnErrorStatus(httpServletResponse,  responseCode, "用户已被强制下线");
                return false;
            }
        }*/

        UserToken userToken = new UserToken(sessionInfo.getSessionId());
        userToken.setUserId(userInfo.getUserId());
        userToken.setUserCode(userInfo.getUserCode());
        userToken.setUserName(userInfo.getUserName());
        userToken.setDeptId(userInfo.getDeptId());
        userToken.setDeptCode(userInfo.getDeptCode());
        userToken.setDeptName(userInfo.getDeptName());
        userToken.setAdmin(userInfo.isAdmin());
        ContextHelper.setUserToken(userToken);
        if((System.currentTimeMillis() - sessionInfo.getUpdateTime()) > 120000){
            sessionInfo.setUpdateTime(System.currentTimeMillis());
            sessionHelper.updateSession(sessionInfo, userInfo, false);
        }

        return true;
    }

    private boolean auth(PermissionAuth permissionAuth, UserInfo userInfo){
        if(userInfo == null) {
            return false;
        }

        boolean authOk = false;//权限认证是否成功
        if(permissionAuth.perm()!=null || permissionAuth.role()!=null){
            boolean permOk = false;
            if(permissionAuth.perm()!=null){//验证权限
                if(userInfo.getPermsMap()!= null){
                    if(permissionAuth.permOr() == true){//or模式
                        for(String permKey: permissionAuth.perm()){
                            if(userInfo.getPermsMap().containsKey(permKey)){//含有权限
                                permOk = true;
                                break;
                            }
                        }
                    }else{//and模式
                        for(String permKey: permissionAuth.perm()){
                            if(!userInfo.getPermsMap().containsKey(permKey)){//不含有权限
                                permOk = false;
                                break;
                            }else{
                                permOk = true;
                            }
                        }
                    }
                }
                if(permOk && (permissionAuth.role()==null || permissionAuth.roleOrPerm())){
                    authOk = true;
                }
            }else{//=null不用验证
                permOk = true;
            }

            if(!authOk){
                boolean roleOk = false;
                if(permissionAuth.role()!=null){//验证角色
                    if(userInfo.getRolesMap()!= null){
                        if(permissionAuth.roleOr() == true){//or模式
                            for(String roleKey: permissionAuth.role()){
                                if(userInfo.getRolesMap().containsKey(roleKey)){//含有角色
                                    roleOk = true;
                                    break;
                                }
                            }
                        }else{//and模式
                            for(String roleKey: permissionAuth.role()){
                                if(!userInfo.getRolesMap().containsKey(roleKey)){//不含有角色
                                    roleOk = false;
                                    break;
                                }else{
                                    roleOk = true;
                                }
                            }
                        }
                    }
                }else{//=null不用验证
                    roleOk = true;
                }
                if(roleOk && permOk){
                    authOk = true;
                }
            }

        }
        return authOk;
    }


    private void returnErrorStatus(HttpServletResponse httpServletResponse, int code, String message) {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");

        try (PrintWriter out = httpServletResponse.getWriter()) {
            HttpResponse response = new HttpResponse(code, message);
            out.write(new Gson().toJson(response));
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        //not used
    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //not used
    }
}
