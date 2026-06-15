package com.ccn.springai.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.security.autoconfigure.web.servlet.PathRequest.toH2Console;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 로그인, 회원가입, 회원 저장 요청은 인증 없이 접근 허용
                        .requestMatchers(
                                "/login",
                                "/signup",
                                "/user"
                        ).permitAll()
                        // 위에서 허용한 URL 외의 모든 요청은 로그인 필요
                        .anyRequest().authenticated())
                // 폼 로그인 설정
                .formLogin(formLogin -> formLogin
                        // 사용자가 직접 만든 로그인 페이지 경로
                        .loginPage("/login")

                        // 로그인 성공 시 이동할 기본 페이지
                        .defaultSuccessUrl("/articles")
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        // 로그아웃 성공 후 이동할 페이지
                        .logoutSuccessUrl("/login")
                        // 로그아웃 시 기존 세션 무효화
                        .invalidateHttpSession(true)
                )
                // CSRF 보호 기능 비활성화
                // 학습용/테스트용에서는 자주 끄지만,
                // 세션 기반 로그인에서는 실무에서 보통 켜두는 편
                .csrf(AbstractHttpConfigurer::disable)

                // 위 설정을 기반으로 SecurityFilterChain 객체 생성
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
