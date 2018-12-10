package com.test.cbd.auth;

/**
 * 功能：
 * 作者：chenjiefeng
 * 日期：2018/9/11
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
public enum FilterRuleEnum {


    /**
     * 拦截规则枚举
     * ANON: 跌名(有token，未验证)
     * USER: 已认证  （默认）
     */

    VIEW,
    ANON,
    AUTHC,
    USER,
    LOGOUT;
}
