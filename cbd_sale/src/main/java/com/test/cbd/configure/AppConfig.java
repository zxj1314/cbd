package com.test.cbd.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <br>
 * <b>功能：</b>App配置<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2018-3-1 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */

@ConfigurationProperties(prefix = "app.framework")
@Data
@SuppressWarnings("all")
public class AppConfig {

    //是否验证token
    private boolean authEnable;
    //是否可以多处登录
    private boolean userMultiLogin;
    //忘记密码找回url
    private String forgetPwUrl;
    //公司注册url
    private String addOrgUrl;

}
