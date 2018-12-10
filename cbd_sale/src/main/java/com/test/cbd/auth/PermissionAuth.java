package com.test.cbd.auth;

import java.lang.annotation.*;

/**
 * 功能：自定义的注解类，用于给springmvc的访问注入访问权限<br>
 *      可以给类加注解，也可以给方法加注解
 *      @ PermissionAuth(filterRule=FilterRule.USER, role = { "ROLE_ADMIN", "ROLE_MANAGER" },perm = { "PERM_READ" })
 * 作者：chenjiefeng
 * 日期：2018/3/29
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Target( { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAuth {


    /*
     * 如果配置在类名，则是全局性的设置
     */
    FilterRuleEnum filterRule() default FilterRuleEnum.USER; //filterRule=FilterRule.AUTHC

    /*
     * 以下设置都是 filterRule=FilterRule.USER 时的附加权限设置
     */


    String[] perm() default {}; //perm = { "PERM_READ","UPDATE"}

    boolean permOr() default false;//权限采用and模式，需要满足所有权限才可以

    String[] role() default {};  //role = { "ROLE_ADMIN", "ROLE_MANAGER" }

    boolean roleOr() default true;//角色采用or模式，只需满足一个角色就可以

    boolean roleOrPerm() default false;//角色--权限 采用并还是或模式，默认是并


    //-------------------------- 只对BaseCrudController类里面的方法有用 begin---------------------------------

    //只在类头配置才有效，当方法没有注解权限时，且类头的注解没有配置角色和权限的，可以用这个配置作为全局的默认配置
    String defPermPrefix() default ""; //defPermPrefix ="SYS:BASE:DIC:"   系统权限表的code的前缀 SYS:BASE:DIC:｛VIEW或者EDIT｝


    //-------------------------- 只对BaseCrudController类里面的方法有用 end---------------------------------
}
