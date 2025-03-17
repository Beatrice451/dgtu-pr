package org.beatrice.hui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final static String SIGNIN_ENTRY_POINT = "/api/auth/signin";
    private final static String SIGNUP_ENTRY_POINT = "/api/auth/signup";


    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(SIGNIN_ENTRY_POINT).permitAll()
                .requestMatchers(SIGNUP_ENTRY_POINT).permitAll()
                .anyRequest().authenticated()
        );

        return http.build();
    }
}
