package com.api.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler
	public ResponseEntity serviceExceptionHandler(ServiceException e) {
		return new ResponseEntity<>(e.getServiceError().getResultError(),
			e.getServiceError().getHttpStatus());
	}

}
