package com.api.error;

import com.api.error.ResultError.FieldValue;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity customExceptionHandler(CustomException e) {
		log.error("error {}", e);
		return new ResponseEntity<>(e.getErrorCode().getResultError(),
			e.getErrorCode().getHttpStatus());
	}

	@ExceptionHandler(AccountException.class)
	public ResponseEntity accountExceptionHandler(AccountException e) {
		log.error("error {}", e);
		return new ResponseEntity<>(e.getErrorCode().getResultError(),
			e.getErrorCode().getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity ExceptionHandler(Exception e) {
		log.error("error {}", e);
		return new ResponseEntity<>(ErrorCode.INTERNAL_SERIVCE_ERROR.getResultError(),
			ErrorCode.INTERNAL_SERIVCE_ERROR.getHttpStatus());
	}

	/**
	 * RequestBody Validation Exception Handler
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> httpMsgConverterValidationHanlder(MethodArgumentNotValidException e){
		return responseValidException(e.getBindingResult());
	}

	/**
	 * Parameter Validation Exception Handler
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public ResponseEntity<?> modelAttributeValidationHanlder(BindException e){
		return responseValidException(e.getBindingResult());
	}



	private ResponseEntity<?> responseValidException(BindingResult bindingResult) {
		List<FieldValue> fieldValues = new ArrayList<>();
		for (FieldError fieldError :bindingResult.getFieldErrors()){
			ResultError.FieldValue fieldValue = ResultError.FieldValue
				.builder()
				.field(fieldError.getField())
				.value(fieldError.getRejectedValue())
				.reason(fieldError.getDefaultMessage())
				.build();
			fieldValues.add(fieldValue);
		}

		ResultError response = ResultError.builder()
			.code(ErrorCode.REQUEST_VALIDATION.getCode())
			.message(ErrorCode.REQUEST_VALIDATION.getMessage())
			.httpStatus(ErrorCode.REQUEST_VALIDATION.getHttpStatus())
			.fieldValues(fieldValues)
			.build();
		return new ResponseEntity(response, response.getHttpStatus());
	}

}
