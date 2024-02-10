package com.hodolog.api.config;

import com.hodolog.api.config.data.UserSession;
import com.hodolog.api.domain.Session;
import com.hodolog.api.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    private final String KEY = "kItB4fBY6rVkxV69VfrHdz/WpHiEVUVc2/8BPTEB3lA=";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String jws = webRequest.getHeader("Authorization");
        if(jws ==null || jws.equals("")){
            throw new Exception();
        }

        byte[] decodedKey = Base64.decodeBase64(KEY);

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseSignedClaims(jws);

            log.info(">>>>>>>>>>>>>{}", claimsJws);

            String userId = claimsJws.getBody().getSubject();

            return new UserSession(Long.parseLong(userId));
            //Jwts.parser().verifyWith(decodedKey).build().parseSignedClaims(jws);

        } catch (JwtException e) {
            // JWT 복호화 실패, 인증 안됨
            throw new Exception();
        }
    }
}
