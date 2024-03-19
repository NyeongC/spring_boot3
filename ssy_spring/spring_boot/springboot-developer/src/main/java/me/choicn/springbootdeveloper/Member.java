package me.choicn.springbootdeveloper;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 생성, 접근제어자 protected
@AllArgsConstructor // 모든 필드를 받는 생성자
@Getter
@Entity
public class Member {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동할당 IDENTITY 전략
    @Column(name = "id", updatable = false) // 엔티티 클래스의 필드와 데이터베이스 테이블의 컬럼을 매핑
    private Long id;

    @Column(name = "name", updatable = false)
    private String name;
}
