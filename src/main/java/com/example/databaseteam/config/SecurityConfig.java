package com.example.databaseteam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth->auth //chuỗi bộ lọc
//                        .dispatcherTypeMatchers("../static/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/","/login","/register","product").permitAll()
                        .anyRequest()// mọi yêu cầu
                        .authenticated())
//                .httpBasic(Customizer.withDefaults())// phương thức xác nhận đơn giản
                .formLogin(login->
                        login.loginPage("/login")
                                .loginProcessingUrl("/process-login")
                                .defaultSuccessUrl("/index", true)
                                .permitAll()// form xác nhận
                )
//                .logout(logout->
//                        logout.invalidateHttpSession(true)
//                                .clearAuthentication(true)
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .logoutSuccessUrl("/login?logout")
//                                .permitAll())
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
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebSecurityCustomizer customizeWebSecurity(){
        return (web) -> web.ignoring().requestMatchers("/img/**","/css/**","/js/**");
    }
}
