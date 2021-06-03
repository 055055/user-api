package com.api.web.dto;


import com.api.entitiy.user.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AccountDto {

	private Long seq;
	private String email;
	private String name;
	private Account.Role role;

}


