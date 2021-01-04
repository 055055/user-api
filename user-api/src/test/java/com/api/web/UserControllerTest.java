package com.api.web;

import com.api.entitiy.user.User;
import com.api.error.ServiceError;
import com.api.error.ServiceException;
import com.api.service.UserService;
import com.api.web.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest( controllers = UserController.class)
class UserControllerTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) //application/json;charset=UTF-8
                .build();
    }

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
                                        .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
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
                                        .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",empty()));
    }


    @DisplayName("유저 조회 성공")
    @Test
    public void USER_FIND_SUCCESS() throws Exception {
        //given
        Long seq = 1L;
        String name = "055055";
        UserDto user = new UserDto();
        user.setSeq(seq);
        user.setEmail("055055@055055.com");
        user.setName(name);
        user.setRole(User.Role.ADMIN);


        given(this.userService.findyBySeq(anyLong())).willReturn(user);


        //when
        ResultActions result = mockMvc.perform(get("/v1/user/{seq}",seq)
                                        .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.seq").value(seq))
            .andExpect(jsonPath("$.name").value(name));
    }


    @DisplayName("유저 조회 실패")
    @Test
    public void USER_FIND_FAIL() throws Exception {
        //given
        Long seq = 1L;

        given(this.userService.findyBySeq(anyLong()))
                .willThrow(new ServiceException(ServiceError.USER_NOT_FOUND));


        //when
        ResultActions result = mockMvc.perform(get("/v1/user/{seq}",seq)
                                        .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        result
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.resultCode").value("4000"))
            .andExpect(jsonPath("$.resultMessage").value("찾고자 하는 유저가 없습니다."));

    }

    @DisplayName("유저 저장 성공")
    @Test
    public void USER_SAVE_SUCCESS() throws Exception {
        //given
        String email = "033033@033033.com";
        String name = "033033";

        UserSaveReqDto req = new UserSaveReqDto();
        req.setEmail(email);
        req.setName(name);
        req.setPassword("033033");


        UserSaveResDto res = new UserSaveResDto();
        res.setSeq(2L);
        res.setEmail(email);
        res.setName(name);
        res.setRole(User.Role.MEMBER);
        res.setModifiedDate(LocalDateTime.now());
        res.setCreateDate(LocalDateTime.now());


        given(this.userService.save(any(UserSaveReqDto.class))).willReturn(res);


        //when
        ResultActions result = mockMvc.perform(post("/v1/user")
                                        .accept(MediaType.APPLICATION_JSON_VALUE)
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(objectMapper.writeValueAsString(req)));

        //then
        result
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.name").value(name));
    }

    @DisplayName("유저 삭제 성공")
    @Test
    public void USER_DELETE_SUCCESS() throws Exception {
        Long seq = 1L;

        //given
        willDoNothing().given(userService).delete(anyLong());


        //when
        ResultActions result = mockMvc.perform(delete("/v1/user/{seq}",seq)
                                        .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        result.andExpect(status().isNoContent());
    }


    @DisplayName("유저 삭제 실패")
    @Test
    public void USER_DELETE_FAIL() throws Exception {
        Long seq = 1L;

        //given
        willThrow(new ServiceException(ServiceError.USER_NOT_FOUND)).given(userService).delete(anyLong());


        //when
        ResultActions result = mockMvc.perform(delete("/v1/user/{seq}",seq)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultCode").value("4000"))
                .andExpect(jsonPath("$.resultMessage").value("찾고자 하는 유저가 없습니다."));
    }


    @DisplayName("유저 업데이트 성공")
    @Test
    public void USER_UPDATE_SUCCESS() throws Exception {
        Long seq = 2L;
        String name = "055055";
        String email = "055055@055055.com";
        LocalDateTime updateDtm = LocalDateTime.now();

        UserUpdateReqDto req = new UserUpdateReqDto();
        req.setName(name);
        req.setEmail(email);

        UserUpdateResDto res = new UserUpdateResDto();
        res.setEmail(email);
        res.setName(name);
        res.setRole(User.Role.MEMBER);
        res.setSeq(seq);
        res.setModifiedDate(updateDtm);
        res.setCreateDate(updateDtm.minusDays(1L));


        //given
        given(userService.update(anyLong(),any(UserUpdateReqDto.class))).willReturn(res);


        //when
        ResultActions result = mockMvc.perform(patch("/v1/user/{seq}",seq)
                                            .accept(MediaType.APPLICATION_JSON_VALUE)
                                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                                            .content(objectMapper.writeValueAsString(req)));


        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.name").value(name));
    }

    @DisplayName("유저 업데이트 실패")
    @Test
    public void USER_UPDATE_FAIL() throws Exception {
        Long seq = 1L;
        String name = "055055";
        String email = "055055@055055.com";

        UserUpdateReqDto req = new UserUpdateReqDto();
        req.setName(name);
        req.setEmail(email);
        //given
        willThrow(new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR))
                .given(userService).update(anyLong(),any(UserUpdateReqDto.class));


        //when
        ResultActions result = mockMvc.perform(patch("/v1/user/{seq}",seq)
                                        .accept(MediaType.APPLICATION_JSON_VALUE)
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(objectMapper.writeValueAsString(req)));

        //then
        result.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.resultCode").value("5000"))
                .andExpect(jsonPath("$.resultMessage").value("내부 서버 오류"));
    }

}