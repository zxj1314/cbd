package com.test.cbd.common.decrypt;

import com.test.cbd.common.exception.BizRuntimeException;
import org.apache.commons.codec.binary.Base64;

/**
 * <br>
 * <b>功能：</b>Base64解密成明文<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public class Base64PropertyPasswordDecoder implements PropertyPasswordDecoder {

    @Override
    public String decodePassword(String encodedPassword) {
        try {
            byte[] decodedData = Base64.decodeBase64(encodedPassword);
            return new String(decodedData, "UTF-8");
        } catch (Exception e) {
            throw new BizRuntimeException("Base64 fail",e);
        }
    }

    public String encodePassword(String textPassword) {
        try {
            return Base64.encodeBase64String(textPassword.getBytes());
        } catch (Exception e) {
            throw new BizRuntimeException("Base64 fail",e);
        }
    }

    public static void main(String[] args) throws Exception{
        Base64PropertyPasswordDecoder util= new Base64PropertyPasswordDecoder();
        System.out.println(util.decodePassword("Um9vdEAxMjM0"));
        System.out.println(util.encodePassword("rcp#*123"));
    }

}
