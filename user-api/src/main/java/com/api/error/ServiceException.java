package com.api.error;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ServiceException extends RuntimeException {

	private ServiceError serviceError;

	public ServiceException() {
		super();
	}

	public ServiceException(ServiceError serviceError) {
		super(new ServiceException());
		this.serviceError = serviceError;
	}

	public ServiceException(String message, Throwable cause, ServiceError serviceError) {
		super(message, cause);
		this.serviceError = serviceError;
	}

	public ServiceException(ServiceError serviceError, Throwable cause) {
		super(cause);
		this.serviceError = serviceError;
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace, ServiceError serviceError) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.serviceError = serviceError;
	}
}
