package com.example.databaseteam.config;

import com.example.databaseteam.model.UserDtls;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OurUserInfoDetails implements UserDetails {
    private UserDtls user;
    private List<GrantedAuthority> roles;
    public OurUserInfoDetails(UserDtls user){
        this.user=user;
        this.roles= Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //tra ve quuyen nguoi dung
        return this.roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {//tra ve tai khoan co het han hay khong
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//tra ve tai khoan co bi khoa hay khong
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {//Kiểm tra xem thông tin xác thực (mật khẩu) của người dùng có hết hạn hay không.
        return true;
    }

    @Override
    public boolean isEnabled() {//Kiểm tra xem tài khoản của người dùng có được kích hoạt hay không.
        return true;
    }
}
