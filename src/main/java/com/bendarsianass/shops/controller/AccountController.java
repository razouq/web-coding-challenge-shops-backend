package com.bendarsianass.shops.controller;

import com.bendarsianass.shops.entity.UserEntity;
import com.bendarsianass.shops.model.UserRegistrationRequest;
import com.bendarsianass.shops.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        return accountService.save(userRegistrationRequest);
    }
}
