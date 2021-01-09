package com.api.web.dto;

import com.api.entitiy.user.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@ToString
@Getter
@Setter
public class AccountSaveReqDto {

    private String email;

    private String name;

    private String password;


    public Account createAccount(PasswordEncoder passwordEncoder){
        return Account.builder()
                    .password(passwordEncoder.encode(this.password))
                    .name(this.name)
                    .email(this.email)
                    .role(Account.Role.MEMBER)
                    .build();
            }

}
