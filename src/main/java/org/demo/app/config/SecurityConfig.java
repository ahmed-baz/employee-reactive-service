package org.demo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        var user1 = User
                .withUsername("ahmed.baz")
                .password(passwordEncoder().encode("123456"))
                .authorities("read")
                .build();
        var user2 = User
                .withUsername("ahmed.ali")
                .password(passwordEncoder().encode("123456"))
                .authorities("read")
                .build();
        return new MapReactiveUserDetailsService(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
        return http
                .httpBasic()
                .and()
                .authorizeExchange()
                .pathMatchers("/api/v1/employee/**").authenticated()
                .and()
                .build();
    }
}
