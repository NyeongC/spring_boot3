package com.hodolog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.api.domain.Post;
import com.hodolog.api.repository.PostRepository;
import com.hodolog.api.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest 간단한거 할때 서비스 없이
@AutoConfigureMockMvc // 이거 하나로 mockmvc 쓸수있음
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("/posts 요청시 Hello Wolrd 를 출력한다.")
    void get_test() throws Exception {
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        //.content("{\"title\": \"제목입니다.\",\"content\": \"내용입니다.\"}")
                        .content("{\"title\": \"\",\"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void test3() throws Exception {

        // given
        PostCreate request = new PostCreate("제목입니다.","내용입니다.");
        ObjectMapper objectMapper = new ObjectMapper();
        String request_json = objectMapper.writeValueAsString(request);

        System.out.println("request_json = " + request_json);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json)
                        //.content("{\"title\": \"제목입니다.\",\"content\": \"내용입니다.\"}") 보기에도 불편하고 짜침
                        //.content("{\"title\": \"\",\"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // then
        assertEquals(1L,postRepository.count()); // 디비에 하나 넣어서 1L

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.2",post.getTitle());
        assertEquals("내용입니다.",post.getContent());
    }
}