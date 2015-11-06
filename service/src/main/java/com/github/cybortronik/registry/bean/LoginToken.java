package com.github.cybortronik.registry.bean;

/**
 * Created by stanislav on 11/6/15.
 */
public class LoginToken {
    private String jwt;

    public LoginToken() {
    }

    public LoginToken(String token) {
        jwt = token;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
