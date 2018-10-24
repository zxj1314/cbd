package com.test.cbd.framework.exception;

/**
 * <br>
 * <b>功能：</b>运行时异常业务类<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public class BizRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6362782762086492531L;

	private int errorCode;

	private String message;

	private Throwable throwable;

	public BizRuntimeException() {
		super();
	}

	public BizRuntimeException( String message ) {
		super();
		this.message = message;
	}

	public BizRuntimeException( int errorCode, Throwable throwable ) {
		super();
		this.errorCode = errorCode;
		this.throwable = throwable;
	}

	public BizRuntimeException(String message, Throwable throwable ) {
		super();
		this.message = message;
		this.throwable = throwable;
	}

	public BizRuntimeException(int errorCode,String message){
		super();
		this.message = message;
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode( int errorCode ) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage( String message ) {
		this.message = message;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable( Throwable throwable ) {
		this.throwable = throwable;
	}

}
