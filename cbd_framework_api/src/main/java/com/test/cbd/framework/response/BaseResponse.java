package com.test.cbd.framework.response;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    public static final int ERROR_CODE = 10000;//可以使用统一的错误编码，错误说明自定义

    private int code = 200;
    private String message;

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
