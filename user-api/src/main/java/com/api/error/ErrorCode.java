package com.api.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

	ACCOUNT_DUPLICATE("1000", "중복된 사용자 아이디", HttpStatus.CONFLICT),
	ACCOUNT_NOT_FOUND("4000", "찾고자 하는 유저가 없습니다.", HttpStatus.NOT_FOUND),
	//최대한 정보를 알려주지 않기 위해 아이디 또는 비밀번호가 틀렸다고 전달
	ACCOUNT_ID_OR_PASSWORD_INVALID("4001", "찾고자 하는 유저가 없거나 비밀번호가 유효하지 않습니다.", HttpStatus.NOT_FOUND),
	REQUEST_VALIDATION("4001","요청값 벨리데이션 체크", HttpStatus.BAD_REQUEST),
	INTERNAL_SERIVCE_ERROR("5000", "내부 서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);

	private String code;
	private String message;
	private HttpStatus httpStatus;

	ErrorCode(String code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public ResultError getResultError() {
		return ResultError.builder()
			.code(this.code)
			.message(this.message)
			.httpStatus(this.httpStatus)
			.build();
	}
}
