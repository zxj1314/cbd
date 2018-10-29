package com.test.cbd.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * VO对象：
 * @author zxj
 * @date 2018-10-24 23:39:38
 * 版权所有：@撸码识途 版权所有(C) 2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO  {

    //
    private String id;

    //
    private String account;

    //
    private String name;

    //
    private String password;

    //
    private String departmentId;

    //
    private String departmentName;

    //
    private String salt;

    //
    private Date createTime;

    //
    private Date updateTime;


}
