package com.data.im.exception;

public class FilterException extends RuntimeException{

	public FilterException() {
		super();
	}

	public FilterException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FilterException(String message, Throwable cause) {
		super(message, cause);
	}

	public FilterException(String message) {
		super(message);
	}

	public FilterException(Throwable cause) {
		super(cause);
	}
	
}
