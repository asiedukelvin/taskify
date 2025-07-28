package com.taskify.taskify.config;

import com.taskify.taskify.security.filter.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                       /* .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("public/**").permitAll()
                        .requestMatchers("/api/uploads/**").permitAll()
                        .requestMatchers("/api/profilepic/**").permitAll()
                        .requestMatchers("/api/tasks/**").authenticated()
                        .requestMatchers("/api/tasklists/**").authenticated()
                        .requestMatchers("/api/users/**").authenticated()*/
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JWTAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
