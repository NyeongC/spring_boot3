package me.choinyeong.springbootdeveloper.controller;


import me.choinyeong.springbootdeveloper.dao.Member;
import me.choinyeong.springbootdeveloper.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/members")
    public List<Member> getAllMembers() {
        List<Member> members = testService.getAMembers();
        return members;
    }

}
