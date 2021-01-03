package com.api.web.dto;


import com.api.entitiy.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserDto {
    private Long seq;

    private String email;

    private String name;

    private User.Role role;

}
