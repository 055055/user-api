package com.api.web.dto;

import com.api.common.type.AccountRole;
import com.api.entitiy.user.Account;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@ToString
@Getter
@Setter
public class AccountSaveReqDto {

	@Email(message = "올바른 email pattern을 입력해주세요."
		, regexp = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$")
	@NotBlank(message = "올바른 email을 입력해주세요.")
	private String email;

	@NotBlank(message = "올바른 name을 입력해주세요.")
	private String name;

	@NotBlank(message = "올바른 password를 입력해주세요.")
	private String password;

	public Account createAccount(PasswordEncoder passwordEncoder) {
		return Account.builder()
			.password(passwordEncoder.encode(this.password))
			.name(this.name)
			.email(this.email)
			.role(AccountRole.USER.getName())
			.build();
	}
}
