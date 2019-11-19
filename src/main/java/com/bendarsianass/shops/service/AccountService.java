package com.bendarsianass.shops.service;

import com.bendarsianass.shops.entity.UserEntity;
import com.bendarsianass.shops.exception.AccountServiceException;
import com.bendarsianass.shops.model.UserRegistrationRequest;
import com.bendarsianass.shops.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public UserEntity save(UserRegistrationRequest userRegistrationRequest) {
        Map<String, String> errors = userRegistrationRequest.validate();
        if(userRepository.findByUsername(userRegistrationRequest.getUsername().trim()) != null) {
            errors.put("username", "this username is already taken");
        }
        if(!errors.isEmpty()) throw new AccountServiceException(errors);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegistrationRequest.getUsername());
        userEntity.setPassword(userRegistrationRequest.getPassword());
        return userService.save(userEntity);
    }
}
