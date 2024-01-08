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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Test
    @DisplayName("글 여러개 조회")
    void test3() {
        // given
        postRepository.saveAll(List.of(
                Post.builder()
                        .title("제목입니다11")
                        .content("내용입니다11")
                        .build(),
                Post.builder()
                        .title("제목입니다22")
                        .content("내용입니다22")
                        .build()
        ));
        Pageable pageable = PageRequest.of(0,5, Sort.by(Sort.Direction.DESC, "id"));
        // when
        List<PostResponse> list = postService.getList(pageable);

        // then
        assertEquals(list.size(), 2L);
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3_1() {
        // given
        List<Post> requestPosts = IntStream.range(0, 30)
                        .mapToObj(i -> {
                            return Post.builder()
                                    .title("호돌맨 제목 " + i)
                                    .content("반포자이 " + i)
                                    .build();
                        })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        Pageable pageable = PageRequest.of(0,5, Sort.by(Sort.Direction.DESC, "id"));

        // when
        List<PostResponse> posts = postService.getList(pageable);

        // then
        assertEquals(5L,posts.size());
        assertEquals("호돌맨 제목 29",posts.get(0).getTitle());
        assertEquals("호돌맨 제목 25",posts.get(4).getTitle());
    }
}