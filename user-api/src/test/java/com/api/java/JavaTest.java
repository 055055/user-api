package com.api.java;


import com.api.error.ServiceError;
import com.api.error.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JavaTest {

    @DisplayName("UnsupportedOperationException Message 테스트 성공")
    @Test
    public void EXCEPTION_MESSAGE_TEST_SUCCESS(){
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });
        assertEquals(exception.getMessage(), "Not supported");
    }

    @DisplayName("ServiceException USER_ID_DUPLICATE 테스트 성공")
    @Test
    public void SERVICE_EXCEPTION_MESSAGE_TEST_SUCCESS(){
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            throw new ServiceException(ServiceError.USER_ID_DUPLICATE);
        });
        assertEquals(exception.getServiceError().getResultMessage(), "중복된 사용자 아이디");
    }

}
