package com.api.service;

import com.api.entitiy.user.User;
import com.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final UserRepository userRepository;

    public User test() {
        return userRepository.save(
                User.builder()
                        .email("055055")
                        .name("055055")
                        .password("pass")
                        .role("admin")
                        .build());

    }
}
