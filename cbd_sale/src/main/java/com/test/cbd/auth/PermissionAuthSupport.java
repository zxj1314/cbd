package com.test.cbd.auth;


/**
 * 功能：
 * 作者：chenjiefeng
 * 日期：2018/3/29
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
public class PermissionAuthSupport {

    public static FilterRuleEnum getFilterRule(PermissionAuth secAuth, boolean isBaseCrudMethod){

        if(secAuth ==null || isBaseCrudMethod){
            return FilterRuleEnum.USER;//如果是BaseCrudController类里面的方法，必须是已登录
        }

        return secAuth.filterRule();
    }

}
