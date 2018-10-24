/**@Project: rcp-java
 *@Copyright: ©2017  广州弘度信息科技有限公司. 版权所有
 */
package com.test.cbd.framework.exception;

/**
 * <br>
 * <b>功能：</b>异常业务，需捕捉处理<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public class BizException extends Exception{

    public BizException(String message)
    {
        super(message);
    }

}
