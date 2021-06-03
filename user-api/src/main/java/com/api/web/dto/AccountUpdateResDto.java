package com.api.web.dto;

import com.api.entitiy.user.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class AccountUpdateResDto {

	private Long seq;
	private String email;
	private String name;
	private Account.Role role;
	private LocalDateTime createDate;
	private LocalDateTime modifiedDate;
}
