package com.api.web;

import com.api.common.filter.CustomFilter;
import com.api.entitiy.user.Account;
import com.api.error.ServiceError;
import com.api.error.ServiceException;
import com.api.service.AccountService;
import com.api.web.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = AccountController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = CustomFilter.class)
})
class AccountControllerTest extends BasicControllerTest {

    @MockBean
    AccountService accountService;


    @DisplayName("유저 전체 조회 성공")
    @Test
    public void USER_FIND_ALL_SUCCESS() throws Exception {
        //given
        AccountDto adminUser = new AccountDto();
        adminUser.setSeq(1L);
        adminUser.setEmail("055055@055055.com");
        adminUser.setName("055055");
        adminUser.setRole(Account.Role.ADMIN);

        AccountDto memberUser = new AccountDto();
        memberUser.setSeq(2L);
        memberUser.setEmail("033033@033033.com");
        memberUser.setName("033033");
        memberUser.setRole(Account.Role.MEMBER);

        given(this.accountService.findAll()).willReturn(Arrays.asList(adminUser, memberUser));


        //when
        ResultActions result = mockMvc.perform(get("/v1/user")
                                        .header("X-AUTH-TOKEN",authToken)
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
        given(this.accountService.findAll()).willReturn(Arrays.asList());


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
        AccountDto user = new AccountDto();
        user.setSeq(seq);
        user.setEmail("055055@055055.com");
        user.setName(name);
        user.setRole(Account.Role.ADMIN);


        given(this.accountService.findyBySeq(anyLong())).willReturn(user);


        //when
        ResultActions result = mockMvc.perform(get("/v1/user/{seq}",seq)
                                            .header("X-AUTH-TOKEN",authToken)
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

        given(this.accountService.findyBySeq(anyLong()))
                .willThrow(new ServiceException(ServiceError.USER_NOT_FOUND));


        //when
        ResultActions result = mockMvc.perform(get("/v1/user/{seq}",seq)
                                              .header("X-AUTH-TOKEN",authToken)
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

        AccountSaveReqDto req = new AccountSaveReqDto();
        req.setEmail(email);
        req.setName(name);
        req.setPassword("033033");


        AccountSaveResDto res = new AccountSaveResDto();
        res.setSeq(2L);
        res.setEmail(email);
        res.setName(name);
        res.setRole(Account.Role.MEMBER);
        res.setModifiedDate(LocalDateTime.now());
        res.setCreateDate(LocalDateTime.now());


        given(this.accountService.save(any(AccountSaveReqDto.class))).willReturn(res);


        //when
        ResultActions result = mockMvc.perform(post("/v1/user")
                                              .header("X-AUTH-TOKEN",authToken)
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
        willDoNothing().given(accountService).delete(anyLong());


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
        willThrow(new ServiceException(ServiceError.USER_NOT_FOUND)).given(accountService).delete(anyLong());


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

        AccountUpdateReqDto req = new AccountUpdateReqDto();
        req.setName(name);
        req.setEmail(email);

        AccountUpdateResDto res = new AccountUpdateResDto();
        res.setEmail(email);
        res.setName(name);
        res.setRole(Account.Role.MEMBER);
        res.setSeq(seq);
        res.setModifiedDate(updateDtm);
        res.setCreateDate(updateDtm.minusDays(1L));


        //given
        given(accountService.update(anyLong(),any(AccountUpdateReqDto.class))).willReturn(res);


        //when
        ResultActions result = mockMvc.perform(patch("/v1/user/{seq}",seq)
                                                .header("X-AUTH-TOKEN",authToken)
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

        AccountUpdateReqDto req = new AccountUpdateReqDto();
        req.setName(name);
        req.setEmail(email);
        //given
        willThrow(new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR))
                .given(accountService).update(anyLong(),any(AccountUpdateReqDto.class));


        //when
        ResultActions result = mockMvc.perform(patch("/v1/user/{seq}",seq)
                                                .header("X-AUTH-TOKEN",authToken)
                                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(objectMapper.writeValueAsString(req)));

        //then
        result.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.resultCode").value("5000"))
                .andExpect(jsonPath("$.resultMessage").value("내부 서버 오류"));
    }

}