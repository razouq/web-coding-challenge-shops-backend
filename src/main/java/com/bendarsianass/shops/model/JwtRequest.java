package com.bendarsianass.shops.model;

public class JwtRequest {

    private String username;
    private String Password;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        Password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
