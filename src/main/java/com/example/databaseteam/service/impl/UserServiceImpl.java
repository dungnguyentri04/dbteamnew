package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.BookTable;
import com.example.databaseteam.model.Product;
import com.example.databaseteam.model.UserDtls;
import com.example.databaseteam.repository.UserRepository;
import com.example.databaseteam.service.BookTableService;
import com.example.databaseteam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BookTableService bookTableService;

    @Override
    public UserDtls saveUser(UserDtls user) {
        String password = passwordEncoder.encode(user.getPassword());//ma hoa
        user.setPassword(password);
        user.setRoles("USER");
        return userRepository.save(user);
    }

    @Override
    public List<UserDtls> getAllUserDtls() {
        return userRepository.findAll();
    }

    @Override
    public UserDtls getUserDtlsById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDtls updateUser(UserDtls user, MultipartFile image) {
        UserDtls changeUser = getUserDtlsById(user.getId());
        String imageName = image.isEmpty() ? changeUser.getProfileImage() : image.getOriginalFilename();
        changeUser.setName(user.getName());
        changeUser.setEmail(user.getEmail());
        changeUser.setAddress(user.getAddress());
        changeUser.setMobileNumber(user.getMobileNumber());
        changeUser.setProfileImage(imageName);
        changeUser.setRoles(user.getRoles());
        UserDtls updateUser = userRepository.save(changeUser);
        if (!ObjectUtils.isEmpty(updateUser)){
            if (!image.isEmpty()){
                try {
                    File saveFile = new ClassPathResource("static/img").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator + image.getOriginalFilename());
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return user;
        }
        return null;
    }
    //phan comment de khach hang tu edit

    @Override
    public Boolean deleteUser(Integer id) {
        UserDtls user = userRepository.findById(id).orElse(null);
        if (!ObjectUtils.isEmpty(user)){
            bookTableService.deleteBookTableByUserId(id);
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDtls getUserDtlsByEmail(String email) {
        UserDtls user = userRepository.findByEmail(email);
        return user;
    }


}
