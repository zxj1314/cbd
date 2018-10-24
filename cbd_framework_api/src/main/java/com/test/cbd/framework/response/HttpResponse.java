package com.test.cbd.framework.response;

public class HttpResponse<T> extends BaseResponse {

    private static final int DEFAULT_CODE = ResponseCode.SUCCESS.getCode();

    private static final String DEFAULT_MESSAGE = ResponseCode.SUCCESS.getMessage();

    T data;
    boolean result;

    public HttpResponse() {
        super(DEFAULT_CODE, DEFAULT_MESSAGE);
        this.result = true;
    }

    public HttpResponse(T data) {
        super(DEFAULT_CODE, DEFAULT_MESSAGE);
        this.result = true;
        this.data = data;
    }

    public HttpResponse(int code, String message) {
        super(code, message);
        this.result = true;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
