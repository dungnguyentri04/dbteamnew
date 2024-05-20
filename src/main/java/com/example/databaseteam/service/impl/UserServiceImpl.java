package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.UserDtls;
import com.example.databaseteam.repository.UserRepository;
import com.example.databaseteam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDtls saveUser(UserDtls user) {
        String password = passwordEncoder.encode(user.getPassword());//ma hoa
        user.setPassword(password);
        user.setRoles("ROLE_USER");
        return userRepository.save(user);
    }

}
