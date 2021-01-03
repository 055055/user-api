package com.api.entitiy.user;

import com.api.entitiy.BaseEntitiy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String email, String password, String name, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public void updateUser(String password, String name, Role role) {
        if(StringUtils.hasText(password)){
            this.password = password;
        }
        if(StringUtils.hasText(name)){
            this.name = name;
        }
        if(!ObjectUtils.isEmpty(role)){
            this.role = role;
        }
    }

    public enum Role{
        MEMBER("01","member"),
        ADMIN("02","admin");

        private String code;
        private String roleName;

        Role(String code, String roleName) {
            this.code = code;
            this.roleName = roleName;
        }

        public String getCode() {
            return code;
        }

        public String getRoleName() {
            return roleName;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", "+super.toString()+
                '}';
    }

}
