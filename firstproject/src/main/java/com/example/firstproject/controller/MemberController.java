package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/members")
    public String members(){
        return "members/new";
    }

    @PostMapping("/join")
    public String join(MemberForm form){
        System.out.println(form.toString());

        // 1. 엔티티 변환
        Member member = form.toEntity();
        System.out.println(member.toString());

        // 2. 레포지토리로 엔티티 DB 저장
        Member saved = memberRepository.save(member);
        System.out.println(saved.toString());

        return "";
    }
}
