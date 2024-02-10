package com.hodolog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.api.domain.Users;
import com.hodolog.api.repository.SessionRepository;
import com.hodolog.api.repository.UserRepository;
import com.hodolog.api.request.Login;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc // 이거 하나로 mockmvc 쓸수있음
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void test() throws Exception{

        // given

        userRepository.save(Users.builder()
                .name("ccn")
                .email("ccn@naver.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("ccn@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())

                .andDo(MockMvcResultHandlers.print());
        // then

    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 1개 생성")
    void test2() throws Exception{

        // given

        Users user = userRepository.save(Users.builder()
                .name("ccn")
                .email("ccn@naver.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("ccn@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())

                .andDo(MockMvcResultHandlers.print());


        Users loginedUser = userRepository.findById(user.getId())
                .orElseThrow(RuntimeException::new);

        Assertions.assertEquals(1L,loginedUser.getSessions().size());


    }

}