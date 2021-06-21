package com.api.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AccountLoginResDto {

	private String email;
	private String role;
}
