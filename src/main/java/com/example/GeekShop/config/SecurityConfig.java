package com.example.GeekShop.config;

import com.example.GeekShop.model.user.Role;
import com.example.GeekShop.service.user.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserAuthService userAuthService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, UserAuthService userAuthService) {
        this.passwordEncoder = passwordEncoder;
        this.userAuthService = userAuthService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/",
                                "/registration",
                                "/product",
                                "/product/find",
                                "/theme",
                                "/season",
                                "/category",
                                "/style/**",
                                "/img/*", "/img/**",
                                "/script/**",
                                "/product/images/**",
                                "/category/images/**",
                                "/theme/images/**",
                                "/season/images/**"
                        ).permitAll()
                        .requestMatchers(
                                "/admin/*/**",
                                 "/product/new", "/product/*/edit",
                                 "/theme/new", "/theme/*/edit",
                                 "/season/new", "/season/*/edit",
                                 "/category/new", "/category/*/edit"
                        ).hasRole(Role.ADMIN.name())
                        .requestMatchers(
                                "/product/**",
                                "/message/**",
                                "/theme/**",
                                "/season/**",
                                "/category/**",
                                "/profile/**"
                        ).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userAuthService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}
