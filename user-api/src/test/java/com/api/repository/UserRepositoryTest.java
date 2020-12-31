package com.api.repository;

import com.api.common.config.JpaConfig;
import com.api.entitiy.user.User;
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
            )//@DataJpaTest시 JpaConfig Config Scan하지 못하는 것 해결
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @DisplayName("유저 생성 성공 테스트")
    @Test
    public void User_CREATE_SUCCESS(){

        //given
        User user = User.builder()
                .email("055055@055055.com")
                .password("055055")
                .name("055055")
                .role("user")
                .build();
        //when
        User save = userRepository.save(user);

        System.out.println("save = " + save.toString());

        //then
        assertThat(user.getEmail()).isEqualTo(save.getEmail());
        assertThat(user.getName()).isEqualTo(save.getName());

    }

}