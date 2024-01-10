package com.hodolog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.api.domain.Post;
import com.hodolog.api.repository.PostRepository;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.response.PostResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        //PostCreate request = new PostCreate("제목입니다.","내용입니다.");
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

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

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
                .title("choi123412341234")
                .content("nyeong")
                .build();
        postRepository.save(post);


        // 클라이언트 요구사항 추가
        // json 응답에서 title의 길이 최대 10개까지
        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}",post.getId())
                                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("choi123412341234"))
                .andExpect(jsonPath("$.content").value("nyeong"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        // given
        Post post = postRepository.save(Post.builder()
                .title("choi")
                .content("nyeong")
                .build());

        Post post2 = postRepository.save(Post.builder()
                .title("choi2")
                .content("yeong")
                .build());

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(post.getId()))
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post.getContent()))
                .andExpect(jsonPath("$[1].id").value(post2.getId()))
                .andExpect(jsonPath("$[1].title").value("choi2"))
                .andExpect(jsonPath("$[1].content").value("yeong"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("글 여러 조회")
    void test5_1() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("호돌맨 제목 " + i)
                            .content("반포자이 " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // expected
        //mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&sort=id,desc&size=5")
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&sort=id,desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",Matchers.is(5)))
                .andExpect(jsonPath("$[0].id").value(30))
                .andExpect(jsonPath("$[0].title").value("호돌맨 제목 30"))
                .andExpect(jsonPath("$[0].content").value("반포자이 30"))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    @DisplayName("글 여러 조회")
    void test5_2() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("foo " + i)
                            .content("bar " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // expected
        //mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&sort=id,desc&size=5")
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("foo 19"))
                .andExpect(jsonPath("$[0].content").value("bar 19"))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test6() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("foo " + i)
                            .content("bar " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // expected
        //mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&sort=id,desc&size=5")
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("foo 19"))
                .andExpect(jsonPath("$[0].content").value("bar 19"))
                .andDo(MockMvcResultHandlers.print());


    }
}