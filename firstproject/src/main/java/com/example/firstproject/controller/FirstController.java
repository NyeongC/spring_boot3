package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class FirstController {

    // RestCotroller 와 Controller 뷰템플릿에 차이가 있는듯
    
    @GetMapping("/hi")
    public String hello(){
        return "greetings";
    }
}
