package com.api.web;

import com.api.entitiy.user.User;
import com.api.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping(value = "/test")
    public String test(){
        return "string";
    }

    @GetMapping(value = "/service")
    public User service(){
        return testService.test();
    }

}
