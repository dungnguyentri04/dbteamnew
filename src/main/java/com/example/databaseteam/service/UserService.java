package com.example.databaseteam.service;

import com.example.databaseteam.model.UserDtls;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    public UserDtls saveUser(UserDtls user);
    public List<UserDtls> getAllUserDtls();
    public UserDtls getUserDtlsById(Integer id);
    public UserDtls updateUser(UserDtls user, MultipartFile file);
    public Boolean deleteUser(Integer id);
    public UserDtls getUserDtlsByEmail(String email);
}
