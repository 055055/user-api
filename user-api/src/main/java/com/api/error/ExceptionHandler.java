package com.api.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
	public ResponseEntity serviceExceptionHandler(CustomException e) {
		log.error("error",e);
		return new ResponseEntity<>(e.getErrorCode().getResultError(),
			e.getErrorCode().getHttpStatus());
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(AccountException.class)
	public ResponseEntity userNotFoundExceptionHandler(AccountException e) {
		log.error("error",e);
		return new ResponseEntity<>(e.getErrorCode().getResultError(),
			e.getErrorCode().getHttpStatus());
	}


	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ResponseEntity ExceptionHandler(Exception e) {
		log.error("error",e);
		return new ResponseEntity<>(ErrorCode.INTERNAL_SERIVCE_ERROR.getResultError(),
			ErrorCode.INTERNAL_SERIVCE_ERROR.getHttpStatus());
	}

}
