package com.example.databaseteam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    private static final String[] PUBLIC_URLS = {
            "/",
            "/login",
            "/register",
            "/product",
            "/index",
            "/saveUser",
            "/api/**",
            "/cart/**",
            "/add-to-cart",
            "/checkout",
            "/admin/**",
            "/deleteUser",
            "/about",
            "/gallery",
            "/blog",
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth->auth //chuỗi bộ lọc
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers("/cart/").hasAuthority("USER")
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest()// mọi yêu cầu
                        .authenticated())
                .formLogin(login->
                        login.loginPage("/login")
                                .loginProcessingUrl("/process-login")
                                .defaultSuccessUrl("/index", true)
                                .permitAll()// form xác nhận
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL để đăng xuất
                        .logoutSuccessUrl("/index") // URL chuyển hướng sau khi đăng xuất thành công
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);// bảo mật
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new OurUserInfoDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());//tai thong tin nguoi dung
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());//ma hoa mat khau
        return daoAuthenticationProvider;//xac thuc nguoi dung
    }

    @Bean
    WebSecurityCustomizer customizeWebSecurity(){
        return (web) -> web.ignoring().requestMatchers("/img/**","/css/**","/js/**");
    }
}
