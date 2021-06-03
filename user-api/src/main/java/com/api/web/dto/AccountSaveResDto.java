package com.api.web.dto;

import com.api.entitiy.user.Account;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AccountSaveResDto {

	private Long seq;
	private String email;
	private String name;
	private Account.Role role;
	private LocalDateTime createDate;
	private LocalDateTime modifiedDate;
}
