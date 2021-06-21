package com.api.web;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.api.common.config.JwtTokenProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@Disabled
public abstract class BasicControllerTest {

    protected MockMvc mockMvc;

    //@WebMvcTest에서 SecurityConfig 로드시 jwtTokenProvider 빈이 생성되지 않아서 실패. 그렇기에 mockBean으로 주입하여 의존성 주입.
    @MockBean
    JwtTokenProcess jwtTokenProcess;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    protected ObjectMapper objectMapper;

    protected String authToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwNTUwNTVAMDU1MDU1LmNvbSIsInJvbGUiOiJ1c2VyIiwiaWF0IjoxNjEwMTEyNjk4LCJleHAiOjE2MTAxMTI5OTh9.wb_Sa8ZIVMIFIzWiNeND3IC7qkPpijyhqBePJJ5A314";

    @BeforeEach
    public void setMockMvc(RestDocumentationContextProvider restDocumenttation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
//                .alwaysDo(print())
//                .addFilters(new CharacterEncodingFilter("UTF-8", true)) //application/json;charset=UTF-8
//                .build();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilter(((request, response, chain) -> {
                response.setCharacterEncoding("UTF-8");
                chain.doFilter(request, response);
            }))
            .apply(documentationConfiguration(restDocumenttation))
            .alwaysDo(print())
            .build();

    }
}
