package me.choicn.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties { //자바 클래스에 프로퍼티값을 가져와서 사용하는 애너테이션
    private String issuer;
    private String secretKey;
}
