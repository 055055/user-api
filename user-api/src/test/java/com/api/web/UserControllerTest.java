package com.api.web;

import com.api.entitiy.user.User;
import com.api.service.UserService;
import com.api.web.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest( controllers = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @DisplayName("유저 전체 조회 성공")
    @Test
    public void USER_FIND_ALL_SUCCESS() throws Exception {
        //given
        UserDto adminUser = new UserDto();
        adminUser.setSeq(1L);
        adminUser.setEmail("055055@055055.com");
        adminUser.setName("055055");
        adminUser.setRole(User.Role.ADMIN);

        UserDto memberUser = new UserDto();
        memberUser.setSeq(2L);
        memberUser.setEmail("033033@033033.com");
        memberUser.setName("033033");
        memberUser.setRole(User.Role.MEMBER);

        given(this.userService.findAll()).willReturn(Arrays.asList(adminUser, memberUser));


        //when
        ResultActions result = mockMvc.perform(get("/v1/user")
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print());

        //then
        result.andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].seq").value("1"))
                .andExpect(jsonPath("$.[1].seq").value("2"));


    }

    @DisplayName("유저 전체 조회시 유저 없음")
    @Test
    public void USER_FIND_ALL_EMPTY() throws Exception {
        //given
        given(this.userService.findAll()).willReturn(Arrays.asList());


        //when
        ResultActions result = mockMvc.perform(get("/v1/user")
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print());

        //then
        result.andExpect(jsonPath("$",empty()));
    }
}