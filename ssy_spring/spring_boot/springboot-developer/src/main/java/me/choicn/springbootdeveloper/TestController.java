package me.choicn.springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/members")
    public List<Member> members(){
        return memberService.memberList();
    }
}
