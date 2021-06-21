package com.api.web.dto;

import com.api.common.anotation.EnumTypeValid;
import com.api.common.type.AccountRole;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AccountUpdateReqDto {

	@NotBlank(message = "올바른 이메일을 입력해주세요.")
	private String email;

	@NotBlank(message = "올바른 name을 입력해주세요.")
	private String name;

	@NotBlank(message = "올바른 비밀빈호를 입력해주세요.")
	private String password;

	@EnumTypeValid(enumClass = AccountRole.class, message = "올바른 Role을 입력해주세요.")
	@NotBlank(message = "올바른 Role을 입력해주세요.")
	private String role;
}
