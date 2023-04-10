package com.example.dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;
    private final String type = "Bearer";
    private final Long expiration;

    public JwtResponse(String token, Long expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return this.token;
    }

    public String getType() {
        return this.type;
    }

    public Long getExpiration() {
        return this.expiration;
    }
}
