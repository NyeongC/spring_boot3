package me.choinyeong.springbootdeveloper.repository;

import me.choinyeong.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    /*
    * UserDetails 인터페이스에서 getUsername() 메서드를 오버라이딩 할때 email을 반환하도록 설정했기에 
    * 스프링 시큐리티는 기본적으로 username을 기준으로 인증하기에 필수
    * */
}
