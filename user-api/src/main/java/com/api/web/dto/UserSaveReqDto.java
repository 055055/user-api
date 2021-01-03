package com.api.web.dto;

import com.api.entitiy.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserSaveReqDto {

    private String email;

    private String name;

    private String password;


    public User createUser(){
        return User.builder()
                    .password(this.password)
                    .name(this.name)
                    .email(this.email)
                    .role(User.Role.MEMBER)
                    .build();
            }

}
