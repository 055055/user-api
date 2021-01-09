package com.api.web.dto;
import com.api.entitiy.user.Account;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

//실수.. org.springframework.security.core.userdetails.User와 도메인 User의 이름이 같다. 여기서는 springSecurity의 유저를 상속.
public class UserAccount extends User{

    private Account account;

    public UserAccount(Account account) {
        super(account.getEmail(),account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+account.getRole())));
        this.account = account;
    }


    public Account getUser() {
        return account;
    }
}
