package com.example.databaseteam.repository;

import com.example.databaseteam.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls,Integer> {
    public UserDtls findByEmail(String email);
}
