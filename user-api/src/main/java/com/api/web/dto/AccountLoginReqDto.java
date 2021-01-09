package com.api.web.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AccountLoginReqDto {
    private String email;
    private String password;

}
