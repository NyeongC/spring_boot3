package me.choinyeong.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.choinyeong.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final or @NonNull 필드에 대한 생성자만 생성
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException((email)));
    }
}
