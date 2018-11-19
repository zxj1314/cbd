package com.test.cbd.shiro;

//import com.alibaba.fastjson.JSONObject;
//import com.test.cbd.vo.UserInfo;
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.LockedAccountException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authz.annotation.RequiresRoles;
//import org.apache.shiro.crypto.hash.SimpleHash;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.net.InetAddress;
//
//@Slf4j
//@Api(value="shiro测试",description="shiro测试")
//@RestController
//@RequestMapping("/")
//public class ShiroLoginController {
//
//    /**
//     * 登录测试
//     * @param userInfo
//     * @return
//     */
//    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
//    @ResponseBody
//    public String ajaxLogin(UserInfo userInfo) {
//        JSONObject jsonObject = new JSONObject();
//        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUserName(), userInfo.getPassword());
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(token);
//            jsonObject.put("token", subject.getSession().getId());
//            jsonObject.put("msg", "登录成功");
//        } catch (IncorrectCredentialsException e) {
//            jsonObject.put("msg", "密码错误");
//        } catch (LockedAccountException e) {
//            jsonObject.put("msg", "登录失败，该用户已被冻结");
//        } catch (AuthenticationException e) {
//            jsonObject.put("msg", "该用户不存在");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
//    /**
//     * 鉴权测试
//     * @param userInfo
//     * @return
//     */
//    @RequestMapping(value = "/check", method = RequestMethod.GET)
//    @ResponseBody
//    @RequiresRoles("guest")
//    public String check() {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("msg", "鉴权测试");
//        return jsonObject.toString();
//    }
//}
//