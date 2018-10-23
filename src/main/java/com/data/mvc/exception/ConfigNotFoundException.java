package com.data.mvc.exception;
/**
 * 
 * @author 找不到对应配置异常
 *
 */
public class ConfigNotFoundException extends RuntimeException{

	public ConfigNotFoundException() {
		super();
	}

	public ConfigNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfigNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigNotFoundException(String message) {
		super(message);
	}

	public ConfigNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
