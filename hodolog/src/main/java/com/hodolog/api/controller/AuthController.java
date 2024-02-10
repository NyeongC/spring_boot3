package com.hodolog.api.controller;

import com.hodolog.api.config.data.UserSession;
import com.hodolog.api.domain.Users;
import com.hodolog.api.repository.UserRepository;
import com.hodolog.api.request.Login;
import com.hodolog.api.response.SessionResponse;
import com.hodolog.api.service.AuthService;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final String KEY = "kItB4fBY6rVkxV69VfrHdz/WpHiEVUVc2/8BPTEB3lA=";

    private final AuthService authService;
    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login){

        // json 아이디/비밀번호
        // DB에서 조회
        String acessToken = authService.signIn(login);

        System.out.println("acessToken = " + acessToken);
        // 토큰을 응답
        return new SessionResponse(acessToken) ;
        

//        SecretKey key = Jwts.SIG.HS256.key().build();
//
//        byte[] encodedKey = key.getEncoded();
//        String strKey = Base64.getEncoder().encodeToString(encodedKey);
//
//        String jws = Jwts.builder().subject("Joe").signWith(key).compact();
//        return new SessionResponse(jws);
    }
}
