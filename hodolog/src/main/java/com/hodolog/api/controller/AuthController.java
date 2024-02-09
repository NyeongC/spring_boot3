package com.hodolog.api.controller;

import com.hodolog.api.config.data.UserSession;
import com.hodolog.api.response.SessionResponse;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;

@RestController
public class AuthController {

    private final String KEY = "kItB4fBY6rVkxV69VfrHdz/WpHiEVUVc2/8BPTEB3lA=";

    @GetMapping("/foo2")
    public String foo2(UserSession userSession){
        return "foo2";
    }

    @GetMapping("/foo3")
    public String foo3(UserSession userSession){
        return userSession.name;
    }

    @PostMapping("/auth/login")
    public SessionResponse login(){
        SecretKey key = Jwts.SIG.HS256.key().build();

        byte[] encodedKey = key.getEncoded();
        String strKey = Base64.getEncoder().encodeToString(encodedKey);

        String jws = Jwts.builder().subject("Joe").signWith(key).compact();
        return new SessionResponse(jws);
    }
}
