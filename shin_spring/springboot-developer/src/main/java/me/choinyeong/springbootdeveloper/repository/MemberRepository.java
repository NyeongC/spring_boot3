package me.choinyeong.springbootdeveloper.repository;

import me.choinyeong.springbootdeveloper.dao.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
