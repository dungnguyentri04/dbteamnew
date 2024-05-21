package com.example.databaseteam.config;

import com.example.databaseteam.model.UserDtls;
import com.example.databaseteam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class OurUserInfoDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<UserDtls> user = userRepository.findByEmail(username);
//        return user.map(OurUserInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("User Does NOt Exist"))
        UserDtls user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        } else {
            return new OurUserInfoDetails(user);
        }
    }
}
