package com.test.cbd.controller;

import com.test.cbd.framework.bean.SessionInfo;
import com.test.cbd.framework.bean.UserInfo;
import com.test.cbd.framework.context.SessionHelper;
import com.test.cbd.framework.response.HttpResponse;
import com.test.cbd.framework.response.ResponseCode;
import com.test.cbd.service.UserService;
import com.test.cbd.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Date;

/**
 * 功能：
 * 作者：chenjiefeng
 * 日期：2018/5/29
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Slf4j
@Api(value="用户登录",description="用户登录")
@RestController
@RequestMapping("/")
public class LoginController {

    //@Autowired
    //private WebConfig config;
    @Autowired
    private SessionHelper sessionHelper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    //@Autowired
    //private LogService logService;



    @ApiOperation(value = "登录并获取token", notes = "登录并获取token", httpMethod = "POST")
    @PostMapping(value = "login")
    public HttpResponse login(String username, String password, boolean force) {
        HttpResponse<String> response = new HttpResponse();
        try {
            UserVO user = userService.login(username, password);
            if(user!=null){
                SessionInfo sessionInfo = new SessionInfo();
                sessionInfo.setUserId(user.getId());
                sessionInfo.setLoginTime(System.currentTimeMillis());

                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(user.getId());
                userInfo.setUserCode(user.getAccount());
                userInfo.setUserName(user.getName());
                userInfo.setDeptId(user.getDepartmentId());
                userInfo.setDeptName(user.getDepartmentName());
                userInfo.setCreateTime(System.currentTimeMillis());
                //userInfo.setUserMultiLogin(config.isUserMultiLogin());

                //保存到session
                response = sessionHelper.createSession(sessionInfo, userInfo, force);

                //logService.insertLoginLog(userInfo.getUserId(),userInfo.getUserCode(),getIpAddr(request), new Date(), 0);
            }
        } catch (Exception e) {
            log.error("login:",e);
            response.setResult(false);
            response.setCode(ResponseCode.FAILURE.getCode());
            response.setMessage("登录异常");
        }
        return response;
    }

    /**
     * @param token
     * @Description 登出
     */
    @ApiOperation(value = "登出并删除token", notes = "登出并删除token", httpMethod = "DELETE")
    @DeleteMapping(value = "logout")
    public HttpResponse logout(String token) {
        sessionHelper.deleteSession(token);
        return new HttpResponse();
    }

    /**
     * @Description: 获取客户端IP地址
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }
}
