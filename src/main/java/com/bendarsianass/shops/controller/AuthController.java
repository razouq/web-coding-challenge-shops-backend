package com.bendarsianass.shops.controller;

import com.bendarsianass.shops.model.JwtRequest;
import com.bendarsianass.shops.model.JwtResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthController {

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) {
        return new JwtResponse("token");
    }

}
