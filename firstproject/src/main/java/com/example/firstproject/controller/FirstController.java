package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class FirstController {

    // RestCotroller 와 Controller 뷰템플릿에 차이가 있는듯
    
    @GetMapping("/hi")
    public String niceToMertYou(Model model){
        model.addAttribute("username","Choi Cheol Nyeong");
        return "greetings";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname","choi_nyeong");
        return "goodbye";
    }
}
