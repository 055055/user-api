package com.api.web.dto;

import com.api.entitiy.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class UserSaveResDto {
    private Long seq;
    private String email;
    private String name;
    private User.Role role;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

}
