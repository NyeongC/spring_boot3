package com.hodolog.api.service;

import com.hodolog.api.crypto.PasswordEncoder;
import com.hodolog.api.domain.Users;
import com.hodolog.api.exception.AlreadyExistsEmailException;
import com.hodolog.api.exception.InvalidSigninInformation;
import com.hodolog.api.repository.UserRepository;
import com.hodolog.api.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
        assertNotNull(user.getPassword());
        assertNotEquals("1234",user.getPassword());
        assertEquals("ccn",user.getName());


    }

    @Test
    @DisplayName("회원가입 시 중복된 이메일 존재")
    void test2(){

        // given
        Users user = Users.builder()
                .email("ccn@naver.com")
                .build();

        userRepository.save(user);

        Signup signup = Signup.builder()
                .name("ccn")
                .email("ccn@naver.com")
                .password("1234")
                .build();


        // expected
        Assertions.assertThrows(AlreadyExistsEmailException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                authService.signup(signup);
            }
        });


    }

}