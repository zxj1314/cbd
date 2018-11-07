package com.test.cbd.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserInfo implements Serializable{

    private String userName;

    private String password;

    private Integer state;

    private String credentialsSalt;

    private List<SysRole> roleList;
}
