package com.api.error;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class CustomException extends RuntimeException {

	private ErrorCode errorCode;

	public CustomException() {
		super();
	}

	public CustomException(ErrorCode errorCode) {
		super(errorCode.name());
		this.errorCode = errorCode;
	}

	public CustomException(String message, Throwable cause, ErrorCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public CustomException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public CustomException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace, ErrorCode errorCode) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.errorCode = errorCode;
	}
}
