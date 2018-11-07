package com.test.cbd.shiro;

import com.alibaba.fastjson.JSONObject;
import com.test.cbd.vo.UserInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

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
public class ShiroLoginController {

    /**
     * 登录测试
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxLogin(UserInfo userInfo) {
        JSONObject jsonObject = new JSONObject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUserName(), userInfo.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            System.out.println(subject.getSession().getAttribute("role"));
            jsonObject.put("token", subject.getSession().getId());
            jsonObject.put("msg", "登录成功");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "密码错误");
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            jsonObject.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 鉴权测试
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles("admin")
    public String check() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg", "check");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "密码错误");
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            jsonObject.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
