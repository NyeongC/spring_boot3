package com.hodolog.api.service;


import com.hodolog.api.domain.Session;
import com.hodolog.api.domain.Users;
import com.hodolog.api.exception.AlreadyExistsEmailException;
import com.hodolog.api.exception.InvalidRequest;
import com.hodolog.api.repository.UserRepository;
import com.hodolog.api.request.Login;
import com.hodolog.api.request.Signup;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signIn(Login request){
        Users users = userRepository.findByEmailAndPassword(request.getEmail(),request.getPassword())
                .orElse(null);

        Session session = users.addSession();

        return users.getId();
    }

    public void signup(Signup signup) {

        Optional<Users> usersOptional = userRepository.findByEmail(signup.getEmail());

        if (usersOptional.isPresent()){
            throw new AlreadyExistsEmailException();

        }

        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(16, 8,1
                ,32,64);

        String encryptedPassword = encoder.encode(signup.getPassword());


        Users user = Users.builder()
                .email(signup.getEmail())
                .name(signup.getName())
                .password(encryptedPassword)
                .build();

        userRepository.save(user);
    }
}
