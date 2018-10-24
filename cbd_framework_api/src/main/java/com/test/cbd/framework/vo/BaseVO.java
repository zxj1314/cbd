package com.test.cbd.framework.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * <br>
 * <b>功能：</b>Vo基础类<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public abstract class BaseVO implements Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
