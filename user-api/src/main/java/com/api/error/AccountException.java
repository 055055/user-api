package com.api.error;

public class AccountException extends CustomException {

	public AccountException(ErrorCode errorCode) {
		super(errorCode);
	}
}
