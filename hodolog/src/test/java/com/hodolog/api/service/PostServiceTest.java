package com.hodolog.api.service;

import com.hodolog.api.domain.Post;
import com.hodolog.api.repository.PostRepository;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.request.PostEdit;
import com.hodolog.api.request.PostSearch;
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
        PostSearch postSearch = PostSearch
                .builder()
                .page(1)
                .size(10).build();


        // when
        List<PostResponse> posts = postService.getList(postSearch);

    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3_1() {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                        .mapToObj(i -> {
                            return Post.builder()
                                    .title("호돌맨 제목 " + i)
                                    .content("반포자이 " + i)
                                    .build();
                        })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch
                .builder()
                .page(1)
                //.size(10)
                .build();


        // when
        List<PostResponse> posts = postService.getList(postSearch);


        // then
        assertEquals(10L,posts.size());
        assertEquals("호돌맨 제목 29",posts.get(0).getTitle());
        assertEquals("호돌맨 제목 25",posts.get(4).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        // given

        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("호돌걸")
                .build();

        // when
        postService.edit(post.getId(),postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(()-> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        assertEquals("호돌걸",changedPost.getTitle());
        assertEquals("반포자이",changedPost.getContent());

    }
}