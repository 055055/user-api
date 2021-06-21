package com.api.java;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.api.error.ErrorCode;
import com.api.error.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JavaTest {

	@DisplayName("UnsupportedOperationException Message 테스트 성공")
	@Test
	public void EXCEPTION_MESSAGE_TEST_SUCCESS() {
		Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
			throw new UnsupportedOperationException("Not supported");
		});
		assertEquals(exception.getMessage(), "Not supported");
	}

	@DisplayName("ServiceException USER_ID_DUPLICATE 테스트 성공")
	@Test
	public void SERVICE_EXCEPTION_MESSAGE_TEST_SUCCESS() {
		CustomException exception = assertThrows(CustomException.class, () -> {
			throw new CustomException(ErrorCode.ACCOUNT_DUPLICATE);
		});
		assertEquals(exception.getErrorCode().getMessage(), "중복된 사용자 아이디");
	}

}
