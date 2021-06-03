package com.api.web.dto;

import com.api.entitiy.user.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AccountUpdateReqDto {

	private String email;
	private String name;
	private String password;
	private Account.Role role;

}
