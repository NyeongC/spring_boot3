package com.hodolog.api.controller;

import com.hodolog.api.domain.Post;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.service.PostService;
import jakarta.persistence.Lob;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public Map<String, String> get(@RequestBody @Valid PostCreate postCreate, BindingResult result){
        //log.info("params={}",postCreate.toString());
        postService.write(postCreate);
        return Map.of();
    }

    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable(name = "postId") Long id){
        Post post = postService.get(id);
        return post;
    }
}
