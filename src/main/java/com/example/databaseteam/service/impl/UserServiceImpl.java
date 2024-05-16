package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.UserDtls;
import com.example.databaseteam.repository.UserRepository;
import com.example.databaseteam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDtls saveUser(UserDtls user) {
        return userRepository.save(user);
    }


}
