package com.test.cbd.vo;

import com.test.cbd.framework.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
@ApiModel(value="",description="")
public class UserVO extends BaseVO {

    //
    @ApiModelProperty(value="")
    private String id;

    //
    @ApiModelProperty(value="")
    private String account;

    //
    @ApiModelProperty(value="")
    private String name;

    //
    @ApiModelProperty(value="")
    private String password;

    //
    @ApiModelProperty(value="")
    private String departmentId;

    //
    @ApiModelProperty(value="")
    private String departmentName;

    //
    @ApiModelProperty(value="")
    private String salt;

    //
    @ApiModelProperty(value="")
    private Date createTime;

    //
    @ApiModelProperty(value="")
    private Date updateTime;


}
