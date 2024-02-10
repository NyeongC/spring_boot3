package com.hodolog.api.service;


import com.hodolog.api.domain.Session;
import com.hodolog.api.domain.Users;
import com.hodolog.api.repository.UserRepository;
import com.hodolog.api.request.Login;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signIn(Login request){
        Users users = userRepository.findByEmailAndPassword(request.getEmail(),request.getPassword())
                .orElse(null);

        Session session = users.addSession();

        return session.getAccessToken();
    }
}
