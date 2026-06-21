package com.ccn.springai.service;

import com.ccn.springai.domain.User;
import com.ccn.springai.dto.AddUserRequest;
import com.ccn.springai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long save(AddUserRequest dto) {
        String role = dto.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .build()).getId();
    }
}