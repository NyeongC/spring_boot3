package com.hodolog.api.service;


import com.hodolog.api.domain.Users;
import com.hodolog.api.repository.UserRepository;
import com.hodolog.api.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public Users signIn(Login request){
        Users users = userRepository.findByEmailAndPassword(request.getEmail(),request.getPassword())
                .orElse(null);
        return users;
    }
}
