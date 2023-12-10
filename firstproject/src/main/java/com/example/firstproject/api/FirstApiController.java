package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstApiController {
    
    /*
    RestController 와 일반 Controller 의 차이
    Rest는 JSON 이나 텍스트 기반 데이터를 반환하는 반면
    일반은 뷰 페이지를 반환
    
    */

    @GetMapping("/api/hello")
    public String hello(){
        return "Hello World!";
    }
}
