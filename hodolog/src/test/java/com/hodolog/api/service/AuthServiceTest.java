package com.hodolog.api.service;

import com.hodolog.api.domain.Users;
import com.hodolog.api.repository.UserRepository;
import com.hodolog.api.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1(){

        // given
        Signup signup = Signup.builder()
                .name("ccn")
                .email("ccn@naver.com")
                .password("1234")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1L, userRepository.count());

        Users user = userRepository.findAll().iterator().next();
        assertEquals("ccn@naver.com",user.getEmail());
        assertEquals("1234",user.getPassword());
        assertEquals("ccn",user.getName());


    }


}