package com.api.repository;

import com.api.common.config.JpaConfig;
import com.api.common.type.AccountRole;
import com.api.entitiy.user.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = ASSIGNABLE_TYPE,
    classes = {JpaConfig.class})
)
//@DataJpaTest시 JpaConfig Config Scan하지 못하는 것 해결.
    // ASSIGNABLE_TYPE는 클래스를 기준으로 객체를 가지고 온다.
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @DisplayName("유저 생성 성공 테스트")
    @Test
    public void User_CREATE_SUCCESS() {

        //given
        Account account = Account.builder()
            .email("055055@055055.com")
            .password("055055")
            .name("055055")
            .role(AccountRole.ADMIN.getName())
            .build();
        //when
        Account save = accountRepository.save(account);

        System.out.println("save = " + save.toString());

        //then
        assertThat(account.getEmail()).isEqualTo(save.getEmail());
        assertThat(account.getName()).isEqualTo(save.getName());

    }

}