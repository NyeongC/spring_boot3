package com.hodolog.api.service;

import com.hodolog.api.domain.Post;
import com.hodolog.api.repository.PostRepository;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }



    @Test
    @DisplayName("글 작성")
    void test1() {

        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(postRepository.count(),1L);

    }
    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다22")
                .content("내용입니다22")
                .build();
        postService.write(postCreate);

        // when
        PostResponse postResponse = postService.get(2L);

        // then
        assertEquals(postResponse.getTitle(),postCreate.getTitle());
    }
}