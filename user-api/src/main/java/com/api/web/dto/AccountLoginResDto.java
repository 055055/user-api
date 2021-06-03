package com.api.web.dto;

import com.api.entitiy.user.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AccountLoginResDto {

	private String email;
	private Account.Role role;
}
