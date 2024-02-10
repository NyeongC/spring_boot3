package com.hodolog.api.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@Data
@ConfigurationProperties(prefix = "hodolman")
public class AppConfig {

    private byte[] jwtKey;

    public byte[] getJwtKey() {
        return jwtKey;
    }

    public AppConfig(String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
    }
}
