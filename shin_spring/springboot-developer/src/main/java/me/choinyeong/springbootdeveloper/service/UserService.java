package me.choinyeong.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.choinyeong.springbootdeveloper.domain.User;
import me.choinyeong.springbootdeveloper.dto.AddUserRequest;
import me.choinyeong.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }
}
