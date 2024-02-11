package com.hodolog.api.service;


import com.hodolog.api.crypto.PasswordEncoder;
import com.hodolog.api.domain.Users;
import com.hodolog.api.exception.AlreadyExistsEmailException;
import com.hodolog.api.exception.InvalidSigninInformation;
import com.hodolog.api.repository.UserRepository;
import com.hodolog.api.request.Signup;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;


    public void signup(Signup signup) {

        Optional<Users> usersOptional = userRepository.findByEmail(signup.getEmail());

        if (usersOptional.isPresent()){
            throw new AlreadyExistsEmailException();

        }

        //String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());
        String encryptedPassword = "";


        Users user = Users.builder()
                .email(signup.getEmail())
                .name(signup.getName())
                .password(encryptedPassword)
                .build();

        userRepository.save(user);
    }
}
