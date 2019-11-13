package com.bendarsianass.shops.service;

import com.bendarsianass.shops.entity.UserEntity;
import com.bendarsianass.shops.model.UserRegistrationRequest;
import com.bendarsianass.shops.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    @Autowired
    private UserService userService;

    public UserEntity save(UserRegistrationRequest userRegistrationRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegistrationRequest.getUsername());
        userEntity.setPassword(userRegistrationRequest.getPassword());
        return userService.save(userEntity);
    }
}
