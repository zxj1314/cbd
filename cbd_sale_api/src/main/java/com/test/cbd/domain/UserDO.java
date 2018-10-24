package com.test.cbd.domain;

import com.test.cbd.framework.domain.BaseDO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import javax.persistence.*;

/**
 * DO对象：
 * @author zxj
 * @date 2018-10-24 23:39:38
 * 版权所有：@撸码识途 版权所有(C) 2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserDO extends BaseDO {

    @Id
    //
    @Column(name = "id")
    private String id;

    //
    @Column(name = "account")
    private String account;

    //
    @Column(name = "name")
    private String name;

    //
    @Column(name = "password")
    private String password;

    //
    @Column(name = "department_id")
    private String departmentId;

    //
    @Column(name = "department_name")
    private String departmentName;

    //
    @Column(name = "salt")
    private String salt;

    //
    @Column(name = "create_time")
    private Date createTime;

    //
    @Column(name = "update_time")
    private Date updateTime;


}
