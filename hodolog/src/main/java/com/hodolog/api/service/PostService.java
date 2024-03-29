package com.hodolog.api.service;

import com.hodolog.api.domain.Post;
import com.hodolog.api.domain.PostEditor;
import com.hodolog.api.repository.PostRepository;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.request.PostEdit;
import com.hodolog.api.request.PostSearch;
import com.hodolog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){

        // postCreate -> Entity

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        /*
        * 응답 클래스 분리
        *
        * */
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch)
                .stream()
                //.map(post-> new PostResponse(post))
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse edit(Long id , PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        post.edit(postEdit.getTitle(), postEdit.getContent());

        return new PostResponse(post);
    }

    public void detele(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다."));

        postRepository.delete(post);
    }
}
