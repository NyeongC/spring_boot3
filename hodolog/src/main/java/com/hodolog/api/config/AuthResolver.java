package com.hodolog.api.config;

import com.hodolog.api.config.data.UserSession;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.catalina.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String accessToken = webRequest.getHeader("Authorization");
        if(accessToken ==null || accessToken.equals("")){
            throw new Exception();
        }
        UserSession userSession = new UserSession();
        userSession.name = accessToken;

        return userSession;
    }
}
