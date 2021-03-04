package com.o4codes.models;

import com.o4codes.helpers.Util;

public class Token {
    private final Util.tokenType tokenType;
    private String token;


    public Token(String token, Util.tokenType tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Util.tokenType getTokenType() {
        return tokenType;
    }
}
