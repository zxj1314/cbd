package com.test.cbd.framework.response;

public enum ResponseCode {

    SUCCESS(200, "请求成功"),
    FAILURE(500, "请求失败"),
    USER_USING(300, "用户已登录"),
    //以下是系统异常时，比较明细的说明
    TOKEN_INVALID(200000, "token无效"),
    BAD_REQUEST(200001, "请求参数不合法");

    public static final int ERROR_CODE =10000;//其它错误，中文说明可以自定义

    private int code;

    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
