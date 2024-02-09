package com.hodolog.api.response;

import lombok.Getter;

@Getter
public class SessionResponse {

    private final String accesToken;

    public SessionResponse(String accesToken){this.accesToken = accesToken;}
}
