package com.hodolog.api.controller;

import com.hodolog.api.domain.Post;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.request.PostEdit;
import com.hodolog.api.request.PostSearch;
import com.hodolog.api.response.PostResponse;
import com.hodolog.api.service.PostService;
import jakarta.persistence.Lob;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request, @RequestHeader String authorization){
        //log.info("params={}",postCreate.toString());
        if(authorization.equals("ccn")){
            postService.write(request);
        }
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id){
        PostResponse response = postService.get(id);
        return response;
    }

    @GetMapping("/posts")
    List<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }

    @PatchMapping("posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request){
        return postService.edit(postId, request);
    }

    @DeleteMapping("posts/{postId}")
    public void edit(@PathVariable Long postId){
        postService.detele(postId);
    }
}
