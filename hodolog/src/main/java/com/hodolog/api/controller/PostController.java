package com.hodolog.api.controller;

import com.hodolog.api.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PostController {


    @PostMapping("/posts")
    public String get(@RequestBody @Valid PostCreate postCreate){
        log.info("params={}",postCreate.toString());
        return "Hello World!";
    }
}
