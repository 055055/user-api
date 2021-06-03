package com.api.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ServiceError {

	USER_ID_DUPLICATE("1000", "중복된 사용자 아이디", HttpStatus.CONFLICT),
	USER_NOT_FOUND("4000", "찾고자 하는 유저가 없습니다.", HttpStatus.NOT_FOUND),
	//최대한 정보를 알려주지 않기 위해 아이디 또는 비밀번호가 틀렸다고 전달
	USER_OR_PASSWORD_INVALID("4001", "찾고자 하는 유저가 없거나 비밀번호가 유효하지 않습니다.", HttpStatus.NOT_FOUND),
	INTERNAL_SERIVCE_ERROR("5000", "내부 서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);

	private String resultCode;
	private String resultMessage;
	private HttpStatus httpStatus;

	ServiceError(String resultCode, String resultMessage, HttpStatus httpStatus) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.httpStatus = httpStatus;
	}

	public ResultError getResultError() {
		return ResultError.builder()
			.resultCode(this.resultCode)
			.resultMessage(this.resultMessage)
			.httpStatus(this.httpStatus)
			.build();
	}
}
