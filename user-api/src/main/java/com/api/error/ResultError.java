package com.api.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
@Setter
@Builder
public class ResultError {

	private String resultCode;
	private String resultMessage;
	private HttpStatus httpStatus;

}