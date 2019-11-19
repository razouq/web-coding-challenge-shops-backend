package com.bendarsianass.shops.model;

import com.bendarsianass.shops.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

public class UserRegistrationRequest {

    private String username;
    private String password;
    private String passwordConfirm;

    public UserRegistrationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Map<String, String> validate() {
        Map<String, String> errors = new HashMap<>();
        if(username.trim().isEmpty()) {
            errors.put("username", "username should not be empty");
        }


        if(!password.trim().equals(passwordConfirm.trim())) {
            errors.put("password", "password and password confirmation fields should match");
        }

        if(password.trim().length() < 6) {
            errors.put("password", "password should contain at least 6 characters");
        }

        return errors;
    }
}
