package me.choinyeong.springbootdeveloper.service;

import me.choinyeong.springbootdeveloper.dao.Member;
import me.choinyeong.springbootdeveloper.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    MemberRepository memberRepository;

    public List<Member> getAMembers() {
        return memberRepository.findAll();
    }

}
