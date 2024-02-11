package com.hodolog.api.controller;

import com.hodolog.api.config.AppConfig;
import com.hodolog.api.config.data.UserSession;
import com.hodolog.api.request.Login;
import com.hodolog.api.request.Signup;
import com.hodolog.api.response.SessionResponse;
import com.hodolog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AppConfig appConfig;
    private final AuthService authService;


    @GetMapping("/test")
    public String test(){
        return "Hello";
    }

    @GetMapping("/foo")
    public Long foo(UserSession session){
        return session.id;
    }
    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login){

        Long userId = authService.signIn(login);

        // 인증키 생성, 매번 됨, 이 부분은 한번 빼서 key ㅂ다아놓으면됨
        SecretKey key = Jwts.SIG.HS256.key().build();
        byte[] encodedKey = key.getEncoded();
        String strKey = Base64.getEncoder().encodeToString(encodedKey);

        SecretKey keys = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        // key와 사용자 id 정보로 jwt 생성
        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .signWith(keys)
                .setIssuedAt(new Date())
                .compact();

        return new SessionResponse(jws);
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup){
        authService.signup(signup);
    }
}
